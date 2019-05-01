package me.anichakra.poc.pilot.vehicle.web;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.service.VehicleNotFoundException;
import me.anichakra.poc.pilot.vehicle.service.VehicleQueryService;

@RestController
@RequestMapping("/vehicle")
@Validated
public class VehicleQueryController {

	@Inject
	private VehicleQueryService vehicleQueryService;

	// @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/{id}")
	@ResponseBody
	public Vehicle getVehicle(@PathVariable("id") @Min(1) Long id) throws VehicleNotFoundException {
		return vehicleQueryService.getVehicle(id).orElseThrow(() -> new VehicleNotFoundException(id));
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/search")
	public List<Vehicle> searchVehicle(
			@RequestParam("manufacturer") 
			@NotEmpty @Size(min = 4, max = 10) 
			@Pattern(regexp = ".*(?=.{4,})(?=.*[\\d[a-z][A-Z]]{4,}).*") 
			String manufacturer) {
		return vehicleQueryService.searchVehicle(manufacturer);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping("/preference")
	public Vehicle getPreference(@RequestBody @Valid Category category) {
		return vehicleQueryService.getPreference(category);
	}

}
