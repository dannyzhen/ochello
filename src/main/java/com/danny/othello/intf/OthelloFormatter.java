package com.danny.othello.intf;

import com.danny.othello.bean.Othello;

public interface OthelloFormatter {
	String format(Othello othello);
	
	String formatResult(Othello othello);

}
