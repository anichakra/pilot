package me.anichakra.poc.pilot.driver.service.impl;

import java.util.List;
import java.util.Optional;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.domain.Vehicle;
import me.anichakra.poc.pilot.driver.repo.DriverRepository;
import me.anichakra.poc.pilot.driver.service.DriverQueryService;
import me.anichakra.poc.pilot.framework.annotation.InjectDataAccess;
import me.anichakra.poc.pilot.framework.annotation.QueryService;
import me.anichakra.poc.pilot.framework.rest.api.PostConsumer;
import me.anichakra.poc.pilot.framework.rest.config.InjectRestConsumer;

@QueryService
public class DefaultDriverQueryService implements DriverQueryService {

	@InjectRestConsumer("vehicle-preference")
	private PostConsumer<Category, Vehicle> postConsumer;

	@InjectDataAccess
	private DriverRepository driverRepository;


	@Override
	public Optional<Driver> getDriver(Long id) {
		return driverRepository.findById(id);
	}

	@Override
	public List<Driver> searchDrivers(Category category) {
		return driverRepository.findByCategory_segmentAndCategory_type(category.getSegment(), category.getType());
	}

	@Override
	public Vehicle assignVehicle(Driver driver) {
		return postConsumer.consume(driver.getCategory());
	}

}
