package telegrambotgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SliderGrid {
	
	public int xCoord_x=0;
	public int xCoord_y=0;
	public int grid_size = 0;
	public List<List<List<String>>> grid_array = new ArrayList<>(); 
	
	public void createGrid(int size) {
		this.xCoord_x=size-1;
		this.xCoord_y=size-1;
		this.grid_size = size;
		int count = 0;
		for (int i=0; i<size; i++) {
			List<List<String>> row = new ArrayList<>();
			for (int j=0; j<size; j++) {
				count++;
				List<String> button = new ArrayList<>();
				if(count != size*size) {
					button.add(String.valueOf(count));
				} else {
					button.add("x");
				}
				button.add(String.valueOf(String.valueOf(i)+String.valueOf(j)));
				row.add(button);
			}
			this.grid_array.add(row);
		}
	}
	
	public boolean modifyGrid(String move) {
		int move_x = Integer.parseInt(String.valueOf(move.charAt(0)));
		int move_y = Integer.parseInt(String.valueOf(move.charAt(1)));
		if((move_x < 0) || (move_x > this.grid_size-1) || (move_y < 0) || (move_y > this.grid_size-1)) {
			return true;
		} else {
			String temp_num = this.grid_array.get(move_x).get(move_y).get(0);
			this.grid_array.get(move_x).get(move_y).set(0, "x");
			this.grid_array.get(this.xCoord_x).get(this.xCoord_y).set(0, temp_num);
			this.xCoord_x=move_x; this.xCoord_y = move_y;
			return false;
		}
	}
	
	public String randomMove() {
		List<String> neighbor_list = new ArrayList<>();
		for (int i=Math.max(0,this.xCoord_x-1); i<=Math.min(this.xCoord_x+1, this.grid_size-1); i++) {
			for (int j=Math.max(0, this.xCoord_y-1); j<=Math.min(this.xCoord_y+1, this.grid_size-1); j++) {
				if(i != this.xCoord_x || j != this.xCoord_y) {
					neighbor_list.add(String.valueOf(i)+String.valueOf(j));
				}
			}
		}
		Collections.shuffle(neighbor_list);
		return neighbor_list.get(0);
	}
	
	public boolean checkGameOver() {
		int checker = 0;
		boolean result = true;
		for(int i=0;i<this.grid_size-1;i++) {
			for(int j=0;j<this.grid_size-1;j++) {
				checker++;
				String current = this.grid_array.get(i).get(j).get(0);
				if (current != String.valueOf(checker) && (current != "x" && checker != this.grid_size*this.grid_size)) {
					result = false;
				}
			}
		}
		return result;
	}
}
