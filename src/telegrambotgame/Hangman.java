package telegrambotgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Hangman {
	
	public HangmanWord hm_wordList = new HangmanWord();
	
	public String hm_word;
	public boolean game_status = false;
	
	public ArrayList<String> art_list= new ArrayList<String>();
	public int fail_counter = 0;
	
	//public ArrayList<String> output_msg = new ArrayList<String>();
	public BotMessage message = new BotMessage();
	
	public BotMessage hm_switch(String input_msg) {
		
		switch(input_msg) {
		case "/exit":
			hm_status();
			message.setMessageText("Stopping Hangman...");
			break;
		default:
			if (input_msg.length() == 1) {
				hm_doAction(input_msg);
			}
			if (hm_checkGameOver() == true) {
				hm_status();
			}
			break;
		}
		return message;
	}
	
	public void hm_status() {
		this.game_status = (this.game_status == true) ? false : true;
	}
	
	public void hm_startGame() {
		hm_status();
		this.hm_word = hm_getWord();
		this.hm_wordList.fillLists(this.hm_word);
		hm_fillArtList();
		message.setMessageText(this.art_list.get(this.fail_counter));
		message.setMessageMarkup(this.hm_wordList.locked_array);
	}
	
	public void hm_fillArtList() {
		
		try {
			Scanner file_scanner = new Scanner(new File("hangman_art.txt"));
				
			while (file_scanner.hasNext()) {
				this.art_list.add(file_scanner.nextLine());
			}
			file_scanner.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void hm_doAction(String input_msg) {
		
		if (!this.hm_wordList.contains(input_msg)) {
			this.fail_counter++;
			message.setMessageText("This letter does not exist. \n" + this.art_list.get(this.fail_counter));
		} else {
			int change_count = this.hm_wordList.modifyArray(input_msg);
			if (change_count == 0) {
				message.setMessageText("This letter does not exist. \n" + this.art_list.get(this.fail_counter));
			} else {
				message.setMessageText(this.art_list.get(this.fail_counter));
				message.setMessageMarkup(this.hm_wordList.locked_array);
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
		
		try {
			Scanner file_scanner = new Scanner(new File("hangman_words.txt"));
				
			while (file_scanner.hasNext()) {
				words.add(file_scanner.nextLine());
			}
			file_scanner.close();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Random random_index = new Random();
		return words.get(random_index.nextInt(words.size()));
	}
	
	public boolean hm_checkGameOver() {
		if (this.fail_counter == 8) {
			return true;
		}
		return false;
	}
}
