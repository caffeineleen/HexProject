package edu.up.cs301.hex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;

import edu.up.cs301.game.util.FlashSurfaceView;

/**
 * A SurfaceView which allows which an animation to be drawn on it by a
 * Animator.
 *
 * @author Justin Jacobs
 * @author Navreen Kaur
 * @author Nathan Relyea
 * @author Kathleen Elisabeth Smith
 * @version November 2016
 *
 *
 */
public class HexSurfaceView extends FlashSurfaceView {

    // instance of the game's state
    protected HexState state;

    // the offset from the left and top to the beginning of our "middle square"; one
    // of these will always be zero
    protected float hBase;
    protected float vBase;

    // the size of one edge of our "middle square", or -1 if we have not determined
    // size
    protected float fullSquare;

    protected int num;
    /**
     * Constructor for the HexSurfaceView class.
     *
     * @param context - a reference to the activity this animation is run under
     */
    public HexSurfaceView(Context context) {
        super(context);
        init();
    }// ctor

    /**
     * An alternate constructor for use when a subclass is directly specified
     * in the layout.
     *
     * @param context - a reference to the activity this animation is run under
     * @param attrs   - set of attributes passed from system
     */
    public HexSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }// ctor

    /**
     * Helper-method for the constructors
     */
    private void init() {
        setBackgroundColor(backgroundColor());
    }// init

    public void setState(HexState state) {
        this.state = state;
    }

    /**
     * @return
     *        the color to paint the hex lines, and the X's and O's
     */
    public int backgroundColor() {
        return Color.rgb(193,188,186);
    }

    /**
     * callback method, called whenever it's time to redraw
     * frame
     *
     * @param g
     *        the canvas to draw on
     */


    //values that control the starting x and y value at which the board is drawn in respect to the top left corner of the screen
    public int startX = 220;
    public int startY = 50;

    public void onDraw(Canvas g) {

        // paint the hex board

        if (state == null) {
            return;
        }

        //paints base for red borders at top and bottom of board
        Paint pp = new Paint();
        pp.setColor(Color.RED);
        g.drawRect(startX+60,startY,startX+1110-50,startY+40,pp);
        g.drawRect(startX+60+500,startY+880,startX+1110+500-50,startY+32+880,pp);
        //paints base for blue borders at left and right of board
        pp.setColor(Color.BLUE);
        Path blue = new Path();
        blue.moveTo(startX+10,startY+85);
        blue.lineTo(startX+1110,startY+30);
        blue.lineTo(startX+1610,startY+830);
        blue.lineTo(startX+510,startY+885);
        g.drawPath(blue, pp);

        //controls the starting X value of each row of hex tiles
        int distance = startX;
        //loops to draw each individual hexagon tile to compose the board
        for(int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {
                drawHex(100*j+10+distance, 80*i+startY,g);
            }
        //increments horizontal distance between each row in order to properly overlap the tiles
            distance += 50;
        }

        //sets up values and objects for displaying whose turn it is in the top right corner of the screen
        Paint r = new Paint();
        Paint b = new Paint();
        Paint back = new Paint();
        back.setColor(Color.WHITE);
        r.setColor(Color.RED);
        b.setColor(Color.BLUE);
        r.setTextSize(80);
        b.setTextSize(80);

        //loops through the board to see if stones should be drawn in each tile, if they should be, they are drawn using drawStone()
        for(int i = 1; i < 12; i++) {
            for(int j = 1; j < 12; j++){
                int value = state.getStone(i,j);
                if(value >= 100 && value < 200){
                    drawStone(i,j,1,g);
                }
                if(value >= 200) {
                    drawStone(i,j,2,g);
                }
            }
        }
        //uses previously set up values to write whose turn it is on screen after checking state.getWhoseMove()
        if(state.getWhoseMove() == 0) {
            g.drawRect(g.getWidth()-560, 100, g.getWidth()-20, 250, r);
            g.drawRect(g.getWidth()-550, 110, g.getWidth()-30, 240, back);
            g.drawText("Player 1 Turn", g.getWidth()-520, 200, r);
        }
        else if (state.getWhoseMove() == 1) {
            g.drawRect(g.getWidth()-560, 100, g.getWidth()-20, 250, b);
            g.drawRect(g.getWidth()-550, 110, g.getWidth()-30, 240, back);
            g.drawText("Player 2 Turn", g.getWidth()-520, 200, b);
        }

        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }
    }

    //method to draw an individual hex tile (called in onDraw loop to construct the board)
    public void drawHex(int x, int y, Canvas g)
    {
        Paint p = new Paint();
        p.setColor(Color.BLACK);

        Paint p2 = new Paint();
        p2.setColor(Color.WHITE);

        //draws the background of hex tile
        Path path = new Path();
        path.moveTo(x+0,y+29);
        path.lineTo(x+50, y+0);
        path.lineTo(x+100,y+29);
        path.lineTo(x+100,y+87);
        path.lineTo(x+50,y+112);
        path.lineTo(x+0,y+87);
        g.drawPath(path, p);

        //draws the foreground of hex tile
        Path path2 = new Path();
        path2.moveTo(x+5,y+34);
        path2.lineTo(x+50,y+8);
        path2.lineTo(x+95,y+34);
        path2.lineTo(x+95,y+82);
        path2.lineTo(x+50,y+104);
        path2.lineTo(x+5,y+82);
        g.drawPath(path2,p2);
    }

    /**
     * update the instance variables that relate to the drawing surface
     *
     * @param g
     *        an object that references the drawing surface
     */

    public void drawStone(int x, int y, int color, Canvas g) {
        Paint stone = new Paint();
        //if stone is for player 1, set stone color to red
        if(color == 1){
            stone.setColor(Color.RED);
        }
        //if stone is for player 2, set stone color to blue
        else if(color == 2)
        {
            stone.setColor(Color.BLUE);
        }
        //draw stone of that color after converting x and y board values to screen pixel values
        g.drawCircle(x*100-70+250+(y-1)*50,y*80-52+80,35,stone);
    }

    /**
     * maps a point from the canvas' pixel coordinates to "square" coordinates
     *
     * @param fingerX
     *        the x pixel-coordinate
     * @param fingerY
     *        the y pixel-coordinate
     * @return
     *    a Point whose components are in the range 0-2, indicating the
     *    column and row of the corresponding square on the tic-tac-toe
     *        board, or null if the point does not correspond to a square
     */
    public Point mapPixelToSquare(int fingerX, int fingerY) {

        //variables for width and height of each hexagon, width is the one to be manually entered (height is dependent on width)
        double w = 100;
        //double h = 0.866 * w;
        double h = 0.8 * w;

        //variables for starting x and y values of the grid (coordinates of the top left corner)
        double beginX = (double)startX;
        double beginY = (double)startY;

        //starting point of row shift, which should always be startX, but I'm keeping it in a separate variable because we increment it later on
        double d = beginX;
        double e = beginY + 30;

        //variable to hold whether or not a touch finds a value on the board
        boolean found = false;

        //iteration variables for nested loops, must be declared outside because they are used in the conditional statement following the loops
        int i = 0;
        int j = 0;

        search:
        {
        //loops through entire board
            for (j=0; j<11; j++)
            {
                for (i=0; i<11; i++)
                {
                    //checks for the finger press to be within the boundaries of a box denoted by the largest possible box
                    //able to be drawn within an equilateral hexagon, and with respect to the i and j values correlating to the actual board
                    if(fingerX>(w*i)+d && fingerX<(w*(i+1))+d && fingerY>(h*j)+e && fingerY<(h*(j+1)-(0.289*w))+e)
                    {
                        //if square boundaries are satisfied, then the user must have pressed within a tile, so a tile was found
                        found = true;
                        //if square boundaries are satisfied, the loop is broken so that the i and j values that emerge relate to the correct tile coordinates
                        break search;
                    }
                }
                //increments the displacement of each row as columns increase
                d += (w/2);
            }
        }
        if(found == true)
        {
            //a pixel was found, so a Point variable is returned containing the appropriate i and j values, which
            //are each incremented forwards once as to account for the fact that in our hexBoard array, the first tile's
            //x,y values are 1,1 (not 0,0 like this test starts with)
            return new Point(i+1, j+1);
        }
        else
        {
            //a pixel was not found, so the method returns null
            return null;
        }
    }
}