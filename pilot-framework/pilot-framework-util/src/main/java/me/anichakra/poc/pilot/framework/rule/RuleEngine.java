package me.anichakra.poc.pilot.framework.rule;

import java.io.FileNotFoundException;

public interface RuleEngine<T> {

	public static enum Type {
		OPENL_TABLETS_ORG; // TODO: more may come
	}

	public RuleService<T> build(String rulePath, Class<T> templateType) throws FileNotFoundException;

}
