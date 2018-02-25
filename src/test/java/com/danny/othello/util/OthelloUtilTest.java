package com.danny.othello.util;

import org.junit.Test;

import com.danny.othello.bean.Coordinate;

import junit.framework.Assert;

public class OthelloUtilTest {

	@Test
	public void testValid() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, new Coordinate(3, 2));
		boolean expected = true;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testNullCoordinate() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, null);
		boolean expected = false;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testNegativeColumn() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, new Coordinate(3, -1));
		boolean expected = false;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testNegativeRow() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, new Coordinate(-1, 2));
		boolean expected = false;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOutbountColumn() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, new Coordinate(3, 3));
		boolean expected = false;
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testOutbountRow() {
		boolean actual = OthelloUtil.isValidCoordinate(3, 4, new Coordinate(4, 2));
		boolean expected = false;
		Assert.assertEquals(expected, actual);
	}

}
