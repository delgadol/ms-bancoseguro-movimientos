package com.bancoseguro.msmovimientos.utils;

public enum TipoOperacion {
	
	ABONO(1),
	CARGO(-1),
	CONSULTA(0);
	
	private int operacion;

	TipoOperacion(int i) {
		this.operacion = i;
	}

	public int getOperacion() {
		return operacion;
	}
	
}
