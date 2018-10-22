package telegrambotgame;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.ArrayList;

public class BotCore {

	public String ongoing_game = "None";
	public TttGrid ttt_grid = new TttGrid();
	public TttPlayer ttt_player = new TttPlayer();
	public TttAI ttt_AI = new TttAI();
	public ArrayList<String> output_msg = new ArrayList<String>();
	
	public ArrayList<String> Analyze(String input_msg) {
		
		output_msg.clear();
		
		switch(this.ongoing_game) {
		case "None":
			
			switch(input_msg) {
			case "/ttt":
				this.ongoing_game = "ttt";
				output_msg.add("Please enter your side: \n x or o");
				break;
			} break;
			case "/mb":
				break;
			case "/hangman":
				break;
			
		case "ttt":
			
			switch(input_msg) {
			case "/exit":
				reset_bot();
				output_msg.add("Stopping TickTackToe...");
				break;
			case "x":
				ttt_setSide("x", "o"); break;
			case "o":
				ttt_setSide("o", "x"); break;
			default:
				ttt_doAction(input_msg);
				if (ttt_checkGameOver() == true) {
					reset_bot();
				}
				break;
			}
		}
		
		return output_msg;
		}
	
	public void reset_bot( ) {
		this.ongoing_game = "None";
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
