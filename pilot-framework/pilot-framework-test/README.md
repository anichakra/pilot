# Introduction

This is a further opinionated unit test framework on top of springboot-test framework. The objective is to simplify the way a test case is written to test a ReST/JSON API using declarative programming style. This framework can be used with JUnit.

# Design
Each test class should pertain to one controller class which implements a set of related APIs. Each test class should be annotated with framework-test's @MicroserviceTest and JUnit's @RunWith(MicroserviceTestRunner). Each test class should @Inject the MockApi to get the API client's mock environment. From MockApi instance any ReST API can be called and the response of the same can be asserted in a declarative way. The unique part of this framework is that all the test input request body json content can be kept in .json files and can be just referred to the test method. Similarly the json message that is required to assert a test case with an API response can be also kept in .json file.

# Example
Following is an example of how to write a test class, check the javadoc of all the classes for more details:

```
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import me.anichakra.poc.pilot.framework.annotation.Inject;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.api.AssertableHttpStatusCode;
import me.anichakra.poc.pilot.framework.test.api.MockApi;
import me.anichakra.poc.pilot.framework.test.api.RequestBody;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	@Inject
	private MockApi mockApi;
	
	
	@Test
	public void a_saveAll() throws Exception {
		mockApi.post("/vehicle/save").call(new RequestBody("saveAll_in")) // "saveAll_in is the name of the json file that is kept in a directory called "data" which should be kept in src/test/resources
				.assertResult(AssertableHttpStatusCode.CREATED, "saveAll_out");
	}

	@Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.post("/vehicle").<Vehicle>call(new RequestBody("save_in")).getResultBean(Vehicle.class);
		mockApi.delete("/vehicle?id={id}").setUriVariables(v.getId()).call()
				.assertResult(AssertableHttpStatusCode.NO_CONTENT);
	}

	@Test
	public void c_retrieve() throws Exception {
		mockApi.get("/vehicle/{id}").setUriVariables(1).call().assertResult(AssertableHttpStatusCode.OK,
				"retrieve_out");
	}

	@Test
	public void d_searchVehicle() throws Exception {
		mockApi.get("/vehicle/search?manufacturer={manufacturer}").setUriVariables("Nissan").call()
				.assertResult(AssertableHttpStatusCode.OK, "searchVehicle_out");
	}

	@Test
	public void e_getPreference() throws Exception {
		mockApi.post("/vehicle/preference").call(new RequestBody("getPreference_in"))
				.assertResult(AssertableHttpStatusCode.OK, "getPreference_out");
	}

	@Test
	public void f_save() throws Exception {
		mockApi.post("/vehicle").call(new RequestBody("save_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"save_out");
	}
}


```
