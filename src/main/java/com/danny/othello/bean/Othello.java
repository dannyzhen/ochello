package com.danny.othello.bean;

import com.danny.othello.OthelloException;
import com.danny.othello.util.OthelloUtil;

public class Othello {
	//Column count
	private int columns;
	//Row count
	private int rows;
	//Pieces in the chessboard, 0 means not occupied
	private char[][] pieces;
	private char player1;
	private char player2;
	private char currentPlayer;
	
	public Othello(int columns, int rows, char player1, char player2, char currentPlayer) {
		if (columns < 1) {
			throw new OthelloException("Invalid column count: " + columns);
		}
		if (rows < 1) {
			throw new OthelloException("Invalid row count: " + rows);
		}
		
		if (player1 == 0) {
			throw new OthelloException("Invalid player1");
		}
		
		if (player2 == 0) {
			throw new OthelloException("Invalid player2");			
		}
		
		if (player1 == player2) {
			throw new OthelloException("Player1 and Player2 can't be same");
		}
		
		if (currentPlayer != player1 && currentPlayer != player2) {
			throw new OthelloException(String.format("Current player must be one of player1 %s or player2 %s", currentPlayer, player1, player2));
		}
		
		this.columns = columns;
		this.rows = rows;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = currentPlayer;
		
		pieces = new char[rows][columns];
	}
	
	
	public void setPiece(Coordinate coordinate) {
		
		if (OthelloUtil.isValidCoordinate(columns, rows, coordinate)) {
			pieces[coordinate.getRow()][coordinate.getColumn()] = currentPlayer;
		}
	}
	
	public char[][] getPieces() {
		return pieces;
	}
	
	public char getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void switchPlayer() {
		currentPlayer = currentPlayer == player1 ? player2 : player1;
	}

	public int getColumns() {
		return columns;
	}

	public int getRows() {
		return rows;
	}

	
	
	

}
