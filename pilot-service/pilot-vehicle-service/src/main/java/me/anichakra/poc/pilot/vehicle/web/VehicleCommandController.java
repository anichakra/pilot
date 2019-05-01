package me.anichakra.poc.pilot.vehicle.web;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.service.VehicleCommandService;

@RestController
@RequestMapping("/vehicle")
@Validated
public class VehicleCommandController {

	@Inject
	private VehicleCommandService vehicleCommandService;

	// @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and
	// hasRole('ROLE_ADMIN')")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Vehicle saveVehicle(@Valid @RequestBody Vehicle vehicle) {
		return vehicleCommandService.saveVehicle(vehicle);
	}

	@PostMapping("/save")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public List<Vehicle> saveVehicles(@Valid @RequestBody List<Vehicle> vehicles) {
		return vehicleCommandService.saveVehicles(vehicles);
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteVehicle(@RequestParam("id") @NotNull @Min(1) Long id) {
		vehicleCommandService.deleteVehicle(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

}
