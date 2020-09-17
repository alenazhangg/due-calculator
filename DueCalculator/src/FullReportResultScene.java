import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The FullReportResultScene displays the dues assessed for all 75
 * members in a specified year. The scene displays the amount of dues
 * assessed for each component (equal payment, number of meters,  
 * revenue minus cost of power) and the total amount of dues assessed.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class FullReportResultScene extends Scene {

	private GridPane myRoot;
	private GridPane resultsPane;
	private Text yearText;
	private Button mainMenuButton;
	private Text[] totalsTexts;
	
	public FullReportResultScene(Parent root){
		super(root);
		myRoot = (GridPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(10, 10, 10, 20));
		myRoot.setAlignment(Pos.TOP_CENTER);
		myRoot.setVgap(10);
		myRoot.setHgap(20);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		totalsTexts = new Text[4];
		
		yearText = new Text("Year: ");
		yearText.setFont(new Font(30));
		
		Text nameText = new Text("Member\t\t\t\t\t\t\t\t\t\t\t\t\t");
		Text idText = new Text("ID\t\t");
		Text epText = new Text("Equal Payment");
		Text nmText = new Text("# of Meters");
		Text rcopText = new Text("Revenue - COP");
		Text totalMemberText = new Text("Total");
		
		/* ScrollPane used to format full report*/
		ScrollPane results = new ScrollPane();
		results.setHbarPolicy(ScrollBarPolicy.NEVER);
		results.setFitToWidth(true);
		results.setFitToHeight(true);
		
		resultsPane = new GridPane();
		resultsPane.setAlignment(Pos.TOP_CENTER);
		resultsPane.setVgap(10);
		resultsPane.setHgap(40);
		resultsPane.setStyle(style);
		
		results.setContent(resultsPane);
		myRoot.add(results, 0, 2, 6, 1);

		
		Text totalText = new Text("Totals: ");
		mainMenuButton = new Button("Main Menu");
		
		myRoot.add(yearText, 0, 0, 6, 1);
		myRoot.add(nameText, 0, 1);
		myRoot.add(idText, 1, 1);
		myRoot.add(epText, 2, 1);
		myRoot.add(nmText, 3, 1);
		myRoot.add(rcopText, 4, 1);
		myRoot.add(totalMemberText, 5, 1);
		myRoot.add(totalText, 0, 3);
		myRoot.add(mainMenuButton, 0, 4);
		
		GridPane.setHalignment(yearText, HPos.CENTER);
	}
	
	public Text getYearText() {return yearText;}
	public GridPane getResultsPane() {return resultsPane;}
	public Button getMainMenuButton() {return mainMenuButton;}
	
	/**
	 * Populates the GridPane with the component dues assessed 
	 * for each member and the total dues assessed across all members.
	 * @param dues		Set of Strings that contain the component dues 
	 * and total dues assessed for each member
	 * @param totals	Array of doubles representing dues assessed per 
	 * component and in total for all members.
	 */
	public void populateResults(Set<String> dues, double[] totals) {
		int row = 0;
		resultsPane.getChildren().removeAll(resultsPane.getChildren());
		
		for(String s: dues) {
			int column = 0;
			String[] parts = s.split("\\t");
			for(String p: parts) {
				Text elementText = new Text(p);
				elementText.setFont(new Font(10.5));
				resultsPane.add(elementText, column, row);
				column++;
			}
			row++;
		}
		int column = 2;
		
		/* Removes previous values in the totals row */
		for(int j = 0; j < 4;j++) {
			myRoot.getChildren().remove(totalsTexts[j]);
		}
		
		NumberFormat nf = NumberFormat.getInstance(new Locale("en", "US"));
		int i = 0;
		
		/* Updates the total dues assessed for all members */
		for(double t: totals) {
			Text elementText = new Text("$"+ nf.format(Math.round(t*100.0)/100.0));
			totalsTexts[i] = elementText;
			myRoot.add(elementText, column, 3);
			column++;
			i++;
		}
	}
}