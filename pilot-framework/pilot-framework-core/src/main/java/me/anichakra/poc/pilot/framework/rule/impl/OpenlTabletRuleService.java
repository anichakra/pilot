package me.anichakra.poc.pilot.framework.rule.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.openl.rules.context.IRulesRuntimeContext;
import org.openl.rules.context.RulesRuntimeContextFactory;
import org.openl.rules.runtime.RulesEngineFactory;
import org.openl.runtime.IEngineWrapper;
import org.openl.runtime.IRuntimeContext;
import org.openl.vm.IRuntimeEnv;
import org.springframework.stereotype.Service;

import me.anichakra.poc.pilot.framework.rule.InvalidRuleTemplatePathException;
import me.anichakra.poc.pilot.framework.rule.RuleService;

@Service
public class OpenlTabletRuleService<T> implements RuleService<T> {
    private RulesEngineFactory<T> ruleEngineFactory;

    @Override
    public void loadRuleTemplate(String ruleTemplatePath, Class<T> clazz) {
        synchronized (this) {
            try {
                this.ruleEngineFactory = new RulesEngineFactory<T>(new URL(ruleTemplatePath), clazz);
            } catch (MalformedURLException e) {
                throw new InvalidRuleTemplatePathException("Rule template is invalid or missing: " + ruleTemplatePath,
                        e);
            }
        }
    }

 

    @Override
    public T getRuleTemplate() {
        return this.ruleEngineFactory.newEngineInstance();
    }

    @Override
    public T getRuleTemplate(String lob) {
        T t = this.ruleEngineFactory.newEngineInstance();
        IRuntimeEnv env = ((IEngineWrapper) t).getRuntimeEnv();
        IRulesRuntimeContext context = RulesRuntimeContextFactory.buildRulesRuntimeContext();
        env.setContext((IRuntimeContext) context);
        context.setLob(lob);
        return t;
    }
}
