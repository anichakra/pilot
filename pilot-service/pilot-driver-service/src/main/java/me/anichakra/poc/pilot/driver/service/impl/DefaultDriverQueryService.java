package me.anichakra.poc.pilot.driver.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.domain.Vehicle;
import me.anichakra.poc.pilot.driver.repo.DriverRepository;
import me.anichakra.poc.pilot.driver.service.DriverQueryService;
import me.anichakra.poc.pilot.framework.annotation.QueryService;
import me.anichakra.poc.pilot.framework.rest.api.PostConsumer;

@QueryService
public class DefaultDriverQueryService implements DriverQueryService {

	@Inject
	@Named("vehicle-preference")
	private PostConsumer<Category, Vehicle> postConsumer;

	@Inject
	private KieContainer kieContainer;

	@Inject
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
try {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.setGlobal("driver", driver);
		kieSession.insert(driver);
		kieSession.fireAllRules();
		System.out.println("My driver:" + driver);
		kieSession.dispose();
} catch (Exception e) {
	e.printStackTrace();
}
		return postConsumer.consume(driver.getCategory());
	}

}
