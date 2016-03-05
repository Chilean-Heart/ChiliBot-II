package com.team2576.robot.io;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import com.ni.vision.NIVision;
import com.ni.vision.VisionException;
import com.ni.vision.NIVision.Image;

import com.team2576.lib.sensors.ADIS16448_IMU;
import com.team2576.lib.util.ChiliConstants;
import com.team2576.lib.util.Informable;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.vision.AxisCamera;
import edu.wpi.first.wpilibj.vision.USBCamera;

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

public class SensorInput implements Informable {
	
	public enum Cameras {
		LEFT_CAM,
		MID_CAM,
		RIGHT_CAM
	}
	
	/** 
	 * Unique instance of object.
	 * 
	 * Instancia unica del objeto.
	 */
	private static SensorInput instance;
	
	private final PowerDistributionPanel pdp;
	
	private final Encoder cuiLeft, cuiRight;
	
	private final Encoder intakeAngle;
	
	private final ADIS16448_IMU adIMU;
	
	private AxisCamera camCenter;
	private USBCamera camLeft, camRight;
	
	public Map<String, Double> inputValues;
	public boolean abort_vision;


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
		
		this.inputValues = Collections.synchronizedMap(new LinkedHashMap<String, Double>());
		
		pdp = new PowerDistributionPanel();
		adIMU = new ADIS16448_IMU();
		cuiLeft = new Encoder(ChiliConstants.iLeftEncoderA, ChiliConstants.iLeftEncoderB, false, EncodingType.k4X);
		cuiRight = new Encoder(ChiliConstants.iRightEncoderA, ChiliConstants.iRightEncoderB, false, EncodingType.k4X);
		intakeAngle = new Encoder(ChiliConstants.iIntakeEncoderA, ChiliConstants.iIntakeEncoderB, false, EncodingType.k4X);
		
		camLeft = new USBCamera(ChiliConstants.kCamLeft);
		camCenter = new AxisCamera(ChiliConstants.kCamCenter);
		camRight = new USBCamera(ChiliConstants.kCamRight);
		
		camLeft.openCamera();
		//camRight.openCamera();
		camLeft.startCapture();
		//camRight.startCapture();	
		
		abort_vision = false;
		
		this.initEncoders();
	}
	
	public double getIMUAngle() {
		return this.adIMU.getAngle();
	}
	
	public Image[] getImages() {
		Image[] images = new Image[3];
		
		for (int i = 0; i < images.length; i++) {
			images[i] = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		}
		
		images[0] = this.getImage(Cameras.LEFT_CAM);
		images[1] = this.getImage(Cameras.MID_CAM);
		images[2] = this.getImage(Cameras.RIGHT_CAM);
		
		return images;
	}
	
	public Image getImage(Cameras cam) {
		
		Image image = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		if (cam != Cameras.LEFT_CAM) {
			return image;
		}
		
		try {
			if (cam == Cameras.LEFT_CAM) {
				//this.camLeft.startCapture();
				this.camLeft.getImage(image);
			} else if (cam == Cameras.MID_CAM) {
				this.camCenter.getImage(image);
			} else if (cam == Cameras.RIGHT_CAM) {
				//this.camRight.startCapture();
				this.camRight.getImage(image);
			}
		} catch (VisionException ve) {
			ve.printStackTrace();
			abort_vision = true;
		}
		
		//this.camLeft.stopCapture();
		//this.camRight.stopCapture();
		
		return image;
	}
	
	public double getBatteryVoltage() {
		return ChiliConstants.kPDPConnected ? this.pdp.getVoltage() : 0;
	}		
	
	public double getCurrentAtChannel(int channel) {
		return ChiliConstants.kPDPConnected ? this.pdp.getCurrent(channel) : 0;
	}
	
	public double getTotalCurrentDraw() {
		return ChiliConstants.kPDPConnected ? this.pdp.getTotalCurrent() : 0;
	}
	
	public LinkedHashMap<String, Double> getDriveCurrents() {
		
		LinkedHashMap<String, Double> currents = new LinkedHashMap<String, Double>();
		
		currents.put(ChiliConstants.iLeftFrontMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iLeftFrontPDPChannel));
		currents.put(ChiliConstants.iLeftMidMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iLeftMidPDPChannel));
		currents.put(ChiliConstants.iLeftRearMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iLeftRearPDPChannel));
		
		currents.put(ChiliConstants.iRightFrontMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iRightFrontPDPChannel));
		currents.put(ChiliConstants.iRightMidMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iRightMidPDPChannel));
		currents.put(ChiliConstants.iRightRearMotorCurrent, this.getCurrentAtChannel(ChiliConstants.iRightRearPDPChannel));
		
		return currents;
	}
	
	private void initEncoders() {
		this.cuiLeft.setMaxPeriod(ChiliConstants.kEncoderMaxPeriod);
		this.cuiRight.setMaxPeriod(ChiliConstants.kEncoderMaxPeriod);
		this.intakeAngle.setMaxPeriod(ChiliConstants.kEncoderMaxPeriod);
		
		this.cuiLeft.setDistancePerPulse(ChiliConstants.kEncoderDistPerPulse);
		this.cuiRight.setDistancePerPulse(ChiliConstants.kEncoderDistPerPulse);
		this.intakeAngle.setDistancePerPulse(ChiliConstants.kIntakeDegsPerPulse);
		
		this.cuiLeft.reset();
		this.cuiRight.reset();
		this.intakeAngle.reset();
	}
	
	public void resetEncoders() {
		this.cuiLeft.reset();
		this.cuiRight.reset();
		this.intakeAngle.reset();
	}
	
	public void resetDriveEncoders() {
		this.cuiLeft.reset();
		this.cuiRight.reset();
	}
	
	public void resetIntakeEncoder() {
		this.intakeAngle.reset();
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
	
	public double getLeftDist() {
		return this.cuiLeft.getDistance();
	}
	
	public double getRightDist() {
		return this.cuiRight.getDistance();
	}
	
	@Override
	public Map<String, Double> inform() {
		
		Map<String, Double> driveInfo = this.getDriveCurrents();
		
		this.inputValues.putAll(driveInfo);
		
		this.inputValues.put(ChiliConstants.iLeftEncoderCount, this.getLeftCount());
		this.inputValues.put(ChiliConstants.iLeftEncoderSpeed, this.getLeftSpeed());
		this.inputValues.put(ChiliConstants.iLeftEncoderDistance, this.getLeftDist());
		
		this.inputValues.put(ChiliConstants.iRightEncoderCount, this.getRightCount());
		this.inputValues.put(ChiliConstants.iRightEncoderSpeed, this.getRightSpeed());
		this.inputValues.put(ChiliConstants.iRightEncoderDistance, this.getRightDist());
		
		this.inputValues.put(ChiliConstants.iIMUAngle, this.getIMUAngle());
		this.inputValues.put(ChiliConstants.iBatteryVoltage, this.getBatteryVoltage());
		this.inputValues.put(ChiliConstants.iTotalCurrent, this.getTotalCurrentDraw());
		
		return this.inputValues;
	}
	
}
