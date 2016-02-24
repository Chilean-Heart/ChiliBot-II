package com.team2576.lib.util;

import com.team2576.lib.util.ConstantsBase;

/**
 * The class ChiliConstants. Contains values used throughout the robot code. This avoids the existance of "magic numbers"
 * and allows for a quick modification of any value without having to search through all the different classes. 
 * Different letters before variable names indicate the purpose of the constant.
 * <ul>
 * <li> <b>i</b> is used for indexing within arrays, lists, vectors, etc.</li>
 * <li> <b>k</b> is used to indicate constant values for equations or amounts.</li>
 * </ul>
 * 
 * La clase ChiliConstants. Contiene valores usados por todo el codigo del robot. Esto evita la existencia de 
 * "numeros magicos" y permite modificar rapidamente cualquier valor usado en el codigo sin tener que buscar
 * por todas las clases. Diferentes letras antes de nombres de variables indican propositos distintos para la misma
 * <ul>
 * <li> <b>i</b> es usada para indicar indices de valores dentros de arreglos, listas, vectores, etc.</li>
 * <li> <b>k</b> es usada para indicar valores constantes usados en ecuaciones o cantidades.</li>
 * </ul>
 *
 * @author Lucas
 */

public class ChiliConstants extends ConstantsBase{
	
	//---------------------------------------------//
	//----------------IO CONSTANTS-----------------//
	//---------------------------------------------//
	
	//DriverInput.java-----------------------------//
	public static final byte iXboxMain = 0;
	
	public static final byte iLeftXAxis = 0;
	public static final byte iLeftYAxis = 1;
	public static final byte iLeftTrigger = 2;
	public static final byte iRightTrigger = 3;
	public static final byte iRightXAxis = 4;
	public static final byte iRightYAxis = 5;
	public static final byte iXboxButtons = 6;
	public static final byte iXboxDriveTrigger = 7;
	
	public static final double kAxisThreshold = 0.2;	
	public static final double kYAxisInvert = -1;
	
	
	//---------------------------------------------//
	//---------------------------------------------//
	
	
	
	
	
	
	//---------------------------------------------//
	//-------------SUBSYSTEMS CONSTANTS------------//
	//---------------------------------------------//
	
	//Kapellmeister.java---------------------------//
	public static final byte kSubSystems = 1;
	public static final byte iDummyDrive = 0;

	

	
	
	
	//---------------------------------------------//
	//---------------------------------------------//
	
	
	//---------------------------------------------//
	//----------MISCELLANEOUS CONSTANTS------------//
	//---------------------------------------------//
	
	//Servers--------------------------------------//
	public static final int webUIPort = 5800;
	public static final byte kServers = 2;

	public static final double kMaxBotixSuppliedVoltage = 5.0;
	public static final double kInchToCm = 2.54;
	public static final double kVoltsPerInch = kMaxBotixSuppliedVoltage / 512;
	public static final double kVoltsPerCm = kVoltsPerInch * kInchToCm;
	@Override
	public String getFileLocation() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

	
	
	
	//---------------------------------------------//
	//---------------------------------------------//

	
	

}
