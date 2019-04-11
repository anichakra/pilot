package me.anichakra.poc.pilot.framework.exception;

public interface ExceptionMapper <T> {

	T map(ErrorDetails errorDetails);
}
