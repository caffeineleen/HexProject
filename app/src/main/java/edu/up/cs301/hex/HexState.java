package edu.up.cs301.hex;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * Contains the state of a Hex game.  Sent by the game when
 * a player wants to enquire about the state of the game. (E.g., to display
 * it, or to help figure out its next move.)
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */
public class HexState extends GameState
{
    private static final long serialVersionUID = 7552321013488624386L;

    ///////////////////////////////////////////////////
    // ************** instance variables ************
    ///////////////////////////////////////////////////

    // the 14x13 array of int that represents the x and y positions on the board
    public int[][] hexBoard;

    // an int that tells whose move it is
    private int playerToMove;

    /**
     * Constructor for objects of class HexState
     */
    public HexState()
    {
        // initialize the state to be a brand new game

        hexBoard = new int[14][13];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                hexBoard[i][j] = 0;
            }
        }

        //sets top and bottom row to 100 and 101
        for (int i=0; i<12; i++)
        {
            hexBoard[i][0] = 100;
        }
        for (int i=1; i<13; i++)
        {
            hexBoard[i][12] = 101;
        }
        //sets left and right columns to 200 and 201
        for (int i=1; i<13; i++)
        {
            hexBoard[0][i] = 200;
        }
        for (int i=0; i<12; i++)
        {
            hexBoard[12][i] = 201;
        }

        //subset value for red
        hexBoard[13][0] = 102;
        //subset value for blue
        hexBoard[13][1] = 202;
        //binary value storing whether or not the first pair of moves have been completed
        hexBoard[13][2] = 0;

        // make it player 0's move
        playerToMove = 0;
    }// constructor

    /**
     * Copy constructor for class HexState
     *
     * @param original
     * 		the HexState object that we want to clong
     */
    public HexState(HexState original)
    {
        // create a new 14x13 array, and copy the values from

        hexBoard = new int[14][13];
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                hexBoard[i][j] = original.hexBoard[i][j];
            }
        }

        // copy the player-to-move information
        playerToMove = original.playerToMove;
    }

    /**
     * @param row
     * @param col
     * @return x and y position of the stone
     */
    public int getStone(int row, int col) {
        return hexBoard[row][col];
    }

    /**
     *
     * @param row
     * @param col
     * @param value sets the x and y position of stone being placed
     */
    public void setStone(int row, int col, int value) {
        hexBoard[row][col] = value;
    }

    /**
     * Tells whose move it is.
     *
     * @return the index (0 or 1) of the player whose move it is.
     */
    public int getWhoseMove() {
        return playerToMove;
    }

    /**
     * set whose move it is
     * @param id
     * 		the player we want to set as to whose move it is
     */
    public void setWhoseMove(int id) {
        playerToMove = id;
    }
}