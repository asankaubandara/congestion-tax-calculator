package com.calculator.tax.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxRequest {

	@JsonProperty(ATTR_CITY)
	public String city;
	public static final String ATTR_CITY = "city";

	@JsonProperty(ATTR_VEHICLE)
	public String vehicle;
	public static final String ATTR_VEHICLE = "vehicle";

	@JsonProperty(ATTR_DATES)
	public List<String> time;
	public static final String ATTR_DATES = "time";

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVehicle() {
		return vehicle;
	}

	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}

	public List<String> getTime() {
		return time;
	}

	public void setTime(List<String> time) {
		this.time = time;
	}

}
