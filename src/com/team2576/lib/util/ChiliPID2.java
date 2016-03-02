package com.team2576.lib.util;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;

/**
*
* @author Lucas
*/

public class ChiliPID2 {
	
	private static class ControlTask implements Runnable {
		private ChiliPID2 controller;
		
		public ControlTask(ChiliPID2 controller) {
			this.controller = controller;
		}
		
		@Override
		public void run() {
			controller.calculate();
			
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private ChiliPID controller;
	private SensorBase input;
	private SpeedController output;
	private double kP, kI, kD;
	
	private Thread controlTaskThread;
	
	public ChiliPID2 (double Kp, double Ki, double Kd, double e, SensorBase source, SpeedController motor) {
		this.kP = Kp;
		this.kI = Ki;
		this.kD = Kd;
		this.input = source;
		this.output = motor;
		
		this.controller = new ChiliPID(this.kP, this.kI, this.kD);
		
		this.controlTaskThread = new Thread(new ControlTask(this));
		this.controlTaskThread.setDaemon(true);
		this.controlTaskThread.start();
		
	}
	
	public ChiliPID2 (double Kp, double Ki, double Kd, SensorBase source, SpeedController motor) {
		this(Kp, Ki, Kd, 0.0, source, motor);
	}
	
	public ChiliPID2 (SensorBase source, SpeedController motor) {
		this(0.0, 0.0, 0.0, 0.0, source, motor);
	}
	
	
	
	public void calculate() {
		
		double currentVal;
		
		if (this.input instanceof Encoder) {
			currentVal = ((Encoder) input).get();
		}

		
	}
	
	

}
