package com.danny.othello.intf;

import com.danny.othello.bean.Coordinate;

public interface OthelloMoveConverter {
	Coordinate convert(String moveString);
}
