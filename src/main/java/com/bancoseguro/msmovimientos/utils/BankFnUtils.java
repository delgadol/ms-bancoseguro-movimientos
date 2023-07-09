package com.bancoseguro.msmovimientos.utils;

import java.util.UUID;

public class BankFnUtils {
	
	public static String uniqueProductCode() {
		
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	
	}

}
