package me.anichakra.poc.pilot.instrumentation.mock.repository;

public interface MockDao {
	Integer callDaoMethod(String string);

	void callMarkIgnoreMethod();
}
