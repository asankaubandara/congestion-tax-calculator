package com.calculator.tax.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calculator.tax.models.City;
import com.calculator.tax.models.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {	
	
	List<Holiday> findByCity(City city);

}
