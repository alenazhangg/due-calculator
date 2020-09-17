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
 * The LaunchScene is the first screen displayed once the application
 * is launched. It allows users to select between entering updated 
 * member information, viewing past due archives, and viewing the newest
 * due archives.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class LaunchScene extends Scene {

	private BorderPane myRoot;
	private Button enterInfoButton;
	private Button pastDueButton;
	private Button newestDueButton;
	
	public LaunchScene(Parent root){
		super(root);
		myRoot = (BorderPane) root;
		myRoot.setPadding(new Insets(20, 20, 20, 20));
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";//light cream color
		myRoot.setStyle(style);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		VBox titlePane = new VBox(20);
		titlePane.setAlignment(Pos.CENTER);
		
		Text titleText = new Text("Membership Due Calculator");
		titleText.setFont(new Font(30));
		titlePane.setPadding(new Insets(20));
		titlePane.getChildren().add(titleText);
		
		VBox mainElements =  new VBox(20);
		mainElements.setAlignment(Pos.TOP_CENTER);
		Text instructionsText = new Text("--Select From the Options Below--");
		instructionsText.setFont(new Font(20));
		enterInfoButton = new Button("Enter Member Information");
		pastDueButton = new Button("View Past Due Archives");
		newestDueButton = new Button("View Newest Due Archive");
		
		mainElements.getChildren().add(instructionsText);
		mainElements.getChildren().add(enterInfoButton);
		mainElements.getChildren().add(pastDueButton);
		mainElements.getChildren().add(newestDueButton);
		myRoot.setCenter(mainElements);
		
		/* Packages image into application */
		InputStream input = this.getClass().getResourceAsStream("money.png");
		Image image = new Image(input);
		ImageView img = new ImageView(image);

		img.setFitHeight(50); 
	    img.setFitWidth(50);       
	    img.setPreserveRatio(true); 
	    
	    
	    titlePane.getChildren().add(img);	
	    myRoot.setTop(titlePane);	
	}
	
	public Button getEnterInfoButton() {return enterInfoButton;}
	public Button getPastDueButton() {return pastDueButton;}
	public Button getNewestDueButton() {return newestDueButton;}
}
