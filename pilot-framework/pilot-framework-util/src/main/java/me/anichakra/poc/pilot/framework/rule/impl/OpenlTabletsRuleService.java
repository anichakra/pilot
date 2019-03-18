package me.anichakra.poc.pilot.framework.rule.impl;

import java.util.Map;
import java.util.Optional;

import org.openl.rules.context.IRulesRuntimeContext;
import org.openl.rules.context.RulesRuntimeContextFactory;
import org.openl.rules.runtime.RulesEngineFactory;
import org.openl.runtime.IEngineWrapper;
import org.openl.runtime.IRuntimeContext;
import org.openl.vm.IRuntimeEnv;

import me.anichakra.poc.pilot.framework.rule.RuleService;

public class OpenlTabletsRuleService<T> implements RuleService<T> {

	private RulesEngineFactory<T> ruleEngineFactory;

	public OpenlTabletsRuleService(RulesEngineFactory<T> ruleEngineFactory) {
		this.ruleEngineFactory = ruleEngineFactory;
	}

	@Override
	public T getRuleTemplate(String contextValue, Map<String, Object> params) {
		T t = this.ruleEngineFactory.newEngineInstance();
		Optional.ofNullable(contextValue).ifPresent(c -> {
			IRuntimeEnv env = ((IEngineWrapper) t).getRuntimeEnv();
			IRulesRuntimeContext context = RulesRuntimeContextFactory.buildRulesRuntimeContext();
			env.setContext((IRuntimeContext) context);
			context.setLob(contextValue);
		});
		
		return t;
	}

	@Override
	public T getRuleTemplate(String context) {
		return getRuleTemplate(context, null);
	}

	@Override
	public T getRuleTemplate() {
		return getRuleTemplate(null);
	}

}
