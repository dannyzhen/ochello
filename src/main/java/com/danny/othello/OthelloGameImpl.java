package com.danny.othello;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danny.othello.bean.Coordinate;
import com.danny.othello.bean.Othello;
import com.danny.othello.util.OthelloMoveConverter;
import com.danny.othello.util.OthelloPrinter;
import com.danny.othello.util.OthelloUtil;

public class OthelloGameImpl implements OthelloGame{
	
	private static Log LOG =LogFactory.getLog(OthelloGameImpl.class);
	
	private OthelloPrinter othelloPrinter;
	private OthelloMoveConverter othelloMoveConverter;
	
	public void start(Othello othello) {
		if (othello == null) {
			throw new IllegalArgumentException("Othello is null");
		}

		othelloPrinter.print(othello);
		while (true) {
			if (canMove(othello)) {

				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Start to accept move from player %s ", othello.getCurrentPlayer()));
				}
				
				move(othello);

				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("End move from player %s ", othello.getCurrentPlayer()));
				}
				othello.switchPlayer();
				othelloPrinter.print(othello);
			}
			else {
				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("No available move for player %s ", othello.getCurrentPlayer()));
				}
				
				othello.switchPlayer();

				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Switch to player %s ", othello.getCurrentPlayer()));
				}
				
				if (canMove(othello)) {
					
					if (LOG.isInfoEnabled()) {
						LOG.info(String.format("Start to accept move from player %s ", othello.getCurrentPlayer()));
					}
					
					move(othello);
					
					if (LOG.isInfoEnabled()) {
						LOG.info(String.format("End move from player %s ", othello.getCurrentPlayer()));
					}
					
					othello.switchPlayer();
					othelloPrinter.print(othello);
				}
				else {
					
					if (LOG.isInfoEnabled()) {
						LOG.info("No further moves available");
					}
					
					System.out.println("No further moves available");
					othelloPrinter.printResult(othello);
					break;
				}
			}
		}
	}
	
	protected void move(Othello othello) {
		while (true) {
			String moveString = readDataFromConsole(String.format("Player '%s' move: ", othello.getCurrentPlayer()));
			Coordinate coordinate = null;

			if (LOG.isInfoEnabled()) {
				LOG.info(String.format("Received move %s from player %s", moveString, othello.getCurrentPlayer()));
			}
			try {
				coordinate = othelloMoveConverter.convert(moveString);
			}
			catch (OthelloException e) {
				//Invalid move format
				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Invalid move format %s", moveString));
				}
				System.out.println("Invalid move. Please try again.");
				continue;
			}
			
			//Coordinate has valid index and the corresponding piece is empty
			if (OthelloUtil.isValidCoordinate(othello.getColumns(), othello.getRows(), coordinate)
					&& othello.getPieces()[coordinate.getRow()][coordinate.getColumn()] == 0) {
				
				
				//Found captured pieces means it's valid move, perform the move and break WHILE
				List<Coordinate> capturedPieces = getCapturedPieces(othello, coordinate);
				if (capturedPieces.size() > 0) {
					othello.setPiece(coordinate);
					for (Coordinate capturedPiece : capturedPieces) {
						if (LOG.isInfoEnabled()) {
							LOG.info(String.format("Captured piece on row %s column %s", capturedPiece.getRow(), capturedPiece.getColumn()));
						}
						othello.setPiece(capturedPiece);
					}
					break;
				}
				else {
					//No captured pieces found, invalid move
					if (LOG.isInfoEnabled()) {
						LOG.info(String.format("Invalid move %s as no captured pieces found", moveString));
					}
					System.out.println("Invalid move. Please try again.");
				}
			}
			else {
				//Invalid row or column or the piece is occupied

				if (LOG.isInfoEnabled()) {
					LOG.info(String.format("Invalid move %s", moveString));
				}
				System.out.println("Invalid move. Please try again.");
			}
		}
	}
	
	/**
	 * Get all the captured pieces for a move on coordinate for current user
	 * @param othello
	 * @param coordinate
	 * @return
	 */
	protected List<Coordinate> getCapturedPieces(Othello othello, Coordinate coordinate) {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		coordinates.addAll(getCapturedPieces(othello, coordinate, 1, 0)); //east
		coordinates.addAll(getCapturedPieces(othello, coordinate, -1, 0)); //west
		coordinates.addAll(getCapturedPieces(othello, coordinate, 0, 1)); //north
		coordinates.addAll(getCapturedPieces(othello, coordinate, 0, -1)); //south
		coordinates.addAll(getCapturedPieces(othello, coordinate, 1, 1)); //southeast
		coordinates.addAll(getCapturedPieces(othello, coordinate, 1, -1)); //northeast
		coordinates.addAll(getCapturedPieces(othello, coordinate, -1, 1)); //southwest
		coordinates.addAll(getCapturedPieces(othello, coordinate, -1, -1)); //northwest
		return coordinates;
	}
	
	/**
	 * Check if there is a valid move for current player
	 * @param othello
	 * @return
	 */
	protected boolean canMove(Othello othello) {
		boolean canMove = false;
		char[][] pieces = othello.getPieces();
		for (int row = 0; row < othello.getRows() && (!canMove); row ++) {
			for (int column = 0; column < othello.getColumns() && (!canMove); column ++) {
				if (pieces[row][column] == 0) {
					Coordinate coordinate = new Coordinate(row, column);
					canMove = canMove(othello, coordinate, 1, 0) //east
							|| canMove(othello, coordinate, -1, 0) //west
							|| canMove(othello, coordinate, 0, 1) //north
							|| canMove(othello, coordinate, 0, -1) //south
							|| canMove(othello, coordinate, 1, 1) //southeast
							|| canMove(othello, coordinate, 1, -1) //northeast
							|| canMove(othello, coordinate, -1, 1) //southwest
							|| canMove(othello, coordinate, -1, -1); //northwest
					if (LOG.isDebugEnabled()) {
						LOG.debug(String.format("CanMove check on row %s column %s for %s :%s ", row, column, othello.getCurrentPlayer(), canMove));
					}
				}
			}
		}
		
		if (LOG.isInfoEnabled()) {
			LOG.info(String.format("CanMove check for %s :%s ", othello.getCurrentPlayer(), canMove));
		}
		
		return canMove;
	}
	
	private boolean canMove(Othello othello, Coordinate coordinate, int columnCoef, int rowCoef) {
		return getCapturedPieces(othello, coordinate, columnCoef, rowCoef).size() > 0;
	}
	
	private List<Coordinate> getCapturedPieces(Othello othello, Coordinate coordinate, int columnCoef, int rowCoef) {
		boolean foundCapturedPieces = false;
		char currentPlayer = othello.getCurrentPlayer();
		char[][] pieces = othello.getPieces();
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		int maxChecks = Math.max(othello.getColumns(), othello.getRows());
		for (int checks = 1; checks < maxChecks; checks ++) {
			int columnIndex = coordinate.getColumn() + checks * columnCoef;
			int rowIndex = coordinate.getRow() + checks * rowCoef;
			
			//break if outbound
			if (columnIndex < 0 
					|| columnIndex >= othello.getColumns() 
					|| rowIndex < 0
					|| rowIndex >= othello.getRows()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug(String.format("Outbound with row %s column %s", rowIndex, columnIndex));
				}
				break;
			}
			
			//Case (first X is the pending move): XOO-X
			//Empty piece found before current player piece
			char player = pieces[rowIndex][columnIndex];
			if (player == 0) {
				foundCapturedPieces = false;
				if (LOG.isDebugEnabled()) {
					LOG.debug(String.format("Empty piece found with row %s column %s", rowIndex, columnIndex));
				}
				break;
			}
			
			//Case check > 1(first X is the pending move): XOOX
			//Case check = 1(first X is the pending move): XX
			//If piece equals to current player and checks > 1, that means at least 1 captured piece found
			if (player == currentPlayer) {
				foundCapturedPieces = checks > 1 ? true : false;
				if (LOG.isDebugEnabled()) {
					LOG.debug(String.format("Piece matches after checks %s with row %s column %s", checks, rowIndex, columnIndex));
				}
				break;
			}
			
			coordinates.add(new Coordinate(rowIndex, columnIndex));
		}
		
		if (!foundCapturedPieces) {
			coordinates.clear();
		}
		
		
		return coordinates;
	}
	
	protected String readDataFromConsole(String prompt) {
        Console console = System.console();
        if (console == null) {
            throw new IllegalStateException("Console is not available!");
        }
        return console.readLine(prompt);
    }

	public void setOthelloPrinter(OthelloPrinter othelloPrinter) {
		this.othelloPrinter = othelloPrinter;
	}

	public void setOthelloMoveConverter(OthelloMoveConverter othelloMoveConverter) {
		this.othelloMoveConverter = othelloMoveConverter;
	}

}
