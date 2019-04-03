package me.anichakra.poc.pilot.driver.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.pilot.driver.domain.Category;
import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.domain.Vehicle;
import me.anichakra.poc.pilot.driver.service.DriverQueryService;

@RestController
@RequestMapping("/driver")
public class DriverQueryController {

    @Inject
    private DriverQueryService driverQueryService;


    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('read')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    @ResponseBody
    public Driver getDriver(@PathVariable("id") Long id) {
        return driverQueryService.getDriver(id).get();
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public List<Driver> searchDrivers(@RequestBody Category category) {
        return driverQueryService.searchDrivers(category);
    }
    
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/assign")
    public Vehicle assignVehicle(@RequestBody Driver driver) {
    	Vehicle  v = driverQueryService.assignVehicle(driver);
        return v;
    }
    

}
