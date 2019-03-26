package me.anichakra.poc.pilot.driver.service;

import java.util.List;

import me.anichakra.poc.pilot.driver.domain.Driver;

/**
 * This is the driver service
 * @author Anirban
 *
 */
public interface DriverCommandService {

	Driver saveDriver(Driver driver);
	
    List<Driver> saveDrivers(List<Driver> drivers);
	
	void deleteDriver(Long id);
	


}
