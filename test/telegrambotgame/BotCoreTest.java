package telegrambotgame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringJoiner;

import org.junit.jupiter.api.Test;

class BotCoreTest {
	
	BotCore testCore = new BotCore();
	
	public String drawGrid(char[][] char_arr) {
		StringJoiner sj = new StringJoiner(System.lineSeparator());
		for(char[] row : char_arr) {
			sj.add(Arrays.toString(row));
		}
		return sj.toString();
	}
	
	@Test
	public void testChooseGame() {
			
			testCore.ongoing_game = "None";
			
			ArrayList<String> result = new ArrayList<String>();
			result.add("Please enter your side: \n x or o");
			
			assertEquals(result, testCore.analyze("/ttt"));
		}
	
	@Test
	public void testExitTTT() {
			
			testCore.ongoing_game = "ttt";
			
			ArrayList<String> result = new ArrayList<String>();
			result.add("Stopping TickTackToe...");
			
			assertEquals(result, testCore.analyze("/exit"));
		}
	
	@Test
	public void testChooseSideTTT() {
			
			char[][] empty_grid = new char[][]{
				  { '_', '_', '_' },
				  { '_', '_', '_' },
				  { '_', '_', '_' }
				};
			
			testCore.ongoing_game = "ttt";
			
			ArrayList<String> result = new ArrayList<String>();
			result.add("you are now playing as: x");
			result.add(drawGrid(empty_grid));
			
			assertEquals(result, testCore.analyze("x"));
		}
	
	@Test
	public void testVerticalTTT() {
		
		char[][] start_grid = new char[][]{
			  { '_', 'x', '_' },
			  { '_', '_', '_' },
			  { '_', '_', '_' }
			};
		char[][] end_grid = new char[][]{
			  { '_', 'x', '_' },
			  { '_', 'x', '_' },
			  { '_', 'o', '_' }
			};
		
		testCore.ongoing_game = "ttt";
		testCore.ttt_game.ttt_player.setSide("x");
		testCore.ttt_game.ttt_AI.setSide("o");
		testCore.ttt_game.ttt_grid.grid_array = start_grid;
		
		ArrayList<String> result = new ArrayList<String>();
		result.add(drawGrid(end_grid));
		
		assertEquals(result, testCore.analyze("B2"));
	}
	
	@Test
	public void testHorizontalTTT() {
			
			char[][] start_grid = new char[][]{
				  { '_', '_', '_' },
				  { 'x', '_', '_' },
				  { '_', '_', '_' }
				};
			char[][] end_grid = new char[][]{
				  { '_', '_', '_' },
				  { 'x', 'x', 'o' },
				  { '_', '_', '_' }
				};
			
			testCore.ongoing_game = "ttt";
			testCore.ttt_game.ttt_player.setSide("x");
			testCore.ttt_game.ttt_AI.setSide("o");
			testCore.ttt_game.ttt_grid.grid_array = start_grid;
			
			ArrayList<String> result = new ArrayList<String>();
			result.add(drawGrid(end_grid));
			
			assertEquals(result, testCore.analyze("B2"));
		}
	
	@Test
	public void test1DiagonalTTT() {
			
			char[][] start_grid = new char[][]{
				  { 'x', '_', '_' },
				  { '_', '_', '_' },
				  { '_', '_', '_' }
				};
			char[][] end_grid = new char[][]{
				  { 'x', '_', '_' },
				  { '_', 'x', '_' },
				  { '_', '_', 'o' }
				};
			
			testCore.ongoing_game = "ttt";
			testCore.ttt_game.ttt_player.setSide("x");
			testCore.ttt_game.ttt_AI.setSide("o");
			testCore.ttt_game.ttt_grid.grid_array = start_grid;
			
			ArrayList<String> result = new ArrayList<String>();
			result.add(drawGrid(end_grid));
			
			assertEquals(result, testCore.analyze("B2"));
		}
	
	@Test
	public void test2DiagonalTTT() {
			
			char[][] start_grid = new char[][]{
				  { '_', '_', 'x' },
				  { '_', '_', '_' },
				  { '_', '_', '_' }
				};
			char[][] end_grid = new char[][]{
				  { '_', '_', 'x' },
				  { '_', 'x', '_' },
				  { 'o', '_', '_' }
				};
			
			testCore.ongoing_game = "ttt";
			testCore.ttt_game.ttt_player.setSide("x");
			testCore.ttt_game.ttt_AI.setSide("o");
			testCore.ttt_game.ttt_grid.grid_array = start_grid;
			
			ArrayList<String> result = new ArrayList<String>();
			result.add(drawGrid(end_grid));
			
			assertEquals(result, testCore.analyze("B2"));
		}
	
	@Test
	public void testBetweenTTT() {
			
			char[][] start_grid = new char[][]{
				  { '_', '_', '_' },
				  { 'x', '_', '_' },
				  { '_', '_', '_' }
				};
			char[][] end_grid = new char[][]{
				  { '_', '_', '_' },
				  { 'x', 'o', 'x' },
				  { '_', '_', '_' }
				};
			
			testCore.ongoing_game = "ttt";
			testCore.ttt_game.ttt_player.setSide("x");
			testCore.ttt_game.ttt_AI.setSide("o");
			testCore.ttt_game.ttt_grid.grid_array = start_grid;
			
			ArrayList<String> result = new ArrayList<String>();
			result.add(drawGrid(end_grid));
			
			assertEquals(result, testCore.analyze("C2"));
		}
	
}
