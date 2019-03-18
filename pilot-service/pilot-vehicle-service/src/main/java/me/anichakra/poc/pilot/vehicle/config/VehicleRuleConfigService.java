package me.anichakra.poc.pilot.vehicle.config;

import me.anichakra.poc.pilot.framework.rule.impl.RuleConfig;
import me.anichakra.poc.pilot.framework.rule.impl.RuleConfigService;
import me.anichakra.poc.pilot.vehicle.rule.VehicleRuleTemplate;

@RuleConfig
public class VehicleRuleConfigService extends RuleConfigService<VehicleRuleTemplate> {

	@Override
	public Class<VehicleRuleTemplate> getRuleTemplateClass() {
		return VehicleRuleTemplate.class;
	}

}
