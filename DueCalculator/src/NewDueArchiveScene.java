import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The NewDueArchiveScene prompts users to select the way they want to 
 * search through the most recent dues assessed. They can search a specific 
 * member's dues based on their name or generate a full report on all dues 
 * assessed.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class NewDueArchiveScene extends Scene {

	private BorderPane myRoot;
	private Button searchButton;
	private Button fullReportButton;
	private Button backButton;
	
	public NewDueArchiveScene(Parent root){
		super(root);
		myRoot = (BorderPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setPadding(new Insets(20));
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		VBox titlePane = new VBox(20);
		titlePane.setAlignment(Pos.CENTER);

		Text titleText = new Text("Newest Due Archives");
		titleText.setFont(new Font(30));
		titlePane.setPadding(new Insets(20));
		titlePane.getChildren().add(titleText);

		/* Packages image into application */
		InputStream input = this.getClass().getResourceAsStream("penAndPaper.png");
		Image image = new Image(input);
		ImageView img = new ImageView(image);

		img.setFitHeight(50); 
	    img.setFitWidth(50);       
	    img.setPreserveRatio(true); 
	    titlePane.getChildren().add(img);	
	    myRoot.setTop(titlePane);	
		
		
		VBox mainElements =  new VBox(20);
		mainElements.setAlignment(Pos.TOP_CENTER);
		Text instructionsText = new Text("--Select From the Options Below--");
		instructionsText.setFont(new Font(20));
		searchButton = new Button("Search Based on Name");
		fullReportButton = new Button("Full Report");
		backButton = new Button("Back");
		
		mainElements.getChildren().add(instructionsText);
		mainElements.getChildren().add(searchButton);
		mainElements.getChildren().add(fullReportButton);
		myRoot.setCenter(mainElements);
		myRoot.setBottom(backButton);
	}
	
	public Button getBackButton() {return backButton;}
	public Button getSubmitButton() {return searchButton;}
	public Button getFullReportButton() {return fullReportButton;}
}