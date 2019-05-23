package me.anichakra.poc.pilot.vehicle.service.impl;

import java.util.List;

import javax.inject.Inject;

import me.anichakra.poc.pilot.framework.annotation.CommandService;
import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.annotation.EventObject;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.repo.VehicleRepository;
import me.anichakra.poc.pilot.vehicle.service.VehicleCommandService;

@CommandService
public class DefaultVehicleCommandService implements VehicleCommandService {

	@Inject
	private VehicleRepository vehicleRepository;

	@Override
	@Event(name = "sourcing", object = EventObject.RESPONSE)
	public Vehicle saveVehicle(Vehicle vehicle) {
		Vehicle v = vehicleRepository.save(vehicle);
		return v;
	}

	@Override
	public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
		try {
			List<Vehicle> v = vehicleRepository.saveAll(vehicles);
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void deleteVehicle(String id) {
		try {
			Vehicle v = vehicleRepository.findById(id).get();
			vehicleRepository.delete(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
