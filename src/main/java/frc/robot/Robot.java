package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  // PWM Channels
  public final int kLeftFrontChannel = 13;
  public final int kLeftRearChannel = 14;
  public final int kRightFrontChannel = 11;
  public final int kRightRearChannel = 12;
  public final int joystickPort = 0;
  public final int rightUltrasonicPort = 1;
  public final int rearUltrasonicPort = 0;
  public final int shooter1Port = 15;
  public final int shooter2Port = 15;

  // Driver Station Ports
  public final int leftStick_X = 0;
  public final int leftStick_Y = 1;
  public final int rightStick_X = 4;
  public final int rightStick_Y = 5;
  public final int trigger_L = 2;
  public final int trigger_R = 3;
  public final int Btn_A = 1;
  public final int Btn_B = 2;
  public final int Btn_X = 3;
  public final int Btn_Y = 4;
  public final int Btn_LB = 5;
  public final int Btn_RB = 6;
  public final int Btn_LS = 9;  
  public final int Btn_RS = 10;

  // Motors
  WPI_TalonSRX LeftFront = new WPI_TalonSRX(kLeftFrontChannel);
  WPI_TalonSRX LeftRear = new WPI_TalonSRX(kLeftRearChannel);
  WPI_TalonSRX RightFront = new WPI_TalonSRX(kRightFrontChannel);
  WPI_TalonSRX RightRear = new WPI_TalonSRX(kRightRearChannel);
  WPI_TalonFX Shooter1 = new WPI_TalonFX(shooter1Port);
  WPI_TalonFX Shooter2 = new WPI_TalonFX(shooter2Port);

  // MotorControllerGroup and DifferentialDrive
  MotorControllerGroup LeftMotor = new MotorControllerGroup(LeftFront, LeftRear);
  MotorControllerGroup RightMotor = new MotorControllerGroup(RightFront, RightRear);
  MotorControllerGroup Shooter = new MotorControllerGroup(Shooter1, Shooter2);
  DifferentialDrive drive = new DifferentialDrive(LeftMotor, RightMotor);

  // Mecanumderive
  public MecanumDrive m_Drive;

  // Joystick
  Joystick joystick = new Joystick(joystickPort);

  // Variables (Base)
  double xSpeed;
  double ySpeed;
  double zRotation;
  double denominator;

  @Override
  public void robotInit() {
    // Motors Inverted
    RightFront.setInverted(true);
    RightRear.setInverted(true);
    LeftFront.setInverted(false);
    LeftRear.setInverted(false); 
  }

  @Override
  public void robotPeriodic() {
    // Post to Smart Dashboard
    SmartDashboard.putData("LeftFront", LeftFront);
    SmartDashboard.putData("LeftRear", LeftRear);
    SmartDashboard.putData("RightFront", RightFront);
    SmartDashboard.putData("RightRear", RightRear);
    SmartDashboard.putNumber("leftStick_Y", joystick.getRawAxis(leftStick_Y));
    SmartDashboard.putNumber("leftStick_X", joystick.getRawAxis(leftStick_X));
    SmartDashboard.putNumber("rightStick_X", joystick.getRawAxis(rightStick_X));
  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {

    // Shooter
    if( joystick.getRawButton(Btn_Y) ){
      Shooter.set(0.8);
    }

    // Base
    ySpeed = -joystick.getRawAxis(leftStick_Y) * 0.3;
    xSpeed = joystick.getRawAxis(leftStick_X) * 0.3;
    zRotation = joystick.getRawAxis(rightStick_X) * 0.3;
    denominator = Math.max(Math.abs(ySpeed) + Math.abs(xSpeed) + Math.abs(zRotation), 1);
    

    // Mecanumderive
    LeftFront.set((ySpeed + xSpeed + zRotation) / denominator);
    RightFront.set((ySpeed - xSpeed - zRotation) / denominator);
    LeftRear.set((ySpeed - xSpeed + zRotation) / denominator);
    RightRear.set((ySpeed + xSpeed - zRotation) / denominator);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}