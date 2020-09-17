import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * The ThankYouScene is displayed when users enter and confirm new member 
 * information (number of meters, revenue, cost of power) in the application.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class ThankYouScene extends Scene {

	private BorderPane myRoot;
	private Button mainMenuButton;
	
	public ThankYouScene(Parent root){
		super(root);
		myRoot = (BorderPane) root;
		String style = "-fx-background-color: rgba(255, 245, 208, 0.3);";
		myRoot.setStyle(style);
		myRoot.setMinWidth(600);
		myRoot.setMinHeight(400);
		
		Text thankText = new Text("Thank you for submitting!");
		thankText.setFont(new Font(40));
		
		/* Packages image into application */
		InputStream input = this.getClass().getResourceAsStream("piggyBank.png");
		Image image = new Image(input);
		ImageView img = new ImageView(image);
		img.setFitHeight(200); 
	    img.setFitWidth(300);       
	    img.setPreserveRatio(true); 
		
		mainMenuButton = new Button("Main Menu"); 
		myRoot.setTop(thankText);
		myRoot.setCenter(img);
		myRoot.setBottom(mainMenuButton);
		BorderPane.setAlignment(thankText, Pos.CENTER);
		BorderPane.setAlignment(mainMenuButton, Pos.CENTER);
		BorderPane.setAlignment(img, Pos.CENTER);
		myRoot.setPadding(new Insets(40, 40, 40, 40));
	}
	
	public Button getMainMenuButton() {return mainMenuButton;}
}