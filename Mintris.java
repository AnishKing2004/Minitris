import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

/**
 * Class Mintris - this program creates a simpler version of the classic
 * Tetris game.  Mintris is turn-based and has only 2x2 pieces.
 * 
 * @author Profs Andrew Nuxoll and Karen Ward
 * @author ANISH KARUMURI
 * 
 */
public class Mintris extends JPanel implements KeyListener {

    /*======================================================================
     * Constants
     *----------------------------------------------------------------------
     */

    public static final int NUM_ROWS = 20;    //number of rows in the playing field
    public static final int NUM_COLS = 10;    //number of columns in the playing field
    public static final int BLOCK_SIZE = 20;  //a block is this many pixels on a side

    //These constants define the possible contents of each cell in the playing field
    public static final int NUM_COLORS    = 3;
    public static final int INVALID_COLOR = 0;
    public static final int RED_BLOCK     = 1;
    public static final int GREEN_BLOCK   = 2;
    public static final int BLUE_BLOCK    = 3;
    public static final int EMPTY         = NUM_COLORS + 1;


    //movement of the blocks on the playing field can be in one of these
    //directions
    public static final int LEFT         = -1;
    public static final int DOWN         =  0;
    public static final int RIGHT        =  1;

    /*======================================================================
     * Instance Variables
     *----------------------------------------------------------------------
     */
    // a 2D array to store the playing field 
    private int[][] field = new int[NUM_ROWS][NUM_COLS];

    // current score
    private int score = 0;     
    //counter
    private int counter = 0;

    /*======================================================================
     * Methods
     *----------------------------------------------------------------------
     */

