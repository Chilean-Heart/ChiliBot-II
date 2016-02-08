package com.team2576.lib.sensors;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Timer;

public class ChiliIMU {
	
	private final ADXL362 accel;
	private final ADXRS450_Gyro gyro;
	private double prev_time;
	private final double alpha = 0.98;
	private final double beta = 1 - this.alpha;
	private double theta = 0, raw_theta = 0, raw_z = 0;
	
	private static class FilterTask implements Runnable {
	    private ChiliIMU imu;
	    
	    public FilterTask(ChiliIMU imu) {
	    	this.imu = imu;
	    }

	    @Override
	    public void run() {
	    	while (true) {
		    	imu.calculate();
		    	
		    	try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}
	    }
	}
	
	private Thread m_task;
	
	public ChiliIMU(ADXL362 accel, ADXRS450_Gyro gyro) {
		this.accel = accel;
		this.gyro = gyro;
		
		this.prev_time = Timer.getFPGATimestamp();		
		
	    this.calibrate();
	    
	    this.theta = this.gyro.getAngle();
	    
	    this.m_task = new Thread(new FilterTask(this));
	    this.m_task.setDaemon(true);
	    this.m_task.start();
	}
	
	public void calibrate() {
		this.gyro.calibrate();
	}
	
	public void calculate () {
		
		double dt;
		double sample_time = Timer.getFPGATimestamp();
		
        synchronized (this) {
        	dt = sample_time - this.prev_time;
        	this.prev_time = sample_time;
        }
        
        double theta_k_1;
        
        synchronized (this) {
			theta_k_1 = this.theta;
		}
        
        double theta_k = this.alpha * (theta_k_1 + this.gyro.getAngle() * dt)+ this.beta * this.accel.getZ() ;
        
        synchronized (this) {
			this.theta = theta_k;
			this.raw_theta = this.gyro.getAngle();
			this.raw_z = this.accel.getZ();
		}        

	}
	
	public synchronized double getAngle() {
		return this.theta;
	}
	
	public synchronized double getRawAngle() {
		return this.raw_theta;
	}
	
	public synchronized double getRawZ() {
		return this.raw_z;
	}

}
