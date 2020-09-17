/**
 * MemberInformation stores the number of electric meters served, revenue, 
 * and cost of power for a member in a specific year.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class MemberInformation {
	private int numMeters;
	private double revenue;
	private double costOfPower;
	
	public MemberInformation() {
		numMeters =  0;
		revenue = 0.0;
		costOfPower = 0.0;
	}
	
	public MemberInformation(int n, double r, double c){
		numMeters = n;
		revenue = r;
		costOfPower = c;
	}
	
	public int getNumMeters() {return numMeters;}
	public double getRevenue() {return revenue;}
	public double getCostOfPower() {return costOfPower;}
	public double getRMinusCOP() {return revenue - costOfPower;}

	public void setNumMeters(int nm){numMeters = nm;}
	public void setRevenue(double r) {revenue = r;}
	public void setCostOfPower(double cop) {costOfPower = cop;}
	
	public String toString() {
		return numMeters + "\t" + revenue + "\t" + costOfPower +"\t";
	}
}
