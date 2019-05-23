package me.anichakra.poc.pilot.vehicle.service;

import java.util.List;

import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

public interface VehicleCommandService {
	
	public Vehicle saveVehicle(Vehicle vehicle) ;

	
	public List<Vehicle> saveVehicles(List<Vehicle> vehicles) ;


	public void deleteVehicle(String id);
}
