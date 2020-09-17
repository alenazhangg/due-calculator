import java.util.Map;
import java.util.TreeMap;

/**
 * The Member class contains the key attributes of a member (name, id, weight), 
 * member information used to assessed dues (number of meters, revenue, cost 
 * of power), and the dues assessed for each component.
 *   
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class Member {
	
	private String name;
	private String id;
	private double weight; //used to calculate equal payment component of dues
	private Map<Integer, MemberInformation> yearlyMemberInfo;
	private Map<Integer, DuesAssessed> yearlyDueInfo;
	
	public Member(String n, String i, double w){
		name = n;
		id = i;
		weight = w;
		yearlyMemberInfo = new TreeMap<>();
		yearlyDueInfo = new TreeMap<>();
	}
	
	public String getName() {return name;}
	public String getId() {return id;}
	public double getWeight() {return weight;}
	
	public int getNumMeters(int year) {
		return yearlyMemberInfo.get(year).getNumMeters();
	}
	public Map<Integer, MemberInformation> getYearlyMemberInfo(){
		return yearlyMemberInfo;
	}
	public Map<Integer, DuesAssessed> getYearlyDueInfo(){
		return yearlyDueInfo;
	}
	
	/**
	 * Calculates the average revenue minus cost of power for the past
	 * five years. The average is used to assess dues for members
	 * @param year	The ending year of the average calculation
	 * @return	the average revenue minus cost of power for the past five years
	 */
	public double getAverageRMCOP(int year) {
		int y = year;
		int count = 0;
		double sum = 0.0;
		
		/* if no data for the past 5 years, the available net revenue will be averaged */
		while(yearlyMemberInfo.get(y) != null && count < 5) {
			sum+=yearlyMemberInfo.get(y).getRMinusCOP();
			count++;
		}
		return sum / count;
	}
	
	public void addMemberInfo(int year, MemberInformation member) {
		yearlyMemberInfo.put(year, member);
	}
	public void addDueInfo(int year, DuesAssessed dues) {
		yearlyDueInfo.put(year, dues);
	}
	
	public void addEqualPaymentInfo(int year, double equalPayment) {
		yearlyDueInfo.get(year).setEqualPayment(equalPayment);
	}
	public void addNumMetersInfo(int year, double numMeters) {
		double nm = yearlyDueInfo.get(year).getNumMeters() + numMeters;
		yearlyDueInfo.get(year).setNumMeters(nm);
	}
	public void addRMinusCOP(int year, double rMinusCOP) {
		double rmcop = yearlyDueInfo.get(year).getRMinusCOP() + rMinusCOP;
		yearlyDueInfo.get(year).setRMinusCOP(rmcop);
	}
}
