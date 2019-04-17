package me.anichakra.poc.pilot.framework.instrumentation.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.anichakra.poc.pilot.framework.annotation.Event;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEvent;
import me.anichakra.poc.pilot.framework.instrumentation.InvocationEventHandler;
import me.anichakra.poc.pilot.framework.instrumentation.Layer;

public abstract class AbstractInvocationEventHandler implements InvocationEventHandler<InvocationEvent> {
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
		if (event != null && event.value() != null && this.eventNames != null) {
			for (String en : this.eventNames) {
				for (String e : event.value()) {
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
