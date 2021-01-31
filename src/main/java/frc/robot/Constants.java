package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public final class Constants {
    public static final class DrivetrainConstants {
        //motors
        public static final int[] motorRightPort = new int[] { 0, 1 };
        public static final int[] motorLeftPort = new int[] { 2, 3 };

        //encoders
        public static final int[] encoderRightPort = new int[] { 6, 7 };
        public static final int[] encoderLeftPort = new int[] { 8, 9 };
        public static final boolean isEncoderRightInverted = true;
        public static final int pulsesRight = 498;
        public static final int pulsesLeft = 494;

        //chassi
        public static final int wheelRadius = 2;

        //voltageConstraint
        public static final double ksVolts = 0;
        public static final double kvVoltSecondsPerMeter = 0;
        public static final double kaVoltSecondsSquaredPerMeter = 0;
        public static final double kTrackwidthMeters = 0;
        public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
        public static final double differentialDriveVoltageConstraintMaxVoltage = 10;
    }

    public static final class OIConstants {
        public static final int driverControllerPort = 0;
    }
}
