package com.bancoseguro.msmovimientos.domain.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import com.bancoseguro.msmovimientos.utils.ResultadoTransaccion;
import com.bancoseguro.msmovimientos.utils.TipoOperacion;
import com.bancoseguro.msmovimientos.utils.TipoProducto;

import lombok.Data;

@Data
public class Transaccion {
	
	@Id
	private String id;
	
	private String codControl;
	
	private TipoProducto tipoProducto;
	
	private String codigoProducto;
	
	private TipoOperacion codigoOperacion;
	
	private Double montoTransaccion;
	
	private Date fechaTransaccion;
	
	private ResultadoTransaccion resultadoTransaccion;
	

}
