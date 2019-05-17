package me.anichakra.poc.pilot.framework.instrumentation.bytecode;

import java.lang.instrument.Instrumentation;

/**
 * 
 * @author anirbanchakraborty
 *
 */
public class InstrumentationAgent {
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Starting the agent1111");
		inst.addTransformer(new LogClassTransformer());
	}
}
