package com.danny.othello.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.danny.othello.bean.Coordinate;

public class YXOthelloMoveConverterTest {
	
	private RCOthelloMoveConverter converter = new RCOthelloMoveConverter();
	
	@Test
	public void testConvert1h() {
		Coordinate result = converter.convert("1h");
		assertNotNull(result);
		assertEquals(0, result.getRow());
		assertEquals(7, result.getColumn());
	}
	
	@Test
	public void testConvert8h() {
		Coordinate result = converter.convert("8h");
		assertNotNull(result);
		assertEquals(7, result.getRow());
		assertEquals(7, result.getColumn());
	}
	
	@Test
	public void testConvert1a() {
		Coordinate result = converter.convert("1a");
		assertNotNull(result);
		assertEquals(0, result.getRow());
		assertEquals(0, result.getColumn());
	}
	
	@Test
	public void testConvert8a() {
		Coordinate result = converter.convert("8a");
		assertNotNull(result);
		assertEquals(7, result.getRow());
		assertEquals(0, result.getColumn());
	}

}
