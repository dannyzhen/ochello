package com.danny.othello.util;

import com.danny.othello.bean.Coordinate;

public class OthelloUtil {
	
	/**
	 * Check if coordinate is valid
	 * @param columns
	 * @param rows
	 * @param coordinate
	 * @return
	 */
	public static boolean isValidCoordinate(int columns, int rows, Coordinate coordinate) {
		boolean valid = false;
		if (coordinate != null 
				&& coordinate.getColumn() >= 0
				&& coordinate.getColumn() < columns
				&& coordinate.getRow() >= 0
				&& coordinate.getRow() < rows) {
			valid = true;
		}
		
		return valid;
	}

}
