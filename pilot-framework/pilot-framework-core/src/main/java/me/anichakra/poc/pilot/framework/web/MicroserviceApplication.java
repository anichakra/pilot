package me.anichakra.poc.pilot.framework.web;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.springframework.boot.SpringApplication;

import me.anichakra.poc.pilot.framework.annotation.Microservice;

/**
 * From main class of a @Microservice annotated application call the start
 * method of this class. The microservice application will be started.
 * 
 * @author anirbanchakraborty
 * @see Microservice
 */
public class MicroserviceApplication {

	public static final String MICROSERVICE_INSTANCE_ID = "microservice.instance.id";
    public static final String MICROSERVICE_INSTANCE_STARTUP_TIME = "microservice.instance.startup.time";
    public static final String MICROSERVICE_NAME = "microservice.name";
    public static final String MICROSERVICE_VERSION = "microservice.version";

    public static void start(String[] args) {
      //TODO: we need to come with some other that can come directly from container or CICD
		Optional.ofNullable(System.getProperty(MICROSERVICE_INSTANCE_ID)).orElseGet(()->System.setProperty(
		        MICROSERVICE_INSTANCE_ID, UUID.randomUUID().toString())); 
		System.setProperty(
		        MICROSERVICE_INSTANCE_STARTUP_TIME, LocalDateTime.now().toString());
			SpringApplication.run(StackLocatorUtil.getCallerClass(2), args);
	}

}
