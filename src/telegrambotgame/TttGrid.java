package telegrambotgame;

import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TttGrid {
	
//	public char[][] grid_array = new char[3][3]; {
//		for (int i=0; i<=2; i++) {
//			for (int j=0; j<=2; j++) {
//				grid_array[i][j] = '_';
//			}
//		}
//	}
	
	//public InlineKeyboardMarkup grid_markup = new InlineKeyboardMarkup();
	public List<List<List<String>>> grid_array = new ArrayList<>(); {
		for (int i=0; i<=2; i++) {
			List<List<String>> row = new ArrayList<>();
			for (int j=0; j<=2; j++) {
				List<String> button = new ArrayList<>();
				button.add("_"); button.add(String.valueOf(i) + String.valueOf(j));
				row.add(button);
			}
			this.grid_array.add(row);
		}
	}
//	public void setMarkup() {
//		grid_markup.setKeyboard(grid_array);
//	}
	
	public Boolean modifyGrid(String action, String side) {
		
		int x_coord = Integer.parseInt(String.valueOf(action.charAt(0)));
		int y_coord = Integer.parseInt(String.valueOf(action.charAt(1)));
		
		System.out.println(x_coord + " " + y_coord);
		
		if (this.grid_array.get(x_coord).get(y_coord).get(0) == "_") {
			this.grid_array.get(x_coord).get(y_coord).set(0, side);
			return false;
		}
		return true;
		
	}
}
