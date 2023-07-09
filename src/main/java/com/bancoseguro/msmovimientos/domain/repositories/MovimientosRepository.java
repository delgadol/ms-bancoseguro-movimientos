package com.bancoseguro.msmovimientos.domain.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bancoseguro.msmovimientos.domain.model.Transaccion;

public interface MovimientosRepository extends ReactiveMongoRepository<Transaccion, String>{

}
