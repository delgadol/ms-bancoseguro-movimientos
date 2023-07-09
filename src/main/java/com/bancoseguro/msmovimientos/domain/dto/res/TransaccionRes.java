package com.bancoseguro.msmovimientos.domain.dto.res;

import java.util.Date;

import com.bancoseguro.msmovimientos.utils.ResultadoTransaccion;
import com.bancoseguro.msmovimientos.utils.TipoOperacion;

import lombok.Data;

@Data
public class TransaccionRes {
	
private String codControl;
	
	private TipoOperacion codigoOperacion;

	private String codigoProducto;	
	
	private Double montoTransaccion;
	
	private Date fechaTransaccion;
	
	private ResultadoTransaccion resultadoTransaccion;
	
	private String observacionTransaccion;
	

}
