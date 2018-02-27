package com.danny.othello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danny.othello.impl.OthelloFactoryImpl;
import com.danny.othello.impl.OthelloFormatterImpl;
import com.danny.othello.impl.OthelloGameImpl;
import com.danny.othello.impl.OthelloMoveConverterImpl;
import com.danny.othello.impl.UiImpl;
import com.danny.othello.intf.OthelloFormatter;
import com.danny.othello.intf.OthelloMoveConverter;
import com.danny.othello.intf.UI;

/**
 * Hello world!
 *
 */
public class OthelloApp {
	
	private static Log LOG =LogFactory.getLog(OthelloApp.class);
	
	public static void main(String[] args) {
		
		LOG.info("START OthelloApp");
		OthelloFormatter othelloFormatter = new OthelloFormatterImpl();
		OthelloMoveConverter othelloMoveConverter = new OthelloMoveConverterImpl();
		OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();  
		othelloFactory.setOthelloMoveConverter(othelloMoveConverter);
		UI ui = new UiImpl();

		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setOthelloFormatter(othelloFormatter);
		othelloGame.setOthelloFactory(othelloFactory);
		othelloGame.setUi(ui);

		othelloGame.start();
		
		LOG.info("END OthelloApp");
	}
}
