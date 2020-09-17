import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The ConfirmationScene is displayed after users enter in
 * new member information (number of meters, revenue, cost of 
 * power) for the most recent year. It is used to provide warnings 
 * to users of values entered that are too low or too high compared 
 * to the past year's member information.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class ConfirmationScene extends Scene {

	private GridPane myRoot;
	private Text yearText;
	private Text nameText;
	private Text idText;
	private Text numMetersText;
	private Text revenueText;
	private Text costOfPowerText;
	private Text warningText;
	private Button verifyButton;
	private Button changeButton;

	public ConfirmationScene(Parent root){
		super(root);
		myRoot = (GridPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setAlignment(Pos.CENTER);
		myRoot.setVgap(25);
		myRoot.setHgap(80);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		yearText = new Text("Year");
		yearText.setFont(new Font(15));
		nameText = new Text("Name");
		nameText.setFont(new Font(15));
		idText = new Text("ID");
		idText.setFont(new Font(15));

		numMetersText = new Text("Number of Meters");
		numMetersText.setFont(new Font(15));

		revenueText = new Text("Revenue");
		revenueText.setFont(new Font(15));

		costOfPowerText = new Text("Cost of Power");
		costOfPowerText.setFont(new Font(15));

		warningText = new Text("Warnings");
		warningText.setFont(new Font(15));
		
		verifyButton = new Button("Verify");
		changeButton = new Button("Change");
		
		myRoot.add(yearText, 0, 0);
		myRoot.add(nameText, 0, 1);
		myRoot.add(idText, 1, 0);
		myRoot.add(numMetersText, 0, 2);
		myRoot.add(revenueText, 0, 3);
		myRoot.add(costOfPowerText, 0, 4);
		myRoot.add(warningText, 0, 5);
		myRoot.add(verifyButton, 1, 6);
		myRoot.add(changeButton, 0, 6);
	}
	
	public Text getNameText() {return nameText;}
	public Text getYearText() {return yearText;}
	public Text getIdText() {return idText;}
	public Text getNumMetersText() {return numMetersText;}
	public Text getRevenueText() {return revenueText;}
	public Text getCostOfPowerText() {return costOfPowerText;}
	public Text getWarningText() {return warningText;}
	
	public Button getVerifyButton() {return verifyButton;}
	public Button getChangeButton() {return changeButton;}
}