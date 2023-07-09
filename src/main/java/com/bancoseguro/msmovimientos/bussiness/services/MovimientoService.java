package com.bancoseguro.msmovimientos.bussiness.services;


import com.bancoseguro.msmovimientos.domain.dto.req.InfoTransacionReq;
import com.bancoseguro.msmovimientos.domain.dto.res.SaldoRes;
import com.bancoseguro.msmovimientos.domain.dto.res.TransaccionRes;

import reactor.core.publisher.Mono;

public interface MovimientoService {
	
	public Mono<SaldoRes> getProductBalance(String idProdcuto);
	
	
	public Mono<TransaccionRes> getAllBalanceByClientId(String idCliente);
	
	
	public Mono<TransaccionRes> postTransaccion(InfoTransacionReq transaccion);

}
