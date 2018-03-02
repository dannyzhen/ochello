package com.danny.othello.intf;

import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;

public interface OthelloFactory {
	Othello createOthello();
	
	Coordinate getCoordinate(int row, int column);
	Coordinate getCoordinate(String moveString);
}
