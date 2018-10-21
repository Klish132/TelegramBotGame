package telegrambotgame;
public class TttPlayer {
	
	public char player_side = '_';
	
	public void setSide(String side) {
		this.player_side = side.charAt(0);
	}
}
