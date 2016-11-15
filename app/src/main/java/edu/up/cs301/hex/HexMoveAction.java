package edu.up.cs301.hex;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-move object that a hex player sends to the game to make
 * a move.
 * 
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */
public class HexMoveAction extends GameAction
{
	private static final long serialVersionUID = -2242980258970485343L;
	
	// instance variables: the selected row and column
    private int row;
    private int col;

    /**
     * Constructor for HexMoveAction
     *
     * @param player the player making the move
     * @param row the row of the square selected (0-2)
     * @param col the column of the square selected
     */
    public HexMoveAction(GamePlayer player, int row, int col)
    {
        // invoke superclass constructor to set the player
        super(player);

        // set the row and column as passed to us
        this.row = Math.max(0, Math.min(2, row));
        this.col = Math.max(0, Math.min(2, col));
    }

    /**
     * get the object's row
     *
     * @return the row selected
     */
    public int getRow() { return row; }

    /**
     * get the object's column
     *
     * @return the column selected
     */
    public int getCol() { return col; }

}
