package com.calculator.tax.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.calculator.tax.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
	
	City findOneByCode(String code);
}
