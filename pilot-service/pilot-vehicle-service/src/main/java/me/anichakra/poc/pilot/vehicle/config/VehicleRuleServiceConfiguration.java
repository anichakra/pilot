package me.anichakra.poc.pilot.vehicle.config;

import me.anichakra.poc.pilot.framework.rule.RuleConfig;
import me.anichakra.poc.pilot.framework.rule.impl.RuleServiceConfiguration;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;

@RuleConfig
public class VehicleRuleServiceConfiguration extends RuleServiceConfiguration<VehicleRuleTemplate> {

	@Override
	public Class<VehicleRuleTemplate> getRuleTemplateClass() {
		return VehicleRuleTemplate.class;
	}

}
