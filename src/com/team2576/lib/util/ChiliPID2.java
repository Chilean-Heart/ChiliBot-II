package com.team2576.lib.util;

/**
*
* @author Lucas
*/

public class ChiliPID2 {
	
	private double kP, kI, kD;
	//private double eps;
	private double ref, prev_val;
	private double error_integral;
	
	private boolean first_run;
	
	public ChiliPID2(double p, double i, double d, double e) {
		this.kP = p;
		this.kI = i;
		this.kD = d;
		//this.eps = e;
		
		this.ref = 0;
		this.prev_val = 0;
		this.error_integral = 0;
		
		this.first_run = true;
	}
	
	public ChiliPID2(double p, double i, double d) {
		this(p, i, d, 0.0);
	}
	
	public ChiliPID2() {
		this(0.0, 0.0, 0.0, 0.0);
	}

	/**
	 * @param eps the deadband value to set
	 *//*
	public void setEpsilon(double eps) {
		this.eps = eps;
	}*/
	
	public double calcPID(double currentVal) {
		
		if(this.first_run) {
			this.prev_val = currentVal;
			this.first_run = !this.first_run;
		}
		
		double error = this.ref - currentVal;
		//double delta = currentVal - this.prev_val;
		double output = 0;
		
		this.error_integral += error;
		
		output += this.kP * error;
		output += this.kD * (currentVal - this.prev_val);
		output += this.kI * this.error_integral;
		     
		output = ChiliFunctions.clamp_output(output);
		
		this.prev_val = currentVal;
		
		return output;
	}
	
	public double calcPIDSpeed(double currentVal) {
		return 0.0;
	}

}
