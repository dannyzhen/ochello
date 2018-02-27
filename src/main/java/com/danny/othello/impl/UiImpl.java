package com.danny.othello.impl;

import java.io.Console;

import com.danny.othello.OthelloException;
import com.danny.othello.intf.UI;

public class UiImpl implements UI{

	public void write(String message) {
		System.out.println(message);
		
	}

	public String read(String prompt) {
		Console console = System.console();
        if (console == null) {
            throw new OthelloException("Console is not available!");
        }
        return console.readLine(prompt);
	}

}
