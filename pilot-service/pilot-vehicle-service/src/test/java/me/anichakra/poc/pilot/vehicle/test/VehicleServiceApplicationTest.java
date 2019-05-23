package me.anichakra.poc.pilot.vehicle.test;

import javax.inject.Inject;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.api.AssertableHttpStatusCode;
import me.anichakra.poc.pilot.framework.test.api.MockApi;
import me.anichakra.poc.pilot.framework.test.api.RequestBody;
import me.anichakra.poc.pilot.framework.test.impl.MicroserviceTestRunner;
import me.anichakra.poc.pilot.vehicle.VehicleServiceApplication;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	@Inject
	private MockApi mockApi;

	//@Test
	public void testApplication() {
		VehicleServiceApplication.main(new String[] {});
	}

	@Test
	public void a_saveAll() throws Exception {
		mockApi.post("/vehicle/save").call(new RequestBody("saveAll_in")).assertResult(AssertableHttpStatusCode.CREATED,
				"saveAll_out");
	}

	@Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.post("/vehicle").<Vehicle>call(new RequestBody("delete_in")).getResultBean(Vehicle.class);
		mockApi.delete("/vehicle?id={id}").setUriVariables(v.getId()).call()
				.assertResult(AssertableHttpStatusCode.NO_CONTENT);
	}

	@Test
	public void c_retrieve() throws Exception {
		Vehicle v = mockApi.post("/vehicle").<Vehicle>call(new RequestBody("retrieve_in")).getResultBean(Vehicle.class);
		mockApi.get("/vehicle/{id}").setUriVariables(v.getId()).call().assertResult(AssertableHttpStatusCode.OK,
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
