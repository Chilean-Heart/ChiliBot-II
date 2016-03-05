package com.team2576.lib.util;

import com.team2576.lib.sensors.ADIS16448_IMU;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
*
* @author Lucas
*/

public class ChiliPID2 {
	
	private class ControlTask implements Runnable {
		private ChiliPID2 controller;
		
		public ControlTask(ChiliPID2 controller) {
			this.controller = controller;
		}
		
		@Override
		public void run() {
			while (true) {
				
				try {
					controller.calculate();
				} catch (Exception ePID) {
					ePID.printStackTrace();
				}
				
				try {
					Thread.sleep(ChiliConstants.kPIDFrequency);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ChiliPID controller;
	private SensorBase input;
	private SpeedController output;
	private double kP, kI, kD, e;
	private int currentChannel;
	private boolean isSpeedController, isStopped;
	private double reference;
	
	private Thread controlTaskThread;
	
	public ChiliPID2 (double Kp, double Ki, double Kd, double e, double ref, SensorBase source, SpeedController motor, int motorCurrent) {
		this.kP = Kp;
		this.kI = Ki;
		this.kD = Kd;
		this.e = e;
		
		this.currentChannel = motorCurrent;
		this.input = source;
		this.output = motor;
		
		this.isSpeedController = false;
		this.isStopped = false;
		
		this.controller = new ChiliPID(this.kP, this.kI, this.kD, this.e);
		this.controller.setReference(ref);
		
		this.controlTaskThread = new Thread(new ControlTask(this));
		this.controlTaskThread.setDaemon(true);
		this.controlTaskThread.start();
		
	}
	
	public ChiliPID2 (double Kp, double Ki, double Kd, SensorBase source, SpeedController motor, int motorCurrent) {
		this(Kp, Ki, Kd, 0.0, 0.0, source, motor, motorCurrent);
	}
	
	public ChiliPID2 (SensorBase source, SpeedController motor, int motorCurrent) {
		this(0.0, 0.0, 0.0, 0.0, 0.0, source, motor, motorCurrent);
	}
	
	public void setSpeedControl(boolean enableSpeed) throws Exception {
		if (input instanceof Encoder) {
			this.isSpeedController = enableSpeed;
		} else {
			throw new Exception("Sensor type does not allow speed control");
		}
	}
	
	public void stop() {
		this.isStopped = true;
	}
	
	public void start() {
		this.isStopped = false;
	}
	
	public void setReference(double ref) {
		this.reference = ref;
		this.controller.setReference(this.reference);
	}
	
	public double getReference() {
		return this.controller.getReference();
	}
	
	public void calculate() throws Exception {
		
		double currentVal, outputVal;
		
		// Obtain current value
		if (this.input instanceof Encoder) {
			if (this.isSpeedController) {
				currentVal = ((Encoder) this.input).getRate();
			} else {
				currentVal = ((Encoder) this.input).get();
			}
		} else if (this.input instanceof Ultrasonic) {
			currentVal = ((Ultrasonic) this.input).pidGet();
		} else if (this.input instanceof ADIS16448_IMU) {
			currentVal = ((ADIS16448_IMU) this.input).getAngle();
		} else if (this.input instanceof PowerDistributionPanel) {
			currentVal = ((PowerDistributionPanel) this.input).getCurrent(this.currentChannel);
		} else {
			throw new Exception("Sensor is not of compatible type");
		}
		
		// Calculate PID output
		if (isSpeedController) {
			outputVal = this.controller.calcPIDInc(currentVal);
		} else {
			outputVal = this.controller.calcPID(currentVal);
		}
		
		// Set PID output
		if (isStopped) {
			outputVal = 0;
		} else {
			outputVal = ChiliFunctions.clamp_output(outputVal);
		}
		this.output.set(outputVal);
		
	}
}
