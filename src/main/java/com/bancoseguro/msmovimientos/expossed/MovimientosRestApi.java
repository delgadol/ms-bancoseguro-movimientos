package com.bancoseguro.msmovimientos.expossed;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bancoseguro.msmovimientos.domain.dto.req.InfoTransacionReq;
import com.bancoseguro.msmovimientos.domain.dto.res.SaldoRes;
import com.bancoseguro.msmovimientos.domain.dto.res.TransaccionRes;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/transacciones")
public class MovimientosRestApi {
	
	@GetMapping("/{idProducto}/saldo")
	public Mono<SaldoRes> getProductBalance(@PathVariable(name="idProducto") String idProdcuto){
		return null;
	}
	
	
	@PostMapping("/clientes/{idCliente}/resumenes")
	public Mono<TransaccionRes> getAllBalanceByClientId(@PathVariable(name="idCliente") String idCliente){
		return null;
	}
	
	
	@PostMapping("/{idProducto}")
	public Mono<TransaccionRes> postTransaccion(@RequestBody InfoTransacionReq transaccion, @PathVariable(name="idProducto") String idPRoducto){
		return null;
	}
	
	
	
}