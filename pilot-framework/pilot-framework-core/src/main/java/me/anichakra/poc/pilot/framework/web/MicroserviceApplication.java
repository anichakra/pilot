package me.anichakra.poc.pilot.framework.web;

import org.apache.logging.log4j.util.StackLocatorUtil;
import org.springframework.boot.SpringApplication;

/**
 * From main class of a @Microservice annotated application call the start
 * method of this class. The microservice application will be started.
 * 
 * @author anirbanchakraborty
 *
 */
public class MicroserviceApplication {

	public static void start(String[] args) {
		if (Thread.currentThread().getStackTrace().length == 3)// boot should boot only if called from external command
																// and not if someone is calling the main class from
																// program.
			SpringApplication.run(StackLocatorUtil.getCallerClass(2), args);
	}

}
