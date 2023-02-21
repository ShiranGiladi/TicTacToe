package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * This class represents the "PopUpWindow" window.
 * This window will show information about who won in a round, or who won the all game, in case that this is the final round.
 * 
 * @author shiran
 */
public class PopUpWindowController {

    @FXML
    private Button btn;
    @FXML
    private Text textMsg;
    
    /**
     * This method closes the "PopUpWindow" window.
     * @param event
     * @throws IOException
     */
    @FXML
    void getBtn(ActionEvent event) throws IOException {
		((Node)event.getSource()).getScene().getWindow().hide();
    }
    
    /**
     * This method set the information about the winner for the round or all game on the window.
     * @param endGame
     * @param nameWonPlayer
     * @param winnerInTheAllGame
     */
    public void start(String endGame, String nameWonPlayer, String winnerInTheAllGame) {
    	if(endGame.equals("1")) {
    		if(winnerInTheAllGame.equals("Draw"))
    			textMsg.setText(winnerInTheAllGame + " - You Both Won ;)");
    		else
    			textMsg.setText(winnerInTheAllGame + " You Won The Game");
    		btn.setText("Back To Menu");
    	}
    	else {
    		if(nameWonPlayer.equals("Draw"))
    			textMsg.setText(nameWonPlayer + " - No Winner This Round");
    		else
    			textMsg.setText(nameWonPlayer + " You Won This Round");
    		btn.setText("Next Round");
    	}    	
    }
}
