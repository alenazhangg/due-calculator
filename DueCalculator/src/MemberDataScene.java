import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The MemberDataScene prompts users to enter the number of meters, revenue, 
 * and cost of power for the most recent year. The information added will be 
 * used to calculate updated dues once member information has been added for
 * all 75 members.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class MemberDataScene extends Scene {

	private GridPane myRoot;
	private Text yearText;
	private Text nameText;
	private Text idText;
	private TextField numMetersField;
	private TextField revenueField;
	private TextField costOfPowerField;
	private Text errorText;
	private Button submitButton;
	private Button backButton;
	
	public MemberDataScene(Parent root){
		super(root);
		myRoot = (GridPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setAlignment(Pos.TOP_CENTER);
		myRoot.setVgap(20);
		myRoot.setHgap(80);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		yearText = new Text("Year");
		yearText.setFont(new Font(15));
		nameText = new Text("Name");
		nameText.setFont(new Font(15));
		idText = new Text("ID");
		idText.setFont(new Font(15));

		Text instructionText = new Text("--Please Enter the Information Below Accurately--");
		instructionText.setFont(new Font(15));

		Text numMetersText = new Text("Number of Meters");
		numMetersText.setFont(new Font(15));
		numMetersField = new TextField();

		Text revenueText = new Text("Revenue");
		revenueText.setFont(new Font(15));

		revenueField = new TextField();
		
		Text costOfPowerText = new Text("Cost of Power");
		costOfPowerText.setFont(new Font(15));

		costOfPowerField = new TextField();
		errorText = new Text();
		errorText.setFont(new Font(15));

		
		submitButton = new Button("Submit");
		backButton = new Button("Back");
		
		myRoot.add(yearText, 0, 0);
		myRoot.add(nameText, 0, 1);
		myRoot.add(idText, 1, 0);
		myRoot.add(instructionText, 0, 2, 2, 1);
		myRoot.add(numMetersText, 0, 3, 2, 1);
		myRoot.add(numMetersField, 0, 4, 2, 1);
		myRoot.add(revenueText, 0, 5);
		myRoot.add(revenueField, 0, 6);
		myRoot.add(costOfPowerText, 1, 5);
		myRoot.add(costOfPowerField, 1, 6);
		myRoot.add(errorText, 0, 7, 2, 1);
		myRoot.add(submitButton, 1, 8);
		myRoot.add(backButton, 0, 8);
		
		GridPane.setHalignment(instructionText, HPos.CENTER);
	}
	
	public Text getNameText() {return nameText;}
	public Text getYearText() {return yearText;}
	public Text getIdText() {return idText;}
	public Text getError() { return errorText;}

	public Button getSubmitButton() {return submitButton;}
	public Button getBackButton() {return backButton;}
	
	public TextField getNumMeters() {return numMetersField;}
	public TextField getRevenue() {return revenueField;}
	public TextField getCostOfPower() {return costOfPowerField;}
}
