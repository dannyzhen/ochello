package com.danny.othello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.util.OthelloMoveConverter;
import com.danny.othello.util.OthelloPrinter;
import com.danny.othello.util.OthelloPrinterImpl;
import com.danny.othello.util.RCOthelloMoveConverter;

/**
 * Hello world!
 *
 */
public class OthelloApp {
	
	private static Log LOG =LogFactory.getLog(OthelloApp.class);
	
	public static void main(String[] args) {
		
		LOG.info("START OthelloApp");
		OthelloPrinter othelloPrinter = new OthelloPrinterImpl();
		OthelloMoveConverter othelloMoveConverter = new RCOthelloMoveConverter();
		
		Othello othello = new Othello(8, 8, OthelloConstant.PLAYER_X, OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_X);
		othello.setPiece(othelloMoveConverter.convert("4e"));
		othello.setPiece(othelloMoveConverter.convert("5d"));
		othello.switchPlayer();
		othello.setPiece(othelloMoveConverter.convert("4d"));
		othello.setPiece(othelloMoveConverter.convert("5e"));
		othello.switchPlayer();
		LOG.info(String.format("Init othello with %s rows, %s columns, current player as %s", 8, 8, OthelloConstant.PLAYER_X));
		LOG.info(String.format("Init othello with 4e=%s, 5d=%s, 4d=%s, 5e=%s", 
				OthelloConstant.PLAYER_X,
				OthelloConstant.PLAYER_X,
				OthelloConstant.PLAYER_O,
				OthelloConstant.PLAYER_O));

		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setOthelloPrinter(othelloPrinter);

		othelloGame.start(othello);
		
		LOG.info("END OthelloApp");
	}
}
