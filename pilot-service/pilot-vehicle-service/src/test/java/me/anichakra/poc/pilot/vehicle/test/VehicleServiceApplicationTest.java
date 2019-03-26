package me.anichakra.poc.pilot.vehicle.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import me.anichakra.poc.pilot.framework.annotation.InjectService;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.api.AssertableHttpStatusCode;
import me.anichakra.poc.pilot.framework.test.api.MockApi;
import me.anichakra.poc.pilot.framework.test.api.RequestBody;
import me.anichakra.poc.pilot.framework.util.StringUtils;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	@InjectService
	private MockApi mockApi;

	@Test
	public void a_saveAll() throws Exception {
		mockApi.post("/vehicle/save").call(new RequestBody("saveAll_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"saveAll_out");
	}

	@Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.post("/vehicle").<Vehicle>call(new RequestBody("save_in")).getResultBean(Vehicle.class);
		mockApi.delete("/vehicle?id={id}").setUriVariables(v.getId()).call()
				.assertResult(AssertableHttpStatusCode.NO_CONTENT);
		String template = "The vehicle with ${id} whose manufacturere is ${manufacturer} with price ${price:0.11} build in ${year} is not available";
		String value = StringUtils.format(template, v);
		System.out.println(value);
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
