package me.anichakra.poc.pilot.driver.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.driver.domain.Vehicle;
import me.anichakra.poc.pilot.framework.rest.api.PostConsumer;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestConfiguration;
import me.anichakra.poc.pilot.framework.test.annotation.MockInjectable;

@MicroserviceTestConfiguration
public class DriverServiceApplicationTestConfiguration {

	@MockInjectable(name="vehicle-preference")
	public PostConsumer<Category, Vehicle> getPostConsumerBean() {
		@SuppressWarnings("unchecked")
		PostConsumer<Category, Vehicle> p = mock(PostConsumer.class);
		Category c = new Category();
		c.setSegment("compact");
		c.setType("suv");
		Vehicle v = new Vehicle();
		v.setManufacturer("Honda");
		v.setYear(2017);
		v.setModel("Pilot");
		v.setPrice(13300);
		p = when(p.consume(c)).thenReturn(v).getMock();
		System.out.println(p.consume(c));
		return p;
	}
}
