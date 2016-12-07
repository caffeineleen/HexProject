package edu.up.cs301.hex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * A GUI that allows a human to play hex. Moves are made by clicking
 * regions on a canvas
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 */
public class HexHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{

    // the current activity
    private Activity myActivity;

    // the surface view
    private HexSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    /**
     * constructor
     *
     * @param name
     * 		the player's name
     * @param layoutId
     *      the id of the layout to use
     */
    public HexHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof HexState))
            // if we do not have a HexState, ignore
            return;
        else {
            surfaceView.setState((HexState)info);
            surfaceView.invalidate();
            Log.i("human player", "receiving");
        }
    }

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activitiy
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (HexSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Log.i("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);

        TextView turn = (TextView) activity.findViewById(R.id.turn);
        turn.setText("Hey hey hey its FAAAAAAAAAAAT ALBERT!");

        Button quitgame = (Button) activity.findViewById(R.id.quitgame);
        quitgame.setOnClickListener(this);

        Button newgame = (Button) activity.findViewById(R.id.newgame);
        newgame.setOnClickListener(this);

        Button manual = (Button) activity.findViewById(R.id.instructions);
        manual.setOnClickListener(this);
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Hex: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch (which we'll detect on
     * the "up" movement" onto a hex square
     *
     * @param event
     * 		the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            //surfaceView.flash(Color.GREEN, 50);
            HexMoveAction action = new HexMoveAction(this, p.y, p.x);
            Log.i("onTouch", "Human player sending HexMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }

        // register that we have handled the event
        return true;

    }

    public void onClick(View view)
    {
        switch (view.getId()){
            case R.id.quitgame: myActivity.finish();
                break;
            case R.id.newgame: myActivity.recreate();
                break;
            case R.id.instructions:
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(myActivity);
                alertDialog.setTitle("Hex Game Instructions");
                alertDialog.setMessage(Html.fromHtml("<p>" + "<u>" + "The Game" + "</u>" + "</p>"+
                        "<p>" + "The game is played by two players." + "</p>" +
                        "<p>" + "There are two types of stones, red and blue stones. The \"red player\" will move the red stones, while the \"blue player\" moves the blue stones. The red player is the first one to move, then the blue player moves, and so forth." + "</p>" +
                        "<p>" + "Since the red player has an advantage in going first, the pie rule is implemented. The blue player, as their first move, has two options: 1) Placing their stone after the red player, or 2) Pressing on the red player's stone, swapping moves, so that the blue player becomes the first-moving player, and the red player makes their “first move”.\n" + "</p>" +
                        "<p>" + "Pressing the New Game button will bring you back to the configuration screen, and pressing the Quit Game button will cause the application to close entirely, and all current data within to be reset." + "</p>" +
                        "<p>" + "<u>" + "Winning" + "</u>" + "</p>" +
                        "<p>" + "To win the game, a player needs to have a link of hexagons that goes from one side of the board to the other, without any breaks. As the red player, you want to create a path from the top to the bottom of the board. The computer, the blue player, will try to create a path from the left to the right of the board." + "</p>" +
                        "<p>" + "The first player to complete a path of hexagons is the winner." + "</p>"));
                alertDialog.show();
                break;
        }
    }
}