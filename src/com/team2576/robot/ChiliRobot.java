package com.team2576.robot;

import com.team2576.lib.Kapellmeister;
import com.team2576.lib.Logger;
import com.team2576.lib.sensors.ChiliIMU;
import com.team2576.lib.util.ChiliConstants;
import com.team2576.robot.subsystems.ChiliIntake;
import com.team2576.robot.subsystems.DummyDrive;

import java.io.IOException;

import com.team2576.lib.ChiliHTTPServer;
import com.team2576.lib.ChiliServerManager;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;


public class ChiliRobot extends IterativeRobot {
	
	/*
	 * Declaration of manager objects.
	 * 
	 * Declaracion de objectos administradores. 
	 */
	Kapellmeister kapellmeister;
	ChiliServerManager serverManager;
	
	/*
	 * Declaration of lib objects.
	 * 
	 * Declaracion de objectos lib. 
	 */
	Logger loggy;
	ChiliHTTPServer httpServer;
	
	/*
	 * Declaration of subsystems.
	 * 
	 * Declaracion de subsistemas.
	 */
	DummyDrive drive; 
	
	ChiliIntake intake;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	
    	Timer.delay(1);
    	
    	kapellmeister = Kapellmeister.getInstance();
		loggy = Logger.getInstance();
		drive = DummyDrive.getInstance();
		serverManager = ChiliServerManager.getInstance();
		intake = ChiliIntake.getInstance();
		
		try {
			httpServer = new ChiliHTTPServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		kapellmeister.addTask(drive, ChiliConstants.iDummyDrive);
		serverManager.addServer(httpServer);
		serverManager.initializeServers();
		
		
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
    	//Open log file
    	loggy.openLog();    	
        
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
