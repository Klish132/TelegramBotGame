package telegrambotgame;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TttAI {
	
	public String ai_side = "_";
	
	public void setSide(String side) {
		this.ai_side = side;
	}
	
	public Map<String, String> MakeRowMap(TttGrid grid) {
		Map<String, String> resultMap = new HashMap<String, String>();
		for(int x=0; x<=2; x++) {
			StringBuilder resultRow = new StringBuilder();
			StringBuilder resultColumn = new StringBuilder();
			String row = String.valueOf(x);
			String column = String.valueOf(x);
			for(int y=0; y<=2; y++) {
				resultRow.append(grid.grid_array.get(x).get(y).get(0));
				resultColumn.append(grid.grid_array.get(y).get(x).get(0));
			}
			resultMap.put(resultRow.toString(), row);
			resultMap.put(resultColumn.toString(), column);
		}
		StringBuilder firstDiag = new StringBuilder();
		StringBuilder secondDiag = new StringBuilder();
		firstDiag.append(grid.grid_array.get(0).get(0).get(0)).append(grid.grid_array.get(1).get(1).get(0)).append(grid.grid_array.get(2).get(2).get(0));
		secondDiag.append(grid.grid_array.get(0).get(2).get(0)).append(grid.grid_array.get(1).get(1).get(0)).append(grid.grid_array.get(2).get(0).get(0));
		resultMap.put(firstDiag.toString(), "FD");
		resultMap.put(secondDiag.toString(), "SD");
		
		return resultMap;
	}
	
	public String[] editLine(Map<String, String> map) {
		String maxPriorityLine = "";
		String maxPriorityIndex = "";
		int maxPriority = 0;
		boolean thisEntry = false;
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			int selfCount = 0;
			int opponentCount = 0;
			int uCount = 0;
			for (char symb : entry.getKey().toCharArray()) {
				System.out.println("Symbol: " + symb);
				if (this.ai_side == "x") {
					switch (symb) {
					case 'x': selfCount++; break;
					case 'o': opponentCount++; break;
					case '_': uCount++; break;
					}
				}
				else if (this.ai_side == "o") {
					switch (symb) {
					case 'o': selfCount++; break;
					case 'x': opponentCount++; break;
					case '_': uCount++; break;
					}
				}
			}
			if (maxPriority < 1 && uCount == 0) {
				thisEntry = true;
				maxPriority = 0;
			}
			else if (maxPriority < 1 && selfCount == 0 && opponentCount == 0) {
				thisEntry = true;
				maxPriority = 1;
			}
			else if (maxPriority < 2 && selfCount == 1 && opponentCount == 0) {
				thisEntry = true;
				maxPriority = 2;
			}
			else if (maxPriority < 3 && selfCount == 0 && opponentCount == 2) {
				thisEntry = true;
				maxPriority = 3;
			}
			else if (maxPriority < 4 && selfCount == 2 && opponentCount == 0) {
				thisEntry = true;
				maxPriority = 4;
			}
			if (thisEntry == true) {
				maxPriorityLine = entry.getKey();
				maxPriorityIndex = entry.getValue();
				thisEntry = false;
			}
		}
		return new String[] {maxPriorityLine, maxPriorityIndex};
	}
	
	public String checkGameOver(TttGrid grid) {
		
		Map<String, String> map = MakeRowMap(grid);
		int uCount = 0;
		int xComplete = 0;
		int oComplete = 0;
		
		for (Map.Entry<String, String> entry : map.entrySet()) {
			int xCount = 0;
			int oCount = 0;
			for (char symb : entry.getKey().toCharArray()) {
				switch (symb) {
				case 'o': oCount++; break;
				case 'x': xCount++; break;
				case '_': uCount++; break;
				}
			}
			if (xCount == 3) {
				xComplete++;
			}
			else if (oCount == 3) {
				oComplete++;
			}
		}
		if (xComplete != 0) {
			if (oComplete != 0) {
				return "xo";
			}
			return "x";
		}
		else if (oComplete != 0) {
			if (xComplete != 0) {
				return "xo";
			}
			return "o";
		}
		else if (uCount == 0) {
			return "draw";
		}
		return "None";
	}
	
	public void doMove(TttGrid grid) {
		
		String[] lineToEdit = editLine(MakeRowMap(grid));
		
		char[] line = lineToEdit[0].toCharArray();
		System.out.println(lineToEdit[0]);
		String lineIndex = lineToEdit[1];
		
		int rowOrColumn = 1;
		StringBuilder result = new StringBuilder();
		
		int inx = 0;
		for (char symb : line) {
			if (symb == '_') {
				rowOrColumn = inx;
				inx = 0;
				break;
			}
			inx++;
		}
		
		if (lineIndex.equals("0") || lineIndex.equals("1") || lineIndex.equals("2")) {
			result.append(lineIndex);
			result.append(rowOrColumn);
			System.out.println(result.toString());
		}
		else if (lineIndex.equals("0") || lineIndex.equals("1") || lineIndex.equals("2")) {
			result.append(rowOrColumn);
			result.append(lineIndex);
			
		}
		else if (lineIndex.equals("FD")){
			switch (rowOrColumn) {
			case 1: result.append("00"); break;
			case 2: result.append("11"); break;
			case 3: result.append("22"); break;
			}
		}
		else if (lineIndex.equals("SD")){
			switch (rowOrColumn) {
			case 1: result.append("02"); break;
			case 2: result.append("11"); break;
			case 3: result.append("20"); break;
			}
		}
		
		if (grid.modifyGrid(result.toString(), this.ai_side) == false) {	
			return;
		}	
	}
}
