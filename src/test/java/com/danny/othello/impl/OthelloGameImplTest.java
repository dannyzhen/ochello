package com.danny.othello.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.danny.othello.OthelloConstant;
import com.danny.othello.OthelloException;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.intf.OthelloFactory;
import com.danny.othello.intf.OthelloFormatter;
import com.danny.othello.intf.OthelloMoveConverter;
import com.danny.othello.intf.UI;

public class OthelloGameImplTest {
	private OthelloMoveConverterImpl othelloMoveConverter = new OthelloMoveConverterImpl();
	private OthelloGameImpl othelloGame = new OthelloGameImpl();
	@Before 
	public void before() {
		OthelloFactoryImpl othelloFactory= new OthelloFactoryImpl();
		othelloMoveConverter.setOthelloFactory(othelloFactory);
		othelloGame.setOthelloFactory(othelloFactory);
	}

	//start
	
	@Test
	public void testStartFull() {
		String steps = "4c 3e 5f 4b 2e 6f 4a 1e 4f 3g 4g 5g 5h 6e 4h 6g 7e 6d 2g 1g 7h 6h 2f 5c 5b 3a 3b 3c 2d 2c 2b 2a 1b 3d 7c 8e 8f 8h 8d 7d 1a 7b 7a 8b 7g 5a 6a 3h 1d 1c 1f 3f 1h 2h 6c 8a 8c 8g 7f 6b";
		
		OthelloFormatter othelloFormatter = new OthelloFormatterImpl();
		OthelloMoveConverterImpl othelloMoveConverter = new OthelloMoveConverterImpl();
		OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();  
		othelloMoveConverter.setOthelloFactory(othelloFactory);
		UI ui = EasyMock.createNiceMock(UI.class);
		String[] stepArray = steps.split(" ");
		for (String step : stepArray) {
			EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn(step);
		}
		
		Capture<String> writeCapture = Capture.newInstance();
        ui.write(EasyMock.capture(writeCapture));  
          
        EasyMock.expectLastCall().times(stepArray.length * 2);  
		
		EasyMock.replay(ui);

		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setOthelloFormatter(othelloFormatter);
		othelloGame.setOthelloFactory(othelloFactory);
		othelloGame.setUi(ui);

		othelloGame.start();
		List<String> messages = writeCapture.getValues();  
		
		Assert.assertTrue(messages.contains("Player 'O' wins ( 38 vs 26 )"));
	}
	
	@Test
	public void testStartFullWithUndo() {
		String steps = "4c 3e 5f 4b 2e 6f u u 2e 6f 4a 1e 4f 3g 4g 5g 5h 6e 4h 6g 7e 6d 2g 1g 7h 6h 2f 5c 5b 3a 3b 3c 2d 2c 2b 2a 1b 3d 7c u u u u 2a 1b 3d 7c 8e 8f 8h 8d 7d 1a 7b 7a 8b 7g 5a 6a 3h 1d 1c 1f 3f 1h 2h 6c 8a 8c 8g 7f 6b";

		OthelloFormatter othelloFormatter = new OthelloFormatterImpl();
		OthelloMoveConverterImpl othelloMoveConverter = new OthelloMoveConverterImpl();
		OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();
		othelloMoveConverter.setOthelloFactory(othelloFactory);
		UI ui = EasyMock.createNiceMock(UI.class);
		String[] stepArray = steps.split(" ");
		for (String step : stepArray) {
			EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn(step);
		}

		Capture<String> writeCapture = Capture.newInstance();
		ui.write(EasyMock.capture(writeCapture));

		EasyMock.expectLastCall().times(stepArray.length * 2 + 20);

		EasyMock.replay(ui);

		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setOthelloFormatter(othelloFormatter);
		othelloGame.setOthelloFactory(othelloFactory);
		othelloGame.setUi(ui);

		othelloGame.start();
		List<String> messages = writeCapture.getValues();

		Assert.assertTrue(messages.contains("Player 'O' wins ( 38 vs 26 )"));
	}
	
