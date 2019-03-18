package me.anichakra.poc.pilot.vehicle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import me.anichakra.poc.pilot.framework.annotation.Inject;
import me.anichakra.poc.pilot.framework.annotation.StatelessService;
import me.anichakra.poc.pilot.framework.rule.RuleService;
import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.repo.VehicleRepository;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;
import me.anichakra.poc.pilot.vehicle.service.VehicleService;

@StatelessService
public class DefaultVehicleService implements VehicleService {

	@Inject
	private RuleService<VehicleRuleTemplate> ruleService;
	
	@Inject
	private VehicleRepository vehicleRepository;

	private VehicleRuleTemplate getRuleTemplate() {
		VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate(null, null);
		return vehicleRuleTemplate;
	}

	private VehicleRuleTemplate getRuleTemplate(String manufacturer) {
		VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate(manufacturer, null);
		return vehicleRuleTemplate;
	}

	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
		Vehicle v = vehicleRepository.saveAndFlush(vehicle);
		return v;
	}

	@Transactional
	@Override
	public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
		List<Vehicle> v = vehicleRepository.saveAll(vehicles);
		return v;

	}

	@Override
	public Optional<Vehicle> getVehicle(Long id) {
		System.out.println("id:" + id);
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		System.out.println(vehicle);
		return vehicle;
	}

	@Override
	public void deleteVehicle(Long id) {
		vehicleRepository.deleteById(id);
	}

	@Override
	public List<Vehicle> searchVehicle(String manufacturer) {
		return vehicleRepository.findByManufacturer(manufacturer);
	}

	@Override
	public Vehicle getPreference(Category category) {
		final Optional<Vehicle> preferredVehicle = Optional
				.ofNullable(getRuleTemplate().getPreference(category, new Vehicle()));

		Optional<List<Vehicle>> vehicles = Optional.ofNullable(searchVehicle(preferredVehicle.get().getManufacturer()));
		Optional<Vehicle> selectedVehicle = Optional.ofNullable(vehicles.get().stream()
				.filter(c -> c.getModel().equalsIgnoreCase(preferredVehicle.get().getModel())).findAny().orElse(null));
		int price = getRuleTemplate(preferredVehicle.get().getManufacturer())
				.getPrice(preferredVehicle.get().getModel());

		int discountedPrice = (int) getRuleTemplate().getDiscountedPrice(preferredVehicle.get().getManufacturer(),
				price);
		selectedVehicle.get().setPrice(discountedPrice);
		return selectedVehicle.get();
	}

}
