package telegrambotgame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
			
			//ArrayList<String> output_msg_list = this.core.analyze(input_msg);
			//for (String message : output_msg_list) {
			//	sendMsg(chat_id, message, markup);
			BotMessage msg = this.core.analyze(input_msg);
			sendMsg(chat_id, msg.getMessageText(), arrayToKeyboard(msg.getMessageMarkup()));
		} else if (update.hasCallbackQuery()) {
			String cb_data = update.getCallbackQuery().getData();
			BotMessage msg = this.core.analyze(cb_data);
			sendMsg(chat_id, msg.getMessageText(), arrayToKeyboard(msg.getMessageMarkup()));
		}
	}
	
	public InlineKeyboardMarkup arrayToKeyboard(List<List<List<String>>> array) {
		List<List<InlineKeyboardButton>> result = new ArrayList<>();
		for (int i=0; i<=2; i++) {
			List<InlineKeyboardButton> row = new ArrayList<>();
			for (int j=0; j<=2; j++) {
				List<String> button_array = array.get(i).get(j);
				row.add(new InlineKeyboardButton().setText(button_array.get(0)).setCallbackData(button_array.get(1)));
			}
			result.add(row);
		}
		return(new InlineKeyboardMarkup().setKeyboard(result));
	}
	
	public synchronized void sendMsg(long chatId, String s, InlineKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(false);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        sendMessage.setReplyMarkup(markup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
        	e.printStackTrace();
        }
    }
	
	@Override
	public String getBotToken() {
		System.out.print(System.getenv("tgbot_token"));
		return System.getenv("tgbot_token");
	}

}