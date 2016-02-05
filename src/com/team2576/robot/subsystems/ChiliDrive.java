package com.team2576.robot.subsystems;

/**
 * 
 * @author Lucas, To be determined
 * 
 */

import com.team2576.lib.Debugger;
import com.team2576.lib.util.ChiliFunctions;
import com.team2576.robot.io.*;

public class ChiliDrive implements SubComponent{
	
	private static ChiliDrive instance;
	
	@SuppressWarnings("unused")
	private Debugger debug;
	
	public static ChiliDrive getInstance() {
    	if(instance == null) {
    		instance = new ChiliDrive();
    	}
    	return instance;
    }
	
	private ChiliDrive() {
		debug = new Debugger(Debugger.Debugs.MESSENGER, true);
	}
	
	@SuppressWarnings("unused")
	private double[] arcadeDrive(double forward, double rotation) {
		double left = forward + rotation;
		double right = forward - rotation;
		
		double[] forces = {left, left, right, right};
		forces = ChiliFunctions.normalize(forces);
		
		return forces;
	}
	
	@SuppressWarnings("unused")
	private double[] tankDrive(double left, double right) {
		return new double[]{left, left, right, right};  
	}
	
	@SuppressWarnings("unused")
	private double[] mecanumDrive(double x, double y, double rotation, double gyroAngle, 
    		double gyroDrift, double accelXDrift, double accelYDrift) {
		return null;
	}	
	
	
	/**
	 * TODO
	 * <ul>
	 * <li>Implement true/false return value</li>
	 * <li>Logger friendly values</li>
	 * <li>Implement the following drive modes:
	 * 	<ul> 
	 * 	<li>Arcade Drive</li>
	 * 	<li>FPS Drive</li>
	 * 	<li>Tank Drive</li>
	 * 	<li>Mecanum Arcade Drive</li>
	 * 	<li>Mecanum FPS Drive</li>
	 * 	<li>Mecanum Tank Drive</li>
	 * 	<li>"Intelligent" Chili Drive for WCD w/3 CIM Ball Shifter with:
	 * 		<ul>
	 * 		<li>Current draw sensing and emergency drive shutdown</li>
	 * 		<li>Driver assist for acceleration, speed control and gyro assistance</li>
	 * 		<li>Automatic gear shifter based on encoder feedback and current draw</li>
	 * 		</ul>
	 * 	</li>
	 * </li> 
	 * </ul>
	 */
	public boolean update(DriverInput driver, SensorInput sensor) {
		return false;
	}

	public void disable() {
				
	} 

}
