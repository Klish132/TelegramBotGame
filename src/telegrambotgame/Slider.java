package telegrambotgame;

import java.util.ArrayList;
import java.util.List;

public class Slider {
	
	public SliderGrid slider_grid = new SliderGrid();
	
	public boolean game_status = false;
	public BotMessage message = new BotMessage();
	
	public BotMessage slider_switch(String input_msg) {
		
		switch(input_msg) {
		case "/exit":
			this.message.setMessageText("Stopping Slider...");
			slider_status();
			break;
		case "3":
			slider_setSize(3); break;
		case "4":
			slider_setSize(4); break;
		case "5":
			slider_setSize(5); break;
		case "6":
			slider_setSize(6); break;
		default:
			slider_doAction(input_msg);
			if (slider_checkGameOver() == true) {
				slider_status();
			}
			break;
		}
		return this.message;
	}
	
	public void slider_status() {
		this.game_status = (this.game_status == true) ? false : true;
		this.slider_grid = new SliderGrid();
	}
	
	public BotMessage slider_startGame( ) {
		this.message.setMessageText("Please choose grid size...");
		List<String> text_list = new ArrayList<String>();
		text_list.add("3");
		text_list.add("4");
		text_list.add("5");
		text_list.add("6");
		List<String> callback_list = new ArrayList<String>();
		callback_list.add("3");
		callback_list.add("4");
		callback_list.add("5");
		callback_list.add("6");
		this.message.setMessageMarkup(text_list, callback_list);
		slider_status();
		return this.message;
	}
	
	public BotMessage slider_setSize(int size) {
		this.slider_grid.createGrid(size);
		for(int i=0;i<size*size;i++) {
			this.slider_grid.modifyGrid(this.slider_grid.randomMove());
		}
		this.message.setMessageText("You are now playing Slider with grid size of " + size + ".");
		this.message.setMessageMarkup(this.slider_grid.grid_array);
		return message;
	}
	
	public void slider_doAction(String input_msg) {
		if(this.slider_grid.modifyGrid(input_msg) == false) {
			this.message.setMessageText("You are playing the Slider.");
		} else {
			this.message.setMessageText("You can't move there!");
		}
		this.message.setMessageMarkup(this.slider_grid.grid_array);
	}
	
	public boolean slider_checkGameOver() {
		if(this.slider_grid.checkGameOver()) {
			return true;
		}
		return false;
	}
}
