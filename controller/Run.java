package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
	public static void main( String args[] ) throws Exception {  
		launch(args);  
	} //end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		MenuController cFrame = new MenuController(); //create Client Connect Frame
		cFrame.start(primaryStage);
	}
}
