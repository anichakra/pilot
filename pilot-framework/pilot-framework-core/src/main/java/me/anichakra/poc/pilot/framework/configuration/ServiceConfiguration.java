package me.anichakra.poc.pilot.framework.configuration;

import javax.validation.constraints.NotNull;

/**
 * The base class for a configuration bean. This is a {@link ServiceConfig} annotated class.
 * @author anirbanchakraborty
 *
 */
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
