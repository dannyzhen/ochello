package com.danny.othello.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.intf.OthelloFactory;

public class OthelloFactoryImpl implements OthelloFactory{
	private static Log LOG =LogFactory.getLog(OthelloFactoryImpl.class);

	private Map<String, Coordinate> coordinates = new HashMap<String, Coordinate>();
	
	public OthelloFactoryImpl() {
		init();
	}

	public Othello createOthello() {
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(getCoordinate("4e"));
		othello.setPiece(getCoordinate("5d"));
		othello.switchPlayer();
		othello.setPiece(getCoordinate("4d"));
		othello.setPiece(getCoordinate("5e"));
		othello.switchPlayer();
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("Init othello with %s rows, %s columns, current player as %s", 8, 8, OthelloConstant.PLAYER_X));
			LOG.info(String.format("Init othello with 4e=%s, 5d=%s, 4d=%s, 5e=%s", 
					OthelloConstant.PLAYER_X,
					OthelloConstant.PLAYER_X,
					OthelloConstant.PLAYER_O,
					OthelloConstant.PLAYER_O));
		}
		
		return othello;
	}
	
	private void init() {
		for (int row = 1; row < 9; row ++) {
			for (char column = 'a'; column < 'i'; column ++) {
				Coordinate coordinate = new Coordinate(row - 1, column - OthelloConstant.CHAR_BASE);
				coordinates.put(String.valueOf(row) + column, coordinate);
				coordinates.put(column + String.valueOf(row), coordinate);
			}
		}
	}

	public Coordinate getCoordinate(int row, int column) {
		return getCoordinate(String.valueOf(row + 1) + ((char)(column + OthelloConstant.CHAR_BASE)));
	}

	public Coordinate getCoordinate(String moveString) {
		Coordinate coordinate = null;
		if (moveString != null && moveString.trim().length() == 2) {
			coordinate = coordinates.get(moveString.trim().toLowerCase());
		}
		return coordinate;
	}

}
