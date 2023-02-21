package logic;

/**
 * This class represents the result in a round.
 * 
 * @author shiran
 */
public class Result {
	
	private String roundNumber, player1Status, player2Status;
	
	/**
	 * This method is the constructor for Result.
	 * @param roundNumber - the number of the round
	 * @param player1Status - the status of player1 ("V" - for victory in that round, "-" - for draw)
	 * @param player2Status - the status of player2 ("V" - for victory in that round, "-" - for draw)
	 */
	public Result(String roundNumber, String player1Status, String player2Status) {
		super();
		this.roundNumber = roundNumber;
		this.player1Status = player1Status;
		this.player2Status = player2Status;
	}

	public String getRoundNumber() {
		return roundNumber;
	}

	public void setRoundNumber(String roundNumber) {
		this.roundNumber = roundNumber;
	}

	public String getPlayer1Status() {
		return player1Status;
	}

	public void setPlayer1Status(String player1Status) {
		this.player1Status = player1Status;
	}

	public String getPlayer2Status() {
		return player2Status;
	}

	public void setPlayer2Status(String player2Status) {
		this.player2Status = player2Status;
	}
}
