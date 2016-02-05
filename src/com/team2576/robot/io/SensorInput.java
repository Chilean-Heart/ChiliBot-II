package com.team2576.robot.io;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * The Class SensorInput. Similar to DriverInput, but instead in charge of overlooking all sensors on the robot.
 * It includes the RoboRIO's build in accelerometer, the Power Distribution Panel 
 * (with the temperature and current draw sensors) and any other sensors required.
 * 
 * La Clase SensorInput. Similar a DriverInput, pero en vez de se encarga de supervisar 
 * todos los sensores en el robot. Inluye el accelerometro integrado del RoboRIO, el Panel 
 * de Distribucion de Poder (con sensores de temperatura y corriente) y cualquier sensor necesario.
 *
 * @author Lucas
 */

public class SensorInput {
	
	/** 
	 * Unique instance of object.
	 * 
	 * Instancia unica del objeto.
	 */
	private static SensorInput instance;
	
	/**  
	 * Internal Accelerometer
	 */
	private final BuiltInAccelerometer accel;
	
	/**
	 * Power Distribution Panel
	 */
	private final PowerDistributionPanel pdp;
	
	/**
	 * Generates a single, static instance of the SensorInput class to allow universal and unique access to all sensors
	 *
	 * @return single instance of SensorInput
	 */
	public static SensorInput getInstance() {
		if(instance == null) {
			instance = new SensorInput();
		}
		return instance;
	}
	
	private SensorInput() {
		accel = new BuiltInAccelerometer(Accelerometer.Range.k2G);
		pdp = new PowerDistributionPanel();
	}
	
	/**
	 * Gets the battery voltage.
	 *
	 * @return The battery voltage
	 */
	public double getBatteryVoltage() {
		return this.pdp.getVoltage();
	}	
	
	/**
	 * Gets the internal accelerometer's X axis.
	 *
	 * @return x axis value
	 */
	public double getAccelX() {
		return this.accel.getX();
	}
	
	/**
	 * Gets the internal accelerometer's Y axis.
	 *
	 * @return y axis value
	 */
	public double getAccelY() {
		return this.accel.getY();
	}
	
	/**
	 * Gets the internal accelerometer's Z axis.
	 *
	 * @return z axis value
	 */
	public double getAccelZ() {
		return this.accel.getZ();
	}

}
