package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tutorial5.model.FlightModel;

public interface FlightDb extends JpaRepository<FlightModel, Long> {
	FlightModel findById(long id);
	FlightModel findByFlightNumber(String flightNumber);
}
