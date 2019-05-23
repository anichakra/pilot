package me.anichakra.poc.pilot.vehicle.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@Repository
public interface VehicleRepository extends MongoRepository <Vehicle, String>{

	List<Vehicle> findByManufacturer(String manufacturer);
}
