/**
 * DuesAssessed stores the components (equal payment, number of meters
 * revenue minus cost of power) that the dues are assessed on for each 
 * member.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class DuesAssessed {
	
	private double equalPayment;
	private double numMeters;
	private double rMinusCOP; //COP -> Cost of power
	
	public DuesAssessed() {
		equalPayment = 0;
		numMeters = 0;
		rMinusCOP = 0;
	}
	
	public DuesAssessed(double ep, double nm, double r) {
		equalPayment = ep;
		numMeters = nm;
		rMinusCOP = r;
	}

	public double getEqualPayment() {return equalPayment;}
	public double getNumMeters() {return numMeters;}
	public double getRMinusCOP() {return rMinusCOP;}
	
	public void setEqualPayment(double ep) {equalPayment = ep;}
	public void setNumMeters(double nm) {numMeters = nm;}
	public void setRMinusCOP(double r) {rMinusCOP = r;}
	
	public String toString() {
		return equalPayment + " " + numMeters + " " + rMinusCOP;
	}
}
