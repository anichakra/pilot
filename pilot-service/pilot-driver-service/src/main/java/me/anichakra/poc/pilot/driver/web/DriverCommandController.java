package me.anichakra.poc.pilot.driver.web;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import me.anichakra.poc.pilot.driver.domain.Driver;
import me.anichakra.poc.pilot.driver.service.DriverCommandService;

@RestController
@RequestMapping("/driver")
public class DriverCommandController {

    @Inject
    private DriverCommandService driverCommandService;
    
    // @PreAuthorize("#oauth2.hasScope('bar') and #oauth2.hasScope('write') and
    // hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Driver saveDriver(@RequestBody Driver driver) {
        return driverCommandService.saveDriver(driver);
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public List<Driver> saveDrivers(@RequestBody List<Driver> drivers) {
        return driverCommandService.saveDrivers(drivers);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteDriver(@RequestParam("id") Long id) {
    	driverCommandService.deleteDriver(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
