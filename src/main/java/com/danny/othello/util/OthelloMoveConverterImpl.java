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
public class OthelloMoveConverterImpl implements OthelloMoveConverter {

	private Pattern pattern = Pattern.compile(OthelloConstant.MOVE_REGEX);

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
			String firstChar = matcher.group(1) == null ? matcher.group(4) : matcher.group(1);
			String secondChar = matcher.group(2) == null ? matcher.group(3) : matcher.group(2);

			move = new Coordinate(Integer.parseInt(firstChar) - 1,
					Integer.valueOf(secondChar.charAt(0)) - OthelloConstant.CHAR_BASE);

		} else {
			throw new OthelloException(String.format("Invalid move %s", moveString));
		}

		return move;

	}

}
