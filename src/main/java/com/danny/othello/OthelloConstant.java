package com.danny.othello;

public interface OthelloConstant {
	
	char PLAYER_X = 'X';
	char PLAYER_O = 'O';
	char EMPTY_DISPLAY_VALUE = '-';
	String MOVE_REGEX = "^([1-8])([a-hA-H])|([a-hA-H])([1-8])$";
	short CHAR_BASE = 97; 
	String UNDO_MOVE = "u";

}
