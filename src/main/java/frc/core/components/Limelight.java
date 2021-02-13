package frc.core.components;

import static java.util.Objects.isNull;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.Constants.LimelightConstants;

public class Limelight {
	private final NetworkTable table;
	private static Limelight limelight;

  private Limelight() {
		this.table = NetworkTableInstance
									.getDefault()
									.getTable(
										LimelightConstants.tableName
									);
  }

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

	public static Limelight getInstance() {
		if (isNull(limelight)) return new Limelight();

		return limelight;	
	}

	private NetworkTableEntry getEntry(Entry entry) {
		return this.table.getEntry(entry.key);
	}

	private double getValue(Entry entry) {
		return this.getEntry(entry).getDouble(0.0);
	}

	public double getX() {
		return this.getValue(Entry.TX);
	}

	public double getY() {
		return this.getValue(Entry.TY);
	}

	public double getA() {
		return this.getValue(Entry.TA);
	}

	public double getV() {
		return this.getValue(Entry.TV);
	}

	public boolean isTarget() {
		return this.getV() == 1;
	}

	private void setEntry(Entry entry, int value) {
		this.getEntry(entry).setNumber(value);
	}

	public void setPipeline(int value) {
		this.setEntry(Entry.PIPELINE, value);
	}

	public void setLed(LedMode ledMode) {
		this.setEntry(Entry.LED_MODE, ledMode.value);
	}

	public void setCam(CamMode camMode) {
		this.setEntry(Entry.CAM_MODE, camMode.value);
	}
}
