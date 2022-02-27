package com.calculator.tax.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.calculator.tax.exception.TaxException;
import com.calculator.tax.models.TaxRequest;
import com.calculator.tax.models.TaxResponse;
import com.calculator.tax.services.TaxService;

@Validated
@RestController
@RequestMapping("/tax")
public class TaxController {

	@Autowired
	private TaxService taxService;

	private static final Logger LOGGER = Logger.getLogger(TaxController.class.getSimpleName());

	/**
	 * Get Tax. HTTP Request handler at the /post endpoint. Only accepts POST
	 * requests returns JSON
	 * 
	 * @param taxReq TaxRequest with vehicle type, city and dates
	 * @return TaxResponse record
	 */
	@PostMapping("/calculate")
	public ResponseEntity<TaxResponse> getTax(@RequestBody TaxRequest taxReq) {
		try {
			return ResponseEntity.ok().body(taxService.initiateTax(taxReq));
		} catch (IllegalArgumentException ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (TaxException ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

}