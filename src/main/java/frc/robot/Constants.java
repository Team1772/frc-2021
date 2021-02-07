package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public final class Constants {
    public static final class DrivetrainConstants {
        //motors
        public static final int[]
        motorRightPort = new int[] { 0, 1 },
        motorLeftPort = new int[] { 2, 3 };

        //encoders
        public static final int[]
        encoderRightPort = new int[] { 6, 7 },
        encoderLeftPort = new int[] { 8, 9 };

        public static final boolean
        isEncoderRightInverted = true;

        public static final int 
        pulsesRight = 498,
        pulsesLeft = 494;

        //chassi
        public static final int 
        wheelRadius = 2;

        //voltageConstraint
        public static final double 
        ksVolts = 0.716,
        kvVoltSecondsPerMeter = 2.23,
        kaVoltSecondsSquaredPerMeter = 0.189,
        kTrackwidthMeters = 2.84,
        differentialDriveVoltageConstraintMaxVoltage = 10;

        public static final DifferentialDriveKinematics
        kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);

        //PID
        public static final double 
        kPDriveVelocity = 2.21,
        kIDriveVelocity = 0,
        kDDriveVelocity = 0;   
    }
    
    public static final class IntakeConstants {
        public static final int
        motorPort = 4,
        intakeActivatorOne = 0,   
        intakeActivatorTwo = 1;
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
}
