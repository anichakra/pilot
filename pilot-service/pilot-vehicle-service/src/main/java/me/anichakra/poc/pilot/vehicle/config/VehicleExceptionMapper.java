package me.anichakra.poc.pilot.vehicle.config;

import me.anichakra.poc.pilot.framework.annotation.ClassMapper;
import me.anichakra.poc.pilot.framework.validation.ErrorInfo;
import me.anichakra.poc.pilot.framework.validation.ExceptionMapper;

@ClassMapper(classes= {Exception.class})
public class VehicleExceptionMapper implements ExceptionMapper<VehicleErrorInfo>{

	@Override
	public VehicleErrorInfo map(ErrorInfo errorInfo) {
		VehicleErrorInfo vehicleErrorInfo = new VehicleErrorInfo();
		vehicleErrorInfo.setCode(errorInfo.getCode() + "." +"vehicle");
		return vehicleErrorInfo;
	}

}
