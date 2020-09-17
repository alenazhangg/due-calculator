import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The SearchResultScene displays the dues assessed for specific member in a 
 * specified year. The scene displays the member name, id, year, component dues
 * (equal payment, number of meters, and revenue minus cost of power),
 * and the total dues assessed.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class SearchResultScene extends Scene {

	private GridPane myRoot;
	private Text yearText;
	private Text idText;
	private Text nameText;
	private Text equalPaymentText;
	private Text numMetersText;
	private Text rMinusCOPText;
	private Text totalDuesText;
	private Button mainMenuButton;
	
	public SearchResultScene(Parent root){
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
		idText = new Text("ID");
		idText.setFont(new Font(15));
		nameText = new Text("Name");
		nameText.setFont(new Font(15));
		
		Text guideText = new Text("--Component Dues--");
		guideText.setFont(new Font(20));
		equalPaymentText = new Text("Equal Payment");
		equalPaymentText.setFont(new Font(15));
		numMetersText = new Text("Number of Meters");
		numMetersText.setFont(new Font(15));
		rMinusCOPText = new Text("Revenue Minus Cost of Power");
		rMinusCOPText.setFont(new Font(15));
		
		Text totalText = new Text("--Total Dues--");
		totalText.setFont(new Font(20));
		totalDuesText = new Text();
		totalDuesText.setFont(new Font(15));

		mainMenuButton = new Button("Main Menu");
		
		myRoot.add(yearText, 0, 0);
		myRoot.add(idText, 1, 0);
		myRoot.add(nameText, 0, 1);
		myRoot.add(guideText, 0, 2, 2, 1);
		myRoot.add(equalPaymentText, 0, 3);
		myRoot.add(numMetersText, 0, 4);
		myRoot.add(rMinusCOPText, 0, 5);
		myRoot.add(totalText, 0, 6, 2, 1);
		myRoot.add(totalDuesText, 0, 7);
		myRoot.add(mainMenuButton, 0, 8, 2, 1);
	    
	    GridPane.setHalignment(guideText, HPos.CENTER);
		GridPane.setHalignment(totalText, HPos.CENTER);
		GridPane.setHalignment(mainMenuButton, HPos.CENTER);
	}
	
	public Text getNameText() {return nameText;}
	public Text getYearText() {return yearText;}
	public Text getIdText() {return idText;}
	public Text getEqualPaymentText() {return equalPaymentText;}
	public Text getNumMetersText() {return numMetersText;}
	public Text getRMinusCOPText() {return rMinusCOPText;}
	public Text getTotalDuesText() {return totalDuesText;}
	
	public Button getMainMenuButton() {return mainMenuButton;}
}