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
				button.add("_"); button.add(Integer.toString(i) + Integer.toString(j));
				row.add(button);
			}
			this.grid_array.add(row);
		}
	}
//	public void setMarkup() {
//		grid_markup.setKeyboard(grid_array);
//	}
	
	public Boolean modifyGrid(String action, String side) {
		
		int x_coord = 0;
		int y_coord = 0;
		
		switch (action.charAt(0)) {
		case 'A': x_coord = 0; break;
		case 'B': x_coord = 1; break;
		case 'C': x_coord = 2; break;
		}
		y_coord = Integer.parseInt(String.valueOf(action.charAt(1))) - 1;
		
		if (this.grid_array.get(y_coord).get(x_coord).get(0) == "_") {
			this.grid_array.get(y_coord).get(x_coord).set(0, side);
			return false;
		}
		return true;
		
	}
}
