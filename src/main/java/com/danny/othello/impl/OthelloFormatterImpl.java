package com.danny.othello.impl;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Othello;
import com.danny.othello.intf.OthelloFormatter;

public class OthelloFormatterImpl implements OthelloFormatter{
	private final static short CHAR_BASE = 97; 
	private final static String SPACE = " ";
	private char emptyDisplayValue = OthelloConstant.EMPTY_DISPLAY_VALUE;

	public String format(Othello othello) {
		String formattedString = null;
		if (othello != null) {
			char[][] pieces = othello.getPieces();
			StringBuffer othelloString = new StringBuffer();
			for (int row = 0; row < othello.getRows(); row ++) {
				othelloString.append(row + 1).append(SPACE);
				for (char piece : pieces[row]) {
					othelloString.append(piece == 0 ? emptyDisplayValue : piece);
				}
				othelloString.append(System.lineSeparator());
			}
			
			int ySize = pieces[0].length;
			othelloString.append(SPACE).append(SPACE);
			for (int i = 0; i < ySize; i ++) {
				othelloString.append((char)(CHAR_BASE + i));
			}
			formattedString =  othelloString.toString();
		}
		
		return formattedString;
		
	}

	public void setEmptyDisplayValue(char emptyDisplayValue) {
		this.emptyDisplayValue = emptyDisplayValue;
	}

	public String formatResult(Othello othello) {
		
		String resultString = null;
		
		char player1 = othello.getCurrentPlayer();
		othello.switchPlayer();
		char player2 = othello.getCurrentPlayer();
		
		int player1Pieces = 0;
		int player2Pieces = 0;
		char[][] pieces = othello.getPieces();
		for (int row = 0; row < othello.getRows(); row ++) {
			for (int column = 0; column < othello.getColumns(); column ++) {
				if (pieces[row][column] == player1) {
					player1Pieces ++;
				}
				else if (pieces[row][column] == player2) {
					player2Pieces ++;
				}
			}
		}
		
		if (player1Pieces > player2Pieces) {
			resultString = String.format("Player '%s' wins ( %s vs %s )", player1, player1Pieces, player2Pieces);
		}
		else if (player1Pieces == player2Pieces) {
			resultString = String.format("Draw ( %s vs %s )", player1Pieces, player2Pieces);
		}
		else {
			resultString = String.format("Player '%s' wins ( %s vs %s )", player2, player2Pieces, player1Pieces);
		}
		
		return resultString;
		
		
	}

}
