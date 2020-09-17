import java.io.InputStream;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The PastSearchScene prompts users to select a member's name and a year from
 * drop-down menus to view the dues that were assessed to that member in the 
 * specified year.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class PastSearchScene extends Scene {

	private GridPane myRoot;
	private ComboBox<String> nameBox;
	private ComboBox<Integer> yearBox;
	private Button submitButton;
	private Button backButton;
	private Text errorText;
	public PastSearchScene(Parent root){
		super(root);
		myRoot = (GridPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setAlignment(Pos.TOP_CENTER);
		myRoot.setVgap(30);
		myRoot.setHgap(80);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		Text titleText = new Text("Past Due Archives Search");
		titleText.setFont(new Font(30));
		
		/* Packages image into application */
		InputStream input = this.getClass().getResourceAsStream("search.png");
		Image image = new Image(input);
		ImageView img = new ImageView(image);
		
		img.setFitHeight(50); 
	    img.setFitWidth(50);       
	    img.setPreserveRatio(true); 
	    
		Text yearText = new Text("Year");
		yearText.setFont(new Font(15));
		Text nameText = new Text("Name");
		nameText.setFont(new Font(15));
		
		errorText = new Text();
		errorText.setFont(new Font(15));

		nameBox = new ComboBox<String>();
		yearBox = new ComboBox<Integer>();
		
		submitButton = new Button("Submit");
		backButton = new Button("Back");
		
		myRoot.add(nameText, 0, 2);
		myRoot.add(nameBox, 0, 3);
		myRoot.add(yearText, 1, 2);
		myRoot.add(yearBox, 1, 3);
		myRoot.add(errorText, 0, 4, 2, 1);
		myRoot.add(submitButton, 1, 5);
		myRoot.add(backButton, 0, 5);
		myRoot.add(img, 0, 1, 2, 1);
		myRoot.add(titleText, 0, 0, 2, 1);
	    
	    GridPane.setHalignment(titleText, HPos.CENTER);
		GridPane.setHalignment(img, HPos.CENTER);
	}
	
	public Text getErrorText() {return errorText;}
	
	public Button getSubmitButton() {return submitButton;}
	public Button getBackButton() {return backButton;}
	
	public ComboBox<String> getNameBox(){return nameBox;}
	public ComboBox<Integer> getYearBox(){return yearBox;}
}