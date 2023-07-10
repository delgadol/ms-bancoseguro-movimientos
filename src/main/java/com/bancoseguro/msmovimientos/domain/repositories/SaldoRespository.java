package com.bancoseguro.msmovimientos.domain.repositories;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bancoseguro.msmovimientos.domain.model.Saldo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SaldoRespository extends ReactiveMongoRepository<Saldo,String> {
	
	Mono<Saldo> findFirstByCodigoProducto(String codigoProducto);
	
	
	Flux<Saldo> findAllByIdPersona(String idPersona);

}
