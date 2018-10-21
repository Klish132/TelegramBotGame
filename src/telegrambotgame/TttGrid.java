package telegrambotgame;

public class TttGrid {
	
	public char[][] grid_array = new char[3][3]; {
		for (int i=0; i<=2; i++) {
			for (int j=0; j<=2; j++) {
				grid_array[i][j] = '_';
			}
		}
	}
	
	public Boolean modifyGrid(String action, char side) {
		
		int x_coord = 0;
		int y_coord = 0;
		
		switch (action.charAt(0)) {
		case 'A': x_coord = 0; break;
		case 'B': x_coord = 1; break;
		case 'C': x_coord = 2; break;
		}
		y_coord = Integer.parseInt(String.valueOf(action.charAt(1))) - 1;
		
		if (grid_array[y_coord][x_coord] == '_') {
			grid_array[y_coord][x_coord] = side;
			return false;
		}
		return true;
		
	}
	public char printChar(int x, int y) {
		return grid_array[x][y];
	}
}
