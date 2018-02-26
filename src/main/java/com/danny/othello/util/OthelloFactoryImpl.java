package com.danny.othello.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Othello;

public class OthelloFactoryImpl implements OthelloFactory{
	private static Log LOG =LogFactory.getLog(OthelloFactoryImpl.class);
	
	private OthelloMoveConverter othelloMoveConverter;

	public Othello createOthello() {
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(othelloMoveConverter.convert("4e"));
		othello.setPiece(othelloMoveConverter.convert("5d"));
		othello.switchPlayer();
		othello.setPiece(othelloMoveConverter.convert("4d"));
		othello.setPiece(othelloMoveConverter.convert("5e"));
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

	public void setOthelloMoveConverter(OthelloMoveConverter othelloMoveConverter) {
		this.othelloMoveConverter = othelloMoveConverter;
	}

}
