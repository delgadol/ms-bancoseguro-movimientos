package com.bancoseguro.msmovimientos.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bancoseguro.msmovimientos.utils.GrupoProducto;
import com.bancoseguro.msmovimientos.utils.ResultadoTransaccion;
import com.bancoseguro.msmovimientos.utils.TipoOperacion;
import com.bancoseguro.msmovimientos.utils.TipoProducto;

import lombok.Data;

@Document(collection = "movimientos")
@Data
public class Transaccion {
	
	@Id
	private String id;
	
	private String codControl;
	
	private GrupoProducto grupoProducto;
	
	private TipoProducto tipoProducto;
	
	private String codigoProducto;
	
	private TipoOperacion codigoOperacion;
	
	private Double montoTransaccion;
	
	private Date fechaTransaccion;
	
	private ResultadoTransaccion resultadoTransaccion;
	
	private String observacionTransaccion;
	

}
