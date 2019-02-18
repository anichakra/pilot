package me.anichakra.poc.pilot.vehicle.rule;

import me.anichakra.poc.pilot.vehicle.domain.Category;
import me.anichakra.poc.pilot.vehicle.domain.Vehicle;

public interface VehicleRuleTemplate {
    Vehicle getPreference(Category c, Vehicle v);
    int getPrice(String model);
    double getDiscountedPrice(String manufacturer, int price);
}
