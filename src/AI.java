import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AI {
	
	public char ai_side = '_';
	
	public void setSide(String side) {
		this.ai_side = side.charAt(0);
	}
	
	public Map<String, String> MakeRowMap(Grid grid) {
		Map<String, String> resultMap = new HashMap<String, String>();
		for(int x=0; x<3; x++) {
			StringBuilder resultRow = new StringBuilder();
			StringBuilder resultColumn = new StringBuilder();
			String row=String.valueOf(x+1);
			StringBuilder column = new StringBuilder();
			switch (x) {
			case 0: column.append('A'); break;
			case 1: column.append('B'); break;
			case 2: column.append('C'); break;
			}
			for(int y=0; y<3; y++) {
				resultRow.append(grid.grid_array[x][y]);
				resultColumn.append(grid.grid_array[y][x]);
			}
			resultMap.put(resultRow.toString(), row);
			resultMap.put(resultColumn.toString(), column.toString());
			//System.out.println(resultRow + "row");
			//System.out.println(resultColumn + "column");
		}
		StringBuilder firstDiag = new StringBuilder();
		StringBuilder secondDiag = new StringBuilder();
		firstDiag.append(grid.grid_array[0][0]).append(grid.grid_array[1][1]).append(grid.grid_array[2][2]);
		secondDiag.append(grid.grid_array[0][2]).append(grid.grid_array[1][1]).append(grid.grid_array[2][0]);
		resultMap.put(firstDiag.toString(), "FD");
		resultMap.put(secondDiag.toString(), "SD");
		//System.out.println(firstDiag + " diag");
		//System.out.println(secondDiag + " diag");
		
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
				if (this.ai_side == 'x') {
					switch (symb) {
					case 'x': selfCount++; break;
					case 'o': opponentCount++; break;
					case '_': uCount++; break;
					}
				}
				else if (this.ai_side == 'o') {
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
			System.out.println(maxPriorityLine + " line");
			System.out.println(maxPriority + " prior");
		}
		
		//System.out.print(maxPriorityLine);
		return new String[] {maxPriorityLine, maxPriorityIndex};
	}
	
	public void doMove(Grid grid) {
		
		String[] lineToEdit = editLine(MakeRowMap(grid));
		
		char[] line = lineToEdit[0].toCharArray();
		String lineIndex = lineToEdit[1];
		
		//System.out.println(lineToEdit[0]);
		//System.out.println(lineIndex + " lineinx");
		
		int rowOrColumn = 1;
		StringBuilder result = new StringBuilder();
		
		int inx = 1;
		for (char symb : line) {
			if (symb == '_') {
				rowOrColumn = inx;
				inx = 1;
				break;
			}
			inx++;
		}
		
		System.out.println(rowOrColumn + " roworcol");
		
		if (lineIndex.equals("A") || lineIndex.equals("B") || lineIndex.equals("C")) {
			result.append(lineIndex);
			result.append(rowOrColumn);
			System.out.println("ABC");
		}
		else if (lineIndex.equals("1") || lineIndex.equals("2") || lineIndex.equals("3")) {
			System.out.println("123");
			switch (rowOrColumn) {
			case 1: result.append("A"); break;
			case 2: result.append("B"); break;
			case 3: result.append("C"); break;
			}
			result.append(lineIndex);
			
		}
		else if (lineIndex.equals("FD")){
			switch (rowOrColumn) {
			case 1: result.append("A1"); break;
			case 2: result.append("B2"); break;
			case 3: result.append("C3"); break;
			}
			System.out.println("FD");
		}
		else if (lineIndex.equals("SD")){
			switch (rowOrColumn) {
			case 1: result.append("C1"); break;
			case 2: result.append("B2"); break;
			case 3: result.append("A3"); break;
			}
			System.out.println("SD");
		}
		System.out.println(result.toString());
		System.out.println("___________");
		if (grid.modifyGrid(result.toString(), this.ai_side).equals("ok")) {	
			return;
		}	
	}
}
