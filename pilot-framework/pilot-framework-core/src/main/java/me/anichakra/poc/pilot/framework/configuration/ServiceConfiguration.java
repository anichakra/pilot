package me.anichakra.poc.pilot.framework.configuration;

import javax.validation.constraints.NotNull;

import me.anichakra.poc.pilot.framework.annotation.ServiceConfig;

@ServiceConfig
public abstract class ServiceConfiguration {
		@NotNull
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

}
