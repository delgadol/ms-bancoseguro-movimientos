package com.bancoseguro.msmovimientos.expossed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.bancoseguro.msmovimientos.bussiness.services.MovimientoService;
import com.bancoseguro.msmovimientos.domain.dto.req.InfoTransacionReq;
import com.bancoseguro.msmovimientos.domain.dto.res.SaldoRes;
import com.bancoseguro.msmovimientos.domain.dto.res.TransaccionRes;

import jakarta.validation.Valid;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/transacciones")
public class MovimientosRestApi {
	
	@Autowired
	private MovimientoService servTransaccion ;
	
	/**
	 * Obtiene el saldo de un producto específico.
	 *
	 * @param idProducto el identificador del producto
	 * @return un Mono que emite el objeto SaldoRes del producto
	 */
	
	@GetMapping("/{idProducto}/saldo")
	public Mono<SaldoRes> getProductBalance(@PathVariable(name="idProducto") String idProdcuto){
		return servTransaccion.getProductBalance(idProdcuto)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entidad no procesable")));
	}
	
	
	/**
	 * Obtiene todos los saldos relacionados a un cliente dado.
	 *
	 * @param idCliente el identificador del cliente
	 * @return un Flux que emite objetos SaldoRes
	 */
	@GetMapping("/clientes/{idCliente}/resumenes")
	public Flux<SaldoRes> getAllBalanceByClientId(@PathVariable(name="idCliente") String idCliente){
		return servTransaccion.getAllBalanceByClientId(idCliente)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entidad no procesable")));
	}
	
	/**
	 * Crea una nueva transacción con la información proporcionada.
	 *
	 * @param transaccion la información de la transacción a crear
	 * @return un Mono que emite el objeto TransaccionRes resultante
	 */
	@PostMapping("")
	public Mono<TransaccionRes> postTransaccion(@Valid @RequestBody InfoTransacionReq transaccion){
		return servTransaccion.postTransaccion(transaccion)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entidad no procesable")));
	}
	
	/**
	 * Obtiene todas las transacciones relacionadas a un producto dado.
	 *
	 * @param idProducto el identificador del producto
	 * @return un Flux que emite objetos TransaccionRes
	 */	
	@GetMapping("/{idProducto}")
	public Flux<TransaccionRes> getAllTransaccionByProductID(@PathVariable(name="idProducto") String idProducto){
		return servTransaccion.getAllTransaccionByProductID(idProducto)
				.switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entidad no procesable")));
	}
	
	
	
}