package me.anichakra.poc.pilot.framework.instrumentation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.anichakra.poc.pilot.framework.annotation.Event;

/**
 * This is the base class of the InvocationEventHandler that event matching
 * logic for {@link Event} annotated methods, and abstracts the
 * handleInvocationEvent() method.
 * 
 * @author anirbanchakraborty
 *
 */
public abstract class AbstractInvocationEventHandler implements InvocationEventHandler {
	private String[] eventNames;
	private boolean enabled;

	private List<Layer> layers = new ArrayList<>();

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEventNames(String eventNames) {
		this.eventNames = eventNames.replaceAll(" ", "").split(",");
		this.enabled = this.eventNames != null && this.eventNames.length > 0
				&& Arrays.asList(this.eventNames).stream().noneMatch(c -> c.equalsIgnoreCase("none"));
	}

	/**
	 * @return the layers
	 */
	public List<Layer> getLayers() {
		return layers;
	}

	/**
	 * @param layers the layers to set
	 */
	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	protected boolean match(Event event) {
		boolean flag = false;
		if (event != null && event.name() != null && event.name().length > 0 && this.eventNames != null) {
			for (String en : this.eventNames) {
				for (String e : event.name()) {
					if (e.equals(en)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
}