	@Test
	public void testStart() {
		OthelloGameImpl othelloGame = new DummpOthelloGameImpl();
		UI ui = EasyMock.createNiceMock(UI.class);
		OthelloFormatter othelloFormatter = EasyMock.createNiceMock(OthelloFormatter.class);
		Othello othello = EasyMock.createNiceMock(Othello.class);
		
		OthelloFactory othelloFactory = EasyMock.createNiceMock(OthelloFactory.class);
		
		EasyMock.expect(othelloFactory.createOthello()).andReturn(othello);
		
		//First try, canMove=true
		othello.switchPlayer();
		EasyMock.expectLastCall();
		EasyMock.expect(othelloFormatter.format(othello)).andReturn("FORMAT1");
		ui.write("FORMAT1");
		EasyMock.expectLastCall();
		
		//Second try, canMove = false
		othello.switchPlayer();
		EasyMock.expectLastCall();
		
		//Third try, canMove=true
		othello.switchPlayer();
		EasyMock.expectLastCall();
		EasyMock.expect(othelloFormatter.format(othello)).andReturn("FORMAT2");
		ui.write("FORMAT2");
		EasyMock.expectLastCall();
		
		//Fourth try, canMove = false
		othello.switchPlayer();
		EasyMock.expectLastCall();
		
		//Fifth try, canMove = false
		EasyMock.expect(othelloFormatter.formatResult(othello)).andReturn("RESULT");
		ui.write("No further moves available");
		EasyMock.expectLastCall();
		ui.write("RESULT");
		EasyMock.expectLastCall();
		
		EasyMock.replay(othelloFormatter, othello, othelloFactory, ui);
		
		othelloGame.setUi(ui);
		othelloGame.setOthelloFormatter(othelloFormatter);
		othelloGame.setOthelloFactory(othelloFactory);
		othelloGame.start();
		
		EasyMock.verify(othelloFormatter, othello, othelloFactory, ui);
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
		
		OthelloGameImpl othelloGame = new DummpOthelloGameImpl2();
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		UI ui = EasyMock.createNiceMock(UI.class);
		
		OthelloMoveConverter othelloMoveConverter = EasyMock.createNiceMock(OthelloMoveConverter.class);
		
		//First try, invalid move format
		EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn("1");
		EasyMock.expect(othelloMoveConverter.convert("1")).andThrow(new OthelloException(""));
		ui.write("Invalid move. Please try again.");
		EasyMock.expectLastCall();
		
		//Second try, invalid Coordinate
		EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn("2");
		EasyMock.expect(othelloMoveConverter.convert("2")).andReturn(new Coordinate(8, 8));
		ui.write("Invalid move. Please try again.");
		EasyMock.expectLastCall();

		//Third try, occupied piece
		EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn("3");
		EasyMock.expect(othelloMoveConverter.convert("3")).andReturn(new Coordinate(2, 6));
		ui.write("Invalid move. Please try again.");
		EasyMock.expectLastCall();

		//Fourth try, No captured pieces found
		EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn("4");
		EasyMock.expect(othelloMoveConverter.convert("4")).andReturn(new Coordinate(1, 1));
		ui.write("Invalid move. Please try again.");
		EasyMock.expectLastCall();
		
		//Fifth try, Found captured pieces
		EasyMock.expect(ui.read(EasyMock.isA(String.class))).andReturn("5");
		EasyMock.expect(othelloMoveConverter.convert("5")).andReturn(new Coordinate(1, 1));
		
		EasyMock.replay(othelloMoveConverter, ui);
		
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setUi(ui);
		othelloGame.move(othello);
		
		EasyMock.verify(othelloMoveConverter);

	}
	
	static class DummpOthelloGameImpl2 extends OthelloGameImpl {
		
		int readDataFromConsoleCalls = 0;
		int getCapturedPiecesCalls = 0;
		
		
		@Override
		protected List<Coordinate> getCapturedPieces(Othello othello, Coordinate coordinate) {
			getCapturedPiecesCalls ++;
			if (getCapturedPiecesCalls == 1) {
				return new ArrayList<Coordinate>();
			}
			else {
				return new ArrayList<Coordinate>(Arrays.asList(new Coordinate(2, 2)));
			}
			
		}
		
	}
	
	//canMove
	@Test
	public void testCanMoveEast() {
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveWest() {
		Othello othello = createOthello(new String[] {"3d"}, new String[] {"3e", "3f"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNorth() {
		Othello othello = createOthello(new String[] {"2d"}, new String[] {"3d", "4d"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSouth() {
		Othello othello = createOthello(new String[] {"5d"}, new String[] {"3d", "4d"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNortheast() {
		Othello othello = createOthello(new String[] {"1h"}, new String[] {"3f", "2g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSouthwest() {
		Othello othello = createOthello(new String[] {"4e"}, new String[] {"3f", "2g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveNorthwest() {
		Othello othello = createOthello(new String[] {"1a"}, new String[] {"2b", "3c"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveSoutheast() {
		Othello othello = createOthello(new String[] {"4d"}, new String[] {"2b", "3c"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanMoveAllDirs() {
		Othello othello = createOthello(new String[] {"1a", "2h", "8b", "8h"}, new String[] {"2b", "3c", "4d", "4f", "3g", "6d", "7c", "6f", "7g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertTrue(result);
		
	}
	
	@Test
	public void testCanNotMove() {
		Othello othello = createOthello(new String[] {"1d"}, new String[] {"2b", "3c", "4d", "4f", "3g", "6d", "7c", "6f", "7g"});
		
		boolean result = othelloGame.canMove(othello);
		Assert.assertFalse(result);
		
	}
	
	//getCapturedPieces 
	@Test
	public void testGetCapturedPiecesEast() {
		Othello othello = createOthello(new String[] {"3g"}, new String[] {"3e", "3f"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("3d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 5))));
		
	}
	
	@Test
	public void testGetCapturedPiecesWest() {
		Othello othello = createOthello(new String[] {"3d"}, new String[] {"3e", "3f"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("3g"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 4), new Coordinate(2, 5))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNorth() {
		Othello othello = createOthello(new String[] {"2d"}, new String[] {"3d", "4d"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("5d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 3), new Coordinate(3, 3))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSouth() {
		Othello othello = createOthello(new String[] {"5d"}, new String[] {"3d", "4d"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("2d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 3), new Coordinate(3, 3))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNortheast() {
		Othello othello = createOthello(new String[] {"1h"}, new String[] {"3f", "2g"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("4e"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 5), new Coordinate(1, 6))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSouthwest() {
		Othello othello = createOthello(new String[] {"4e"}, new String[] {"3f", "2g"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("1h"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(2, 5), new Coordinate(1, 6))));
		
	}
	
	@Test
	public void testGetCapturedPiecesNorthwest() {
		Othello othello = createOthello(new String[] {"1a"}, new String[] {"2b", "3c"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("4d"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(1, 1), new Coordinate(2, 2))));
		
	}
	
	@Test
	public void testGetCapturedPiecesSoutheast() {
		Othello othello = createOthello(new String[] {"4d"}, new String[] {"2b", "3c"});
		
		List<Coordinate> result = othelloGame.getCapturedPieces(othello, othelloMoveConverter.convert("1a"));
		Assert.assertNotNull(result);
		Assert.assertEquals(2, result.size());
		Assert.assertTrue(result.containsAll(Arrays.asList(new Coordinate(1, 1), new Coordinate(2, 2))));
		
	}
	
	@Test
	public void testGetCapturedPiecesAllDirs() {
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
