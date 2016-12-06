package edu.up.cs301.hex;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import android.graphics.Point;

/**
 * A computerized hex player that recognizes the opponents
 * stones and attempts to block by placing in any 8 locations
 * around the stone. If no such play exists, it will connect
 * its own stones.
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version December 2016
 *
 */
public class HexComputerPlayer2 extends GameComputerPlayer
{
    /**
     * constructor for a computer player
     *
     * @param name
     *        the player's name
     */
    public HexComputerPlayer2(String name) {
        // invoke superclass constructor
        super(name);
    }// constructor

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     *        the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {

        // if it's not a HexState message, ignore it; otherwise
        // cast it
        if (!(info instanceof HexState)) return;
        HexState myState = (HexState)info;
        int xVal = (int) (11 * Math.random()) + 1;
        int yVal = (int) (11 * Math.random()) + 1;

        /*this loop searches for a red player to place a stone around to block it
          if a red piece is found, computer player attempts to place its piece in one of the 8
          positions around the human player's piece. Randomly selects which position out of 8 is best.
         */
        for(int i = 1; i < 12; i++) {
            for(int j = 1; j < 12; j++) {
                double ran = Math.random();
                if (ran < .5 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j+1));
                    return;
                }
                else if (ran < .5 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j-1));
                    return;
                }
                else if (ran < .5 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j-1));
                    return;
                }
                else if (ran < .5 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j+1));
                    return;
                }
                else if (ran > .50 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j));
                    return;
                }
                else if (ran > .50 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j));
                    return;
                }
                else if (ran > .50 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j+1, i) == 0) {
                    game.sendAction(new HexMoveAction(this, i, j+1));
                    return;
                }
                else if (ran > .50 && myState.getStone(j,i) > 100 && myState.getStone(j,i) < 200 && myState.getStone(j-1, i) == 0) {
                    game.sendAction(new HexMoveAction(this, i, j-1));
                    return;
                }

            }
        }

        //if user choose to be blue player instead of red
        for(int i = 1; i < 12; i++) {
            for(int j = 1; j < 12; j++) {
                double ran = Math.random();
                if (ran < .50 && myState.getStone(j, i) > 200 && myState.getStone(j+1, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j+1));
                    return;
                }
                else if (ran < .50 && myState.getStone(j, i) > 200 && myState.getStone(j-1, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j-1));
                    return;
                }
                else if (ran < .50 && myState.getStone(j, i) > 200 && myState.getStone(j-1, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j-1));
                    return;
                }
                else if (ran > .50 && myState.getStone(j, i) > 200 && myState.getStone(j+1, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j+1));
                    return;
                }
                else if (ran > .50 && myState.getStone(j, i) > 200 && myState.getStone(j, i+1) == 0) {
                    game.sendAction(new HexMoveAction(this, i+1, j));
                    return;
                }
                else if (ran > .50 && myState.getStone(j, i) > 200 && myState.getStone(j, i-1) == 0) {
                    game.sendAction(new HexMoveAction(this, i-1, j));
                    return;
                }
                else if(myState.getStone(j,i) > 200){
                    game.sendAction(new HexMoveAction(this, xVal, yVal));
                    return;
                }
                else {
                    game.sendAction(new HexMoveAction(this, xVal, yVal));
                    return;
                }

            }
        }

    }// receiveInfo
}