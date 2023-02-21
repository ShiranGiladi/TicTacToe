package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class represents the "GoToMenuPopUpWindow" window.
 * You can choose rather to go to menu - and by that restart the game, or rather keep play - and continue from where you stop.
 * 
 * @author shiran
 */
public class GoToMenuPopUpWindowController {
	
    @FXML
    private Button goToMenuBtn;
    @FXML
    private Button keepPlayBtn;
    
    private ActionEvent boardEvent;
    
    /**
     * This method closes the "GoToMenuPopUpWindow" and "GameBoard" windows and opens the "Menu" window.
     * @param event
     * @throws IOException
     */
    @FXML
    void getGoToMenu(ActionEvent event) throws IOException {
    	//close "GoToMenuPopUpWindow" window
    	((Node)event.getSource()).getScene().getWindow().hide();
    	//close "GameBoard" window
    	((Node)boardEvent.getSource()).getScene().getWindow().hide();
    	//open "Menu" window
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/Menu.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
    /**
     * This method closes the "GoToMenuPopUpWindow" window.
     * @param event
     */
    @FXML
    void getKeepPlayBtn(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    /**
     * This method set boardEvent to the "GameBoard" window event.
     * @param event
     */
	public void setEvent(ActionEvent event) {
		this.boardEvent = event;
	}
}
