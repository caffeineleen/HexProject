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
	
	// the 13x13 array of char that represents the X's and O's on the board
    private char[][] board;
    public int[][] hexBoard;

    
    // an int that tells whose move it is
    private int playerToMove;


    public int subRed;
    public int subBlue;
    public int win;


    /**
     * Constructor for objects of class HexState
     */
    public HexState()
    {
        // initialize the state to be a brand new game
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
        	for (int j = 0; j < 3; j++) {
        		board[i][j] = ' ';
        	}
        }
        hexBoard = new int[14][13];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 13; j++) {
                hexBoard[i][j] = 0;
            }
        }

        for (int i=0; i<12; i++)
        {
            hexBoard[i][0] = 100;
        }
        for (int i=1; i<13; i++)
        {
            hexBoard[i][12] = 101;
        }
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
    	// create a new 13x13 array, and copy the values from
    	// the original
    	board = new char[3][3];
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 3; j++) {
    			board[i][j] = original.board[i][j];
    		}
    	}
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
     * Find out which piece is on a square
     * 
     * @param row
	 *		the row being queried
     * @param col
     * 		the column being queried
     * @return
     * 		the piece at the given square; ' ' if no piece there;
     * 		'?' if it is an illegal square
     */
    public char getPiece(int row, int col) {
        // if we're out of bounds or anything, return '?';
        if (board == null || row < 0 || col < 0) return '?';
        if (row >= board.length || col >= board[row].length) return '?';

        // return the character that is in the proper position
        return board[row][col];
    }

    public int getStone(int row, int col) {
        //if (hexBoard == null || row < 0 || col < 0) return '?';
        //if (row >= hexBoard.length || col >= hexBoard[row].length) return '?';
        return hexBoard[row][col];
    }

    /**
     * Sets a piece on a square
     * 
     * @param row
     * 		the row being queried
     * @param
     * 		col the column being queried
     * @param
     * 		piece the piece to place
     */
    public void setPiece(int row, int col, char piece) {
        // if we're out of bounds or anything, return;
        if (board == null || row < 0 || col < 0) return;
        if (row >= board.length || col >= board[row].length) return;

        // return the character that is in the proper position
        board[row][col] = piece;
    }

    public void setStone(int row, int col, int value) {
        //if (board == null || row < 0 || col < 0) return;
        //if (row >= board.length || col >= board[row].length) return;
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