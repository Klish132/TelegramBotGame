package telegrambotgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class Ttt {
	
	
	public TttGrid ttt_grid = new TttGrid();
	public TttPlayer ttt_player = new TttPlayer();
	public TttAI ttt_AI = new TttAI();
	
	public boolean game_status = false;
	public ArrayList<String> output_msg = new ArrayList<String>();
	
	public ArrayList<String> ttt_switch(String input_msg) {
		
		switch(input_msg) {
		case "/exit":
			ttt_status();
			output_msg.add("Stopping TickTackToe...");
			break;
		case "x":
			ttt_setSide("x", "o"); break;
		case "o":
			ttt_setSide("o", "x"); break;
		default:
			ttt_doAction(input_msg);
			if (ttt_checkGameOver() == true) {
				ttt_status();
			}
			break;
		}
		return output_msg;
	}
	
	public void ttt_status() {
		this.game_status = (this.game_status == true) ? false : true;
		this.ttt_grid = new TttGrid();
		this.ttt_player = new TttPlayer();
		this.ttt_AI = new TttAI();
	}
	
	public String ttt_drawGrid(TttGrid grid) {
		StringJoiner sj = new StringJoiner(System.lineSeparator());
		for(char[] row : grid.grid_array) {
			sj.add(Arrays.toString(row));
		}
		return sj.toString();
	}
	
	public void ttt_setSide(String player_side, String ai_side) {
		if (this.ttt_player.player_side == '_') {
			this.ttt_player.setSide(player_side);
			this.ttt_AI.setSide(ai_side);
			output_msg.add("you are now playing as: " + player_side);
			output_msg.add(ttt_drawGrid(this.ttt_grid));
		}
	}
	public void ttt_doAction(String move) {
		if (this.ttt_player.player_side != '_') {
			Set<String> good_moves = new HashSet<String>(); {
				for (int i=1; i<=3; i++) {
					good_moves.add("A" + String.valueOf(i));
					good_moves.add("B" + String.valueOf(i));
					good_moves.add("C" + String.valueOf(i));
				}
			}
			if (good_moves.contains(move)) {
				if (this.ttt_grid.modifyGrid(move, this.ttt_player.player_side) == false) {
					this.ttt_AI.doMove(this.ttt_grid);
					output_msg.add(ttt_drawGrid(this.ttt_grid));
				} else {
					output_msg.add("This spot is taken!");
				}
			}
		}
		else {
			output_msg.add("Something is wrong, please restart ttt...");
		}
	}
	public boolean ttt_checkGameOver() {
		
		boolean result = true;
		
		switch (this.ttt_AI.checkGameOver(this.ttt_grid)) {
		case "x":
			output_msg.add("Winner: X");
			break;
		case "o":
			output_msg.add("Winner: O");
			break;
		case "xo":
			if (this.ttt_player.player_side == 'x') {
				output_msg.add("Winner: X");
			} else {
				output_msg.add("Winner: O");
			}
			break;
		case "draw":
			output_msg.add("Game ended in a DRAW!");
			break;
		default:
			result = false;
			break;
		}
		return result;
	}
}
