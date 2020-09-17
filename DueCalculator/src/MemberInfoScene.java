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
 * The MemberInfoScene allows users to select from a drop-down menu the member 
 * that they want to input new member information for.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class MemberInfoScene extends Scene {

	private GridPane myRoot;
	private Button submitButton;
	private Button backButton;
	private Text yearSelected;
	private ComboBox<String> nameBox;
	private Text errorText;
	
	public MemberInfoScene(Parent root){
		super(root);
		myRoot = (GridPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		myRoot.setAlignment(Pos.CENTER);
		myRoot.setVgap(20);
		myRoot.setHgap(80);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);

		/* Packages image into application */
		InputStream input = this.getClass().getResourceAsStream("piggyBank.png");
		Image image = new Image(input);
		ImageView img = new ImageView(image);
		
		img.setFitHeight(40); 
	    img.setFitWidth(40);       
	    img.setPreserveRatio(true); 
		
		Text titleText = new Text("Membership Information");
		titleText.setFont(new Font(30));
	    
	    Text instructionText = new Text("--Please Enter the Information Below--");
	    instructionText.setFont(new Font(20));

		Text yearText = new Text("Reported Year");
		yearText.setFont(new Font(15));
		Text nameText = new Text("Name");
		nameText.setFont(new Font(15));
	    yearSelected = new Text();
	    yearSelected.setFont(new Font(15));
		nameBox = new ComboBox<String>();
		errorText = new Text();
		errorText.setFont(new Font(15));
		submitButton = new Button("Submit");
		backButton = new Button("Back");
		
		myRoot.add(yearText, 0, 3); 
		myRoot.add(nameText, 1, 3);
		myRoot.add(yearSelected, 0, 4);
		myRoot.add(nameBox, 1, 4);
		myRoot.add(errorText, 0, 5, 2, 1);
		myRoot.add(submitButton, 1, 6);
		myRoot.add(backButton, 0, 6);
		myRoot.add(img, 0, 1, 2, 1);
		myRoot.add(titleText, 0, 0, 2, 1);
		myRoot.add(instructionText, 0, 2, 2, 1);
	    
	    GridPane.setHalignment(titleText, HPos.CENTER);
		GridPane.setHalignment(instructionText, HPos.CENTER);
		GridPane.setHalignment(img, HPos.CENTER);
		GridPane.setHalignment(yearSelected, HPos.CENTER);
	}
	
	public Text getYear() {return yearSelected;}
	public Text getErrorText() {return errorText;}

	public Button getSubmitButton() {return submitButton;}
	public Button getBackButton() {return backButton;}
	
	public ComboBox<String> getName(){return nameBox;}
}