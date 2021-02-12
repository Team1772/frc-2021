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
        ksVolts = 0,
        kvVoltSecondsPerMeter = 0,
        kaVoltSecondsSquaredPerMeter = 0,
        kTrackwidthMeters = 0,
        differentialDriveVoltageConstraintMaxVoltage = 10;

        public static final DifferentialDriveKinematics
        kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
        
        //PID
        public static final class PIDConstants {
            public static final double 
            kPDriveVelocity = 0,
            kIDriveVelocity = 0,
            kDDriveVelocity = 0;

            public static final int
            kPIDLoopIdx = 0,
            kTimeoutMs = 30,
            nominalOutputForwardValue = 0,
            nominalOutputReverseValue = 0,
            peakOutputForwardValue = 1,
            peakOutputReverseValue = -1;

            public static final boolean
            isSensorPhase = true;
        }
    }
    
    public static final class IntakeConstants {
        public static final int
        motorPort = 4,
        activatorOne = 0,   
        activatorTwo = 1;
    }

    public static final class OIConstants {
        public static final int
        driverControllerPort = 0;
    }

    public static final class AutoConstants {
        public static final double 
        kMaxSpeedMetersPerSecond = 2,
        kMaxAccelerationMetersPerSecondSquared = 2,
        kRamseteB = 2,
        kRamseteZeta = 0.7;
    }
}
