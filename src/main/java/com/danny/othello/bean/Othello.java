package com.danny.othello.bean;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

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
	private Deque<List<Coordinate>> moveCaptureds;
	private char startPlayer;
	
	public Othello(int columns, int rows, char player1, char player2, char startPlayer) {
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
		
		if (startPlayer != player1 && startPlayer != player2) {
			throw new OthelloException(String.format("Start player must be one of player1 %s or player2 %s", startPlayer, player1, player2));
		}
		
		this.columns = columns;
		this.rows = rows;
		this.player1 = player1;
		this.player2 = player2;
		this.currentPlayer = startPlayer;
		this.startPlayer = startPlayer;
		
		pieces = new char[rows][columns];
		moveCaptureds = new LinkedList<List<Coordinate>>();
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
	
	public void addMoveCaptureds(List<Coordinate> captureds) {
		moveCaptureds.addLast(captureds);
	}
	
	public List<Coordinate> removeLastMoveCaptureds() {
		List<Coordinate> captureds = null;
		if (moveCaptureds.size() > 0) {
			captureds = moveCaptureds.removeLast();
		}
		return captureds;
	}
	
	public void resetPiece(Coordinate coordinate) {
		
		if (OthelloUtil.isValidCoordinate(columns, rows, coordinate)) {
			pieces[coordinate.getRow()][coordinate.getColumn()] = 0;
		}

	}
	
	public void resetCurrentPlayerAfterUndo() {
		if (moveCaptureds.size() % 2 == 1 && currentPlayer != startPlayer) {
			switchPlayer();
		}
	}
	
	

}
