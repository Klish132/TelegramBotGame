package telegrambotgame;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

public class BotCore {

	public String ongoing_game = "None";
	public Ttt ttt_game = new Ttt();
	public Hangman hangman_game = new Hangman();
	//public ArrayList<String> output_msg = new ArrayList<String>();
	public BotMessage message = new BotMessage();
	
	public BotMessage analyze(String input_msg) {
		
		//output_msg.clear();
		
		switch(this.ongoing_game) {
		case "None":
			
			switch(input_msg) {
			case "/ttt":
				this.ongoing_game = "ttt";
				this.ttt_game.ttt_startGame();
				break;
			case "/hangman":
				this.ongoing_game = "hangman";
				this.hangman_game.hm_startGame();
				break;
			}
			break;
			
		case "ttt":
			message = this.ttt_game.ttt_switch(input_msg);
			//output_msg = this.ttt_game.ttt_switch(input_msg);
			if (this.ttt_game.game_status == false) {
				this.ongoing_game = "None";
			}
			break;
		case "hangman":
			message = this.hangman_game.hm_switch(input_msg);
			//output_msg = this.hangman_game.hm_switch(input_msg);
			if (this.hangman_game.game_status == false) {
				this.ongoing_game = "None";
			}
			break;
		}
		return message;
		}
}
