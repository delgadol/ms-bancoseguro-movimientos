package com.bancoseguro.msmovimientos.utils;

import java.util.Arrays;
import java.util.List;



import lombok.Data;

@Data
public class TransaccionReglas {
	
	private static final List<TipoProducto> reqLimiteMensual = Arrays.asList(TipoProducto.CTAA);
	
	public Boolean reqCheckLimiteMensual(TipoProducto tipoProducto) {
		return reqLimiteMensual.contains(tipoProducto);
	}
	
	

}
