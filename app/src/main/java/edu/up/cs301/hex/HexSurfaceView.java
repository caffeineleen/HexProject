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
    private final static float LINE_WIDTH_PERCENT = 3; // width of a tic-tac-toe line
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
     * 		the color to paint the hex lines, and the X's and O's
     */
    public int backgroundColor() {
        return Color.BLUE;
    }

    /**
     * callback method, called whenever it's time to redraw
     * frame
     *
     * @param g
     * 		the canvas to draw on
     */

    public int startX = 220;
    public int startY = 50;

    public void onDraw(Canvas g) {

        // update the variables that relate
        // to the dimensions of the animation surface
        updateDimensions(g);

        // paint the hex board

        //Paint p = new Paint();
        //p.setColor(Color.WHITE);
        //g.drawCircle(500,500, 100, p);
        //g.drawCircle(100,100, 50, p);




        int distance = startX;
        for(int i = 0; i < 11; i++) {
            for(int j = 0; j < 11; j++) {
                drawHex(100*j+10+distance, 80*i+startY,g);
            }
            distance += 50;
        }

        drawStone(4,4,1,g);
        drawStone(4,5,2,g);
        drawStone(4,8,1,g);
        drawStone(10,10,2,g);
        drawStone(11,6,1,g);
        drawStone(1,1,2,g);



        // if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }

        // for each square that has an X or O, draw it on the appropriate
        // place on the canvas
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                char result = state.getPiece(row, col); // get piece
                drawSymbol(g, result, col, row);
            }
        }
//        for (int row = 0; row < 13; row++) {
//            for (int col = 0; col < 13; col++) {
//                char result = state.getPiece(row, col); // get piece
//                drawSymbol(g, result, col, row);
//            }
//        }
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
     * 		an object that references the drawing surface
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

    /**
     * Draw a symbol (X or O) on the canvas in a particular location
     *
     * @param g
     *            the graphics object on which to draw
     * @param sym
     *            the symbol to draw (X or O)
     * @param col
     *            the column number of the square on which to draw (0, 1 or 2)
     * @param col
     *            the row number of the square on which to draw (0, 1 or 2)
     */
    protected void drawSymbol(Canvas g, char sym, int col, int row)
    {


    }

    public void drawStone(int x, int y, int color, Canvas g) {
        Paint stone = new Paint();
        if(color == 1){
            stone.setColor(Color.RED);
        }
        else
        {
            stone.setColor(Color.BLUE);
        }

        g.drawCircle(x*100-70+250+(y-1)*50,y*80-52+80,35,stone);


    }


    /**
     * maps a point from the canvas' pixel coordinates to "square" coordinates
     *
     * @param fingerX
     * 		the x pixel-coordinate
     * @param fingerY
     * 		the y pixel-coordinate
     * @return
     *		a Point whose components are in the range 0-2, indicating the
     *		column and row of the corresponding square on the tic-tac-toe
     * 		board, or null if the point does not correspond to a square
     */
    public Point mapPixelToSquare(int fingerX, int fingerY) {






        //variables for width and height of each hexagon, width is the one to be manually entered (height is dependent on width)
        double w = 100;
        double h = 0.866 * w;

        //variables for starting x and y values of the grid (coordinates of the top left corner)
        double beginX = (double)startX;
        double beginY = (double)startY;

        //starting point of row shift, which should always be startX, but I'm keeping it in a separate variable because we increment it later on
        double d = beginX;

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
                    if(fingerX>(w*i)+d && fingerX<(w*(i+1))+d && fingerY>(h*j) && fingerY<(h*(j+1)-(0.289*w)))
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
            if(state.hexBoard[i+1][j+1] == 0)
            {
                //placeStone(turn, i+1,j+1);
                return new Point(i+1, j+1);
            }
            else
            {
                return null;
            }
        }
        else
        {
            return null;
        }





        //return null;
    }


}