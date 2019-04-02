package me.anichakra.poc.pilot.framework.web;

import org.apache.logging.log4j.util.StackLocatorUtil;
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
		SpringApplication.run(StackLocatorUtil.getCallerClass(2), args);
	}

}
