package me.anichakra.poc.pilot.framework.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anichakra.poc.pilot.framework.annotation.ServiceConfig;

@ServiceConfig
public class SystemConfiguration {
	private List<Internal> internal = new ArrayList<>();
	private List<External> external = new ArrayList<>();

	public static class Internal {

		private String name;

		private Map<String, String> properties = new HashMap<>();

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the properties
		 */
		public Map<String, String> getProperties() {
			return properties;
		}

		/**
		 * @param properties the properties to set
		 */
		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}
	}

	public static class External {
		private String name;

		private Map<String, String> properties = new HashMap<>();

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the properties
		 */
		public Map<String, String> getProperties() {
			return properties;
		}

		/**
		 * @param properties the properties to set
		 */
		public void setProperties(Map<String, String> properties) {
			this.properties = properties;
		}
	}

	/**
	 * @return the external
	 */
	public List<External> getExternal() {
		return external;
	}

	/**
	 * @param external the external to set
	 */
	public void setExternal(List<External> external) {
		this.external = external;
	}

	/**
	 * @return the internal
	 */
	public List<Internal> getInternal() {
		return internal;
	}

	/**
	 * @param internal the internal to set
	 */
	public void setInternal(List<Internal> internal) {
		this.internal = internal;
	}

}
