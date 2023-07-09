package com.bancoseguro.msmovimientos.domain.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bancoseguro.msmovimientos.domain.model.Saldo;

public interface SaldoRespository extends ReactiveMongoRepository<Saldo,String> {

}
