package com.team2576.robot.io;

import java.util.HashMap;

import com.team2576.lib.sensors.ADIS16448_IMU;
import com.team2576.lib.util.ChiliConstants;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

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
	
	private final Encoder cuiLeft, cuiRight;
	
	private final ADIS16448_IMU adIMU;
	
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
		adIMU = new ADIS16448_IMU();
		cuiLeft = new Encoder(ChiliConstants.iLeftEncoderA, ChiliConstants.iLeftEncoderB, false, EncodingType.k4X);
		cuiRight = new Encoder(ChiliConstants.iRightEncoderA, ChiliConstants.iRIghtEncoderB, false, EncodingType.k4X);
	}
	
	public double getBatteryVoltage() {
		return this.pdp.getVoltage();
	}		
	
	public double getCurrentAtChannel(int channel) {
		return this.pdp.getCurrent(channel);
	}
	
	public HashMap<String, Double> getDriveCurrents() {
		
		HashMap<String, Double> currents = new HashMap<String, Double>();
		
		currents.put(ChiliConstants.iLeftFrontMotor, this.getCurrentAtChannel(ChiliConstants.iLeftFrontPDPChannel));
		currents.put(ChiliConstants.iLeftMidMotor, this.getCurrentAtChannel(ChiliConstants.iLeftMidPDPChannel));
		currents.put(ChiliConstants.iLeftRearMotor, this.getCurrentAtChannel(ChiliConstants.iLeftRearPDPChannel));
		
		currents.put(ChiliConstants.iRightFrontMotor, this.getCurrentAtChannel(ChiliConstants.iRightFrontPDPChannel));
		currents.put(ChiliConstants.iRightMidMotor, this.getCurrentAtChannel(ChiliConstants.iRightMidPDPChannel));
		currents.put(ChiliConstants.iRightRearMotor, this.getCurrentAtChannel(ChiliConstants.iRightRearPDPChannel));
		
		return currents;
		
	}
	
	public void initEncoders() {
		this.cuiLeft.setMaxPeriod(ChiliConstants.kEncoderMaxPeriod);
		this.cuiRight.setMaxPeriod(ChiliConstants.kEncoderMaxPeriod);
		
		this.cuiLeft.setDistancePerPulse(ChiliConstants.kEncoderDistPerPulse);
		this.cuiRight.setDistancePerPulse(ChiliConstants.kEncoderDistPerPulse);
		
		this.cuiLeft.reset();
		this.cuiRight.reset();
	}
	
	public void resetEncoders() {
		this.cuiLeft.reset();
		this.cuiRight.reset();
	}
	
	public double getIMUAngle() {
		return this.adIMU.getAngle();
	}
	
	public double getLeftCount() {
		return this.cuiLeft.get();
	}
	
	public double getRightCount() {
		return this.cuiRight.get();
	}

	public double getLeftSpeed() {
		return this.cuiLeft.getRate();
	}
	
	public double getRightSpeed() {
		return this.cuiRight.getRate();
	}
	
}
