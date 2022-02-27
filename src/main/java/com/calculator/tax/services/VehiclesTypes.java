package com.calculator.tax.services;

public enum VehiclesTypes {

	CAR("Car"),
	MOTORBIKE("Motorbike"),
	TRACTOR("Tractor"),
	EMERGENCY("Emergency"),
	DIPLOMAT("Diplomat"),
	FOREIGN("Foreign"),
	MILITARY("Military");

	private final String type;

	VehiclesTypes(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
