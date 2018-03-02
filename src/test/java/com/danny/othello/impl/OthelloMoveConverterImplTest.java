package com.danny.othello.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.danny.othello.OthelloException;
import com.danny.othello.bean.Coordinate;
import com.danny.othello.intf.OthelloFactory;

public class OthelloMoveConverterImplTest {
	
	private OthelloMoveConverterImpl converter = new OthelloMoveConverterImpl();
	private OthelloFactory othelloFactory;
	
	@Before
	public void before() {
		othelloFactory = EasyMock.createNiceMock(OthelloFactory.class);
		EasyMock.expect(othelloFactory.getCoordinate("1a")).andReturn(new Coordinate(1,1));
		EasyMock.replay(othelloFactory);
		converter.setOthelloFactory(othelloFactory);
		
	}
	
	@Test
	public void testConvertValid() {
		Coordinate actual = converter.convert("1A");
		assertNotNull(actual);
		assertEquals(1, actual.getRow());
		assertEquals(1, actual.getColumn());
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalidNull() {
		converter.convert(null);
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalidMoreThan2Chars() {
		converter.convert("a1a3");
	}
	
	@Test(expected=OthelloException.class)
	public void testConvertInvalid() {
		converter.convert("9a");
	}

	
	@Test(expected=OthelloException.class)
	public void testConvertInvalid3aa() {
		converter.convert("3aa");
	}

}
