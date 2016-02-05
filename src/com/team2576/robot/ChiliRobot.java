
package com.team2576.robot;

import com.team2576.lib.Kapellmeister;
import com.team2576.lib.Logger;
import edu.wpi.first.wpilibj.IterativeRobot;


public class ChiliRobot extends IterativeRobot {
	
	/*
	 * Declaration of manager objects.
	 * 
	 * Declaracion de objectos administradores. 
	 */
	
	Kapellmeister kapellmeister;
	
	
	/*
	 * Declaration of lib objects.
	 * 
	 * Declaracion de objectos lib. 
	 */
	Logger loggy;
	
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	kapellmeister = Kapellmeister.getInstance();
		loggy = Logger.getInstance();

    }
    
    /**
     * Initialization code for autonomous mode should go here.
     *
     */
    public void autonomousInit() {
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }
    
    /**
     * Initialization code for teleop mode should go here.
     *
     */
    public void teleopInit() {
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	
    	/*
    	 * Main loop of teleop. 2 important tasks happen within the while loop:
    	 * a)The Logger registers all input and output states to a CSV log file.
    	 * b)The Kappelmeister updates all subsystems contained within the subsystem vector.
    	 * 
    	 * Loop principal de teleop. Suceden 2 tareas importantes al interior del while:
    	 * a)El Logger registra todos los estados de entradas y salidas a un archivo CSV.
    	 * b)El Kappelmeister actualiza todos los subsistemas contenidos al interior de su vector de subsistemas.
    	 */
    	while(isOperatorControl() && isEnabled()) {
    		loggy.addLog();
    		kapellmeister.conduct();
    	}        
    }
    
    /**
     * Periodic code for disabled mode should go here.
     *
     */
    public void disabledPeriodic() {
        
    }
    
    /**
     * Initialization code for disabled mode should go here.
     *
     */
    public void disabledInit() {    	
    	loggy.closeLog();
    	kapellmeister.silence();        
    }
    
}
