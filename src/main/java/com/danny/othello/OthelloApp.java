package com.danny.othello;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;

import com.danny.othello.impl.OthelloFactoryImpl;
import com.danny.othello.impl.OthelloFormatterImpl;
import com.danny.othello.impl.OthelloGameImpl;
import com.danny.othello.impl.OthelloMoveConverterImpl;
import com.danny.othello.impl.UiImpl;
import com.danny.othello.intf.OthelloFormatter;
import com.danny.othello.intf.UI;

/**
 * Hello world!
 *
 */
public class OthelloApp {

	private static Log LOG = LogFactory.getLog(OthelloApp.class);

	public static void main(String[] args) {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		Logger.getRootLogger().removeAllAppenders();
		Logger.getRootLogger().addAppender(new NullAppender());

		LOG.info("START OthelloApp");
		OthelloFormatter othelloFormatter = new OthelloFormatterImpl();
		OthelloMoveConverterImpl othelloMoveConverter = new OthelloMoveConverterImpl();
		OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();
		othelloMoveConverter.setOthelloFactory(othelloFactory);
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
