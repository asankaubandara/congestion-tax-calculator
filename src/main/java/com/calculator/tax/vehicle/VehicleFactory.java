package com.calculator.tax.vehicle;

import com.calculator.tax.services.VehiclesTypes;

public class VehicleFactory {
	
	public Vehicle getVehicle(String vehicleType)
    {
        if (vehicleType == null || vehicleType.isEmpty())
            return null;
        if (VehiclesTypes.CAR.getType().equalsIgnoreCase(vehicleType)) {
            return new Car();
        }
        else if (VehiclesTypes.MOTORBIKE.getType().equals(vehicleType)) {
            return new Motorbike();
        }
        else if (VehiclesTypes.TRACTOR.getType().equals(vehicleType)) {
            return new Tractor();
        }
        else if (VehiclesTypes.EMERGENCY.getType().equals(vehicleType)) {
            return new Emergency();
        }
        else if (VehiclesTypes.DIPLOMAT.getType().equals(vehicleType)) {
            return new Diplomat();
        }
        else if (VehiclesTypes.FOREIGN.getType().equals(vehicleType)) {
            return new Foreign();
        }
        else if (VehiclesTypes.MILITARY.getType().equals(vehicleType)) {
            return new Military();
        }
        return null;
    }

}
