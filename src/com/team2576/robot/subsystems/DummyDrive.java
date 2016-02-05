package com.team2576.robot.subsystems;

import com.team2576.lib.Debugger;
import com.team2576.robot.io.DriverInput;
import com.team2576.robot.io.SensorInput;

public class DummyDrive implements SubComponent {

private static DummyDrive instance;
	
	@SuppressWarnings("unused")
	private Debugger debug;
	
	public static DummyDrive getInstance() {
    	if(instance == null) {
    		instance = new DummyDrive();
    	}
    	return instance;
    }
	
	private DummyDrive() {
		debug = new Debugger(Debugger.Debugs.TESTER, true);
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
