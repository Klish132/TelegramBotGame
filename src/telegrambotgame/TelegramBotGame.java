package telegrambotgame;
import java.util.ArrayList;
import java.util.Map;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramBotGame extends TelegramLongPollingBot {
	
	BotCore core = new BotCore();
     
	@Override
	public String getBotUsername() {
		return "KlishLordOfLights_bot";
	}

	@Override
	public void onUpdateReceived(Update update) {
		
		String input_msg = update.getMessage().getText();
		long chat_id = update.getMessage().getChatId();
		
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			ArrayList<String> output_msg_list = this.core.Analyze(input_msg);
			for (String message : output_msg_list) {
				sendMsg(chat_id, message);
			}
		}
	}
	
	public synchronized void sendMsg(long chatId, String s) {
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
		return System.getenv("tgbot_token");
	}

}