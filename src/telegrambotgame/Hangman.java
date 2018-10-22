package telegrambotgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {
	
	public String hm_word;
	public boolean game_status = false;
	
	public ArrayList<Character> char_list;
	public ArrayList<Character> locked_list;
	public int fail_counter = 0;
	
	public ArrayList<String> output_msg = new ArrayList<String>();
	
	public ArrayList<String> hm_switch(String input_msg) {
		
		switch(input_msg) {
		case "/exit":
			hm_status();
			output_msg.add("Stopping Hangman...");
			break;
		default:
			hm_doAction(input_msg);
			if (hm_checkGameOver() == true) {
				hm_status();
			}
			break;
		}
		return output_msg;
	}
	
	public void hm_status() {
		this.game_status = (this.game_status == true) ? false : true;
	}
	
	public void hm_startGame() {
		output_msg.add("");
		hm_status();
		this.hm_word = hm_getWord();
		hm_fillLists(this.hm_word);
	}
	
	public void hm_doAction(String input_msg) {
		
		char letter = input_msg.charAt(0);
		int change_count = 0;
		
		if (!this.char_list.contains(letter)) {
			this.fail_counter++;
			output_msg.add("This letter does not exist.");
		} else {
			for (int i=0; i<char_list.size(); i++) {
				if (char_list.get(i) == letter) {
					locked_list.set(i, letter);
					change_count++;
				}
			}
			if (change_count == 0) {
				output_msg.add("This letter was already unlocked!");
			} else {
				output_msg.add(hm_joinList(locked_list));
			}
		}	
	}
	
	public String hm_joinList(ArrayList<Character> list) {
		
		StringBuilder result = new StringBuilder();
		String prefix = "";
		
		for (char letter : list) {
			result.append(prefix);
			prefix = " ";
			result.append(letter);
		}
		return result.toString();
	}
	
	public String hm_getWord() {
		
		ArrayList<String> words = new ArrayList<String>();
		Random random_index = new Random(words.size());
		
		try {
			Scanner file_scanner = new Scanner(new File("hangman_words.txt"));
				
			while (file_scanner.hasNext()) {
				words.add(file_scanner.nextLine());
			}
			file_scanner.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return words.get(random_index.nextInt());
	}
	
	public void hm_fillLists(String word) {
		for (char letter : word.toCharArray()) {
			char_list.add(letter);
			locked_list.add('_');
		}
	}
	
	public boolean hm_checkGameOver() {
		if (this.fail_counter == 7) {
			return true;
		}
		return false;
	}
}
