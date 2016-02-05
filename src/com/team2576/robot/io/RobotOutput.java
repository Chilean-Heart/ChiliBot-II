package com.team2576.robot.io;

/**
 * 
 * @author Lucas
 */

public class RobotOutput {
	
	/** 
	 * Unique instance of object.
	 * 
	 * Instancia unica del objeto.
	 */
	private static RobotOutput instance;
	
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

}
