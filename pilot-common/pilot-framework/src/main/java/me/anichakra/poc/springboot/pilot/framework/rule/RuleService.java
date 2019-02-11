package me.anichakra.poc.springboot.pilot.framework.rule;

public interface RuleService<T> {
    public void loadRuleTemplate(String templatePath, Class<T> clazz);

    public T getRuleTemplate();

    public T getRuleTemplate(String parameter);
}

