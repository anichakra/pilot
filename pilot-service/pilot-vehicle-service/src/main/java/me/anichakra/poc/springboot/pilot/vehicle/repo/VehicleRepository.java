package me.anichakra.poc.springboot.pilot.vehicle.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import me.anichakra.poc.springboot.pilot.vehicle.domain.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, Long>{

	public List<Vehicle> findByManufacturer(String manufacturer);
}
