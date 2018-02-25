package com.danny.othello.util;

import com.danny.othello.bean.Coordinate;

public interface OthelloMoveConverter {
	Coordinate convert(String moveString);
}
