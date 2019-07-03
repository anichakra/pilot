package me.anichakra.poc.pilot.driver.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.DependsOn;

import me.anichakra.poc.pilot.driver.DriverServiceApplication;
import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.impl.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.rest.api.AssertableHttpStatusCode;
import me.anichakra.poc.pilot.framework.test.rest.api.MockRestApi;
import me.anichakra.poc.pilot.framework.test.rest.api.RequestBody;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@DependsOn("vehicle-preference")
public class DriverServiceApplicationTest {

	@Inject
	private MockRestApi mockApi;
	
	//@Test
	public void testApplication() throws Exception {
		DriverServiceApplication.main(new String[] {});
	}

	@Test
	public void saveAll() throws Exception {
		mockApi.post("/driver/save").call(new RequestBody("saveAll_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"saveAll_out");
	}

	@Test
	public void deleteDriver() throws Exception {
		Driver v = mockApi.post("/driver").<Driver>call(new RequestBody("save_in")).getResultBean(Driver.class);
		mockApi.delete("/driver?id={id}").setUriVariables(v.getId()).call()
				.assertResult(AssertableHttpStatusCode.NO_CONTENT);
	}

	@Test
	public void retrieve() throws Exception {
		Driver v = mockApi.post("/driver").<Driver>call(new RequestBody("save_in")).getResultBean(Driver.class);
		mockApi.get("/driver/{id}").setUriVariables(v.getId()).call().assertResult(AssertableHttpStatusCode.OK,
				"retrieve_out");
	}

	@Test
	public void searchDrivers() throws Exception {
		mockApi.post("/driver/save").call(new RequestBody("saveAll_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"saveAll_out");
		mockApi.post("/driver/search").call(new RequestBody("searchDrivers_in"))
				.assertResult(AssertableHttpStatusCode.OK, "searchDrivers_out");
	}


	@Test
	public void save() throws Exception {
		mockApi.post("/driver").call(new RequestBody("save_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"save_out");
	}
	
	@Test
	public void assign() throws Exception {
		mockApi.post("/driver/assign").call(new RequestBody("save_in")).assertResult(AssertableHttpStatusCode.OK,
				"assign_out");

	}

}
