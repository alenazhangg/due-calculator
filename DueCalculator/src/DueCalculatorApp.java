import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The DueCalculatorApp starts the running of the application.
 * 
 * @author	Alena Zhang
 * @since	2020-03-11
 */
public class DueCalculatorApp extends Application{
	
	private View myView;
	
	public DueCalculatorApp() {
		myView = new View();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception {
		myView.setStage(primaryStage);
	}
}


