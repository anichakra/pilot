package me.anichakra.poc.pilot.driver.service;

import java.util.List;
import java.util.Optional;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.domain.Vehicle;

/**
 * This is the driver service
 * @author Anirban
 *
 */
public interface DriverQueryService {
		
	Optional<Driver> getDriver(Long id);
		
	List<Driver> searchDrivers(Category category);
	
	Vehicle assignVehicle(Driver driver);

}
