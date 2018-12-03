package telegrambotgame;

import java.util.ArrayList;
import java.util.List;

public class BotMessage {
	public String text = "";
	public List<List<List<String>>> markup_array = new ArrayList<>();
	
	public void setMessageText(String input_text) {
		this.text = input_text;
	}
	
	public void setMessageMarkup(List<List<List<String>>> input_grid) {
		this.markup_array = input_grid;
	}
	
	public String getMessageText() {
		return text;
	}
	
	public List<List<List<String>>> getMessageMarkup() {
		return markup_array;
	}
}
