import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The MemberDatabase loads previous membership data from a file provided 
 * by the client and calculates dues assessed for each member for all the 
 * years information was provided.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class MemberDatabase {
	
	private Map<String, Member> sortedByMember;
	private Map<Integer, Set<String>> sortedByYear;
	private Map<Integer, double[]> totalsByYear;
	private static final double firstYrTotalDues = 2000000;//total dues assessed for 2005
	private Set<String> names;
	private Map<Integer, Integer> totalMeters;
	private double totalWeight;
	private int maxYear;//most recent year that dues were assessed
	private int count;//number of members that have entered new info for due calculations
	
	public MemberDatabase() {
		sortedByMember = new TreeMap<>();
		sortedByYear = new TreeMap<>();
		totalMeters = new HashMap<>();
		totalWeight = 0;
		totalsByYear = new TreeMap<>();
		names = new TreeSet<>();
		maxYear = 0;
		count = 0;
		
		loadDataFromFile();
	
		populateSortedByMemberMap();
		populateSortedByYearMap();
	}
	
	/**
	 * Populates sortedByMemberMap, which has the member name as a key and
	 * a Member as the value, from the data file provided by the client.
	 * Populates map with member name, id, weight, DuesAssessed, and 
	 * MemberInformation.
	 */
	private void loadDataFromFile() {
		Scanner fin = null;
		try {
			fin = new Scanner(new File("./duesData.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception");
		}
		
		while (fin.hasNext()) {
			String line = fin.nextLine();
			String[] parts = line.split("\\t");
			String name = parts[0];
			String id = parts[1];
			double weight = Double.parseDouble(parts[2]);
			totalWeight+=weight;
			
			Member member = new Member(name, id, weight);
			sortedByMember.put(name, member);
			names.add(name);
			
			for(int i = 3; i < parts.length; i += 4) {
				int year = Integer.parseInt(parts[i]);
				if (year == maxYear) {
					count++;
				}
				if(year > maxYear) {
					maxYear = year;
					count = 1;
				}
				
				/* for special members that only need to pay the equal payment component of dues */
				if (parts[i+1].equals("")) {
					MemberInformation mem = new MemberInformation();
					DuesAssessed dues = new DuesAssessed();
					
					sortedByMember.get(name).addMemberInfo(year, mem);
					sortedByMember.get(name).addDueInfo(year, dues);
				}else {
					/* for normal members */
					int numMeters = Integer.parseInt(parts[i+1]);
					double revenue = Double.parseDouble(parts[i+2]);
					double cop = Double.parseDouble(parts[i+3]);
					
					MemberInformation mem2 = new MemberInformation(numMeters, revenue, cop);
					sortedByMember.get(name).addMemberInfo(year, mem2);
					
					DuesAssessed dues = new DuesAssessed();
					sortedByMember.get(name).addDueInfo(year, dues);
					
					if (totalMeters.containsKey(year)){
						int updateTotal = totalMeters.get(year) + numMeters;
						totalMeters.put(year, updateTotal);
					}else {
						totalMeters.put(year, numMeters);
					}
				}
			}
		}
		if(count==75) {
			/* all members have entered their information for the next due calculations */
			count=0;
		}else {
			/* not all members have entered their information for the next due calculations */
			maxYear--;
		}
		fin.close();
	}
	
	public void incrementCount() {count++;}
	
	public int getMaxYear() {return maxYear;}
	public Set<String> getNames() {return names;}
	public Map<String, Member> getSortedByMember() {return sortedByMember;}
	public Map<Integer, Set<String>> getSortedByYear() {return sortedByYear;}
	public Map<Integer, double[]> getTotalsByYear() {return totalsByYear;}
	
	/**
	 * Adds new member information to the sortedByMember map and updates the new information
	 * to the client's .txt file.
	 * @param name	The name of the member.
	 * @param year	The year that the member information is for.
	 * @param numMeters	The number of electric meters served.
	 * @param revenue	The revenue earned.
	 * @param cop		The cost of providing power.
	 */
	public void addMemberData(String name, int year, int numMeters, double revenue, double cop) {
		MemberInformation mem = new MemberInformation(numMeters, revenue, cop); 
		sortedByMember.get(name).addMemberInfo(year, mem);
		incrementCount();
		
		try {
			File file=new File("./duesData.txt");
			FileWriter output = new FileWriter(file, false); //creates a FileWriter to write to duesData.txt

			for (String n: sortedByMember.keySet()){
				String id = sortedByMember.get(n).getId();
				double weight = sortedByMember.get(n).getWeight();
				String print = n +"\t" + id + "\t" + weight +"\t";
				
				for(int y: sortedByMember.get(n).getYearlyMemberInfo().keySet())
					print+=y + "\t" + sortedByMember.get(n).getYearlyMemberInfo().get(y);
				
				output.write(print+"\n");//writes the line to the file
			}
			output.close();
		} catch (IOException e) {
			System.out.println("File not found exception");
		}
	}
	
	/**
	 * Once all members have had their newest number of meters, revenue, and cost
	 * of power entered, the newest dues assessed will be calculated for the next
	 * year.
	 * @return	if all members have entered their newest information
	 */
	public boolean addYearlyData() {
		if(count != 75) {
			return false;
		}else {
			maxYear++;
			calculateEqualPayment(maxYear);
			calculateNumMeters(maxYear);
			calculateRMinusCOP(maxYear);
			updateYearlyData(maxYear);
		}
		return true;
	}
	
	/**
	 * Calculates the amount of total dues assessed across all members for the 
	 * given year. It is increased from the previous year's total dues by the
	 * percent increase in number of meters served.
	 * @param year	The year for which the total dues are calculated.
	 * @return		The total dues to be assessed across all members.
	 */
	public double calculateTotalDues(int year) {
		/* 2005 is the base year provided in the client's file */
		if (year == 2005) {
			return firstYrTotalDues;
		}else {
			double increase = (double)(totalMeters.get(year) - totalMeters.get(year-1))/totalMeters.get(year-1);
			/* Use of recursion */
			return calculateTotalDues(year-1) * (increase+ 1);
		}
	}
	
	/** 
	 * Calculates the equal payment component of dues assessed for each member. 
	 * The total equal payment component assessed across all members is 1/3 of the 
	 * total dues returned by calculateTotalDues(). The amount assessed for each 
	 * member is dependent on the weight, or status, of the member.
	 * @param year	The year for which equal payment component of dues are calculated.
	 */
	public void calculateEqualPayment(int year) {
		double duesAssessed = calculateTotalDues(year) / 3.0; 
		for (String n: sortedByMember.keySet()){
			double equalPayment = (sortedByMember.get(n).getWeight()/totalWeight) * duesAssessed;
			sortedByMember.get(n).addEqualPaymentInfo(year, equalPayment);
		}
	}
	
	/**
	 * Calculates the number of meters component of dues assessed for each member.
	 * The total number of meters component assessed across all members is 1/3 of 
	 * the total dues returned by calculateTotalDues(). 
	 * 
	 * Number of meters is assessed based on the declining block:
	 * 1-5000 meters --> 172.5%
	 * 5001-10000 meters --> 67.5%
	 * 10001-15000 meters --> 65.0%
	 * 15001-20000 meters --> 62.5%
	 * 20001-25000 meters --> 60.0%
	 * 25001 meters and above --> 57.5%
	 * 
	 * @param year	The year for which number of meters component of dues are calculated.
	 */
	public void calculateNumMeters(int year) {
		double duesAssessed = calculateTotalDues(year) / 3.0;
		int[] tierTotals = new int[6];//record the total number of meters that fall between specified ranges
		double[] weight = {1.725, .675, .65, .625, .60, .575};//weights of tiers based on declining block
		double[] tierDues = new double[6];//dues assessed for each range of number of meters
	
		int numMeters = 0;
		
		/* Populates the tierTotals with the number of meters that fall within each range for a member */
		for (String n: sortedByMember.keySet()) {
			numMeters = sortedByMember.get(n).getNumMeters(year);
			int index = 0;
			while (numMeters > 0) {
				if (numMeters <= 5000 && index < 6) {
					tierTotals[index] += numMeters;
				}else if(index < 6) {
					tierTotals[index] += 5000;
				}else {
					tierTotals[5] += numMeters;
					numMeters = 0;
				}
				numMeters -= 5000;
				index++;
			}
		}
		
		double sum = 0;
		
		/* Sums the product of the number of meters in each tier and their weights */
		for(int i = 0; i < tierTotals.length; i++) {
			sum += tierTotals[i] * weight[i];
		}

		/* Calculates total dues assessed for each tier based on the percentage of the sum they take up */
		for(int i = 0; i < tierDues.length; i++) {
			tierDues[i] = duesAssessed * (double)((tierTotals[i]*weight[i])/sum);
		}
		
		/* Calculates and sums dues assessed for each tier for each member */
		for (String n: sortedByMember.keySet()) {
			numMeters = sortedByMember.get(n).getNumMeters(year);
			int index = 0;
			
			while(numMeters > 0) {
				if(numMeters <= 5000 && index < 6) {
					double dues = tierDues[index] * ((double)numMeters/tierTotals[index]);
					sortedByMember.get(n).addNumMetersInfo(year, dues);
				}else if(index < 6) {
					double dues = tierDues[index] * (5000.0/tierTotals[index]);
					sortedByMember.get(n).addNumMetersInfo(year, dues);
				}else {
					double dues = tierDues[5] * ((double)numMeters/tierTotals[5]);
					sortedByMember.get(n).addNumMetersInfo(year, dues);
					numMeters = 0;
				}
				numMeters -= 5000;
				index++;
			}
		}
	}
	
	/**
	 * Calculates the revenue minus cost of power component of dues assessed for 
	 * each member. The total revenue minus cost of product assessed across all 
	 * members is 1/3 of the total dues returned by calculateTotalDues(). 
	 * 
	 * Revenue minus cost of power is assessed based on the declining block:
	 * First $2.5 million --> 162.5%
	 * $2.5-5.0 million --> 62.5%
	 * $5.0-10.0 million --> 57.5%
	 * $10.0-15.0 million --> 55.0%
	 * $15.0-20.0 million --> 52.5%
	 * $20.0-25.0 million --> 50.0%
	 * Over $25.0 million --> 47.5%
	 * 
	 * @param year	The year for which revenue minus cost of power component of 
	 * dues are calculated.
	 */
	public void calculateRMinusCOP(int year) {
		double duesAssessed = calculateTotalDues(year) / 3.0;
		int[] revTotals = new int[7];//record the net revenue that falls between specified ranges
		double[] weight = {1.625, .625, .575, .55, .525, .50, .475};//relative weights of each of the tiers
		double[] revDues = new double[7];//dues assessed for each range of net revenue
		
		/* Populates the tierTotals with the net revenue that fall within each range for a member */
		for(String n: sortedByMember.keySet()) {
			double rmcop = sortedByMember.get(n).getAverageRMCOP(year);
			int index = 0;
			while (rmcop > 0) {
				if(index == 0 || index ==1){
					if(rmcop <= 2500000) {
						revTotals[index] += rmcop;
						rmcop = 0;
					}else{
						revTotals[index] += 2500000;
					}
					rmcop -= 2500000;
				}else if(1< index && index < 7) {
					if(rmcop <= 5000000) {
						revTotals[index] += rmcop;
					}
					else {
						revTotals[index] += 5000000;
					}
					rmcop -= 5000000;
				}else {
					revTotals[6] += rmcop;
					rmcop = 0;
				}
				index++;
			}
		}
		
		double sum = 0;
		
		/* Sums the product of the net revenue in each tier and their weights */
		for(int i = 0; i < revTotals.length; i++) {
			sum += revTotals[i] * weight[i];
		}
		
		/* Calculates total dues assessed for each tier based on the percentage of the sum they take up */
		for(int i = 0; i < revTotals.length; i++) {
			revDues[i] = duesAssessed * ((revTotals[i]*weight[i])/sum);
		}
		
		/* Calculates and sums dues assessed for each tier for each member */
		for(String n: sortedByMember.keySet()) {
			double rmcop = sortedByMember.get(n).getAverageRMCOP(year);
			int index = 0;
			while (rmcop > 0) {
				if(index == 0 || index ==1){
					if(rmcop <= 2500000) {
						double dues = revDues[index] * (rmcop/revTotals[index]);
						sortedByMember.get(n).addRMinusCOP(year, dues);
						rmcop = 0;
					}else{
						double dues = revDues[index] * (2500000.0/revTotals[index]);
						sortedByMember.get(n).addRMinusCOP(year, dues);
					}
					rmcop -= 2500000;
				}else if(1< index && index < 7) {
					if(rmcop <= 5000000) {
						double dues = revDues[index] * (rmcop/revTotals[index]);
						sortedByMember.get(n).addRMinusCOP(year, dues);
					}else {
						double dues = revDues[index] * (5000000.0/revTotals[index]);
						sortedByMember.get(n).addRMinusCOP(year, dues);
					}
					rmcop -= 5000000;
				}else {
					double dues = revDues[6] * (rmcop/revTotals[6]);
					sortedByMember.get(n).addRMinusCOP(year, dues);
					rmcop = 0;
				}
				index++;
				
			}
		}
	}

	/**
	 * Sums the each component of dues assessed across all members for a specifed 
	 * year and stores the totals in the totalsByYear map.
	 * @param year	The year for which the total dues assessed are added to the 
	 * totalsByYear map.
	 */
	public void updateYearlyData(int year) {
		double[] totals = new double[4];
		
		for (String n: sortedByMember.keySet()) {
			NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
			String id = sortedByMember.get(n).getId();
			
			double ep = sortedByMember.get(n).getYearlyDueInfo().get(year).getEqualPayment();
			totals[0] += ep;
			
			double numMeters = sortedByMember.get(n).getYearlyDueInfo().get(year).getNumMeters();
			totals[1] += numMeters;
			
			double rmcop = sortedByMember.get(n).getYearlyDueInfo().get(year).getRMinusCOP();
			totals[2] += rmcop;
			
			double total = ep + numMeters + rmcop;
			totals[3] += total;
			
			String due = n + "\t"+id+ "\t$" + nf.format(Math.round(ep * 100.0)/100.0) + "\t$" +  
					nf.format(Math.round(numMeters * 100.0)/100.0) + "\t$" + 
					nf.format(Math.round(rmcop * 100.0)/100.0) + "\t$" + 
					nf.format(Math.round(total * 100.0)/100.0);
			if (!sortedByYear.containsKey(year)) {
				sortedByYear.put(year, new TreeSet<>());
			}
			sortedByYear.get(year).add(due);
		}
		totalsByYear.put(year, totals);
	}
	
	/**
	 * Populates the sortedByYearMap by iterating through the sortedByMemberMap
	 */
	public void populateSortedByYearMap() {
		for (int year: totalMeters.keySet()) {
			if(year <= maxYear)
				updateYearlyData(year);
		}
	}
	
	/**
	 * Populates sortedByMemberMap with dues assessed by calculating the equal
	 * payment, number of meters, and net revenue components of dues.
	 */
	public void populateSortedByMemberMap() {
		for (int year: totalMeters.keySet()) {
			if(year <= maxYear){	
				calculateEqualPayment(year);
				calculateNumMeters(year);
				calculateRMinusCOP(year);
			}
		}
	}
}
