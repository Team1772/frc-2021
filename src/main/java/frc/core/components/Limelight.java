package frc.core.components;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.Constants.LimelightConstants;

public class Limelight {
	private static final NetworkTable table =
		NetworkTableInstance.getDefault().getTable(LimelightConstants.tableName);

  public enum Entry {
    TX("tx"),
		TY("ty"),
		TA("ta"),
		TV("tv"),
		PIPELINE("pipeline"),
		LED_MODE("ledMode"),
		CAM_MODE("camMode");

		public final String key;
		private Entry(String key) {
			this.key = key;
		}
	}

	public enum LedMode {
		CURRENT(0),
		OFF(1),
		BLINK(2),
		ON(3);

		public final int value;
		private LedMode(int value) {
			this.value = value;
		}
	}

	public enum CamMode {
		VISION_PROCESSOR(0),
		DRIVER_CAMERA(1);

		public final int value;
		private CamMode(int value) {
			this.value = value;
		}
	}

	private static NetworkTableEntry getEntry(Entry entry) {
		return table.getEntry(entry.key);
	}

	private static double getValue(Entry entry) {
		return getEntry(entry).getDouble(0.0);
	}

	public static double getX() {
		return getValue(Entry.TX);
	}

	public static double getY() {
		return getValue(Entry.TY);
	}

	public static double getA() {
		return getValue(Entry.TA);
	}

	public static double getV() {
		return getValue(Entry.TV);
	}

	public static boolean isTarget() {
		return getV() == 1;
	}

	private static void setEntry(Entry entry, int value) {
		getEntry(entry).setNumber(value);
	}

	public static void setPipeline(int value) {
		setEntry(Entry.PIPELINE, value);
	}

	public static void setLed(LedMode ledMode) {
		setEntry(Entry.LED_MODE, ledMode.value);
	}

	public static void setCam(CamMode camMode) {
		setEntry(Entry.CAM_MODE, camMode.value);
	}
}
