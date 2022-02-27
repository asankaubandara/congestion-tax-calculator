package com.calculator.tax.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaxResponse {

	@JsonProperty(ATTR_TAX)
	public double tax;
	public static final String ATTR_TAX = "tax";

	@JsonProperty(ATTR_CURRENCY)
	public String currency = "SEK";
	public static final String ATTR_CURRENCY = "currency";

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
