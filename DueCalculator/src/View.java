import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * The View class is called by the DueCalculatorApp once the application 
 * is running. It is used to instantiate each scene of the GUI and to 
 * transition between scenes based on user input.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class View {
	
	private static Stage myStage;
	public LaunchScene myLaunchScene;
	public MemberInfoScene myMemberInfoScene;
	public MemberDataScene myMemberDataScene;
	public ConfirmationScene myConfirmationScene;
	public ThankYouScene myThankYouScene;
	public PastDueArchiveScene myPastDueArchiveScene;
	public PastSearchScene myPastSearchScene;
	public SearchResultScene mySearchResultScene;
	public PastFullReportsScene myPastFullReportsScene;
	public FullReportResultScene myFullReportResultScene;
	public NewDueArchiveScene myNewDueArchiveScene;
	public NewSearchScene myNewSearchScene;
	public MemberDatabase mDatabase;
	
	public View() {
		mDatabase = new MemberDatabase();
		myLaunchScene = new LaunchScene(new BorderPane());
		myMemberInfoScene = new MemberInfoScene(new GridPane());
		myMemberDataScene = new MemberDataScene(new GridPane());
		myConfirmationScene = new ConfirmationScene(new GridPane());
		myThankYouScene = new ThankYouScene(new BorderPane());
		myPastDueArchiveScene = new PastDueArchiveScene(new BorderPane());
		myPastSearchScene = new PastSearchScene(new GridPane());
		mySearchResultScene = new SearchResultScene(new GridPane());
		myPastFullReportsScene = new PastFullReportsScene(new GridPane());
		myFullReportResultScene = new FullReportResultScene(new GridPane()); 
		myNewDueArchiveScene = new NewDueArchiveScene(new BorderPane()); 
		myNewSearchScene = new NewSearchScene(new GridPane()); 
	}
	
	public Stage getStage() { return myStage; }
	
	public void setStage(Stage stage) { 
		myStage = stage;
		myStage.setX(400);
		myStage.setY(200);
		myStage.sizeToScene();
		myStage.setTitle("Due Calculator");
		myStage.setScene(myLaunchScene);
		
		/* Actions carried out when buttons on LaunchScene are pressed */
		myLaunchScene.getEnterInfoButton().setOnAction(e->{
			for(String n: mDatabase.getNames()){
				/* Adds each member name to the drop-down menu in the MemberInfoScene */
				myMemberInfoScene.getName().getItems().add(n);
			}
			myMemberInfoScene.getYear().setText((mDatabase.getMaxYear() + 1) + "\n\n(To calculate " 
												+ (mDatabase.getMaxYear() + 3) + " dues)");
			myMemberInfoScene.getErrorText().setText("");

			myStage.setScene(myMemberInfoScene);
		});
		myLaunchScene.getPastDueButton().setOnAction(e->{
			myStage.setScene(myPastDueArchiveScene);
		});
		myLaunchScene.getNewestDueButton().setOnAction(e->{
			myStage.setScene(myNewDueArchiveScene);
		});
		
		/* Actions carried out when buttons on MemberInfoScene are pressed */
		myMemberInfoScene.getBackButton().setOnAction(e->{
			myMemberInfoScene.getErrorText().setText("");
			myStage.setScene(myLaunchScene);
		});
		myMemberInfoScene.getSubmitButton().setOnAction(e->{
			try {
				myMemberInfoScene.getErrorText().setText("");
				String name = myMemberInfoScene.getName().getSelectionModel().getSelectedItem();
				int year = mDatabase.getMaxYear()+1;
				
				if(mDatabase.getSortedByMember().get(name).getYearlyMemberInfo().get(year) != null) {
					myMemberInfoScene.getErrorText().setText("Error: Information already entered for"
															+ " this member for " + year);
				}else {
					String id = mDatabase.getSortedByMember().get(name).getId();
					myMemberDataScene.getYearText().setText("Year: " + year);
					myMemberDataScene.getNameText().setText("Name: " + name);
					myMemberDataScene.getIdText().setText("ID: " + id);
					
					myStage.setScene(myMemberDataScene);
				}
			}catch(Exception e1) {
				myMemberInfoScene.getErrorText().setText("Error: Please select a name");
			}

		});
		
		/* Actions carried out when buttons on MemberDataScene are pressed */
		myMemberDataScene.getBackButton().setOnAction(e->{
			myMemberDataScene.getError().setText("");
			myStage.setScene(myMemberInfoScene);
		});
		myMemberDataScene.getSubmitButton().setOnAction(e->{
			try {
			   myMemberDataScene.getError().setText("");

			   String name = myMemberDataScene.getNameText().getText().substring(6);
			   int year = Integer.parseInt(myMemberDataScene.getYearText().getText().substring(6));
			   String warning = "Warnings:\n";
			   myConfirmationScene.getWarningText().setText(warning);
			   NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));//format numbers to be more readable

			   /* Comparing new number of meters entered with the previous year's number of meters for the member */ 
			   int nm1 = mDatabase.getSortedByMember().get(name).getYearlyMemberInfo().get(year -1).getNumMeters();
			   int nm2 = Integer.parseInt(myMemberDataScene.getNumMeters().getText());
			   myConfirmationScene.getNumMetersText().setText("Number of Meters: " + nf.format(nm2) + "   "
					   											+ "	(Last Year: " + nf.format(nm1) + ")");
			   if((1.5 * nm1) < nm2) {
				   warning += "Please double check if the number of meters entered is too high\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   }else if(nm1 > nm2) {
				   warning+= "Please double check if the number of meters entered is too low\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   }
			   
			   /* Comparing new revenue entered with the previous year's revenue for the member */ 
			   double revenue1 = mDatabase.getSortedByMember().get(name).getYearlyMemberInfo().get(year -1).getRevenue();
			   double revenue2 = Double.parseDouble(myMemberDataScene.getRevenue().getText());
			   myConfirmationScene.getRevenueText().setText("Revenue: $" + nf.format(revenue2) + 
					   										"   (Last Year: $" + nf.format(revenue1) + ")");
			   if((1.5 * revenue1) < revenue2) {
				   warning += "Please double check if the revenue entered is too high\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   }else if(revenue1 > revenue2) {
				   warning+= "Please double check if the revenue entered is too low\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   }
			   
			   /* Comparing new cost of power entered with the previous year's cost of power for the member */ 
			   double cop1 = mDatabase.getSortedByMember().get(name).getYearlyMemberInfo().get(year -1).getCostOfPower();
			   double cop2 = Double.parseDouble(myMemberDataScene.getCostOfPower().getText());
			   myConfirmationScene.getCostOfPowerText().setText("Cost of Power: $" + nf.format(cop2) + 
					   											"   (Last Year: $" + nf.format(cop1) + ")");
			   if((1.5 * cop1) < cop2) {
				   warning += "Please double check if the cost of power entered is too high\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   } else if(cop1 > cop2) {
				   warning+= "Please double check if the cost of power entered is too low\n";
				   myConfirmationScene.getWarningText().setText(warning);
			   }
			   
			   myConfirmationScene.getNameText().setText(myMemberDataScene.getNameText().getText());
			   myConfirmationScene.getYearText().setText(myMemberDataScene.getYearText().getText());
			   myConfirmationScene.getIdText().setText(myMemberDataScene.getIdText().getText());
			  
			   myStage.setScene(myConfirmationScene);
			   
			} catch(Exception e1) {
			    myMemberDataScene.getError().setText("Please enter valid numbers");
			}
		});
		
		/* Actions carried out when buttons on ConfirmationScene are pressed */
		myConfirmationScene.getChangeButton().setOnAction(e->{
			myStage.setScene(myMemberDataScene);
		});
		myConfirmationScene.getVerifyButton().setOnAction(e->{
			String name = myMemberDataScene.getNameText().getText().substring(6);
		    int year = Integer.parseInt(myMemberDataScene.getYearText().getText().substring(6));
		    int nm = Integer.parseInt(myMemberDataScene.getNumMeters().getText());
			double revenue = Double.parseDouble(myMemberDataScene.getRevenue().getText());
			double cop = Double.parseDouble(myMemberDataScene.getCostOfPower().getText());
			
			/* Adds member information to the sortedByMemberMap and duesData.txt */
			mDatabase.addMemberData(name, year, nm, revenue, cop);
			
			myStage.setScene(myThankYouScene);
		});
		
		/* Actions carried out when buttons on ThankYouScene are pressed */
		myThankYouScene.getMainMenuButton().setOnAction(e->{
			myStage.setScene(myLaunchScene);
		});
		
		/* Actions carried out when buttons on PastDueArchiveScene are pressed */
		myPastDueArchiveScene.getBackButton().setOnAction(e->{
			myStage.setScene(myLaunchScene);
		});
		myPastDueArchiveScene.getSearchButton().setOnAction(e->{
			for(String n: mDatabase.getNames()){
				/* Adds each member name to the drop-down menu in the PastSearchScene */
				myPastSearchScene.getNameBox().getItems().add(n);
			}
			
			for(int i: mDatabase.getTotalsByYear().keySet()) {
				if(i != mDatabase.getMaxYear())
					/* Adds years in the database to the drop-down menu in the PastSearchScene */
					myPastSearchScene.getYearBox().getItems().add((i+2));
			}
		
			myStage.setScene(myPastSearchScene);
		});
		myPastDueArchiveScene.getFullReportButton().setOnAction(e->{
			for(int i: mDatabase.getTotalsByYear().keySet()) {
				if(i != mDatabase.getMaxYear())
					myPastFullReportsScene.getYearBox().getItems().add(i+2);
			}
			
			myStage.setScene(myPastFullReportsScene);
		});
		
		/* Actions carried out when buttons on PastSearchScene are pressed */
		myPastSearchScene.getBackButton().setOnAction(e->{
			myPastSearchScene.getErrorText().setText("");
			myStage.setScene(myPastDueArchiveScene);
		});
		myPastSearchScene.getSubmitButton().setOnAction(e->{
			try{
				myPastSearchScene.getErrorText().setText("");

				NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
				
				/* Obtains the name, year, id, and the due components for a member based on the user's selection */
				String name = myPastSearchScene.getNameBox().getSelectionModel().getSelectedItem();
				int year = myPastSearchScene.getYearBox().getSelectionModel().getSelectedItem() -2;
				String id = mDatabase.getSortedByMember().get(name).getId();
				double ep = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getEqualPayment();
				double nmeters = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getNumMeters();
				double rmcop = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getRMinusCOP();
				double totalDues = ep + nmeters + rmcop;
				
				/* Assigns the desired results to the text in the SearchResultsScene */
				mySearchResultScene.getYearText().setText("Year: " + (year +2) + " (Based on "+(year) + " statistics)" );
				mySearchResultScene.getNameText().setText("Name: "+ name);
				mySearchResultScene.getIdText().setText("ID: "+ id);
				mySearchResultScene.getEqualPaymentText().setText("Equal Payemnt: $"+ 
																	nf.format(Math.round(ep * 100.0)/100.0));
				mySearchResultScene.getNumMetersText().setText("Number of Meters: $"+ 
																	nf.format(Math.round(nmeters * 100.0)/100.0));
				mySearchResultScene.getRMinusCOPText().setText("Revenue Minus Cost of Power: $" + 
																	nf.format(Math.round(rmcop * 100.0)/100.0));
				mySearchResultScene.getTotalDuesText().setText("$" + nf.format(Math.round(totalDues *100.0)/100.0));
				myStage.setScene(mySearchResultScene);
			}catch(Exception e2) {
				myPastSearchScene.getErrorText().setText("Error: Please select a name and year");
			}
		});
		
		/* Actions carried out when buttons on SearchResultScene are pressed */
		mySearchResultScene.getMainMenuButton().setOnAction(e->{
			myStage.setScene(myLaunchScene);
		});
		
		/* Actions carried out when buttons on PastFullReportsScene are pressed */
		myPastFullReportsScene.getBackButton().setOnAction(e->{
			myPastFullReportsScene.getErrorText().setText("");
			myStage.setScene(myPastDueArchiveScene);
		});
		myPastFullReportsScene.getSubmitButton().setOnAction(e->{
			try{
				myPastFullReportsScene.getErrorText().setText("");
				int year = myPastFullReportsScene.getYearBox().getSelectionModel().getSelectedItem()-2;
				Set<String> dues = mDatabase.getSortedByYear().get(year);
				double[] totals = mDatabase.getTotalsByYear().get(year);
				myFullReportResultScene.getYearText().setText("Year: " + (year +2) + " (Based on "+(year) + " statistics)");
				
				myFullReportResultScene.populateResults(dues, totals);//formats the scroll pane in the FullReportsResultScene
				myStage.setX(300);			
				myStage.setY(100);
				myStage.setScene(myFullReportResultScene);
			}catch (Exception e2) {
				myPastFullReportsScene.getErrorText().setText("Error: Please select a year");
			}
		});
		
		/* Actions carried out when buttons on FullReportResultScene are pressed */
		myFullReportResultScene.getMainMenuButton().setOnAction(e->{
			myStage.setX(400);
			myStage.setY(200);

			myStage.setScene(myLaunchScene);
		});
		
		/* Actions carried out when buttons on NewDueArchiveScene are pressed */
		myNewDueArchiveScene.getBackButton().setOnAction(e->{
			myStage.setScene(myLaunchScene);
		});
		myNewDueArchiveScene.getSubmitButton().setOnAction(e->{
			for(String n: mDatabase.getNames()){
				myNewSearchScene.getNameBox().getItems().add(n);
			}
			myStage.setScene(myNewSearchScene);
		});
		myNewDueArchiveScene.getFullReportButton().setOnAction(e->{
			int year = mDatabase.getMaxYear();//year is automatically set to be the most recent year
			Set<String> dues = mDatabase.getSortedByYear().get(year);
			double[] totals = mDatabase.getTotalsByYear().get(year);
			myFullReportResultScene.getYearText().setText("Year: " + (year +2) + " (Based on "+(year) + " statistics)");
			
			myFullReportResultScene.populateResults(dues, totals);//formats the scroll pane in the FullReportsResultScene
			
			myStage.setX(300);			
			myStage.setY(100);
			myStage.setScene(myFullReportResultScene);
		});
		
		/* Actions carried out when buttons on NewSearchScene are pressed */
		myNewSearchScene.getBackButton().setOnAction(e->{
			myNewSearchScene.getErrorText().setText("");
			myStage.setScene(myNewDueArchiveScene);
		});
		myNewSearchScene.getSubmitButton().setOnAction(e->{
			try{
				myNewSearchScene.getErrorText().setText("");

				NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
				
				/* Obtains the name, year, id, and the due components for a member based on the user's selection */
				String name = myNewSearchScene.getNameBox().getSelectionModel().getSelectedItem();
				int year = mDatabase.getMaxYear();
				String id = mDatabase.getSortedByMember().get(name).getId();
				double ep = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getEqualPayment();
				double nmeters = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getNumMeters();
				double rmcop = mDatabase.getSortedByMember().get(name).getYearlyDueInfo().get(year).getRMinusCOP();
				double totalDues = ep + nmeters + rmcop;
				
				/* Assigns the desired results to the text in the SearchResultsScene */
				mySearchResultScene.getYearText().setText("Year: " + (year +2) + " (Based on "+(year) + " statistics)");
				mySearchResultScene.getNameText().setText("Name: "+ name);
				mySearchResultScene.getIdText().setText("ID: "+ id);
				mySearchResultScene.getEqualPaymentText().setText("Equal Payemnt: $"+ 
																	nf.format(Math.round(ep * 100.0)/100.0));
				mySearchResultScene.getNumMetersText().setText("Number of Meters: $"+ 
																	nf.format(Math.round(nmeters * 100.0)/100.0));
				mySearchResultScene.getRMinusCOPText().setText("Revenue Minus Cost of Power: $" + 
																	nf.format(Math.round(rmcop * 100.0)/100.0));
				mySearchResultScene.getTotalDuesText().setText("$" + nf.format(Math.round(totalDues *100.0)/100.0));
				myStage.setScene(mySearchResultScene);
			}catch(Exception e2) {
				myNewSearchScene.getErrorText().setText("Error: Please select a name");
			}
		});
		stage.show();
	}
}