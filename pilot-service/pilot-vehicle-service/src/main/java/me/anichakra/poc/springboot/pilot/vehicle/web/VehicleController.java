package me.anichakra.poc.springboot.pilot.vehicle.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.springboot.pilot.vehicle.domain.Category;
import me.anichakra.poc.springboot.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.springboot.pilot.vehicle.service.VehicleService;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    @Autowired
    @Qualifier("default")
    private VehicleService vehicleService;

    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
    @GetMapping("/{id}")
    @ResponseBody
    public Vehicle getVehicle(@PathVariable("id") Long id) {
        return vehicleService.getVehicle(id).get();
    }

    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and
    // hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Vehicle saveVehicle(@RequestBody Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<Vehicle> saveVehicles(@RequestBody List<Vehicle> vehicles) {
        return vehicleService.saveVehicles(vehicles);
    }

    @DeleteMapping
    public HttpStatus deleteVehicle(@RequestBody Vehicle vehicle) {
        vehicleService.deleteVehicle(vehicle.getId());
        return HttpStatus.OK;
    }

    @PostMapping("/search")
    public List<Vehicle> searchVehicle(@RequestParam("manufacturer") String manufacturer) {
        return vehicleService.searchVehicle(manufacturer);
    }

    @PostMapping("/preference")
    public Vehicle getPreference(@RequestBody Category category) {
        return vehicleService.getPreference(category);
    }

}
