package telegrambotgame;

import java.util.ArrayList;
import java.util.List;

public class HangmanWord {

	public ArrayList<Character> char_array = new ArrayList<Character>();	
	public List<List<List<String>>> locked_array = new ArrayList<>();
	
	public void fillLists(String word) {
		List<List<String>> row = new ArrayList<>();
		for (int i=0; i<=word.length()-1; i++) {
			List<String> button = new ArrayList<>();
			button.add("_"); button.add("None");
			row.add(button);
			this.char_array.add(word.charAt(i));
		}
		this.locked_array.add(row);
	}
	
	public boolean contains(String input) {
		char letter = input.charAt(0);
		if (this.char_array.contains(letter)) {
			return true;
		}
		return false;
	}
	
	public int modifyArray(String input) {
		
		char letter = input.charAt(0);
		int change_count = 0;
		
		for (int i=0; i<this.char_array.size(); i++) {
			if (this.char_array.get(i) == letter) {
				System.out.println(this.locked_array.get(0).size());
				this.locked_array.get(0).get(i).set(0, String.valueOf(letter));
				change_count++;
			}
		}
		return change_count;
	}
}
