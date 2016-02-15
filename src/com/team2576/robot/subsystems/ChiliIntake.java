package com.team2576.robot.subsystems;

import com.team2576.robot.io.DriverInput;
import com.team2576.robot.io.SensorInput;

public class ChiliIntake implements SubComponent {


	private static ChiliIntake instance; 
	
	private ChiliIntake() {
		
	}
	
	public static ChiliIntake getInstance() {
		if (instance == null) {
				instance = new ChiliIntake();
		}
		return instance;
	}

	@Override
	public boolean update(DriverInput driver, SensorInput sensor) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub

	}

}
