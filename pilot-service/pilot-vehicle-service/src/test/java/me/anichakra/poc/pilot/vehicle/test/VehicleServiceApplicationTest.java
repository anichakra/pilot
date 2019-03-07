package me.anichakra.poc.pilot.vehicle.test;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.mock.MockApi;
import me.anichakra.poc.pilot.framework.test.mock.TestData;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VehicleServiceApplicationTest {

	@Autowired
	private MockApi mockApi;

	@Test
		public void a_saveAll() throws Exception {
			mockApi.assertCall("/vehicle/save", HttpMethod.POST, HttpStatus.CREATED,
					new TestData("saveAll_in", "saveAll_out"));
		}


	@Test
	public void b_deleteVehicle() throws Exception {
		Vehicle v = mockApi.<Vehicle>call("/vehicle", HttpMethod.POST, new TestData("save_in", null))
				.getResultBean(Vehicle.class);
		mockApi.assertCall("/vehicle?id=" + v.getId(), HttpMethod.DELETE, HttpStatus.NO_CONTENT);
	}
	
	@Test
	public void c_retrieve() throws Exception {
		mockApi.assertCall("/vehicle/1", HttpMethod.GET, HttpStatus.OK, new TestData("retrieve_out"));
	}

	@Test()
	public void d_searchVehicle() throws Exception {
		mockApi.assertCall("/vehicle/search?manufacturer=Nissan", HttpMethod.GET, HttpStatus.OK,
				new TestData("searchVehicle_out"));
	}

	@Test
	public void e_getPreference() throws Exception {
		mockApi.assertCall("/vehicle/preference", HttpMethod.POST, HttpStatus.OK,
				new TestData("getPreference_in", "getPreference_out"));
	}

	@Test
	public void f_save() throws Exception {
		mockApi.assertCall("/vehicle", HttpMethod.POST, HttpStatus.CREATED, new TestData("save_in", "save_out"));
	}

}
