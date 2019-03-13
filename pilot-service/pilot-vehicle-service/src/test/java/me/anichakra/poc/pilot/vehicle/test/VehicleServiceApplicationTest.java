package me.anichakra.poc.pilot.vehicle.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.mock.AssertableHttpStatusCode;
import me.anichakra.poc.pilot.framework.test.mock.AssertionData;
import me.anichakra.poc.pilot.framework.test.mock.InputData;
import me.anichakra.poc.pilot.framework.test.mock.MockApi;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	@Autowired
	private MockApi mockApi;

	@Test
	public void a_saveAll() throws Exception {
		mockApi.assertPostCall("/vehicle/save", new InputData("saveAll_in"),
				new AssertionData(AssertableHttpStatusCode.CREATED, "saveAll_out"));
	}

	@Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.<Vehicle>postCall("/vehicle", new InputData("save_in")).getResultBean(Vehicle.class);
		mockApi.assertDeleteCall("/vehicle?id={id}", new InputData().setUriVariables(v.getId()),
				new AssertionData(AssertableHttpStatusCode.NO_CONTENT));
	}

	@Test
	public void c_retrieve() throws Exception {
		mockApi.assertGetCall("/vehicle/{id}", new InputData().setUriVariables(1),
				new AssertionData(AssertableHttpStatusCode.OK, "retrieve_out"));
	}

	@Test()
	public void d_searchVehicle() throws Exception {
		mockApi.assertGetCall("/vehicle/search?manufacturer={manufacturer}", new InputData().setUriVariables("Nissan"),
				new AssertionData(AssertableHttpStatusCode.OK, "searchVehicle_out"));
	}

	@Test
	public void e_getPreference() throws Exception {
		mockApi.assertPostCall("/vehicle/preference", new InputData("getPreference_in"),
				new AssertionData(AssertableHttpStatusCode.OK, "getPreference_out"));
	}

	@Test
	public void f_save() throws Exception {
		mockApi.assertPostCall("/vehicle", new InputData("save_in"),
				new AssertionData(AssertableHttpStatusCode.CREATED, "save_out"));
	}

}
