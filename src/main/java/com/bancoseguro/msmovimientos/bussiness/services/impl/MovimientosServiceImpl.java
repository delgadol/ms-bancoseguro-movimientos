package com.bancoseguro.msmovimientos.bussiness.services.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.bancoseguro.msmovimientos.bussiness.services.MovimientoService;
import com.bancoseguro.msmovimientos.domain.dto.req.InfoTransacionReq;
import com.bancoseguro.msmovimientos.domain.dto.res.SaldoRes;
import com.bancoseguro.msmovimientos.domain.dto.res.TransaccionRes;
import com.bancoseguro.msmovimientos.domain.model.Producto;
import com.bancoseguro.msmovimientos.domain.model.Saldo;
import com.bancoseguro.msmovimientos.domain.model.Transaccion;
import com.bancoseguro.msmovimientos.domain.repositories.MovimientosRepository;
import com.bancoseguro.msmovimientos.domain.repositories.SaldoRespository;
import com.bancoseguro.msmovimientos.utils.BankFnUtils;
import com.bancoseguro.msmovimientos.utils.ModelMapperUtils;
import com.bancoseguro.msmovimientos.utils.ResultadoTransaccion;
import com.bancoseguro.msmovimientos.utils.TipoOperacion;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovimientosServiceImpl implements MovimientoService {

	@Autowired
	private MovimientosRepository servMovRepo;
	
	@Autowired
	private SaldoRespository servSaldoRep;
	
	private final WebClient webClient;
	
	private final ReactiveMongoOperations mongoOperations;

    public MovimientosServiceImpl(WebClient.Builder webClientBuilder, ReactiveMongoOperations mongoOperations) {
        this.webClient = webClientBuilder.build();
        this.mongoOperations = mongoOperations;
    }
    
    
    /**
     * Obtiene información de un producto desde una API externa.
     *
     * @param idProducto el identificador del producto
     * @return un Mono que emite el objeto Producto obtenido desde la API
     */
    
    private Mono<Producto> getProdcuctosApi(String idProducto){
		return webClient.get()
                .uri(String.format("http://localhost:8086/v1/productos/%s", idProducto))
                .retrieve()
                .bodyToMono(Producto.class)
                .doOnError(WebClientResponseException.class, e -> {
                    HttpStatus statusCode = (HttpStatus) e.getStatusCode();
                    System.out.println("Error: " + statusCode);
                });
	}
    
     
    /**
     * Obtiene el saldo de un producto específico.
     *
     * @param idProducto el identificador del producto
     * @return un Mono que emite el objeto SaldoRes del producto
     */
    
    @Override
	public Mono<SaldoRes> getProductBalance(String idProdcuto) {
		return getProdcuctosApi(idProdcuto)
				.flatMap(productoApi -> {
					return servSaldoRep.findFirstByCodigoProducto(idProdcuto)
							.flatMap(entidad -> {
								return Mono.just(ModelMapperUtils.map(entidad,SaldoRes.class));
							});							
				});
	}

    /**
     * Obtiene todos los saldos relacionados a un cliente dado.
     *
     * @param idCliente el identificador del cliente
     * @return un Flux que emite objetos SaldoRes
     */
	@Override
	public Flux<SaldoRes> getAllBalanceByClientId(String idCliente) {
		return servSaldoRep.findAllByIdPersona(idCliente)
				.flatMap(saldoDisp ->{
					return Mono.just(ModelMapperUtils.map(saldoDisp, SaldoRes.class)).flux();
				});
	}
	
	/**
	 * Crea una nueva transacción con la información proporcionada.
	 *
	 * @param transaccion la información de la transacción a crear
	 * @return un Mono que emite el objeto TransaccionRes resultante
	 */

	@Override
	public Mono<TransaccionRes> postTransaccion(InfoTransacionReq transaccion) {
		Mono<Transaccion> transaccionNueva = getProdcuctosApi(transaccion.getIdProducto())
				.flatMap(productoApi -> {
					return mongoOperations.count(servMovRepo.getDatosDeEsteMesQuery(),Transaccion.class)
							.filter(t ->  t < productoApi.getMaxOperacionesMes())
							.flatMap(val ->{
								return servSaldoRep.findFirstByCodigoProducto(productoApi.getId())
										.flatMap(saldoCliente -> {
											/* Nuevo Saldo */
											Saldo nuevoSaldo = new Saldo();
											nuevoSaldo = ModelMapperUtils.map(saldoCliente, Saldo.class);
											/* Nueva Transaccion */
											Transaccion nuevaTransaccion = new Transaccion();
											nuevaTransaccion.setCodControl(BankFnUtils.uniqueProductCode());
											nuevaTransaccion.setCodigoOperacion(transaccion.getTipoOperacion());
											nuevaTransaccion.setCodigoProducto(transaccion.getIdProducto());
											nuevaTransaccion.setFechaTransaccion(java.sql.Timestamp.valueOf(LocalDateTime.now()));
											nuevaTransaccion.setGrupoProducto(productoApi.getGrupoProducto());
											nuevaTransaccion.setMontoTransaccion(transaccion.getMontoOperacion());
											nuevaTransaccion.setTipoProducto(productoApi.getTipoProducto());
											nuevaTransaccion.setObservacionTransaccion(transaccion.getObervacionTransaccion());
											nuevaTransaccion.setResultadoTransaccion(ResultadoTransaccion.RECHAZADA);
											/* operatividad de la Transaccion */
											if(transaccion.getTipoOperacion() == TipoOperacion.ABONO) {
												nuevaTransaccion.setResultadoTransaccion(ResultadoTransaccion.APROBADA);
												nuevoSaldo.setSaldoActual(nuevoSaldo.getSaldoActual() + transaccion.getMontoOperacion());
											} else if (transaccion.getTipoOperacion() == TipoOperacion.CARGO) {
												if (nuevoSaldo.getSaldoActual()>=transaccion.getMontoOperacion()) {
													nuevaTransaccion.setResultadoTransaccion(ResultadoTransaccion.APROBADA);
													nuevoSaldo.setSaldoActual(nuevoSaldo.getSaldoActual() - transaccion.getMontoOperacion());
												} 
											}else {
												nuevaTransaccion.setResultadoTransaccion(ResultadoTransaccion.APROBADA);
											}
											return servSaldoRep.save(nuevoSaldo)
													.flatMap(saldoW -> {
														return servMovRepo.save(nuevaTransaccion);
													});
										});
							});				
				});
		return ModelMapperUtils.mapToMono(transaccionNueva, TransaccionRes.class);
	}
	

	/**
	 * Obtiene todas las transacciones relacionadas a un producto dado.
	 *
	 * @param idProducto el identificador del producto
	 * @return un Flux que emite objetos TransaccionRes
	 */
	
	@Override
	public Flux<TransaccionRes> getAllTransaccionByProductID(String idProducto) {
		return getProdcuctosApi(idProducto)
				.flux()
				.flatMap(productoApi ->{
					return mongoOperations.find(servMovRepo.getDatosPorCodigoYFechaActualY3MesesAtrasQuery(idProducto),Transaccion.class)
							.flatMap(entidad -> {
								return Mono.just(ModelMapperUtils.map(entidad, TransaccionRes.class)).flux();
							});
				});
	}

}
