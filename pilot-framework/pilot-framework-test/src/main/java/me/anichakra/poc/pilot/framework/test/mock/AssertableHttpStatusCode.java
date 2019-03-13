package me.anichakra.poc.pilot.framework.test.mock;

import org.springframework.lang.Nullable;

public enum AssertableHttpStatusCode {
	OK(200, "OK"),
	CREATED(201, "Created"),
	ACCEPTED(202, "Accepted"),
	NON_AUTHORITATIVE_INFORMATION(203, "Non-Authoritative Information"),
	NO_CONTENT(204, "No Content"),
	RESET_CONTENT(205, "Reset Content"),
	FOUND(302, "Found"),
	TEMPORARY_REDIRECT(307, "Temporary Redirect"),
	PERMANENT_REDIRECT(308, "Permanent Redirect"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"),
	PAYMENT_REQUIRED(402, "Payment Required"),
	FORBIDDEN(403, "Forbidden"),
	NOT_FOUND(404, "Not Found"),
	METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
	NOT_ACCEPTABLE(406, "Not Acceptable"),
	PROXY_AUTHENTICATION_REQUIRED(407, "Proxy Authentication Required"),
	REQUEST_TIMEOUT(408, "Request Timeout"),
	CONFLICT(409, "Conflict"),
	GONE(410, "Gone"),
	LENGTH_REQUIRED(411, "Length Required"),
	PRECONDITION_FAILED(412, "Precondition Failed"),
	PAYLOAD_TOO_LARGE(413, "Payload Too Large"),
	URI_TOO_LONG(414, "URI Too Long"),
	UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
	UNPROCESSABLE_ENTITY(422, "Unprocessable Entity"),
	LOCKED(423, "Locked"),
	FAILED_DEPENDENCY(424, "Failed Dependency"),
	PRECONDITION_REQUIRED(428, "Precondition Required"),
	REQUEST_HEADER_FIELDS_TOO_LARGE(431, "Request Header Fields Too Large"),
	UNAVAILABLE_FOR_LEGAL_REASONS(451, "Unavailable For Legal Reasons"),
	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
	NOT_IMPLEMENTED(501, "Not Implemented"),
	SERVICE_UNAVAILABLE(503, "Service Unavailable"),
	GATEWAY_TIMEOUT(504, "Gateway Timeout"),
	INSUFFICIENT_STORAGE(507, "Insufficient Storage");


	private final int value;

	private final String reasonPhrase;


	AssertableHttpStatusCode(int value, String reasonPhrase) {
		this.value = value;
		this.reasonPhrase = reasonPhrase;
	}


	/**
	 * Return the integer value of this status code.
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return the reason phrase of this status code.
	 */
	public String getReasonPhrase() {
		return this.reasonPhrase;
	}

		/**
	 * Return a string representation of this status code.
	 */
	@Override
	public String toString() {
		return this.value + " " + name();
	}


	/**
	 * Return the enum constant of this type with the specified numeric value.
	 * @param statusCode the numeric value of the enum to be returned
	 * @return the enum constant with the specified numeric value
	 * @throws IllegalArgumentException if this enum has no constant for the specified numeric value
	 */
	public static AssertableHttpStatusCode valueOf(int statusCode) {
		AssertableHttpStatusCode status = resolve(statusCode);
		if (status == null) {
			throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
		}
		return status;
	}

	@Nullable
	public static AssertableHttpStatusCode resolve(int statusCode) {
		for (AssertableHttpStatusCode status : values()) {
			if (status.value == statusCode) {
				return status;
			}
		}
		return null;
	}


}
