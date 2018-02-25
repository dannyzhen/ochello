package com.danny.othello;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.util.OthelloMoveConverter;
import com.danny.othello.util.OthelloPrinter;
import com.danny.othello.util.OthelloMoveConverterImpl;

public class OthelloGameImplTest {
	private OthelloMoveConverter othelloMoveConverter = new OthelloMoveConverterImpl();

	//start
	@Test(expected=IllegalArgumentException.class)
	public void testStartNullOthello() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.start(null);
	}
	
	@Test
	public void testStart() {
		OthelloGameImpl othelloGame = new DummpOthelloGameImpl();
		OthelloPrinter othelloPrinter = EasyMock.createNiceMock(OthelloPrinter.class);
		Othello othello = EasyMock.createNiceMock(Othello.class);
		
		//First try, canMove=true
		othello.switchPlayer();
		EasyMock.expectLastCall();
		othelloPrinter.print(othello);
		EasyMock.expectLastCall();
		
		//Second try, canMove = false
		othello.switchPlayer();
		EasyMock.expectLastCall();
		
		//Third try, canMove=true
		othello.switchPlayer();
		EasyMock.expectLastCall();
		othelloPrinter.print(othello);
		EasyMock.expectLastCall();
		
		//Fourth try, canMove = false
		othello.switchPlayer();
		EasyMock.expectLastCall();
		
		//Fifth try, canMove = false
		othelloPrinter.printResult(othello);
		EasyMock.expectLastCall();
		
		EasyMock.replay(othelloPrinter, othello);
		
		othelloGame.setOthelloPrinter(othelloPrinter);
		othelloGame.start(othello);
		
		EasyMock.verify(othelloPrinter, othello);
	}
	
	static class DummpOthelloGameImpl extends OthelloGameImpl {
		int canMoveCalls = 0;
		
		@Override
		public boolean canMove(Othello othello) {
			canMoveCalls ++;
			if (canMoveCalls == 2 || canMoveCalls > 3) {
				return false;
			}
			return true;
		}
		
		@Override
		public void move(Othello othello) {
			//do nothing
		}
		
	}
	 
	//move
	@Test
	public void testMoveInvalidMoveFormat() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		OthelloGameImpl othelloGame = new DummpOthelloGameImpl2();
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		OthelloMoveConverter othelloMoveConverter = EasyMock.createNiceMock(OthelloMoveConverter.class);
		
		//First try, invalid move format
		EasyMock.expect(othelloMoveConverter.convert("1")).andThrow(new OthelloException(""));
		
		//Second try, invalid Coordinate
		EasyMock.expect(othelloMoveConverter.convert("2")).andReturn(new Coordinate(8, 8));
		

		//Third try, occupied piece
		EasyMock.expect(othelloMoveConverter.convert("3")).andReturn(new Coordinate(2, 6));
		

		//Fourth try, No captured pieces found
		EasyMock.expect(othelloMoveConverter.convert("4")).andReturn(new Coordinate(1, 1));
		
		//Fifth try, Found captured pieces
		EasyMock.expect(othelloMoveConverter.convert("5")).andReturn(new Coordinate(1, 1));
		
		EasyMock.replay(othelloMoveConverter);
		
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.move(othello);
		
		EasyMock.verify(othelloMoveConverter);
		
		String actual = outContent.toString();
		String expected = "";
		for (int i = 0; i < 4; i ++) {
			expected += "Invalid move. Please try again." + System.lineSeparator();
		}
		Assert.assertEquals(expected, actual);

	}
	
	static class DummpOthelloGameImpl2 extends OthelloGameImpl {
		
		int readDataFromConsoleCalls = 0;
		int getCapturedPiecesCalls = 0;
		
		@Override
		public String readDataFromConsole(String prompt) {
			readDataFromConsoleCalls ++;
			return String.valueOf(readDataFromConsoleCalls);
		}
		
		@Override
		protected List<Coordinate> getCapturedPieces(Othello othello, Coordinate coordinate) {
			getCapturedPiecesCalls ++;
			if (getCapturedPiecesCalls == 1) {
				return new ArrayList<Coordinate>();
			}
			else {
				return Arrays.asList(new Coordinate(2, 2));
			}
			
		}
		
	}
	
	//canMove
	@Test
	public void testCanMoveEast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveWest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"3d"}, new String[] {"3e", "3f"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNorth() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"2d"}, new String[] {"3d", "4d"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSouth() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"5d"}, new String[] {"3d", "4d"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNortheast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1h"}, new String[] {"3f", "2g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSouthwest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"4e"}, new String[] {"3f", "2g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNorthwest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1a"}, new String[] {"2b", "3c"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSoutheast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"4d"}, new String[] {"2b", "3c"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveAllDirs() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1a", "2h", "8b", "8h"}, new String[] {"2b", "3c", "4d", "4f", "3g", "6d", "7c", "6f", "7g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanNotMove() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1d"}, new String[] {"2b", "3c", "4d", "4f", "3g", "6d", "7c", "6f", "7g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertFalse(result);
		
	}
	
	//getCapturedPieces 
	@Test
	public void testGetCapturedPiecesEast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("3d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 5))));
		
	}
	
	@Test
	public void testGetCapturedPiecesWest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"3d"}, new String[] {"3e", "3f"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("3g"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 5))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNorth() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"2d"}, new String[] {"3d", "4d"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("5d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 3), new Coordinate(3, 3))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSouth() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"5d"}, new String[] {"3d", "4d"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("2d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 3), new Coordinate(3, 3))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNortheast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1h"}, new String[] {"3f", "2g"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("4e"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 5), new Coordinate(1, 6))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSouthwest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"4e"}, new String[] {"3f", "2g"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("1h"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 5), new Coordinate(1, 6))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNorthwest() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1a"}, new String[] {"2b", "3c"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("4d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(1, 1), new Coordinate(2, 2))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSoutheast() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"4d"}, new String[] {"2b", "3c"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("1a"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(1, 1), new Coordinate(2, 2))));
		
	}
	
	@Test
	public void testGetCapturedPiecesAllDirs() {
		OthelloGameImpl othelloGame = new OthelloGameImpl();
		Othello othello = createOthello(new String[] {"1a", "2h", "8b", "8h"}, new String[] {"2b", "3c", "4d", "4f", "3g", "6d", "7c", "6f", "7g"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("5e"));
		Assert.assertNotNull(result);
		Assert.assertEquals(9, result.size());
		Assert.assertTrue(result.containsAll(
				Arrays.asList(new Coordinate(1, 1), new Coordinate(2, 2),
						new Coordinate(3, 3), new Coordinate(3, 5),
						new Coordinate(2, 6), new Coordinate(5, 3),
						new Coordinate(6, 2),
						new Coordinate(5, 5), new Coordinate(6, 6)
						)));
		
	}
	
	private Othello createOthello(String[] xPieces, String[] oPieces) {
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		for (String piece : xPieces) {
			othello.setPiece(othelloMoveConverter.convert(piece));
		}

		othello.switchPlayer();
		for (String piece : oPieces) {
			othello.setPiece(othelloMoveConverter.convert(piece));
		}
		othello.switchPlayer();
		
		return othello;
	}

}
