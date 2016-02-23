package edu.wpi.first.wpilibj;

public class PowerDistributionPanel {
	static PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	public static double getCurrent(){
	double current = pdp.getCurrent();
	return current;
	}
}
