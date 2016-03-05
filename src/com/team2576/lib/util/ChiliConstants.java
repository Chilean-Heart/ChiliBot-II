package com.team2576.lib.util;

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

public class ChiliConstants {
	
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
	
	//RobotOutput.java-----------------------------//
	public static final String iLeftFrontMotor = "LeftFront";
	public static final String iLeftMidMotor = "LeftMid";
	public static final String iLeftRearMotor = "LeftRear";
	public static final String iRightFrontMotor = "RightFront";
	public static final String iRightMidMotor = "RightMid";
	public static final String iRightRearMotor = "RightRear";
	public static final String iGear = "Gear";
	
	public static final String iIntaker = "IntakeInput";
	public static final String iIntakePosition = "IntakeDeployer";
	
	public static final String iLeftArm = "LeftArm";
	public static final String iRightArm = "RightArm";
	public static final String iWinch = "Winch";
	
	public static final double kZeroValue = 0;
	
	//SensorInput.java-----------------------------//
	public static final String iLeftEncoderCount = "LeftTicks";
	public static final String iLeftEncoderSpeed = "LeftSpeed";
	public static final String iLeftEncoderDistance = "LeftDist";
	public static final String iRightEncoderCount = "RightTicks";
	public static final String iRightEncoderSpeed = "RightSpeed";
	public static final String iRightEncoderDistance = "RightDist";
	
	public static final String iLeftFrontMotorCurrent = "LeftFrontCurrent";
	public static final String iLeftMidMotorCurrent = "LeftMidCurrent";
	public static final String iLeftRearMotorCurrent = "LeftRearCurrent";
	public static final String iRightFrontMotorCurrent = "RightFrontCurrent";
	public static final String iRightMidMotorCurrent = "RightMidCurrent";
	public static final String iRightRearMotorCurrent = "RightRearCurrent";
	
	public static final String iIMUAngle = "IMUAngle";
	public static final String iBatteryVoltage = "BatteryVoltage";
	public static final String iTotalCurrent = "TotalCurrent";
	
	public static final int iLeftFrontPDPChannel = 0;
	public static final int iLeftMidPDPChannel = 1;
	public static final int iLeftRearPDPChannel = 2;
	
	public static final int iRightFrontPDPChannel = 3;
	public static final int iRightMidPDPChannel = 4;
	public static final int iRightRearPDPChannel = 5;
	
	public static final int iLeftEncoderA = 0;
	public static final int iLeftEncoderB = 1;
	public static final int iRightEncoderA = 2;
	public static final int iRightEncoderB = 3;
	public static final int iIntakeEncoderA = 4;
	public static final int iIntakeEncoderB = 5;
	
	private static final double kEncoderPulsesPerRev = 512;
	private static final double kEncoderPulsesPerRevAtOutputStage = kEncoderPulsesPerRev * 3;
	private static final double kWheelPerimeter = 2 * Math.PI * 4.1;
	private static final double kIntakePulsesPerRev = 71 * 7;
	
	public static final double kEncoderMaxPeriod = 0.3;
	public static final double kEncoderDistPerPulse = kWheelPerimeter / kEncoderPulsesPerRevAtOutputStage;
	public static final double kIntakeDegsPerPulse = 360 / kIntakePulsesPerRev;

	public static final String kCamLeft = "cam1";
	public static final String kCamRight = "cam0";
	public static final String kCamCenter = "axis-00408cef43ee.local";
	
	//---------------------------------------------//
	//---------------------------------------------//
	
		
	
	//---------------------------------------------//
	//-------------SUBSYSTEMS CONSTANTS------------//
	//---------------------------------------------//
	
	//Kapellmeister.java---------------------------//
	public static final byte kSubSystems = 3;
	public static final byte iDummyDrive = 0;
	public static final byte iChiliIntake = 1;
	public static final byte iChiliHanger = 2;
	
	//ChiliDrive.java------------------------------//
	public static final int leftFrontId = 21;
	public static final int leftMidId = 22;
	public static final int leftRearId = 23;
	public static final int rightFrontId = 24;
	public static final int rightMidId = 25;
	public static final int rightRearId = 26;
		
	public static final int iShifterA = 0;
	public static final int iShifterB = 1;
	
	//ChiliIntake.java-----------------------------//
	public static final int intakeDeployerId = 20;
	public static final int iIntakeTube = 0;
	
	public static final double kIntakeBoulders = 1.0;
	public static final double kReleaseBoulders = -1.0;
	
	//ChiliHanger.java-----------------------------//
	public static final int iLeftHanger = 1;
	public static final int iRightHanger = 2;
	public static final int iWinchMotor = 3;
	
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
	
	//ChiliPID2.java-------------------------------//
	public static final int kPIDFrequency = 20;

	public static final String iDateTimeStamp = "DateTime";

	public static final boolean kCustomVisionDashboard = false;

	public static final boolean kPDPConnected = false;
	
	
	
	
	//---------------------------------------------//
	//---------------------------------------------//

	
	

}
