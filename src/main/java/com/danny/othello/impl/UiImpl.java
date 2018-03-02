package com.danny.othello.impl;

import java.util.Scanner;

import com.danny.othello.intf.UI;

public class UiImpl implements UI{
	private Scanner scanner = new Scanner(System.in);

	public void write(String message) {
		System.out.println(message);
		
	}

	public String read(String prompt) {
		System.out.print(prompt);
		System.out.flush();
        String input = scanner.nextLine();
        return input;
	}

}
