package com.calculator.tax.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calculator.tax.models.City;
import com.calculator.tax.models.Holiday;
import com.calculator.tax.models.Rate;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {	
	
	List<Rate> findByCity(City city);

}
