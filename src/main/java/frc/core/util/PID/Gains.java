package frc.core.util.PID;

public class Gains {
  public final double kP, kI, kD, kF, kPeakOutput;
	public final int kIzone;

	public Gains(double kP, double kI, double kD, double kF, int kIzone, double kPeakOutput) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.kIzone = kIzone;
		this.kPeakOutput = kPeakOutput;
	}
}