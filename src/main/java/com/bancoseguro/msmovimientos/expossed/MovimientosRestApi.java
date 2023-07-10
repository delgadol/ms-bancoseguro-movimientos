package com.bancoseguro.msmovimientos.expossed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@GetMapping("/{idProducto}/saldo")
	public Mono<SaldoRes> getProductBalance(@PathVariable(name="idProducto") String idProdcuto){
		return servTransaccion.getProductBalance(idProdcuto);
	}
	
	
	@GetMapping("/clientes/{idCliente}/resumenes")
	public Flux<SaldoRes> getAllBalanceByClientId(@PathVariable(name="idCliente") String idCliente){
		return servTransaccion.getAllBalanceByClientId(idCliente);
	}
	
	
	@PostMapping("")
	public Mono<TransaccionRes> postTransaccion(@Valid @RequestBody InfoTransacionReq transaccion){
		return servTransaccion.postTransaccion(transaccion);
	}
	
	@GetMapping("/{idProducto}")
	public Flux<TransaccionRes> getAllTransaccionByProductID(@PathVariable(name="idProducto") String idProducto){
		return servTransaccion.getAllTransaccionByProductID(idProducto);
	}
	
	
	
}