package com.calculator.tax.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.calculator.tax.exception.TaxException;
import com.calculator.tax.models.City;
import com.calculator.tax.models.Holiday;
import com.calculator.tax.models.Rate;
import com.calculator.tax.models.TaxRequest;
import com.calculator.tax.models.TaxResponse;
import com.calculator.tax.repositories.CityRepository;
import com.calculator.tax.repositories.HolidayRepository;
import com.calculator.tax.repositories.RateRepository;
import com.calculator.tax.vehicle.Vehicle;
import com.calculator.tax.vehicle.VehicleFactory;

@Service
public class TaxService {

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private HolidayRepository holidayRepository;

	@Autowired
	private RateRepository rateRepository;

	private static final Logger LOGGER = Logger.getLogger(TaxService.class.getSimpleName());

	private static Map<String, Integer> tollFreeVehicles = new HashMap<>();

	private final long DAYS_BEFORE_HOLIDAY = 2;
	private final String DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";

	static {
		tollFreeVehicles.put("Motorcycle", 0);
		tollFreeVehicles.put("Tractor", 1);
		tollFreeVehicles.put("Emergency", 2);
		tollFreeVehicles.put("Diplomat", 3);
		tollFreeVehicles.put("Foreign", 4);
		tollFreeVehicles.put("Military", 5);
	}

	/**
	 * Initiate the Tax request from controller
	 *
	 * @param taxReq TaxRequest with vehicle type, city and dates
	 * @return response TaxResponse
	 * @throws TaxException
	 */
	public TaxResponse initiateTax(TaxRequest taxReq) throws TaxException {

		VehicleFactory vehicleFactory = new VehicleFactory();
		Vehicle vehicle = vehicleFactory.getVehicle(taxReq.getVehicle());
		if (vehicle == null) {
			LOGGER.log(Level.WARNING, "Invalid Vehicle Type");
			throw new TaxException("Invalid Vehicle Type");
		}

		Date[] dates = getDates(taxReq.getTime());

		if (dates.length == 0) {
			LOGGER.log(Level.WARNING, "Dates are Empty");
			throw new TaxException("Dates are Empty");
		}

		TaxResponse response = new TaxResponse();
		double tax = getTax(vehicle, dates, taxReq.getCity());
		response.setTax(tax);

		return response;
	}

	/**
	 * Return the Tax payment amount for the Vehicle
	 *
	 * @param vehicle vehicle
	 * @param dates   array of dates passed troll stations
	 * @return total Tax fee
	 * @throws TaxException
	 */
	public double getTax(Vehicle vehicle, Date[] dates, String cityStr) throws TaxException {

		double totalFee = 0;

		if (dates.length > 0) {
			Date intervalStart = dates[0];

			City city = cityRepository.findOneByCode(cityStr);

			if (city == null) {
				LOGGER.log(Level.WARNING, "Invalid City");
				throw new TaxException("Invalid City");
			}

			for (int i = 0; i < dates.length; i++) {
				Date date = dates[i];
				double nextFee = getTollFee(date, vehicle, city);
				double tempFee = getTollFee(intervalStart, vehicle, city);

				long diffInMillies = date.getTime() - intervalStart.getTime();
				long minutes = diffInMillies / 1000 / 60;

				if (minutes <= 60) {
					if (totalFee > 0)
						totalFee -= tempFee;
					if (nextFee >= tempFee)
						tempFee = nextFee;
					totalFee += tempFee;
				} else {
					totalFee += nextFee;
				}
			}

			if (totalFee > 60)
				totalFee = 60;
		}

		return totalFee;
	}

	private boolean isTollFreeVehicle(Vehicle vehicle) {
		if (vehicle == null)
			return false;
		String vehicleType = vehicle.getVehicleType();
		return tollFreeVehicles.containsKey(vehicleType);
	}

	/**
	 * Return the TollFee
	 *
	 * @param Date    Date of the Toll
	 * @param vehicle vehicle
	 * @param city    city of the toll
	 * @return total Tax fee
	 * @throws TaxException
	 */
	public double getTollFee(Date date, Vehicle vehicle, City city) {
		LocalDateTime localDate = convertToLocalDateTimeViaMilisecond(date);

		if (isTollFreeDate(date, city) || isTollFreeVehicle(vehicle)) {
			return 0.0d;
		}

		List<Rate> rates = rateRepository.findByCity(city);

		double taxRate = 0.0d;

		for (Rate rate : rates) {

			LocalDateTime startDate = convertToLocalDateTimeViaMilisecond(rate.getStartDate());
			LocalDateTime endDate = convertToLocalDateTimeViaMilisecond(rate.getEndDate());

			LocalDateTime newStartDate = localDate.withHour(startDate.getHour()).withMinute(startDate.getMinute());
			LocalDateTime endStartDate = localDate.withHour(endDate.getHour()).withMinute(endDate.getMinute());

			Boolean containsToday = (!localDate.isBefore(newStartDate)) && (localDate.isBefore(endStartDate));

			if (containsToday) {
				taxRate = rate.getTax();
			}
		}

		return taxRate;

	}

	/**
	 * Check is the given date is a Toll Free Date
	 *
	 * @param Date Date of the Toll
	 * @param city city of the toll
	 * @return boolean is weather toll free
	 * @throws TaxException
	 */
	private Boolean isTollFreeDate(Date date, City city) {
		boolean isHoliday = false;
		LocalDateTime localDate = convertToLocalDateTimeViaMilisecond(date);

		int year = localDate.getYear();

		if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY)
			return true;

		List<Holiday> holidayList = holidayRepository.findByCity(city);

		// validate only for year 2013
		if (year == 2013) {
			for (Holiday holiday : holidayList) {

				LocalDateTime startDate = convertToLocalDateTimeViaMilisecond(holiday.getDate());
				LocalDateTime beforeDate = startDate.minusDays(DAYS_BEFORE_HOLIDAY);

				// check holiday with public holidays, days before a public holiday
				isHoliday = ((!localDate.isBefore(beforeDate)) && localDate.isBefore(startDate));

				if (isHoliday) {
					break;
				}
			}

			return isHoliday;
		} else if (localDate.getMonth() == Month.JULY) {
			// during the month of July
			isHoliday = true;
		}
		return isHoliday;
	}

	public LocalDateTime convertToLocalDateTimeViaMilisecond(Date dateToConvert) {
		return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

	public Date[] getDates(List<String> dates) throws TaxException {
		Date[] dateList = new Date[dates.size()];
		for (int i = 0; i < dateList.length; i++) {
			dateList[i] = getDateFromString(dates.get(i));
		}
		return dateList;
	}

	/**
	 * Convert String date to Date format
	 *
	 * @param dateStr String Date
	 * @return Date date object
	 * @throws TaxException
	 */
	public Date getDateFromString(String dateStr) throws TaxException {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMATE, Locale.ENGLISH);
		String dateInString = dateStr;
		try {
			return formatter.parse(dateInString);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new TaxException("Invalid Date Format");
		}
	}

}
