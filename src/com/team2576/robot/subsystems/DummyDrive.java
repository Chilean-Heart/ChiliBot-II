package com.team2576.robot.subsystems;

import java.util.Arrays;

import com.team2576.lib.Debugger;
import com.team2576.robot.io.DriverInput;
import com.team2576.robot.io.SensorInput;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DummyDrive implements SubComponent {

	private static DummyDrive instance;
	private double timeStamp;

	private Debugger debug;
	
	public static DummyDrive getInstance() {
    	if(instance == null) {
    		instance = new DummyDrive();
    	}
    	return instance;
    }
	
	private DummyDrive() {
		debug = new Debugger(Debugger.Debugs.TESTER, true);
		timeStamp = Timer.getFPGATimestamp();
		
	}
	
	private double[] tankDrive(double left, double right) {
		return new double[]{left, left, right, right};  
	}	
	
	@Override
	public boolean update(DriverInput driver, SensorInput sensor) {
		
		if (Timer.getFPGATimestamp() - timeStamp > 0.5) {
		
			double [] vals = this.tankDrive(driver.getXboxLeftY(), driver.getXboxRightY());
			debug.println(Arrays.toString(vals));
			timeStamp = Timer.getFPGATimestamp();
		}
		
		//ADDED COMMENT LOL
		SmartDashboard.putBoolean("Hall A", sensor.getHallA());
		SmartDashboard.putNumber("Mag Encoder", sensor.getEncoder());
		//SmartDashboard.putNumber("IMU Filtered", sensor.getAngleIMU());
		//SmartDashboard.putNumber("IMU Raw Angle", sensor.getRawAngleIMU());
		//SmartDashboard.putNumber("IMU Raw Z Acceleration", sensor.getRawZIMU());
		SmartDashboard.putNumber("Maxbotix Sensor", sensor.getSensorVoltage());
			
		return false;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
		double [] vals = this.tankDrive(0, 0);
		debug.println("Began disabled");
		debug.println(Arrays.toString(vals));

	}

}
