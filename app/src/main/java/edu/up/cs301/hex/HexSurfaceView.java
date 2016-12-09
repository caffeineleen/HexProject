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

    // some constants, which are percentages with respect to the minimum
    // of the height and the width. All drawing will be done in the "middle
    // square".
    //
    // The divisions both horizontally and vertically within the
    // playing square are:
    // - first square starts at 5% and goes to 33%
    // - second square starts at 36% and goes to 64%
    // - third square starts at 67& and goes to 95%
    // There is therefore a 5% border around the edges; each square
    // is 28% high/wide, and the lines between squares are 3%
    private final static float BORDER_PERCENT = 5; // size of the border
    private final static float SQUARE_SIZE_PERCENT = 28; // size of each of our 9 squares
    private final static float LINE_WIDTH_PERCENT = 3; // width of a hex line
    private final static float SQUARE_DELTA_PERCENT = SQUARE_SIZE_PERCENT
            + LINE_WIDTH_PERCENT; // distance from left (or top) edge of square to the next one

    /*
    * Instance variables
    */

    // the game's state
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

    public int startX = 220;
    public int startY = 50;

    public void onDraw(Canvas g) {

        // update the variables that relate
        // to the dimensions of the animation surface
        updateDimensions(g);

        // paint the hex board

        if (state == null) {
            return;
        }

        Paint pp = new Paint();
        pp.setColor(Color.RED);
        g.drawRect(startX+60,startY,startX+1110-50,startY+40,pp);
        g.drawRect(startX+60+500,startY+880,startX+1110+500-50,startY+32+880,pp);
        pp.setColor(Color.BLUE);
        Path blue = new Path();
        blue.moveTo(startX+10,startY+85);
        blue.lineTo(startX+1110,startY+30);
        blue.lineTo(startX+1610,startY+830);
        blue.lineTo(startX+510,startY+885);
        g.drawPath(blue, pp);


        int distance = startX;
        for(int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {
                drawHex(100*j+10+distance, 80*i+startY,g);
            }
            distance += 50;
        }

        Paint r = new Paint();
        Paint b = new Paint();
        Paint back = new Paint();
        back.setColor(Color.WHITE);
        r.setColor(Color.RED);
        b.setColor(Color.BLUE);
        r.setTextSize(80);
        b.setTextSize(80);

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

    public void setP(int num) {
        this.num = num;
    }
    public int getP(){
        return num;
    }
    public void drawHex(int x, int y, Canvas g)
    {
        Paint p = new Paint();
        p.setColor(Color.BLACK);

        Paint p2 = new Paint();
        p2.setColor(Color.WHITE);

        //draws the background of hex shape
        Path path = new Path();
        path.moveTo(x+0,y+29);
        path.lineTo(x+50, y+0);
        path.lineTo(x+100,y+29);
        path.lineTo(x+100,y+87);
        path.lineTo(x+50,y+112);
        path.lineTo(x+0,y+87);
        g.drawPath(path, p);

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
    private void updateDimensions(Canvas g) {

        // initially, set the height and width to be that of the
        // drawing surface
        int width = g.getWidth();
        int height = g.getHeight();

        // Set the "full square" size to be the minimum of the height and
        // the width. Depending on which is greater, set either the
        // horizontal or vertical base to be partway across the screen,
        // so that the "playing square" is in the middle of the screen on
        // its long dimension
        if (width > height) {
            fullSquare = height;
            vBase = 0;
            hBase = (width - height) / (float) 2.0;
        } else {
            fullSquare = width;
            hBase = 0;
            vBase = (height - width) / (float) 2.0;
        }
    }

    public void drawStone(int x, int y, int color, Canvas g) {
        Paint stone = new Paint();
        if(color == 1){
            stone.setColor(Color.RED);
            //g.drawRect(20, 20, 200, 200, stone);
        }
        else if(color == 2)
        {
            stone.setColor(Color.BLUE);
        }
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
            for (j=0; j<11; j++)
            {
                for (i=0; i<11; i++)
                {
                    if(fingerX>(w*i)+d && fingerX<(w*(i+1))+d && fingerY>(h*j)+e && fingerY<(h*(j+1)-(0.289*w))+e)
                    {
                        found = true;
                        break search;
                    }
                }
                d += (w/2);
            }
        }
        if(found == true)
        {
            return new Point(i+1, j+1);
        }
        else
        {
            return null;
        }
    }
}