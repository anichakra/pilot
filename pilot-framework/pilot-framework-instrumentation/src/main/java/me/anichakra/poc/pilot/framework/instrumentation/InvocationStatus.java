package me.anichakra.poc.pilot.framework.instrumentation;

import org.springframework.lang.Nullable;

public enum InvocationStatus {

	/**
	 * Started
	 */
	Started("S"),
	/**
	 * Completed
	 */
	Completed("C"),
	/**
	 * Failed
	 */
	Failed("F");
	/**
	 * Return the integer value of this status code.
	 */
	private final String statusCode;

	InvocationStatus(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return this.statusCode;
	}

	/**
	 * Resolve the given status code to an {@code HttpStatus}, if possible.
	 * 
	 * @param statusCode the HTTP status code (potentially non-standard)
	 * @return the corresponding {@code HttpStatus}, or {@code null} if not found
	 * @since 5.0
	 */
	@Nullable
	public static InvocationStatus resolve(String statusCode) {
		for (InvocationStatus status : values()) {
			if (status.statusCode == statusCode) {
				return status;
			}
		}
		return null;
	}

}
