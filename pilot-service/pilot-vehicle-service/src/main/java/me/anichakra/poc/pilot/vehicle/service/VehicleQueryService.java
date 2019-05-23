package me.anichakra.poc.pilot.vehicle.service;

import java.util.List;
import java.util.Optional;

import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

/**
 * This is the vehicle service
 * @author Anirban
 *
 */
public interface VehicleQueryService {

	
	Optional<Vehicle> getVehicle(String id) ;
	List<Vehicle> searchVehicle(String manufacturer);

    Vehicle getPreference(Category category);



}
