package frc.robot;

public final class Constants {
    public static final class DrivetrainConstants {
        public static final int[] motorRightPort = new int[] { 0, 1 };
        public static final int[] motorLeftPort = new int[] { 2, 3 };
        public static final int[] encoderRightPort = new int[] { 6, 7 };
        public static final int[] encoderLeftPort = new int[] { 8, 9 };
        public static final int wheelRadius = 2;
    }

    public static final class OIConstants {
        public static final int driverControllerPort = 0;
        public static final int operatorControllerPort = 1;
    }

    // public static final class IntakeConstants {
    //     public static final int motorPort = 4;
    //     public static final int solenoidPort = 0;
    // }

    // public static final class ShooterConstants {
    //     public static final int motorRight = 5;
    //     public static final int motorLeft = 6;
    // }

    // public static final class BufferConstants {
    //     public static final int motor = 7;
    // }
}