    /**
     * clearField
     *
     * creates a new playing field sets all cells in the field to EMPTY.
     *
     */
    public void clearField() {
        field = new int[NUM_ROWS][NUM_COLS]; 
        for (int i = 0; i < field.length; i++) { //double loops to go through the array and make everything empty
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = EMPTY;
            }
        }
        
       

    }//clearField

    /**
     * rotate
     *
     * This method rotates a 2x2 block by 90 degrees. 
     * It does not check that the rotation is valid.  
     *
     * @param row the row of the upper-left corner of the 2x2 block
     * @param col the column of the upper-left block
     *
     */
    public void rotate(int row, int col) {

        int topLeft = field[row][col]; //set the value of the locations as variables for brain to understand easier 
        int topRight = field[row][col+1];
        int bottomLeft = field[row + 1][col];
        int bottomRight = field[row + 1][col + 1];


        field[row][col] = bottomLeft; //assigning the values to other locations to make the block rotate 90 degress
        field[row+1][col] = bottomRight;
        field[row][col+1] = topLeft;
        field[row+1][col+1] = topRight;

        

    }
    //rotate

    /**
     * move
     *
     * This method moves a 2x2 piece by one space (left, right, or down)
     * This method does *not* check to make sure that the movement is valid.
     *
     * This method is only called after the validMove() method (below) has
     * affirmed that it is is a valid move.
     * 
     *
     * The block is moved as would be expected for a Tetris game.
     *
     * @param row the row of the upper-left corner of the block
     * @param col the column of the upper-left corner of the block
     * @param direction the direction to move (LEFT, DOWN, or RIGHT)
     *        (see the constants defined at the top of this class)
     *
     */
    public void move(int row, int col, int direction) {
        int topLeft = field[row][col]; // made variables again to make it easier to write and understand 
        int topRight = field[row][col+1];
        int bottomLeft = field[row + 1][col];
        int bottomRight = field[row + 1][col + 1];

        
        if(direction == RIGHT){ //shifts the value of the blocks to the right. 
            //this is achieved by adding 1 and 2 as the number increases it moves to the right 
            field[row][col+2] = topRight;
            field[row][col+1] = topLeft;
            field[row][col] = EMPTY;
            field[row +1][col +2] = bottomRight;
            field[row + 1][col + 1] = bottomLeft;
            field[row + 1][col] =  EMPTY;

       

        }
        
        if (direction == LEFT) { //shifts the values of the block to the left 
            // this is achieved by substracting 1 and 2 as the number decreases it moves to the left. 
            field[row][col-1] = topLeft;
            field[row][col] = topRight;
            field[row][col + 1] = EMPTY;
            field[row +1][col - 1] = bottomLeft;
            field[row+1][col] = bottomRight;
            field[row+1][col+1] = EMPTY;
            
            
            
            
        }
        
        if (direction == DOWN){ // shifts the values of the blocks down
            // this is achived by adding 1 and 2 as the number increases it moved further down 
            field[row][col] = EMPTY;
            field[row+1][col] = topLeft;
            field[row][col+1] = EMPTY;
            field[row+ 1][col + 1] = topRight;
            field[row+2][col] = bottomLeft;
            field[row+2][col+1] = bottomRight;
          
            
            
        }
    }

    /**
     * validMove
     *
     * This method calculates whether a block may be moved one space in a particular
     * direction: left, right or down.  A move is invalid if there is another block in
     * the target location, or if the movement would take the block off of the
     * playing field
     *
     * @param row the row of the upper-left corner of the block
     * @param col the column of the upper-left corner of the block 
     * @param direction direction to move (LEFT, RIGHT, or DOWN)
     *              (see the constants defined at the top of this class)
     *
     * @return    true if the movement is legal and false otherwise
     */
    private boolean validMove(int row, int col, int direction) {
        int topLeft = field[row][col]; // made variables again to make it easier to write and understand 
        int topRight = field[row][col+1];
        int bottomLeft = field[row + 1][col];
        int bottomRight = field[row + 1][col + 1];
        
        if (direction == RIGHT){ //checks if the values to the right are valid 

            if (col >= NUM_COLS - 2){ // this is to make sure the block doesn't go of the border to the right 
                return false;
            }
            if ((field[row][col+2] != EMPTY) || (field[row+1][col+2] != EMPTY)){ // this is check if there is a block to the right
                return false;
            }
            
        }

        if (direction == DOWN){ //checks if it is valid to move down. 
                if (row == NUM_ROWS-2){ //makes sure it doesnt cross the bottom border
                return false;
            }
            if(field[row+1][col] == EMPTY && field[row+2][col] != EMPTY){ //check if there is an empty space at bottomLeft
                field[row+1][col] = field[row][col];
                field[row+1][col+1] = field[row + 1][col];
                field[row+2][col+1] = field[row + 1][col];
                field[row][col] = EMPTY;
                field[row][col+1] = EMPTY;
                
            }
            if(field[row+1][col+1] == EMPTY && field[row+2][col+1] != EMPTY){ // checks if there is an empty space at bottomRight
                field[row+1][col+1] = field[row][col];
                field[row+1][col] = field[row + 1][col + 1];
                field[row+2][col] = field[row + 1][col + 1];
                field[row][col] = EMPTY;
                field[row][col+1] = EMPTY;
                
            }
            if ((field[row+2][col] != EMPTY) || (field[row+2][col+1] != EMPTY)){ //checks if there is a block in the way 
                return false;
            }
            
            
            
        }

        if (direction == LEFT){ //checks the spaces to the left of the block to see if it is valid
            if (col == 0){ //make sure its doesnt cross the border 
                return false;
            }
            if ((field[row][col-1] != EMPTY) || (field[row+1][col-1] != EMPTY)){
                return false;
            }
            

        }
        return true;

        }

    /**
     * removeRows
     *
     * This method searches the field for any complete rows of blocks and
     * removes them.  Rows above the removed show shift down one row.
     * The score is incremented for each complete row that is removed.
     */
    private void moveDown (int row){ //this method moves down the row above 
       for(int i = row; i > 0;i--){
           for(int j = 0; j < field[i].length; j++){ //moves the above row down 
               field[i][j] = field[i-1][j];
           }
           
       }
        
    }
    private void removeRows() { //runs a double loop to check for a full row and increases the score
          
       for (int j = 0; j < field.length; j++){
           int filledBlock = 0;
           for (int i = 0; i < field[j].length; i++){ //checking for full column 
               if (field[j][i] != EMPTY){
                   filledBlock += 1;
               }
           }
           if(filledBlock == NUM_COLS){
               moveDown(j);
               score++; // score increase
           }
       }
        
        
    }//removeRows
    
    

    /*======================================================================
     *                    ATTENTION STUDENTS!
     *
     * The code below this point should not be edited.  However, you are
     * encouraged to examine the code to learn a little about how the rest
     * of the game was implemented.
     * ----------------------------------------------------------------------
     */
    /*======================================================================
     * More Instance Variables and Constants
     *
     * ==> You should not modify the values of these variables <==
     *----------------------------------------------------------------------
     */
    public static final int WINDOW_WIDTH = 230;
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_MARGIN = 10;

    //The location of the current piece.
    //Students: DO NOT USED THESE VARIABLES IN YOUR CODE
    private int currRow = 0;
    private int currCol = 0;

    //random number generator
    private Random randGen = new Random();

    // colors array for drawing the pieces
    // Constants for valid colors, INVALID_COLOR, and EMPTY are defined above, in the
    // area that students are expected to study
    // Additional block colors, if desired, should be inserted before EMPTY (the last 
    // entry below), and the defined constant NUM_COLORS (defined above) adjusted accordingly

    // possible block colors
    private Color[] blockColors = { 
            Color.MAGENTA,          // invalid (so must be cleared explicitly)
            Color.RED,              // red
            new Color(0, 110, 0),   // green
            new Color(0,0,170),     // blue
            Color.BLACK };          // EMPTY (should never be displayed)

    //A flag used to keep multiple keypresses from being handled simultaneously
    private boolean synch = false;

    /**
     * createRandomPiece
     *
     * creates a new piece at the top of the Mintris board
     *
     */
    public void createRandomPiece() {
        //Select a random starting column and color
        int col = randGen.nextInt(NUM_COLS - 1);
        int type = randGen.nextInt(NUM_COLORS) + 1;

        //Fill the indicated 2x2 area
        for(int x = 0; x < 2; ++x) {
            for(int y = 0; y < 2; ++y) 
            {
                this.field[x][y + col] = type;
            }
        }

        //randomly select which block in the 2x2 area of the piece will be empty
        int which = randGen.nextInt(4);
        int x = which / 2;
        int y = which % 2;
        field[x][y+col] = EMPTY;

        //record the location of this new piece
        this.currRow = 0;
        this.currCol = col;

    }//createRandomPiece

    /**
     * drawBlock
     *
     * a helper method for {@link paint}.  This method draws a Mintris block of a
     * given color at a given x,y coordinate.
     *
     * @param  g          the Graphics object for this application
     * @param  x, y       the coordinates of the block
     * @param  blockColor the main color of the block
     */
    public void drawBlock(Graphics g, int x, int y, Color blockColor) {
        //draw the main block
        g.setColor(blockColor);
        g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

        //draw some shading on the edges for a 3D effect
        g.setColor(Color.white); //blockColor.brighter());
        g.drawLine(x, y+1, x + BLOCK_SIZE, y+1);
        g.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1, y + BLOCK_SIZE);
        g.setColor(blockColor.darker());
        g.drawLine(x+1, y, x+1, y + BLOCK_SIZE);
        g.drawLine(x+1, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1, y + BLOCK_SIZE - 1);

        //draw a black border around it
        g.setColor(Color.BLACK);
        g.drawRect(x, y, BLOCK_SIZE, BLOCK_SIZE);

    }//drawBlock

    /**
     * paint
     *
     * This methods draws the current state of the game on a given canvas.  The
     * field occupies the bottom left corner.  A title is at the top and the
     * current score is shown at right.
     * 
     * @param  g   the Graphics object for this application
     */
    public void paint(Graphics g) {
        //start with the background color
        Color bgColor = new Color(0x330088);  //medium-dark purple
        g.setColor(bgColor);
        g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);

        //Calculate the position of the playing field
        int margin = 5;
        int topSide = WINDOW_HEIGHT - ( NUM_ROWS * BLOCK_SIZE + margin + WINDOW_MARGIN);
        int bottomSide = topSide + NUM_ROWS * BLOCK_SIZE;
        int leftSide = WINDOW_MARGIN + margin;
        int rightSide = leftSide + NUM_COLS * BLOCK_SIZE;

        //Draw the playing field
        Color fieldColor = new Color(0x9966FF);  //lavender
        g.setColor(fieldColor);
        g.fillRect(leftSide, topSide, NUM_COLS * BLOCK_SIZE, NUM_ROWS * BLOCK_SIZE);

        //Draw a thick border around the playing field 
        g.setColor(Color.WHITE);
        for(int i = 1; i <= 5; ++i) {
            g.drawRect(leftSide - i, topSide - i,
                NUM_COLS * BLOCK_SIZE + margin , NUM_ROWS * BLOCK_SIZE + margin);
        }

        //Draw the blocks
        for(int row = 0; row < field.length; ++row) {
            for (int col = 0; col < field[row].length; ++col) {
                //calculate block position
                int xPos = leftSide + col * BLOCK_SIZE;
                int yPos = topSide + row * BLOCK_SIZE;

                //Verify the color index is valid
                // (NUM_COLORS + 1 is EMPTY)
                if ( (field[row][col] < 0) || (field[row][col] > EMPTY)) {
                    field[row][col] = INVALID_COLOR;
                }

                //draw the block
                if (field[row][col] != EMPTY) {
                    drawBlock(g, xPos, yPos, blockColors[field[row][col]]);
                }
            }//for
        }//for

        //draw the title
        g.setColor(Color.WHITE);
        Font bigFont = new Font("SansSerif", Font.BOLD, 32);
        g.setFont(bigFont);
        g.drawString("Mintris",45,50);

        //draw the score
        g.setColor(Color.WHITE);
        Font medFont = new Font("SansSerif", Font.PLAIN, 18);
        g.setFont(medFont);
        int leftMargin = rightSide + 15;
        g.drawString("Score:" + this.score, 70, 75);

    }//paint

    /**
     * keyPressed
     *
     * when the user presses a key, this method examines it to see
     * if the key is one that the program responds to and then calls the
     * appropriate method.
     */
    public void keyPressed(KeyEvent e) {
        //Don't handle the keypress until the prev one is handled
        synchronized(this) {
            while(synch);
            synch = true;
        }

        //Call the appropriate student method(s) based upon the key pressed
        int key = e.getKeyCode();
        switch(key) {
                //Move the piece left
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_KP_LEFT:
            case 'a':
            case 'A':
                if (validMove(currRow, currCol, LEFT)) {
                    move(currRow, currCol, LEFT);
                    --currCol;
                }
                break;

                //Move the piece right
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_KP_RIGHT:
            case 'd':
            case 'D':
                if (validMove(currRow, currCol, RIGHT)) {
                    move(currRow, currCol, RIGHT);
                    ++currCol;
                }
                break;

                //Drop the current piece down one row
            case KeyEvent.VK_DOWN:
            case 's':
            case 'S':
                if (validMove(currRow, currCol, DOWN)) {
                    move(currRow, currCol, DOWN);
                    ++currRow;
                }
                break;

                //Drop the current piece all the way down
            case ' ':
                while (validMove(currRow, currCol, DOWN)) {
                    move(currRow, currCol, DOWN);
                    ++currRow;
                }
                break;

            case KeyEvent.VK_UP:
            case 'w':
            case 'W':
            case 'r':
            case 'R':
                rotate(currRow, currCol);
                break;

                //Create a new game
            case 'n':
            case 'N':
                clearField();
                createRandomPiece();
                score = 0;
                break;

                //create a quick layout to aid in testing
            case 't':
            case 'T':
                clearField();
                for(int i = 3; i < field.length; ++i) {
                    field[i][NUM_COLS/2] = BLUE_BLOCK;
                }
                for(int x = field.length - 2; x < field.length; ++x) {
                    for (int y = 0; y < field[x].length; ++y) {
                        field[x][y] = RED_BLOCK;
                    }
                }
                int lastRow = field.length - 1;
                field[lastRow][0]   = EMPTY;
                field[lastRow-1][1] = EMPTY;
                field[lastRow-1][0] = EMPTY;
                createRandomPiece();
                break;

                //Quit the game
            case 'q':
            case 'Q':
                System.exit(0);

        }//switch

        //Regardless of keypress check for a piece that has bottomed out
        if (! validMove(currRow, currCol, DOWN)) {
            removeRows();
            createRandomPiece();
        }

        //Release the synch for the next keypress
        synch = false;

        //redraw the screen so user can see changes
        repaint();
    }//keyPressed

    //These two method must be implemented but we don't care about these events.
    //We only care about key presses (see method above)
    public void keyReleased(KeyEvent e){}

    public void keyTyped(KeyEvent e){}

    /**
     * This method creates a window frame and displays the Mintris
     * game inside of it.  
     */
    public static void main(String[] args) {
        //Create a properly sized window for this program
        final JFrame myFrame = new JFrame();
        myFrame.setSize(WINDOW_WIDTH+10, WINDOW_HEIGHT+30);

        //Tell this window to close when someone presses the close button
        myFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                };
            });

        //Display a new Mintris object in the window
        Mintris mintrisGame = new Mintris();
        mintrisGame.clearField();
        mintrisGame.createRandomPiece();
        myFrame.addKeyListener(mintrisGame);
        myFrame.getContentPane().add(mintrisGame);

        //show the user
        myFrame.setVisible(true);

    }//main

}//class Mintris