package com.danny.othello.impl;

import com.danny.othello.OthelloException;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.intf.OthelloFactory;
import com.danny.othello.intf.OthelloMoveConverter;

/**
 * Convert input move string into Move
 *
 */
public class OthelloMoveConverterImpl implements OthelloMoveConverter {
	
	private OthelloFactory othelloFactory;
	/**
	 * 
	 */
	public Coordinate convert(String moveString) {
		Coordinate coordinate = null;
		if (moveString == null || moveString.trim().length() != 2) {
			throw new OthelloException(String.format("Invalid move %s", moveString));
		}

		coordinate = othelloFactory.getCoordinate(moveString.trim().toLowerCase());
		if (coordinate == null) {
			throw new OthelloException(String.format("Invalid move %s", moveString));
		}

		return coordinate;

	}
	
	public void setOthelloFactory(OthelloFactory othelloFactory) {
		this.othelloFactory = othelloFactory;
	}

}
