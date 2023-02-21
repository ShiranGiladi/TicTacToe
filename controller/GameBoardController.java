package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Player;
import logic.Result;

/**
 * This class represents the "GameBoard" window.
 * Each player in his turn can choose a slot (which has not already taken).
 * The mark ("X"/"O") for each player will be in the color that he choose in the Menu window.
 * On the right side of the window there his a results table that summarize (show who won in specific round by putting "V" on the right place
 * or put "-" in case that round ended in draw) all the rounds that made in that game.
 * After each round there is a pop-up window ("PopUpWindow") that show who won in the current round, in the final round it will show
 * the winner of the all game.
 * On the left-up side of the window there is a button for getting back to the menu.
 * 
 * @author shiran
 */
public class GameBoardController {
	
	@FXML
    private Button backToMenuBtn;
    @FXML
    private TableColumn<Result, String> player1Column;
    @FXML
    private TableColumn<Result, String> player2Column;
    @FXML
    private TableColumn<Result, String> roundNumberColumn;
    @FXML
    private TableView<Result> tableResults;
    @FXML
    private Text textOFnumber, textRoundsNumber, textCurrPlayerTurn;
    @FXML
    private GridPane gridPaneBoard;
    
    private Player p1, p2, playerInMove;
    private String roundsNumber;
    private int currPlayerTurn, countWinsForPlayer1 = 0, countWinsForPlayer2 = 0, countClickOnButtonPerRound = 0;
    private String[][] gameBoard = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
    private ObservableList<Result> result = FXCollections.observableArrayList();
    private boolean drawForRound = false;
    
    /**
     * This method initializes the table that shows on the screen.
     */
    public void initialize() {
    	roundNumberColumn.setCellValueFactory(new PropertyValueFactory<Result, String>("roundNumber"));
    	player1Column.setCellValueFactory(new PropertyValueFactory<Result, String>("player1Status"));
    	player2Column.setCellValueFactory(new PropertyValueFactory<Result, String>("player2Status"));
    }
    
    /**
     * This method set the results table after each round.
     * @param event
     * @return list of results
     * @throws IOException
     */
    public ObservableList<Result> setTable(ActionEvent event) throws IOException {
    	String player1Status = "", player2Status = "";
    	if(drawForRound && countClickOnButtonPerRound == 9) {
    		player1Status = "-";
    		player2Status = "-";
    	}
    	else {
    		if(currPlayerTurn == 1) {
        		player1Status = "V";
        		countWinsForPlayer1++;
        	}
    		else {
    			player2Status = "V";
    			countWinsForPlayer2++;
    		}
    	}
    	result.add(new Result(textRoundsNumber.getText(), player1Status, player2Status));
    	
    	tableResults.setItems(result);
		tableResults.refresh();
		
		showPopUpWindow(event);
		
    	return result;
    }
    
    /**
     * This method opens the PopUpWindow window.
     * @param event
     * @throws IOException
     */
    private void showPopUpWindow(ActionEvent event) throws IOException {
    	String endGame = "0", winnerInTheAllGame = "", winnerForThisRound = playerInMove.getName();
    	if(textRoundsNumber.getText().equals(roundsNumber)) {
    		if(countWinsForPlayer1 > countWinsForPlayer2)
    			winnerInTheAllGame = p1.getName();
    		else if(countWinsForPlayer1 < countWinsForPlayer2)
    			winnerInTheAllGame = p2.getName();
    		else
    			winnerInTheAllGame = "Draw";
    		endGame = "1";
    	}
    	else {
    		if(drawForRound && countClickOnButtonPerRound == 9)
    			winnerForThisRound = "Draw";
    	}
    	
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PopUpWindow.fxml"));
    	Parent root = (Parent) loader.load();
    	PopUpWindowController control = loader.getController();
    	control.start(endGame, winnerForThisRound, winnerInTheAllGame);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.showAndWait();
		
		if(endGame.equals("1")) {
			openMenuWindow();
    		((Node)event.getSource()).getScene().getWindow().hide();
		}
		else {
			resetGameBoard();
			textRoundsNumber.setText(Integer.parseInt(textRoundsNumber.getText()) + 1 + "");
		}
    }
    
    /**
     * This method reset the gameBoard, gridPaneBoard and initialize drawForRound to false and countClickOnButtonPerRound to 0.
     */
    private void resetGameBoard() {
    	drawForRound = false;
    	countClickOnButtonPerRound = 0;
    	
    	for(int i=0; i<3; i++)
    		for(int j=0; j<3; j++)
    			gameBoard[i][j] = "";
    	
    	setButton();
    }
    
    /**
     * This method set the buttons in the gridPaneBoard.
     */
    private void setButton() {
    	gridPaneBoard.getChildren().clear();
    	for(int i=0; i<3; i++) {
    		for(int j=0; j<3; j++) {
    			Button btn = new Button();
    			btn.setStyle("-fx-font-family: Franklin Gothic Heavy; -fx-font-weight: bold; -fx-font-size: 48;"
    					+ " -fx-min-height: 101; -fx-min-width: 104");
    			btn.setOnAction(event -> {
					try {getBtn(event);} 
					catch (IOException e) {}
				});
    			gridPaneBoard.add(btn, i, j);
    		}
    	}
    }
    
