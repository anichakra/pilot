package me.anichakra.poc.pilot.framework.web;

import org.springframework.boot.SpringApplication;

/**
 * From main class of a @Microservice annotated application call the start
 * method of the application.
 * 
 * @author anirbanchakraborty
 *
 */
public class MicroserviceApplication {

	public static void start(String[] args) {
		String className = Thread.currentThread().getStackTrace()[2].getClassName();
		try {
			SpringApplication.run(Class.forName(className), args);
		} catch (ClassNotFoundException e) {
			throw new MicroserviceInitiationException(className);
		}
	}
	
	
}
