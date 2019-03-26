package me.anichakra.poc.pilot.vehicle.service.impl;

import java.util.List;

import me.anichakra.poc.pilot.framework.annotation.CommandService;
import me.anichakra.poc.pilot.framework.annotation.InjectDataAccess;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.repo.VehicleRepository;
import me.anichakra.poc.pilot.vehicle.service.VehicleCommandService;

@CommandService
public class DefaultVehicleCommandService implements VehicleCommandService {

	@InjectDataAccess
	private VehicleRepository vehicleRepository;


	@Override
	public Vehicle saveVehicle(Vehicle vehicle) {
		Vehicle v = vehicleRepository.save(vehicle);
		return v;
	}

	@Override
	public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
		List<Vehicle> v = vehicleRepository.saveAll(vehicles);
		return v;

	}

	@Override
	public void deleteVehicle(Long id) {
		vehicleRepository.deleteById(id);
	}

	
}
