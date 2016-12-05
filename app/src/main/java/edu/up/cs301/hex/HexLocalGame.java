package edu.up.cs301.hex;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * The HexLocalGame class for a simple hex game.  Defines and enforces
 * the game rules; handles interactions with players.
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */

public class HexLocalGame extends LocalGame {

	// the game's state
	protected HexState state;

	// the marks for player 0 and player 1, respectively
	private final static char[] mark = {'X','O'};
	private final static int[] stone = {102,202};


	// the number of moves that have been played so far, used to
	// determine whether the game is over
	protected int moveCount;

	/**
	 * Constructor for the HexLocalGame.
	 */
	public HexLocalGame() {

		// perform superclass initialization
		super();

		// create a new, unfilled-in HexState object
		state = new HexState();
	}

	/**
	 * Check if the game is over. It is over, return a string that tells
	 * who the winner(s), if any, are. If the game is not over, return null;
	 *
	 * @return
	 *        a message that tells who has won the game, or null if the
	 *        game is not over
	 */
	@Override
	protected String checkIfGameOver() {

		// the idea is that we simultaneously look at a row, column and
		// a diagonal, using the variables 'rowToken', 'colToken' and
		// 'diagToken'; we do this three times so that we get all three
		// rows, all three columns, and both diagonals.  (The way the
		// math works out, one of the diagonal tests tests the middle
		// column.)  The respective variables get set to ' ' unless
		// all characters in the line that have currently been seen are
		// identical; in this case the variable contains that character

		// the character that will eventually contain an 'X' or 'O' if we
		// find a winner
		char resultChar = ' ';

		// to all three lines in the current group
		for (int i = 0; i < 3; i++) {
			// get the initial character in each line
			char rowToken = state.getPiece(i,0);
			char colToken = state.getPiece(0,i);;
			char diagToken = state.getPiece(0,i);
			// determine the direction that the diagonal moves
			int diagDelta = 1-i;
			// look for matches for each of the three positions in each
			// of the current lines; set the corresponding variable to ' '
			// if a mismatch is found
			for (int j = 1; j < 3; j++) {
				if (state.getPiece(i,j) != rowToken) rowToken = ' ';
				if (state.getPiece(j,i) != colToken) colToken = ' ';
				if (state.getPiece(j, i+(diagDelta*j)) != diagToken) diagToken = ' ';
			}

			////////////////////////////////////////////////////////////
			// At this point, if any of our three variables is non-blank
			// then we have found a winner.
			////////////////////////////////////////////////////////////

			// if we find a winner, indicate such by setting 'resultChar'
			// to the winning mark.
			if (rowToken != ' ') resultChar = rowToken;
			else if (colToken != ' ') resultChar = colToken;
			else if (diagToken != ' ') resultChar = diagToken;
		}

		// if resultChar is blank, we found no winner, so return null,
		// unless the board is filled up. In that case, it's a cat's game.
//    if (resultChar == ' ') {
//       if  (moveCount >= 121) {
//          // no winner, but all 9 spots have been filled
//          return "It's a cat's game.";
//       }
//       else {
//          return null; // no winner, but game not over
//       }
//    }

		if (state.getStone(0,0) == state.getStone(12,12))
		{
			return "Red player Wins!";
		}
		else if (state.getStone(12,0) == state.getStone(0,12))
		{
			return "Blue player Wins!";
		}
		else
		{
			return null;
		}

		// if we get here, then we've found a winner, so return the 0/1
		// value that corresponds to that mark; then return a message

		//int gameWinner = resultChar == mark[0] ? 0 : 1;
		//return playerNames[gameWinner]+" is the winner.";
	}

	/**
	 * Notify the given player that its state has changed. This should involve sending
	 * a GameInfo object to the player. If the game is not a perfect-information game
	 * this method should remove any information from the game that the player is not
	 * allowed to know.
	 *
	 * @param p
	 *           the player to notify
	 */
	@Override
	protected void sendUpdatedStateTo(GamePlayer p) {
		// make a copy of the state, and send it to the player
		p.sendInfo(new HexState(state));

	}

	/**
	 * Tell whether the given player is allowed to make a move at the
	 * present point in the game.
	 *
	 * @param playerIdx
	 *        the player's player-number (ID)
	 * @return
	 *        true iff the player is allowed to move
	 */
	protected boolean canMove(int playerIdx) {
		return playerIdx == state.getWhoseMove();
	}

	/**
	 * Makes a move on behalf of a player.
	 *
	 * @param action
	 *           The move that the player has sent to the game
	 * @return
	 *           Tells whether the move was a legal one.
	 */
	@Override
	protected boolean makeMove(GameAction action) {

		// get the row and column position of the player's move
		HexMoveAction tm = (HexMoveAction) action;
		int row = tm.getRow();
		int col = tm.getCol();

		// get the 0/1 id of our player
		int playerId = getPlayerIdx(tm.getPlayer());



		if (state.getStone(row, col) == 102)
		{
			for (int i=0; i<12; i++)
			{
				state.setStone(i,0,100);
			}
			for (int i=1; i<13; i++)
			{
				state.setStone(i,12,101);
			}
		}
		// if that space is not blank, indicate an illegal move
		else if (state.getStone(row, col) != 0) {
			return false;
		}

		// get the 0/1 id of the player whose move it is
		int whoseMove = state.getWhoseMove();

		// place the player's stone on the selected square
		//state.setPiece(row, col, mark[playerId]);
		state.setStone(row, col, stone[playerId]);

		int x = tm.getRow();
		int y = tm.getCol();

		int[] xAdj = {0,1,-1,1,-1,0};
		int[] yAdj = {-1,-1,0,0,1,1};

		if (stone[playerId] >= 100 && stone[playerId] < 200)
		{
			for (int i = 0; i < 6; i++)
			{
				if (state.getStone(x+xAdj[i],y+yAdj[i]) >= 100 && state.getStone(x+xAdj[i],y+yAdj[i]) < 200 && state.getStone(x+xAdj[i],y+yAdj[i]) != state.getStone(x,y))
				{
					int temp = state.getStone(x+xAdj[i],y+yAdj[i]);
					for(int j = 0; j < 13; j++)
					{
						for(int k = 0; k < 13; k++)
						{
							if (state.getStone(j,k) == temp)
							{
								state.setStone(j,k,state.getStone(x,y));
							}
						}
					}
				}
			}
		}
		else if (stone[playerId] >= 200)
		{
			for (int i = 0; i < 6; i++)
			{
				if (state.getStone(x+xAdj[i],y+yAdj[i]) >= 200 && state.getStone(x+xAdj[i],y+yAdj[i]) != state.getStone(x,y))
				{
					int temp = state.getStone(x+xAdj[i],y+yAdj[i]);
					for(int j = 0; j < 13; j++)
					{
						for(int k = 0; k < 13; k++)
						{
							if (state.getStone(j,k) == temp)
							{
								state.setStone(j,k,state.getStone(x,y));
							}
						}
					}
				}
			}
		}

		stone[playerId]++;

		// make it the other player's turn
		state.setWhoseMove(1-whoseMove);

		// bump the move count
		moveCount++;

		// return true, indicating the it was a legal move
		return true;
	}
}