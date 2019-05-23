package me.anichakra.poc.pilot.vehicle.service;

import me.anichakra.poc.pilot.framework.validation.InvalidValue;

public class VehicleNotFoundException extends Exception {

	@InvalidValue
	private String id;

	public VehicleNotFoundException(String id) {
		super("Vehicle with id:" + id + " not found!");
		this.setId(id);
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
