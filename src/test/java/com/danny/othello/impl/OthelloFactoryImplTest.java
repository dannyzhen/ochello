package com.danny.othello.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import com.danny.othello.OthelloConstant;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;

public class OthelloFactoryImplTest {
	
	OthelloFactoryImpl othelloFactory = new OthelloFactoryImpl();

	@Test
	public void test() {
		
		Othello othello= othelloFactory.createOthello();
		
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getCurrentPlayer());
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getPieces()[3][4]);
		Assert.assertEquals(OthelloConstant.PLAYER_X, othello.getPieces()[4][3]);
		Assert.assertEquals(OthelloConstant.PLAYER_O, othello.getPieces()[3][3]);
		Assert.assertEquals(OthelloConstant.PLAYER_O, othello.getPieces()[4][4]);
	}


	@Test
	public void testConvert1h() {
		Coordinate actual = othelloFactory.getCoordinate("1h");
		assertNotNull(actual);
		assertEquals(0, actual.getRow());
		assertEquals(7, actual.getColumn());
	}
	
	@Test
	public void testConvert8h() {
		Coordinate actual = othelloFactory.getCoordinate("8h");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(7, actual.getColumn());
	}
	
	@Test
	public void testConvert1a() {
		Coordinate actual = othelloFactory.getCoordinate("1a");
		assertNotNull(actual);
		assertEquals(0, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConvert8a() {
		Coordinate actual = othelloFactory.getCoordinate("8a");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConvert8A() {
		Coordinate actual = othelloFactory.getCoordinate("8A");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConverth1() {
		Coordinate actual = othelloFactory.getCoordinate("h1");
		assertNotNull(actual);
		assertEquals(0, actual.getRow());
		assertEquals(7, actual.getColumn());
	}
	
	@Test
	public void testConverth8() {
		Coordinate actual = othelloFactory.getCoordinate("h8");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(7, actual.getColumn());
	}
	
	@Test
	public void testConverta1() {
		Coordinate actual = othelloFactory.getCoordinate("a1");
		assertNotNull(actual);
		assertEquals(0, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConverta8() {
		Coordinate actual = othelloFactory.getCoordinate("a8");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConvertA8() {
		Coordinate actual = othelloFactory.getCoordinate("A8");
		assertNotNull(actual);
		assertEquals(7, actual.getRow());
		assertEquals(0, actual.getColumn());
	}
	
	@Test
	public void testConvertInvalidi8() {
		Coordinate actual = othelloFactory.getCoordinate("i8");
		Assert.assertNull(actual);
	}
	
	@Test
	public void testConvertInvalid9a() {
		Coordinate actual = othelloFactory.getCoordinate("9a");
		Assert.assertNull(actual);
	}
	
	@Test
	public void testConvertInvalid3ab4() {
		Coordinate actual = othelloFactory.getCoordinate("3ab4");
		Assert.assertNull(actual);
	}

	
	@Test
	public void testConvertInvalid3aa() {
		Coordinate actual = othelloFactory.getCoordinate("3aa");
		Assert.assertNull(actual);
	}
}
