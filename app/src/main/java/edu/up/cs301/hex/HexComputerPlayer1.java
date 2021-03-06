package edu.up.cs301.hex;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * This is a really dumb computer player that always just makes a random move
 * it's so stupid that it sometimes tries to make moves on non-blank spots.
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */
public class HexComputerPlayer1 extends GameComputerPlayer
{
    /*
     * Constructor for the HexComputerPlayer1 class
     */
    public HexComputerPlayer1(String name) {
        // invoke superclass constructor
        super(name); // invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        //creates boolean to determine
        boolean found = false;

        //
        while(found == false) {
            // if it was a "not your turn" message, just ignore it
            if (info instanceof NotYourTurnInfo) return;

            // pick x and y positions at random (0-12)
            int xVal = (int) (11 * Math.random()) + 1;
            int yVal = (int) (11 * Math.random()) + 1;

                // delay for a second to make opponent think we're thinking
                sleep(100);

                // Submit our move to the game object. We haven't even checked it it's
                // our turn, or that that position is unoccupied. If it was not our turn,
                // we'll get a message back that we'll ignore. If it was an illegal move,
                // we'll end up here again (and possibly again, and again). At some point,
                // we'll end up randomly pick a move that is legal.
                game.sendAction(new HexMoveAction(this, yVal, xVal));
                found = true;

        }
    }
}