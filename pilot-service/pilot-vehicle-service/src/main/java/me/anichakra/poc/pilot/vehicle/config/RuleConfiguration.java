package me.anichakra.poc.pilot.vehicle.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import me.anichakra.poc.pilot.framework.rule.RuleService;
import me.anichakra.poc.pilot.framework.rule.impl.OpenlTabletRuleService;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;

@Configuration
@ConfigurationProperties(prefix = "rule")
@Validated
public class RuleConfiguration {

    @NotNull
    String templatePath;
    
    @Autowired
    OpenlTabletRuleService<VehicleRuleTemplate> vehicleRuleService;

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Bean
    public RuleService<VehicleRuleTemplate> getVehicleRuleTemplate() {
        vehicleRuleService.loadRuleTemplate(templatePath, VehicleRuleTemplate.class);
        return vehicleRuleService;
    }

}
