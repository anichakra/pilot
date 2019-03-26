package me.anichakra.poc.pilot.framework.rule.config;
import java.util.Optional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class RuleServiceConfigurationCondition implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		Optional<String> fileUrl = Optional.ofNullable(context.getEnvironment().getProperty(
				"rule.file-url"));
		Optional<String> templateClass = Optional.ofNullable(context.getEnvironment().getProperty(
				"rule.template-class"));
		Optional<String> engine = Optional.ofNullable(context.getEnvironment().getProperty(
				"rule.engine"));
		return fileUrl.isPresent() || templateClass.isPresent() || engine.isPresent();
	}
}