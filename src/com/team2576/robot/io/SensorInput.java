package com.team2576.robot.io;

import com.team2576.lib.sensors.ChiliIMU;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;

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

	
	
	private final PowerDistributionPanel pdp;
	
	private final DigitalInput hallA;
	
	private final Encoder encoderA;
	
	private final ADXRS450_Gyro adGyro;
	private final ADXL362 adAccel;
	private final ChiliIMU imu;
	
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
		pdp = new PowerDistributionPanel();
		hallA = new DigitalInput(0);
		encoderA = new Encoder(2, 3, false);
		adAccel = new ADXL362(Range.k4G);
		adGyro = new ADXRS450_Gyro();
		imu = new ChiliIMU(adAccel, adGyro);
	}
	
	/**
	 * Gets the battery voltage.
	 *
	 * @return The battery voltage
	 */
	public double getBatteryVoltage() {
		return this.pdp.getVoltage();
	}	
	
	
	public boolean getHallA() {
		return this.hallA.get();
	}
	
	public double getEncoder() {
		return this.encoderA.get();
	}
	
	public double getAngleIMU() {
		return this.imu.getAngle();
	}
	
	public double getRawAngleIMU() {
		return this.imu.getRawAngle();
	}
	
	public double getRawZIMU() {
		return this.imu.getRawZ();
	}

}
