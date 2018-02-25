package com.danny.othello.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.util.OthelloMoveConverter;
import com.danny.othello.util.OthelloPrinterImpl;
import com.danny.othello.util.RCOthelloMoveConverter;

import junit.framework.Assert;

public class OthelloPrinterImplTest {
	private final static String SPACE = " ";
	private int columns = 8;
	private int rows = 9;
	
	private OthelloPrinterImpl othelloPrinter= new OthelloPrinterImpl();
	private OthelloMoveConverter othelloMoveConverter = new RCOthelloMoveConverter();
	
	@Test
	public void testPrintWithAllEmpty() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		Othello othello = new Othello(columns, rows, OthelloConstant.PLAYER_O, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O);
		othelloPrinter.print(othello);
		
		String actual = outContent.toString();
		String expected = generateExpectedResult(columns, rows, OthelloConstant.EMPTY_DISPLAY_VALUE);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testPrintWithAllPieces() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		Othello othello = new Othello(columns, rows, OthelloConstant.PLAYER_O, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O);
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column ++) {
				othello.setPiece(new Coordinate(row, column));
			}
		}
		othelloPrinter.print(othello);
		
		String actual = outContent.toString();
		String expected = generateExpectedResult(columns, rows, OthelloConstant.PLAYER_O);
		Assert.assertEquals(expected, actual);
		
	}
	
	private String generateExpectedResult(int columns, int rows, char value) {
		StringBuffer othelloString = new StringBuffer();
		for (int row = 0; row < rows; row ++) {
			othelloString.append(row + 1).append(SPACE);
			for (int column = 0; column < columns; column ++) {
				othelloString.append(value);
			}
			othelloString.append(System.lineSeparator());
		}
	
		othelloString.append(SPACE).append(SPACE);
		for (int i = 0; i < columns; i ++) {
			othelloString.append((char)(OthelloConstant.CHAR_BASE + i));
		}
		othelloString.append(System.lineSeparator());
		return othelloString.toString();
	}
	
	@Test
	public void testPrintResultPlayer1Win() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(othelloMoveConverter.convert("4e"));
		othello.setPiece(othelloMoveConverter.convert("5d"));
		othello.setPiece(othelloMoveConverter.convert("5e"));
		othello.setPiece(othelloMoveConverter.convert("6e"));
		othello.switchPlayer();
		othello.setPiece(othelloMoveConverter.convert("4d"));
		
		othelloPrinter.printResult(othello);
		String actual = outContent.toString();
		String expected = "Player 'X' wins ( 4 vs 1 )" + System.lineSeparator();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPrintResultDraw() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(othelloMoveConverter.convert("4e"));
		othello.setPiece(othelloMoveConverter.convert("5d"));
		othello.switchPlayer();
		othello.setPiece(othelloMoveConverter.convert("4d"));
		othello.setPiece(othelloMoveConverter.convert("6d"));
		
		othelloPrinter.printResult(othello);
		String actual = outContent.toString();
		String expected = "Draw ( 2 vs 2 )" + System.lineSeparator();
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPrintResultPlayer2Win() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(othelloMoveConverter.convert("4e"));
		othello.setPiece(othelloMoveConverter.convert("5d"));
		othello.switchPlayer();
		othello.setPiece(othelloMoveConverter.convert("4d"));
		othello.setPiece(othelloMoveConverter.convert("6d"));
		othello.setPiece(othelloMoveConverter.convert("7d"));
		
		othelloPrinter.printResult(othello);
		String actual = outContent.toString();
		String expected = "Player 'O' wins ( 3 vs 2 )" + System.lineSeparator();
		
		Assert.assertEquals(expected, actual);
	}

}
