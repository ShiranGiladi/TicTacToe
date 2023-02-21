package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Player;

/**
 * This class represents the "Menu" window.
 * Each player choose name and color.
 * In addition you can decide how much rounds to play
 * 
 * @author shiran
 */
public class MenuController {
	
    @FXML
    private Button choosenColorForP1, choosenColorForP2;
    @FXML
    private Button startBtn, exitBtn;
    @FXML
    private TextField textFieldPlayer1, textFieldPlayer2, textFieldRoundsNumber;
    @FXML
    private Text errorMsg;
    @FXML
    private ColorPicker colorPickerP1, colorPickerP2;
    
    private Paint choosenColorP1 = new Color(0, 0, 0, 1), choosenColorP2 = new Color(0, 0, 0, 1);
    
    /**
     * This method save the current color for player1 and show his decision on choosenColorForP1 button
     * @param event
     */
    @FXML
    void getColorP1(ActionEvent event) {
    	choosenColorP1 = colorPickerP1.getValue();
    	String setCurColor = "-fx-background-color: " + choosenColorP1 + ";";
    	choosenColorForP1.setStyle(setCurColor);
    	choosenColorForP1.setBackground(new Background(new BackgroundFill(choosenColorP1, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * This method save the current color for player2 and show his decision on choosenColorForP2 button
     * @param event
     */
    @FXML
    void getColorP2(ActionEvent event) {
    	choosenColorP2 = colorPickerP2.getValue();
    	String setCurColor = "-fx-background-color: " + choosenColorP2 + ";";
    	choosenColorForP2.setStyle(setCurColor);
    	choosenColorForP2.setBackground(new Background(new BackgroundFill(choosenColorP2, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    
    /**
     * This method closes the current window ("Menu") and opens a "GameBoard" window.
     * In addition, there is input check for relevant TextFields
     * @param event
     * @throws IOException
     */
    @FXML
    void getStartBtn(ActionEvent event) throws IOException {
    	String roundsNumber = textFieldRoundsNumber.getText();
    	if(!isNumeric(roundsNumber) || roundsNumber.isEmpty())
    		errorMsg.setText("Rounds must be a positive integer number!");
    	else if (roundsNumber.equals("0"))
    		errorMsg.setText("Rounds must be at least 1!");
    	else if(roundsNumber.charAt(0) == '0')
    		errorMsg.setText("Rounds number can not start with ZERO");
    	else if (textFieldPlayer1.getText().isEmpty() || textFieldPlayer2.getText().isEmpty())
    		errorMsg.setText("Players Must choose a name");
    	else {
    		Player p1 = new Player(textFieldPlayer1.getText(), choosenColorP1, "X");
    		Player p2 = new Player(textFieldPlayer2.getText(), choosenColorP2, "O");
    		
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GameBoard.fxml"));
	    	Parent root = (Parent) loader.load();
	    	GameBoardController control = loader.getController();
	    	control.start(roundsNumber, p1, p2);
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
    	}
    }
    
    /**
     * This method check if String is a positive integer
     * @param str
     * @return
     */
    private boolean isNumeric(String str) {
        for (char c : str.toCharArray())
            if (!Character.isDigit(c)) return false;
        return true;
    }
    
    /**
     * This method opens the Menu window.
     * @param primaryStage
     * @throws IOException
     */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Menu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * This method closes the current window ("Menu").
	 * @param event
	 */
    @FXML
    void getExitBtn(ActionEvent event) {
    	((Node)event.getSource()).getScene().getWindow().hide();
    }
}
