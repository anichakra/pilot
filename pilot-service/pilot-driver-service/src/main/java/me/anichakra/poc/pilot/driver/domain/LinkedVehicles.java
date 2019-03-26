package me.anichakra.poc.pilot.driver.domain;

import java.io.Serializable;
import java.util.List;

public class LinkedVehicles implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Vehicle> vehicles;

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	

}
