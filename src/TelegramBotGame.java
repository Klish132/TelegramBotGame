
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramBotGame extends TelegramLongPollingBot {
	
	public String ongoing_game = "None";
	public Grid ttt_grid = new Grid();
	public Player ttt_player = new Player();
	public AI ttt_AI = new AI();
     
	@Override
	public String getBotUsername() {
		return "KlishLordOfLights_bot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		String input_msg = update.getMessage().getText();
		long chat_id = update.getMessage().getChatId();
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			switch(this.ongoing_game) {
			case "None":
				
				switch(input_msg) {
				case "/ttt":
					this.ongoing_game = "ttt";
					sendMsg(update.getMessage().getChatId().toString(), "Please enter your side (x, o): ");
					break;
				} break;
				case "/mb":
					break;
				case "/hangman":
					break;
				
			case "ttt":
				
				Set<String> good_moves = new HashSet<String>(); {
					
					for (int i=1; i<=3; i++) {
						good_moves.add("A" + String.valueOf(i));
						good_moves.add("B" + String.valueOf(i));
						good_moves.add("C" + String.valueOf(i));
					}
				}
				
				switch(input_msg) {
				case "x":
					if (this.ttt_player.player_side == '_') {
						this.ttt_player.setSide("x");
						this.ttt_AI.setSide("o");
						sendMsg(update.getMessage().getChatId().toString(), "you are now playing x");
						sendMsg(update.getMessage().getChatId().toString(), ttt_drawGrid(this.ttt_grid));
					} break;
				case "o":
					if (this.ttt_player.player_side == '_') {
						this.ttt_player.setSide("o");
						this.ttt_AI.setSide("x");
						sendMsg(update.getMessage().getChatId().toString(), "you are now playing o");
						sendMsg(update.getMessage().getChatId().toString(), ttt_drawGrid(this.ttt_grid));
					} break;
				case "/exit":
					this.ongoing_game = "None";
					this.ttt_grid = new Grid();
					this.ttt_player = new Player();
					sendMsg(update.getMessage().getChatId().toString(), "Stopping ttt...");
					break;
				default:
					if (this.ttt_player.player_side != '_') {
						if (good_moves.contains(input_msg)) {
							if (this.ttt_grid.modifyGrid(input_msg, this.ttt_player.player_side).equals("ok")) {
								this.ttt_AI.doMove(this.ttt_grid);
								sendMsg(update.getMessage().getChatId().toString(), ttt_drawGrid(this.ttt_grid));
							}
						} break;
					}
				}
			}
		}
	}
				
				
	public String ttt_drawGrid(Grid grid) {
		StringJoiner sj = new StringJoiner(System.lineSeparator());
		for(char[] row : grid.grid_array) {
			sj.add(Arrays.toString(row));
		}
		return sj.toString();
	}
	
	public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
    }
	
	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "680527118:AAHs4wOyPxfKuFgRQhvdQXlhK5AKVduiaOQ";
	}

}