package telegrambotgame;

import java.util.ArrayList;
import java.util.List;

public class BotMessage {
	public String text = "";
	public List<List<List<String>>> markup_array = new ArrayList<>();
	public boolean hasText = false;
	public boolean hasMarkup = false;
	
	public void setMessageText(String input_text) {
		this.text = input_text;
		this.hasText = true;
	}
	
	public void setMessageMarkup(List<List<List<String>>> input_grid) {
		this.markup_array = input_grid;
		this.hasMarkup = true;
	}
	public void setMessageMarkup(List<String> text_list, List<String> callback_list) {
		List<List<List<String>>> markup = new ArrayList<>(); {
			int list_count = text_list.size();
			for (int i=0; i<list_count; i++) {
				List<List<String>> row = new ArrayList<>();
				List<String> button = new ArrayList<>();
				button.add(text_list.get(i)); button.add(callback_list.get(i));
				row.add(button);
				markup.add(row);
				}
		}
		
		this.markup_array = markup;
		this.hasMarkup = true;
	}
	
	public String getMessageText() {
		return text;
	}
	
	public List<List<List<String>>> getMessageMarkup() {
		return markup_array;
	}
}
