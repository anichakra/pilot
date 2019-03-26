package me.anichakra.poc.pilot.vehicle;

import me.anichakra.poc.pilot.framework.annotation.Microservice;
import me.anichakra.poc.pilot.framework.web.MicroserviceApplication;

@Microservice
public class VehicleServiceApplication {

	public static void main(String[] args) {
		MicroserviceApplication.start(args);
	}
}
