package com.calculator.tax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import com.calculator.tax.exception.TaxException;
import com.calculator.tax.models.City;
import com.calculator.tax.repositories.CityRepository;
import com.calculator.tax.services.TaxService;
import com.calculator.tax.vehicle.Vehicle;
import com.calculator.tax.vehicle.VehicleFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unused")
@SpringBootTest()
@AutoConfigureMockMvc
class TaxApplicationTests {

	@Autowired
	@InjectMocks
	private TaxService taxService;

	@Test
	public void testCarTax() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 15:31:59"), getDateFromString("25-11-2013 16:10:59") };
		assertEquals(Double.valueOf(18), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testSingleChargeRule() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 09:08:59") };
		assertEquals(Double.valueOf(13), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testNoneSingleChargeRule() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("25-11-2013 08:10:59"), getDateFromString("25-11-2013 10:20:59") };
		assertEquals(Double.valueOf(21), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testHoliday() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("28-03-2013 15:31:59"), getDateFromString("28-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testHolidayWithBeforeDay() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("27-03-2013 15:31:59"), getDateFromString("27-03-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testExceptVehicles() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Military");
		Date[] dates = { getDateFromString("27-02-2013 15:31:59"), getDateFromString("27-02-2013 16:10:59") };
		assertEquals(Double.valueOf(0), taxService.getTax(vehicle, dates, "GB"));
	}

	@Test
	public void testMaximumAmount() throws TaxException {
		// create Test Data
		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle("Car");
		Date[] dates = { getDateFromString("12-02-2013 06:10:59"), getDateFromString("12-02-2013 06:30:59"),
				getDateFromString("12-02-2013 07:05:59"), getDateFromString("12-02-2013 07:30:59"),
				getDateFromString("12-02-2013 08:20:59"), getDateFromString("12-02-2013 08:40:59"),
				getDateFromString("12-02-2013 09:30:59"), getDateFromString("12-02-2013 15:10:59"),
				getDateFromString("12-02-2013 15:40:59"), getDateFromString("12-02-2013 16:30:59"),
				getDateFromString("12-02-2013 17:30:59"), getDateFromString("12-02-2013 18:10:59") };
		assertEquals(Double.valueOf(60), taxService.getTax(vehicle, dates, "GB"));
	}

	public Date getDateFromString(String dateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
		String dateInString = dateStr;
		Date date;
		try {
			date = formatter.parse(dateInString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return date;
	}

}
