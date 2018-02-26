package com.danny.othello.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.danny.othello.OthelloException;
import com.danny.othello.bean.Coordinate;

public class OthelloMoveConverterImplTest {
	
	private OthelloMoveConverterImpl converter = new OthelloMoveConverterImpl();
	
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
	
	@Test
	public void testConverth1() {
		Coordinate result = converter.convert("h1");
		assertNotNull(result);
		assertEquals(0, result.getRow());
		assertEquals(7, result.getColumn());
	}
	
	@Test
	public void testConverth8() {
		Coordinate result = converter.convert("h8");
		assertNotNull(result);
		assertEquals(7, result.getRow());
		assertEquals(7, result.getColumn());
	}
	
	@Test
	public void testConverta1() {
		Coordinate result = converter.convert("a1");
		assertNotNull(result);
		assertEquals(0, result.getRow());
		assertEquals(0, result.getColumn());
	}
	
	@Test
	public void testConverta8() {
		Coordinate result = converter.convert("a8");
		assertNotNull(result);
		assertEquals(7, result.getRow());
		assertEquals(0, result.getColumn());
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalidi8() {
		converter.convert("i8");
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalid9a() {
		converter.convert("9a");
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalid3ab4() {
		converter.convert("3ab4");
	}

	
	@Test(expected=OthelloException.class)
	public void testConvertInvalid3aa() {
		converter.convert("3aa");
	}

}
