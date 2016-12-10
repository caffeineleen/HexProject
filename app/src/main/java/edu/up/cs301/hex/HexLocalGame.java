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

		//if a member of the top invisible row matches a member of the bottom invisible row,
		//then the red player must have successfully connected a path between the top and bottom
		//sides and therefore Red wins
		if (state.getStone(0,0) == state.getStone(12,12))
		{
			return "Red player Wins!";
		}
		//if a member of the left invisible row matches a member of the right invisible row,
		//then the blue player must have successfully connected a path between the left and right
		//sides and therefore Red wins
		else if (state.getStone(12,0) == state.getStone(0,12))
		{
			return "Blue player Wins!";
		}
		//if none of the invisible row values match, nobody has won, so return null
		else
		{
			return null;
		}
		// if we get here, then we've found a winner, so return the 0/1
		// value that corresponds to that mark; then return a message

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
		//sets values for rows and columns via the getRow() and getCol() from the HexMoveAction instance we created
		int row = tm.getRow();
		int col = tm.getCol();

		// get the 0/1 id of the player whose move it is
		int playerId = state.getWhoseMove();

		//checks if pie rule is an option / has been satisfied
		if (state.getStone(row, col) == 102 && state.getStone(13,2) == 0)
		{
			for (int i=0; i<12; i++)
			{
				//resets values for invisible top row
				state.setStone(i,0,100);
			}
			for (int i=1; i<13; i++)
			{
				//resets values for invisible bottom row
				state.setStone(i,12,101);
			}
			//removes stone at focus location
			state.setStone(row, col, 0);
			//replaces stone for player 2 at the mirror of that location
			state.setStone(col, row, state.getStone(13, playerId));

			//switches row and col values because we will need to evaluate subset info on the new spots
			int temp = row;
			row = col;
			col = temp;
		}
		// if that space is not blank, indicate an illegal move
		else if (state.getStone(row, col) != 0) {
			return false;
		}
		else {
			// place the player's stone on the selected square
			state.setStone(row, col, state.getStone(13, playerId));
		}

		if (playerId == 1)
		{
			//disables pie rule
			state.setStone(13,2,1);
		}

		int x = row;
		int y = col;

		//creates arrays that store modifier variables for comparing focus tile with adjacent tiles
		int[] xAdj = {0,1,-1,1,-1,0};
		int[] yAdj = {-1,-1,0,0,1,1};

		//if stone is a red stone
		if (state.getStone(13,playerId) >= 100 && state.getStone(13,playerId) < 200)
		{
			//loops through to account for all possible 6 adjacent tiles
			for (int i = 0; i < 6; i++)
			{
				//if the adjacent stone is also red
				if (state.getStone(x+xAdj[i],y+yAdj[i]) >= 100 && state.getStone(x+xAdj[i],y+yAdj[i]) < 200 && state.getStone(x+xAdj[i],y+yAdj[i]) != state.getStone(x,y))
				{
					//set all red stones to same subset value as focus red stone
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

		//if stone is a blue stone
		else if (state.getStone(13,playerId) >= 200)
		{
			//loops through to account for all possible 6 adjacent tiles
			for (int i = 0; i < 6; i++)
			{
				//if adjacent stone is also blue
				if (state.getStone(x+xAdj[i],y+yAdj[i]) >= 200 && state.getStone(x+xAdj[i],y+yAdj[i]) != state.getStone(x,y))
				{
					//set all blue stones to same subset value as focus blue stone
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

		//add one to the next subset value for the appropriate player
		state.setStone(13,playerId,(state.getStone(13,playerId) + 1));

		// make it the other player's turn
		state.setWhoseMove(1-playerId);

		// return true, indicating a legal move was completed
		return true;
	}
}