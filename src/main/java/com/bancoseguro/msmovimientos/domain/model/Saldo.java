package com.bancoseguro.msmovimientos.domain.model;

import org.springframework.data.annotation.Id;

import com.bancoseguro.msmovimientos.utils.GrupoProducto;
import com.bancoseguro.msmovimientos.utils.TipoProducto;

import lombok.Data;

@Data
public class Saldo {
	
	@Id
	private String id;
	
	private String codControl;
	
	private GrupoProducto grupoProdcuto;
	
	private TipoProducto tipoProducto;
	
	private String codigoProducto;
	
	private Double saldoActual;

}
