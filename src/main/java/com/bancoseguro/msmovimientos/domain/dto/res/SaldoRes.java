package com.bancoseguro.msmovimientos.domain.dto.res;

import com.bancoseguro.msmovimientos.utils.TipoProducto;

import lombok.Data;

@Data
public class SaldoRes {
	

	private String codControl;

	private TipoProducto tipoProducto;

	private String codigoProducto;

	private Double saldoActual;
	

}
