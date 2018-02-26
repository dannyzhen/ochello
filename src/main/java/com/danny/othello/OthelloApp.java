package com.danny.othello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danny.othello.util.OthelloFactoryImpl;
import com.danny.othello.util.OthelloMoveConverter;
import com.danny.othello.util.OthelloMoveConverterImpl;
import com.danny.othello.util.OthelloPrinter;
import com.danny.othello.util.OthelloPrinterImpl;

/**
 * Hello world!
 *
 */
public class OthelloApp {
	
	private static Log LOG =LogFactory.getLog(OthelloApp.class);
	
	public static void main(String[] args) {
		
		LOG.info("START OthelloApp");
		OthelloPrinter othelloPrinter = new OthelloPrinterImpl();
		OthelloMoveConverter othelloMoveConverter = new OthelloMoveConverterImpl();
		OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();  
		othelloFactory.setOthelloMoveConverter(othelloMoveConverter);

		OthelloGameImpl othelloGame = new OthelloGameImpl();
		othelloGame.setOthelloMoveConverter(othelloMoveConverter);
		othelloGame.setOthelloPrinter(othelloPrinter);
		othelloGame.setOthelloFactory(othelloFactory);

		othelloGame.start();
		
		LOG.info("END OthelloApp");
	}
}
