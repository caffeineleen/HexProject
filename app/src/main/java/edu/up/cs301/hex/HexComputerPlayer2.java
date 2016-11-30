package edu.up.cs301.hex;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import android.graphics.Point;

/**
 * A computerized hex player that recognizes an immediate win
 * or loss, and plays appropriately.  If there is not an immediate win
 * (which it plays) or loss (which it blocks), it moves randomly.
 * 
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 * 
 */
public class HexComputerPlayer2 extends GameComputerPlayer
{
	/**
	 * instance variable that tells which piece am I playing ('X' or 'O').
	 * This is set once the player finds out which player they are, in the
	 * 'initAfterReady' method.
	 */
	protected char piece;

	/**
	 * constructor for a computer player
	 * 
	 * @param name
	 * 		the player's name
	 */
	public HexComputerPlayer2(String name) {
		// invoke superclass constructor
		super(name);
	}// constructor

	/**
	 * perform any initialization that needs to be done after the player
	 * knows what their game-position and opponents' names are.
	 */
	protected void initAfterReady() {
		// initialize our piece
		piece = "XO".charAt(playerNum);
	}// initAfterReady

	/**
	 * Called when the player receives a game-state (or other info) from the
	 * game.
	 * 
	 * @param info
	 * 		the message from the game
	 */
	@Override
	protected void receiveInfo(GameInfo info) {

		// if it's not a HexState message, ignore it; otherwise
		// cast it
		if (!(info instanceof HexState)) return;
		HexState myState = (HexState)info;

		// if it's not our move, ignore it
		//if (myState.getWhoseMove() != this.playerNum) return;

		// sleep for a second to make any observers think that we're thinking
		//sleep(1000);


		for(int i = 1; i < 12; i++) {
			for(int j = 1; j < 12; j++) {
				double ran = Math.random();
				if (ran < .166 &&myState.getStone(j, i) > 200 && myState.getStone(j+1, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j+1));
					return;
				}
				else if (ran > .166 && ran < .33 && myState.getStone(j, i) > 200 && myState.getStone(j-1, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j-1));
					return;
				}
				else if (ran > .33 && ran < .499 && myState.getStone(j, i) > 200 && myState.getStone(j-1, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j-1));
					return;
				}
				else if (ran > .499 && ran < .66 && myState.getStone(j, i) > 200 && myState.getStone(j+1, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j+1));
					return;
				}
				else if (ran > .66 && ran < .833 && myState.getStone(j, i) > 200 && myState.getStone(j, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j));
					return;
				}
				else if (ran > .833 && myState.getStone(j, i) > 200 && myState.getStone(j, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j));
					return;
				}

				else if(myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200) {
					int xVal = (int) (11 * Math.random() + 1);
					int yVal = (int) (11 * Math.random() + 1);
					game.sendAction(new HexMoveAction(this, xVal, yVal));
					return;
				}
			}
		}

		for(int i = 1; i < 12; i++) {
			for(int j = 1; j < 12; j++) {
				double ran = Math.random();
				if (ran < .125 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j+1));
					return;
				}
				else if (ran > .125 && ran < .25 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j-1));
					return;
				}
				else if (ran > .25 && ran < .375 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j-1));
					return;
				}
				else if (ran > .375 && ran < .50 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j+1));
					return;
				}
				else if (ran > .50 && ran < .625 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j, i+1) == 0) {
					game.sendAction(new HexMoveAction(this, i+1, j));
					return;
				}
				else if (ran > .625 && ran < .75 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j, i-1) == 0) {
					game.sendAction(new HexMoveAction(this, i-1, j));
					return;
				}
				else if (ran > .75 && ran < .875 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i) == 0) {
					game.sendAction(new HexMoveAction(this, i, j+1));
					return;
				}
				else if (ran > .875 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i) == 0) {
					game.sendAction(new HexMoveAction(this, i, j-1));
					return;
				}

			}
		}


	}// receiveInfo

	/**
	 * finds a winning move for a player
	 * 
	 * @param state  the state of the game
	 * @param thePiece  the piece we're trying to place ('X' or 'O') for a
	 *   win
	 * @return  If a winning move was found, a Point object containing
	 *   the coordinates.  If no winning move was found, null.
	 */
	private Point findWin(HexState state, char thePiece) {

		// the winning move--initialized to null because we haven't found
		// one yet
		Point found = null;

		// iterate through each of the positions 0, 1 and 2, examining a
		// vertical, horizontal and diagonal on each iteration
		//
		for (int i = 0; i < 13; i++) {

			// winning value we found, if any
			Point temp = null;

			// examine row that begins at (i, 0)
			if ((temp = helpFindWin(state, thePiece, i, 0, 0, 1)) != null) {
				found = temp;
			}

			// examine column that begins at (0, i)
			if ((temp = helpFindWin(state, thePiece, 0, i, 1, 0)) != null) {
				found = temp;
			}

			// examine diagonal that beings at (i, 0).  (When i = 1, we'll
			// actually be redundantly examining a row.)
			if ((temp = helpFindWin(state, thePiece, i, 0, 1-i, 1)) != null) {
				found = temp;
			}
		}

		// return whatever we've found--either a winning move or null
		return found;
	}// findWin

	/**
	 * examines a particular row, column or diagonal to see if a move there
	 * would cause a given player to win.  <p>
	 * 
	 * We can examine row by specifying rowDelta=0 and colDelta=1.  We can
	 * examine a column by specifying rowDelta=1 and colDelta=0.  We can
	 * examine a diagonal by specifying rowDelta=1 and colDelta=-1 or
	 * vice versa.
	 * 
	 * @param state  the state of the game
	 * @param thePiece  the piece that we would place to achieve the win
	 * @param rowStart the row-position of first square in the row/col
	 *   we're examining
	 * @param colStart the columnPosition of the first square in the row/col
	 *   we're examining
	 * @param rowDelta  the amount to change the row-position to get to the
	 *   next square we're examining
	 * @param colDelta  the amount to change the column-position to get to
	 *   the next square we're examining
	 * @return  If a winning move was found, a Point object containing
	 *   the coordinates.  If no winning move was found, null.
	 */
	// helper method to find a winning move
	private Point helpFindWin(HexState state, char thePiece, int rowStart,
							  int colStart, int rowDelta, int colDelta) {

		// our starting position
		int row = rowStart;
		int col = colStart;

		// number of pieces we've found so far on our line
		int matchingPieceCount = 0;

		// the last spot we've found that contains a blank, if any
		Point blankSpot = null;

		// determine if the three squares in question contain exactly two
		// square of the given piece and one square of that is blank
		//
		for (int i = 0; i < 13; i++) {

			// get the piece at the position
			char pc = state.getPiece(row,col);

			// if we match the given piece, bump the matching piece-count; otherwise,
			// if we match a blank, set the blank-spot
			if (pc == thePiece) {
				matchingPieceCount++;
			}
			else if (pc == ' ') {
				blankSpot = new Point(col, row);
			}

			// bump row and column positions for next iteration
			row += rowDelta;
			col += colDelta;
		}

		// at this point, we've examined all three squares.  We have a
		// candidate for a "win" if we matched two pieces and had one blank
		// (i.e., pieceCount and blankSpot is non-null)
		if (matchingPieceCount == 2 && blankSpot != null) {
			// have a winning move
			return blankSpot;
		}
		else {
			// no winner this time
			return null;
		}
	}// helpFindWin

}