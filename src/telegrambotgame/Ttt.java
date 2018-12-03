package telegrambotgame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Ttt {
	
	
	public TttGrid ttt_grid = new TttGrid();
	public TttPlayer ttt_player = new TttPlayer();
	public TttAI ttt_AI = new TttAI();
	
	public boolean game_status = false;
	//public ArrayList<String> output_msg = new ArrayList<String>();
	public BotMessage message = new BotMessage();
	
	public BotMessage ttt_switch(String input_msg) {
		
		switch(input_msg) {
		case "/exit":
			ttt_status();
			//output_msg.add("Stopping TickTackToe...");
			message.setMessageText("Stopping TickTackToe...");
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
		//return output_msg;
		return message;
	}
	
	public void ttt_status() {
		this.game_status = (this.game_status == true) ? false : true;
		this.ttt_grid = new TttGrid();
		this.ttt_player = new TttPlayer();
		this.ttt_AI = new TttAI();
	}
	
	public void ttt_startGame( ) {
		message.setMessageText("Please enter your side: \n x or o");
		ttt_status();
	}
	
	public void ttt_setSide(String player_side, String ai_side) {
		if (this.ttt_player.player_side == "_") {
			this.ttt_player.setSide(player_side);
			this.ttt_AI.setSide(ai_side);
			//output_msg.add("you are now playing as: " + player_side);
			//output_msg.add(ttt_drawGrid(this.ttt_grid));
			message.setMessageText("you are now playing as: " + player_side);
			message.setMessageMarkup(this.ttt_grid.grid_array);
		}
	}
	public void ttt_doAction(String move) {
		if (this.ttt_player.player_side != "_") {
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
					message.setMessageText("Your turn!!");
				} else {
					message.setMessageText("This spot is taken!");
				}
				message.setMessageMarkup(this.ttt_grid.grid_array);
			}
		} else {
			message.setMessageText("Something is wrong, please restart ttt...");
		}
	}
	public boolean ttt_checkGameOver() {
		
		boolean result = true;
		
		switch (this.ttt_AI.checkGameOver(this.ttt_grid)) {
		case "x":
			message.setMessageText("Winner: X");
			break;
		case "o":
			message.setMessageText("Winner: O");
			break;
		case "xo":
			if (this.ttt_player.player_side == "x") {
				message.setMessageText("Winner: X");
			} else {
				message.setMessageText("Winner: O");
			}
			break;
		case "draw":
			message.setMessageText("Game ended in a DRAW!");
			break;
		default:
			result = false;
			break;
		}
		return result;
	}
}
