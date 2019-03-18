package me.anichakra.poc.pilot.vehicle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.anichakra.poc.pilot.framework.annotation.DataAccess;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@DataAccess
public interface VehicleRepository extends JpaRepository <Vehicle, Long>{

	public List<Vehicle> findByManufacturer(String manufacturer);
}
