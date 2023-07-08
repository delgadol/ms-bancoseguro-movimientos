package com.bancoseguro.msmovimientos.domain.model;

import lombok.Data;

@Data
public abstract class Producto {
	
	private String grupoProducto;
	
	private String codigoProducto;
	
	private Integer indEleiminado;
	
	private Integer estadoProducto;
	

}
