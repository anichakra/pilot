package me.anichakra.poc.pilot.vehicle.service.impl;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.annotation.EventObject;
import me.anichakra.poc.pilot.framework.annotation.QueryService;
import me.anichakra.poc.pilot.framework.rule.api.RuleService;
import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.repo.VehicleRepository;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;
import me.anichakra.poc.pilot.vehicle.service.VehicleQueryService;

@QueryService
public class DefaultVehicleQueryService implements VehicleQueryService {

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
	public Optional<Vehicle> getVehicle(String id) {
		Optional<Vehicle> vehicle = vehicleRepository.findById(id);
		System.out.println(vehicle);
		return vehicle;
	}

	@Override
	@Event(name="tracing", object=EventObject.RESPONSE)
	public List<Vehicle> searchVehicle(String manufacturer) {
		return vehicleRepository.findByManufacturer(manufacturer);
	}

	@Override
	@Event(name= {"sourcing", "tracing"}, object=EventObject.RESPONSE)
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
		selectedVehicle.ifPresent(v->v.setPrice(discountedPrice));
		return selectedVehicle.orElse(null);
	}

}
