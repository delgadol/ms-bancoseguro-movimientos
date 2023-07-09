package com.bancoseguro.msmovimientos.domain.dto.req;

import com.bancoseguro.msmovimientos.utils.TipoOperacion;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InfoTransacionReq {
	
	@NotEmpty
	private String idProducto;
	
	@NotNull
	private TipoOperacion tipoOperacion;
	
	@NotNull
	private Double montoOperacion;
	
	@NotEmpty
	private String obervacionTransaccion;
	
	

}
