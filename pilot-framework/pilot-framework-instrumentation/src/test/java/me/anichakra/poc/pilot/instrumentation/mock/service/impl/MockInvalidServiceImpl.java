package me.anichakra.poc.pilot.instrumentation.mock.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockRequest;
import me.anichakra.poc.pilot.framework.instrumentation.mock.model.MockResponse;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockBusinessException;
import me.anichakra.poc.pilot.framework.instrumentation.mock.service.api.MockService;

@SuppressWarnings("unused")
@Component(value="invalid")
public final class MockInvalidServiceImpl implements MockService {

	public MockResponse callServiceMethod(MockRequest request)
			throws MockBusinessException {
		return null;
	}

	public MockResponse callWebServiceMethod(MockRequest request) {
		return null;
	}

	private int variableOne;
	private String variableTwo;
	private int variableThree;
	private final static int X = 10;
	private final static String Y = "ABC";
	private Integer iVariableFour;
	private String iVariableFive = "Test";
	private double variableSix = 1.5;
	private Long variableSeven = 2l;
	private Date date = new Date();
	private Byte objByte;
	private Short objShort;
	private Float objFloat;
	private Double objDouble;
	private Boolean objBoolean;
	private Character objCharacter;
	private byte bt;
	private short st;
	private long lng;
	private float ft;
	private boolean bn;
	private char cr;

	private Byte objByte2 = Byte.MAX_VALUE;
	private Short objShort2 = Short.MAX_VALUE;
	private Float objFloat2 = Float.MAX_VALUE;
	private Double objDouble2 = Double.MAX_VALUE;
	private Boolean objBoolean2 = Boolean.FALSE;
	private Character objCharacter2 = Character.MAX_VALUE;
	private byte bt2;
	private short st2 = 1;
	private long lng2 = 1l;
	private float ft2 = 1.0f;
	private boolean bn2 = false;
	private char cr2 = 'C';

	@Autowired
	@Qualifier("valid")
	private MockService instanceUsedForAutoWiring;

	public int getVariableOne() {
		return variableOne;
	}

	public void setVariableOne(int variableOne) {
		this.variableOne = variableOne;
	}

	public String getVariableTwo() {
		return variableTwo;
	}

	public void setVariableTwo(String variableTwo) {
		this.variableTwo = variableTwo;
	}

	public void setVariableFour(Integer variableFour) {
		this.iVariableFour = variableFour;
	}

	public void setVariableFive(String variableFive) {
		this.iVariableFive = variableFive;
	}

	public void setVariableSeven(Long variableSeven) {
		this.variableSeven = variableSeven;
	}
}
