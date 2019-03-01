package me.anichakra.poc.pilot.vehicle.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTest;
import me.anichakra.poc.pilot.framework.test.annotation.MicroserviceTestRunner;
import me.anichakra.poc.pilot.framework.test.mock.MockApi;

@MicroserviceTest
@RunWith(MicroserviceTestRunner.class)

public class VehicleServiceApplicationTest {

    //private static String JSON_INPUT_V1 = "{\"id\":1,\"manufacturer\":\"Nissan\",\"year\":2015,\"model\":\"Altima\"}";
    //private static String JSON_INPUT_V2 = "{\"id\":2,\"manufacturer\":\"Toyota\",\"year\":2016,\"model\":\"Camri\"}";
    //private static String JSON_INPUT_PREF = "{\"segment\":\"compact\",\"type\":\"sedan\"}";
    //private static String JSON_INPUT_V = "[{\"id\":3,\"manufacturer\":\"Nissan\",\"year\":2015,\"model\":\"Altima\"},"
           // + "{\"id\":4,\"manufacturer\":\"Toyota\",\"year\":2016,\"model\":\"Camri\"}]";

    @Autowired
    private MockApi mockApi;

    @Test
    public void save() throws Exception {
        mockApi.assertCall("/vehicle", HttpMethod.POST, HttpStatus.CREATED, "save_in", "save_out");
    }

    @Test
    public void retrieve() throws Exception {
        save();
        mockApi.assertCall("/vehicle/1", HttpMethod.GET, HttpStatus.OK, null, "retrieve_out");
    }

  //  @Test
    public void deleteVehicle() throws Exception {
        save();
        mockApi.assertCall("/vehicle?id=1", HttpMethod.DELETE, HttpStatus.NO_CONTENT);
    }

    //@Test
    public void searchVehicle() throws Exception {
        save();
        mockApi.assertCall("/vehicle/search?manufacturer=Nissan", HttpMethod.POST, HttpStatus.OK, null, "searchVehicle_out");
    }

    //@Test
    public void getPreference() throws Exception {
        save();
        mockApi.assertCall("/vehicle/preference", HttpMethod.POST, HttpStatus.OK, "getPreference_in", "getPreference_out");
    }

    //@Test
    public void saveAll() throws Exception {
        mockApi.assertCall("/vehicle/save", HttpMethod.POST, HttpStatus.CREATED, "saveAll_in", "saveAll_out");
    }

}
