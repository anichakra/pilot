package me.anichakra.poc.pilot.vehicle;

import org.springframework.boot.SpringApplication;

import me.anichakra.poc.pilot.framework.annotation.Microservice;

@Microservice
public class VehicleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleServiceApplication.class, args);
	}
}
