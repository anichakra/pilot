package me.anichakra.poc.pilot.driver.service.impl;

import java.util.List;

import javax.inject.Inject;

import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.repo.DriverRepository;
import me.anichakra.poc.pilot.driver.service.DriverCommandService;
import me.anichakra.poc.pilot.framework.annotation.CommandService;

@CommandService
public class DefaultDriverCommandService implements DriverCommandService {


	@Inject
	private DriverRepository driverRepository;

	@Override
	public Driver saveDriver(Driver driver) {
		return driverRepository.saveAndFlush(driver);
	}
	
	@Override
	public List<Driver> saveDrivers(List<Driver> drivers) {
		return driverRepository.saveAll(drivers);

	}

	@Override
	public void deleteDriver(Long id) {
		driverRepository.deleteById(id);
	}

}
