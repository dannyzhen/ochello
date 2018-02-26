package com.danny.othello.util;

import org.junit.Assert;
import org.junit.Test;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Othello;

public class OthelloFactoryImplTest {
	
	OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();
	OthelloMoveConverterImpl othelloMoveConverter = new OthelloMoveConverterImpl();

	@Test
	public void test() {
		othelloFactory.setOthelloMoveConverter(othelloMoveConverter);
		
		Othello othello= othelloFactory.createOthello();
		
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getCurrentPlayer());
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getPieces()[3][4]);
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getPieces()[4][3]);
		Assert.assertEquals(OthelloConstant.PLAYER_O, othello.getPieces()[3][3]);
		Assert.assertEquals(OthelloConstant.PLAYER_O, othello.getPieces()[4][4]);
	}

}
