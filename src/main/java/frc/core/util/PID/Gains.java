package frc.core.util.PID;

public class Gains {
    public final double kP;
	public final double kI;
	public final double kD;
	public final double kF;
	public final int kIzone;
	public final double kPeakOutput;
	
	//constructor
	public Gains(double kP, double kI, double kD, double kF, int kIzone, double kPeakOutput){
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
		this.kF = kF;
		this.kIzone = kIzone;
		this.kPeakOutput = kPeakOutput;
	}
}
