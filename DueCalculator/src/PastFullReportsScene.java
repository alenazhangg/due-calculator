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
 * The PastFullReportsScene allows users to select a year from a drop-down 
 * menu to view a full report of dues assessed to all members in that year.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class PastFullReportsScene extends Scene {

	private GridPane myRoot;
	private ComboBox<Integer> yearBox;
	private Button submitButton;
	private Button backButton;
	private Text errorText;
	
	public PastFullReportsScene(Parent root){
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
		
		Text titleText = new Text("Full Reports Search");
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
		
		yearBox = new ComboBox<Integer>();
		errorText = new Text();
		errorText.setFont(new Font(15));

		submitButton = new Button("Submit");
		backButton = new Button("Back");
	
		myRoot.add(titleText, 0, 0, 2, 1);
		myRoot.add(img, 0, 1, 2, 1);
		myRoot.add(yearText, 0, 2, 2, 1);
		myRoot.add(yearBox, 0, 3, 2, 1);
		myRoot.add(errorText, 0, 4, 2, 1);
		myRoot.add(submitButton, 1, 5);
		myRoot.add(backButton, 0, 5);
	    
	    GridPane.setHalignment(titleText, HPos.CENTER);
		GridPane.setHalignment(img, HPos.CENTER);
		GridPane.setHalignment(yearText, HPos.CENTER);
		GridPane.setHalignment(yearBox, HPos.CENTER);
		GridPane.setHalignment(submitButton, HPos.RIGHT);
	}
	
	public Text getErrorText() {return errorText;}
	
	public Button getSubmitButton() {return submitButton;}
	public Button getBackButton() {return backButton;}
	
	public ComboBox<Integer> getYearBox(){return yearBox;}
}