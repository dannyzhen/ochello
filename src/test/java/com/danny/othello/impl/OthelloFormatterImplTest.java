package com.danny.othello.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;

import junit.framework.Assert;

public class OthelloFormatterImplTest {
	private final static String SPACE = " ";
	private int columns = 8;
	private int rows = 9;
	
	private OthelloFormatterImpl othelloFormatter= new OthelloFormatterImpl();
	
	@Test
	public void testFormatWithAllEmpty() {
		Othello othello = new Othello(columns, rows, OthelloConstant.PLAYER_O, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O);
		String actual = othelloFormatter.format(othello);
		
		String expected = generateExpectedResult(columns, rows, OthelloConstant.EMPTY_DISPLAY_VALUE);
		Assert.assertEquals(expected, actual);
		
	}
	
	@Test
	public void testPrintWithAllPieces() {

		Othello othello = new Othello(columns, rows, OthelloConstant.PLAYER_O, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O);
		for (int row = 0; row < rows; row ++) {
			for (int column = 0; column < columns; column ++) {
				othello.setPiece(new Coordinate(row, column));
			}
		}
		String actual =  othelloFormatter.format(othello);
		
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
		return othelloString.toString();
	}
	
	@Test
	public void testFormatResultPlayer1Win() {
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(new Coordinate(3, 4));
		othello.setPiece(new Coordinate(4, 3));
		othello.setPiece(new Coordinate(4, 4));
		othello.setPiece(new Coordinate(5, 4));
		othello.switchPlayer();
		othello.setPiece(new Coordinate(3, 3));
		
		String actual = othelloFormatter.formatResult(othello);
		String expected = "Player 'X' wins ( 4 vs 1 )";
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testFormatResultDraw() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(new Coordinate(3, 4));
		othello.setPiece(new Coordinate(4, 3));
		othello.switchPlayer();
		othello.setPiece(new Coordinate(3, 3));
		othello.setPiece(new Coordinate(5, 3));
		
		String actual = othelloFormatter.formatResult(othello);
		String expected = "Draw ( 2 vs 2 )";
		
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testPrintResultPlayer2Win() {
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(new Coordinate(3, 4));
		othello.setPiece(new Coordinate(4, 3));
		othello.switchPlayer();
		othello.setPiece(new Coordinate(3, 3));
		othello.setPiece(new Coordinate(5, 3));
		othello.setPiece(new Coordinate(6, 3));
		
		String actual = othelloFormatter.formatResult(othello);
		String expected = "Player 'O' wins ( 3 vs 2 )";
		
		Assert.assertEquals(expected, actual);
	}

}
