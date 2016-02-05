package com.team2576.robot.io;

import com.team2576.lib.util.ChiliConstants;
import com.team2576.lib.util.ChiliFunctions;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The Class DriverInput. Administers all inputs from the driver and makes them
 * available via public getter functions. Includes Xbox controller as example.
 * Wrapper functions are used to avoid unsightly lines of code (mainly in classes from
 * com.team2576.robot.subsystems) and implement deadbands and other modifiers more easily.
 * 
 * La Clase DriverInput. Administra todas las entradas del driver y las vuelve disponible 
 * a traves de funciones getter publicas. Incluye controlador Xbox como ejemplo. Funciones
 * "wrapper" (envoltorias) son usadas para evitar largas lineas de codigo (principalmente 
 * en las clases de com.team2576.robot.subsystems) y para implementar modificadores como
 * deadbands de manera mas sencilla.
 *
 * @author Lucas
 */


public class DriverInput {
	
	/** 
	 * Unique instance of object.
	 * 
	 * Instancia unica del objeto.
	 */
	private static DriverInput instance;
	
	/** 
	 * Xbox Controller (Main).
	 */ 
	private final Joystick xbox_controller;
	
	/**
	 * Gets the private static instance of DriverInput.
	 * 
	 * Retorna la instancia privada y estatica de DriverInput.
	 *
	 * @return static instance of DriverInput
	 */
	public static DriverInput getInstance() {
		if(instance == null) {
			instance = new DriverInput();
		}
		return instance;
	}
	
	private DriverInput() {
		xbox_controller = new Joystick(ChiliConstants.iXboxMain);
	}
	
	public double getXboxLeftY() {
		return ChiliFunctions.deadBand(this.xbox_controller.getRawAxis(ChiliConstants.iLeftYAxis), ChiliConstants.kAxisThreshold) * ChiliConstants.kYAxisInvert;
	}
	
	public double getXboxLeftX() {
		return ChiliFunctions.deadBand(this.xbox_controller.getRawAxis(ChiliConstants.iLeftXAxis), ChiliConstants.kAxisThreshold);
	}
	
	public double getXboxRightY() {
		return ChiliFunctions.deadBand(this.xbox_controller.getRawAxis(ChiliConstants.iRightYAxis), ChiliConstants.kAxisThreshold) * ChiliConstants.kYAxisInvert;
	}
	
	public double getXboxRightX() {
		return ChiliFunctions.deadBand(this.xbox_controller.getRawAxis(ChiliConstants.iRightXAxis), ChiliConstants.kAxisThreshold);
	}
	
	public double getXboxLeftTrigger() {
		return this.xbox_controller.getRawAxis(ChiliConstants.iLeftTrigger);
	}
	
	public double getXboxRightTrigger() {
		return this.xbox_controller.getRawAxis(ChiliConstants.iRightTrigger);
	}
	
	public boolean getXboxButton(int button_code) {
		return this.xbox_controller.getRawButton(button_code);
	}

}
