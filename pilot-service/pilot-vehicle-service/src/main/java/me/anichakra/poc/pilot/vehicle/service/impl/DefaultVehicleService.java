package me.anichakra.poc.pilot.vehicle.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.anichakra.poc.pilot.framework.rule.RuleService;
import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;
import me.anichakra.poc.pilot.vehicle.repo.VehicleRepository;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;
import me.anichakra.poc.pilot.vehicle.service.VehicleService;

@Service("default")
public class DefaultVehicleService implements VehicleService {

    @Autowired
    @Qualifier("vehicle")
    private RuleService<VehicleRuleTemplate> ruleService;

    private VehicleRuleTemplate getRuleTemplate() {
        VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate();
        return vehicleRuleTemplate;
    }

    private VehicleRuleTemplate getRuleTemplate(String manufacturer) {
        VehicleRuleTemplate vehicleRuleTemplate = ruleService.getRuleTemplate(manufacturer);
        return vehicleRuleTemplate;
    }

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        Vehicle v = vehicleRepository.saveAndFlush(vehicle);
        return v;
    }

    @Transactional
    @Override
    public List<Vehicle> saveVehicles(List<Vehicle> vehicles) {
        List<Vehicle> v= vehicleRepository.saveAll(vehicles);
        return v;

    }

    @Override
    public Optional<Vehicle> getVehicle(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    @Override
    public List<Vehicle> searchVehicle(String manufacturer) {
        return vehicleRepository.findByManufacturer(manufacturer);
    }

    @Override
    public Vehicle getPreference(Category category) {
        final Optional<Vehicle> preferredVehicle = Optional
                .ofNullable(getRuleTemplate().getPreference(category, new Vehicle()));

        Optional<List<Vehicle>> vehicles = Optional.ofNullable(searchVehicle(preferredVehicle.get().getManufacturer()));
        Optional<Vehicle> selectedVehicle = Optional.ofNullable(vehicles.get().stream()
                .filter(c -> c.getModel().equalsIgnoreCase(preferredVehicle.get().getModel())).findAny().orElse(null));
        int price = getRuleTemplate(preferredVehicle.get().getManufacturer())
                .getPrice(preferredVehicle.get().getModel());

        int discountedPrice = (int) getRuleTemplate().getDiscountedPrice(preferredVehicle.get().getManufacturer(),
                price);
        selectedVehicle.get().setPrice(discountedPrice);
        return selectedVehicle.get();
    }

}
