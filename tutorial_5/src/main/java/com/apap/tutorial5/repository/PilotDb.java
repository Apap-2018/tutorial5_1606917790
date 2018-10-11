package com.apap.tutorial5.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tutorial5.model.PilotModel;

public interface PilotDb extends JpaRepository<PilotModel, Long> {
	PilotModel findByLicenseNumber(String licenseNumber);
}
