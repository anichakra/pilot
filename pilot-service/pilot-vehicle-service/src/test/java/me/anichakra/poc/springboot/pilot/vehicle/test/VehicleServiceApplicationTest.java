package me.anichakra.poc.springboot.pilot.vehicle.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@SpringBootTest
@RunWith(SpringRunner.class)
public class VehicleServiceApplicationTest {

	private static String JSON_INPUT_V1 = "{\"id\":1,\"manufacturer\":\"Nissan\",\"year\":2015,\"model\":\"Altima\"}";
	private static String JSON_INPUT_V2 = "{\"id\":2,\"manufacturer\":\"Toyota\",\"year\":2016,\"model\":\"Camri\"}";
	private static String JSON_INPUT_PREF = "{\"segment\":\"compact\",\"type\":\"sedan\"}";
	private static String JSON_INPUT_V = "[{\"id\":3,\"manufacturer\":\"Nissan\",\"year\":2015,\"model\":\"Altima\"},"
	        + "{\"id\":4,\"manufacturer\":\"Toyota\",\"year\":2016,\"model\":\"Camri\"}]";

	
	private MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;

	@Before
	public void setup() {
		// this.mockMvc = MockMvcBuilders.standaloneSetup(new
		// WeatherApiController()).build();
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void retrievetest_ok() throws Exception {
		saveVehicle_ok();
		mockMvc.perform(get("/vehicle/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.manufacturer").value("Nissan"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.year").value(2015))
				.andExpect(MockMvcResultMatchers.jsonPath("$.model").value("Altima"));
	}

	@Test
	public void saveVehicle_ok() throws Exception {
		mockMvc.perform(post("/vehicle")// .andDo(print())
				.content(JSON_INPUT_V1).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
		mockMvc.perform(post("/vehicle")// .andDo(print())
				.content(JSON_INPUT_V2).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void deleteVehicle_ok() throws Exception {
		saveVehicle_ok();
		mockMvc.perform(delete("/vehicle?id=1")// .andDo(print())
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
	}

	@Test
	public void searchVehicle_ok() throws Exception {
		saveVehicle_ok();
		mockMvc.perform(get("/vehicle/search?manufacturer=Nissan")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[" + JSON_INPUT_V1 + "]"));
	}
	
	@Test
    public void getPreference_ok() throws Exception {
        saveVehicle_ok();
        mockMvc.perform(post("/vehicle/preference")// .andDo(print())
                .content(JSON_INPUT_PREF).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
	
	@Test
    public void saveAll() throws Exception {
        mockMvc.perform(post("/vehicle/save")// .andDo(print())
                .content(JSON_INPUT_V).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

}
