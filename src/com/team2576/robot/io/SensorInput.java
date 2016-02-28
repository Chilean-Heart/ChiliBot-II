package com.team2576.robot.io;

import java.util.HashMap;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.IMAQdxCameraControlMode;
import com.ni.vision.NIVision.Image;

import com.team2576.lib.sensors.ADIS16448_IMU;
import com.team2576.lib.util.ChiliConstants;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.vision.AxisCamera;

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
	
	enum Cameras {
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
	private int camLeft, camRight;
	private int currentSession;
	private Cameras currentCam;
	
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
		cuiRight = new Encoder(ChiliConstants.iRightEncoderA, ChiliConstants.iRightEncoderB, false, EncodingType.k4X);
		intakeAngle = new Encoder(ChiliConstants.iIntakeEncoderA, ChiliConstants.iIntakeEncoderB, false, EncodingType.k4X);
		
		camLeft = NIVision.IMAQdxOpenCamera(ChiliConstants.kCamLeft, IMAQdxCameraControlMode.CameraControlModeController);
		camCenter = new AxisCamera(ChiliConstants.kCamCenter);
		camRight = NIVision.IMAQdxOpenCamera(ChiliConstants.kCamRight, IMAQdxCameraControlMode.CameraControlModeController);
		
		currentSession = camLeft;
		currentCam = Cameras.LEFT_CAM;
		
		NIVision.IMAQdxConfigureGrab(currentSession);
	}
	
	
	
	
	public double getIMUAngle() {
		return this.adIMU.getAngle();
	}
	
	
	
	
	public void changeCamera(Cameras cam) {
		
		if (cam == Cameras.MID_CAM) {
			currentCam = cam;
			return;
		}
		
		NIVision.IMAQdxStopAcquisition(currentSession);
		currentCam = cam;
		
		if (cam == Cameras.LEFT_CAM) currentSession = camLeft;
		else if (cam == Cameras.RIGHT_CAM) currentSession = camRight;
		
		NIVision.IMAQdxConfigureGrab(currentSession);
		
	}
	
	public Image getImage(Cameras cam) {
		
		if (cam != currentCam) this.changeCamera(cam);
		
		Image frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		if (cam == Cameras.MID_CAM) camCenter.getImage(frame);
		else NIVision.IMAQdxGrab(currentSession, frame, 1);
		
		return frame;
	}
	
	public Image getImage() {
		return this.getImage(currentCam);
	}
	
	public void postImage() {
		CameraServer.getInstance().setImage(this.getImage());
	}
	
	public void postImage(Cameras cam) {
		CameraServer.getInstance().setImage(this.getImage(cam));
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