    /**
     * This method put the correct mark for each player in his turn.
     * @param event
     * @throws IOException
     */
    @FXML
    void getBtn(ActionEvent event) throws IOException {    	
       	Button currBtn = (Button) event.getSource();
       	if(currBtn.getText().isEmpty()) {
       		currBtn.setText(playerInMove.getMark());
           	currBtn.setTextFill(playerInMove.getColor());
           	countClickOnButtonPerRound++;
       	}
       	else
       		return;
       	       	
       	int column = GridPane.getColumnIndex(currBtn);
        int row = GridPane.getRowIndex(currBtn);
            	
    	markLocation(column, row);
    	
    	//check victory in column or row
    	if(checkVictoryInColumOrRow(column, row)) {
    		setTable(event);
    		return;
    	}
    	
    	//check if button in main diagonal
    	if((column==0 && row==0) || (column==1 && row==1) || (column==2 && row==2)) {
    		//check victory in main diagonal
    		if(checkVictoryInMainDiagonal()) {
    			setTable(event);
    			return;
    		}
    	}
    	
    	//check if button in secondary diagonal
    	if((column==2 && row==0) || (column==1 && row==1) || (column==0 && row==2)) {
    		//check victory in secondary diagonal
    		if(checkVictoryInSecondaryDiagonal()) {
    			setTable(event);
    			return;
    		}
    	}
    	
    	//if there is no winner for this round (countClickOnButtonPerRound == 9), set drawForRound to true.
    	if(countClickOnButtonPerRound == 9) {
    		drawForRound = true;
    		setTable(event);
    	}
    	
    	setNextPlayerTurn();
    	setTurn();
    }
    
    /**
     * This method mark the slot corresponding to the column and row.
     * @param column
     * @param row
     */
    private void markLocation(int column, int row) {
    	gameBoard[row][column] = playerInMove.getMark();
    }
    
    /**
     * This method check victory in column or row.
     * @param column
     * @param row
     * @return true if player won
     */
    private boolean checkVictoryInColumOrRow(int column, int row) {
    	if(gameBoard[row][0].equals(gameBoard[row][1]) && gameBoard[row][1].equals(gameBoard[row][2]))
    		return true;
    	if(gameBoard[0][column].equals(gameBoard[1][column]) && gameBoard[1][column].equals(gameBoard[2][column]))
    		return true;
    	return false;
    }
    
    /**
     * This method check victory in main diagonal.
     * @return true if player won
     */
    private boolean checkVictoryInMainDiagonal() {
    	if(gameBoard[0][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[2][2]))
    		return true;
    	return false;
    }
    
    /**
     * This method check victory in secondary diagonal.
     * @return true if player won
     */
    private boolean checkVictoryInSecondaryDiagonal() {
    	if(gameBoard[2][0].equals(gameBoard[1][1]) && gameBoard[1][1].equals(gameBoard[0][2]))
    		return true;
    	return false;
    }
    
    /**
     * This method opens a pop-up window ("GoToMenuPopUpWindow").
     * @param event
     * @throws IOException
     */
    @FXML
    void getBackToMenuBtn(ActionEvent event) throws IOException {
    	Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/GoToMenuPopUpWindow.fxml"));
    	Parent root = (Parent) loader.load();
    	GoToMenuPopUpWindowController control = loader.getController();
    	control.setEvent(event);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.showAndWait();
    }
    
    /**
     * This method opens the Menu window.
     * @throws IOException
     */
    private void openMenuWindow() throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("/gui/Menu.fxml"));
		Stage primaryStage = new Stage();
		Scene scene = new Scene(root);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
    /**
     * This method initialize variables at the beginning of any game.
     * @param roundsNumber
     * @param p1
     * @param p2
     */
    public void start(String roundsNumber, Player p1, Player p2) {
    	textOFnumber.setText("Of " + roundsNumber);
    	this.roundsNumber = roundsNumber;
    	this.p1 = p1;
    	this.p2 = p2;
    	player1Column.setText(p1.getName());
    	player2Column.setText(p2.getName());
    	setButton();
    	randomPlayerToStart();
    	setTurn();
    }
    
    /**
     * This method random number (1 or 2) for player to start.
     */
    private void randomPlayerToStart() {
    	currPlayerTurn = (Math.random() <= 0.5) ? 1 : 2;
    }
    
    /**
     * This method show the name for the player in turn.
     */
    private void setTurn() {
    	if(currPlayerTurn == 1) {
    		textCurrPlayerTurn.setText(p1.getName() + "'s turn");
    		playerInMove = p1;
    	}
    	else {
    		textCurrPlayerTurn.setText(p2.getName() + "'s turn");
    		playerInMove = p2;
    	}
    }
    
    /**
     * This method change the turn to the next player.
     */
    private void setNextPlayerTurn() {
    	if(currPlayerTurn == 1)
    		currPlayerTurn = 2;
    	else
    		currPlayerTurn = 1;
    }
}
