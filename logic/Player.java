package logic;

import javafx.scene.paint.Paint;

/**
 * This class represents the player.
 * 
 * @author shiran
 */
public class Player {
	
	private String name, mark;
	private Paint color;
	
	/**
	 * This method is the constructor for Player.
	 * @param name - the name of the player
	 * @param color - the color that chosen by the player
	 * @param mark - the mark of that player ("X"/"O")
	 */
	public Player(String name, Paint color, String mark) {
		super();
		this.name = name;
		this.color = color;
		this.mark = mark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Paint getColor() {
		return color;
	}

	public void setColor(Paint color) {
		this.color = color;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}
}
