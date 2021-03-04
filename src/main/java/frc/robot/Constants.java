package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public final class Constants {
	public static final class DrivetrainConstants {
		//motors
		public static final int[]
			motorRightPort = new int[] { 0, 1 },
			motorLeftPort = new int[] { 2, 3 };

		public static final boolean[]
			isMotorsInverted = new boolean[] { true, true };

		//encoders
		public static final int[]
			encoderRightPort = new int[] { 6, 7 },
			encoderLeftPort = new int[] { 8, 9 };

		public static final boolean[]
			isEcondersInverted = new boolean[] { true, true };

		public static final int 
			pulsesRight = 498,
			pulsesLeft = 494;

		//chassi
		public static final int 
			wheelRadius = 2;

		//voltageConstraint
		public static final double 
			ksVolts = 0.976, //kS
			kvVoltSecondsPerMeter = 2.21, //kV
			kaVoltSecondsSquaredPerMeter = 0.726, //kA
			kTrackwidthMeters = 2.81,
			differentialDriveVoltageConstraintMaxVoltage = 10;

		public static final DifferentialDriveKinematics
			kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

		//PID
		public static final double 
			kPDriveVelocity = 2.62,
			kIDriveVelocity = 0,
			kDDriveVelocity = 0;   
	}
	
	public static final class IntakeConstants {
		public static final int[]
			motorsPorts = new int[] { 2, 3 },
			activatorsPorts = new int[] { 0, 1 };
	}

	public static final class PIDTalonConstants{
		public static final boolean
		isSensorPhase = true;

		public static final int
		kPIDLoopIdx = 0,
		kTimeoutMs = 30,
		nominalOutputForwardValue = 0,
		nominalOutputReverseValue = 0,
		peakOutputForwardValue = 1,
		peakOutputReverseValue = -1;
	}

	public static final class BufferConstants {
		public static final int
			motorPort = 4;
	}

	public static final class ShooterConstants {
		public static final int
			motorRightPort = 0,
			motorLeftPort = 1,
			activatorOne = 0,   
			activatorTwo = 1;

		public static final double wheelRadius = 0.0762;

		public static final class PID {
			public static final double 
				kPVelocity = 0,
				kIVelocity = 0,
				kDVelocity = 0, 
				kFVelocity = 0, 
				kPeakOutputVelocity = 0,
				dutyCycle = 0.8;
	
			public static final int
				kIZoneVelocity = 0;
		}
	}

	public static final class OIConstants {
		public static final int
			driverControllerPort = 0,
			operatorControllerPort = 1;
	}

	public static final class AutoConstants {
		public static final double 
			kMaxSpeedMetersPerSecond = 2,
			kMaxAccelerationMetersPerSecondSquared = 2,
			kRamseteB = 2,
			kRamseteZeta = 0.7;
	}

	public static final class LimelightConstants {
		public static final String tableName = "limelight";
		public static final int pipeline = 0;
		public static final double kP = 0,
			minCommand = 0,
			kpAim = 0,
			kpDistance = 0,
			minAimCommand = 0;
	}
}
