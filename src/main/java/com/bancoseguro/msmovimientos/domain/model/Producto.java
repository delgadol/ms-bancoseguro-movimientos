package com.bancoseguro.msmovimientos.domain.model;

import com.bancoseguro.msmovimientos.utils.GrupoProducto;
import com.bancoseguro.msmovimientos.utils.TipoProducto;

import lombok.Data;

@Data
public class Producto {
	
	private String id;
	
	private GrupoProducto grupoProducto;
	
	private TipoProducto tipoProducto;
	
	private String codigoProducto ;
	
	private String estado ;

}
