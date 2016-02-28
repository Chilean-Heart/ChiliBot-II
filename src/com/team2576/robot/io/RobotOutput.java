package com.team2576.robot.io;

import java.util.concurrent.ConcurrentHashMap;

import com.team2576.lib.util.ChiliConstants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;

/**
 * 
 * @author Lucas
 */

public class RobotOutput {
	
	
	enum Shifter {
		HIGH_GEAR,
		LOW_GEAR,
		NEUTRAL,
		PRESERVE
	}
	
	/** 
	 * Unique instance of object.
	 * 
	 * Instancia unica del objeto.
	 */
	private static RobotOutput instance;
	
	public ConcurrentHashMap<String, Double> outputValues;
	
	
	private final CANTalon leftFront, leftMid, leftRear;
	private final CANTalon rightFront, rightMid, rightRear;
	private final DoubleSolenoid shifter;
	
	private final CANTalon intakeDeployer;
	private final VictorSP intakeTube;
	
	private final Talon leftArm, rightArm;
	private final Talon winch;
	
	private final Compressor compressor;
	
	
	private RobotOutput() {
		
		//HashMap object containing output information
		
		outputValues = new ConcurrentHashMap<String, Double>();
		
		outputValues.put(ChiliConstants.iLeftFrontMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iLeftMidMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iLeftRearMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iRightFrontMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iRightMidMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iRightRearMotor, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iGear, ChiliConstants.kZeroValue);
		
		outputValues.put(ChiliConstants.iIntaker, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iIntakePosition, ChiliConstants.kZeroValue);
		
		outputValues.put(ChiliConstants.iLeftArm, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iRightArm, ChiliConstants.kZeroValue);
		outputValues.put(ChiliConstants.iWinch, ChiliConstants.kZeroValue);
		
		//Extra Actuators
		
		compressor = new Compressor();
				
		//ChiliDrive Actuators
		
		leftFront = new CANTalon(ChiliConstants.leftFrontId);
		leftMid = new CANTalon(ChiliConstants.leftMidId);
		leftRear = new CANTalon(ChiliConstants.leftRearId);
		
		rightFront = new CANTalon(ChiliConstants.rightFrontId);
		rightMid = new CANTalon(ChiliConstants.rightMidId);
		rightRear = new CANTalon(ChiliConstants.rightRearId);
		
		shifter = new DoubleSolenoid(ChiliConstants.iShifterA, ChiliConstants.iShifterB);
		
		//ChiliIntake Actuators
		
		intakeDeployer = new CANTalon(ChiliConstants.intakeDeployerId);
		
		intakeTube = new VictorSP(ChiliConstants.iIntakeTube);
		
		//ChiliHanger Actuators
		
		leftArm = new Talon(ChiliConstants.iLeftHanger);
		rightArm = new Talon(ChiliConstants.iRightHanger);
		
		winch = new Talon(ChiliConstants.iWinchMotor);
		
	}
	
	
	
	//Getter de instancia para asegurarse de la existencia global de un solo objeto
	/**
	 * Gets the single instance of RobotOutput.
	 *
	 * @return single instance of RobotOutput
	 */
	public static RobotOutput getInstance() {
		if(instance == null) {
			instance = new RobotOutput();
		}
		return instance;
	}
	
	
	
	
	public void compressorInit() {
		this.compressor.start();
		this.compressor.setClosedLoopControl(true);
	}
	
	
	
	
	private void setLeftFrontDrive(double n) {
		outputValues.put(ChiliConstants.iLeftFrontMotor, n);
		leftFront.set(n);
	}
	
	private void setLeftMiddleDrive(double n) {
		outputValues.put(ChiliConstants.iLeftMidMotor, n);
		leftMid.set(n);
	}
	
	private void setLeftRearDrive(double n) {
		outputValues.put(ChiliConstants.iLeftRearMotor, n);
		leftRear.set(n);
	}
	
	private void setRightFrontDrive(double n) {
		outputValues.put(ChiliConstants.iRightFrontMotor, n);
		rightFront.set(n);
	}
	
	private void setRightMiddleDrive(double n) {
		outputValues.put(ChiliConstants.iRightMidMotor, n);
		rightMid.set(n);
	}
	
	private void setRightRearDrive(double n) {
		outputValues.put(ChiliConstants.iRightRearMotor, n);
		rightRear.set(n);
	}
	
	public void setShifterPosition(Shifter gear) {
		
		if (gear == Shifter.HIGH_GEAR) {
			this.outputValues.put(ChiliConstants.iGear, 1.0);
			this.shifter.set(Value.kForward);
		} 
		else if (gear == Shifter.NEUTRAL) {
			this.outputValues.put(ChiliConstants.iGear, 0.0);
			this.shifter.set(Value.kOff);
		} 
		else if (gear == Shifter.LOW_GEAR) {
			this.outputValues.put(ChiliConstants.iGear, -1.0);
			this.shifter.set(Value.kReverse);
		} 
		else if (gear == Shifter.PRESERVE){
			return;
		}
	}
	
	public void setChiliDrive(double leftSpeed, double rightSpeed, Shifter gear) {
		
		this.setLeftFrontDrive(leftSpeed);
		this.setLeftMiddleDrive(leftSpeed);
		this.setLeftRearDrive(leftSpeed);
		
		this.setRightFrontDrive(rightSpeed);
		this.setRightMiddleDrive(rightSpeed);
		this.setRightRearDrive(rightSpeed);
		
		this.setShifterPosition(gear);
	}
	
	public void setChiliDrive(double leftSpeed, double rightSpeed) {
		this.setChiliDrive(leftSpeed, rightSpeed, Shifter.PRESERVE);
	}
	
	
	
	
	private void setIntaker(double n) {
		outputValues.put(ChiliConstants.iIntaker, n);
		intakeTube.set(n);
	}
	
	public void setIntakeDeployer(double speed) {
		this.outputValues.put(ChiliConstants.iIntakePosition, speed);
		this.intakeDeployer.set(speed);
	}
	
	public void intakeBoulder() {
		this.setIntaker(ChiliConstants.kIntakeBoulders);
	}
	
	public void releaseBoulder() {
		this.setIntaker(ChiliConstants.kReleaseBoulders);
	}
	
	public void disableBoulder() {
		this.setIntaker(ChiliConstants.kZeroValue);
	}
	
	
	
	private void setLeftHanger(double n) {
		outputValues.put(ChiliConstants.iLeftArm, n);
		leftArm.set(n);
	}
	
	private void setRightHanger(double n) {
		outputValues.put(ChiliConstants.iRightArm, n);
		rightArm.set(n);
	}
	
	public void setHangerSpeed(double speed) {
		this.setLeftHanger(speed);
		this.setRightHanger(-1 *speed);
		
	}
	
	public void setWinchSpeed(double speed) {
		this.outputValues.put(ChiliConstants.iWinch, speed);
		this.winch.set(speed);
	}

}
