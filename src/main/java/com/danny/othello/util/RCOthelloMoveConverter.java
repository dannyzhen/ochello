package com.danny.othello.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.danny.othello.OthelloConstant;
import com.danny.othello.OthelloException;
import com.danny.othello.bean.Coordinate;

/**
 * Convert input move string into Move
 *
 */
public class RCOthelloMoveConverter implements OthelloMoveConverter{
	
	private Pattern pattern = Pattern.compile(OthelloConstant.MOVE_YX_REGEX);

	/**
	 * 
	 */
	public Coordinate convert(String moveString) {
		Coordinate move = null;
		if (moveString == null || !pattern.matcher(moveString.trim()).matches()) {
			throw new OthelloException(String.format("Invalid move %s", moveString));
		}
		
		Matcher matcher = pattern.matcher(moveString.trim());
		if (matcher.matches()) {
			move = new Coordinate(Integer.valueOf(matcher.group(1)) - 1,
					Integer.valueOf(matcher.group(2).charAt(0)) - OthelloConstant.CHAR_BASE);
		}
		else {
			throw new OthelloException(String.format("Invalid move %s", moveString));
		}
		
		return move;
		
	}

}
