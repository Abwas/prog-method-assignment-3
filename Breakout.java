/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Total number of bricks */
    private static final int TOTAL_BRICKS = NBRICK_ROWS * NBRICKS_PER_ROW;
    
/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
/** instance variable*/
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private GLabel result;
	private double vx;
    private double vy;
    private RandomGenerator rgen = RandomGenerator.getInstance();
    private int turn = 0;
    private int brickCounter = 0;
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setUpGame();
		addMouseListeners();
		playGame();
		
		
		
	}
		
		//setup the game environment
		private void setUpGame() {
			createBricks();
			createPaddle();
			createBall();
		}
		
		//create the bricks for the game
		private void createBricks() {
			
			double x = 0.5;
			
			double y = BRICK_Y_OFFSET;
			
			for (int row = 0; row < NBRICK_ROWS; row++) {  //this creates the brick rows
				
				for (int column = 0; column < NBRICKS_PER_ROW; column++) {   //this creates the brick columns
				
					brick = new GRect (BRICK_WIDTH, BRICK_HEIGHT);
					
					brick.setFilled(true);
					
					add(brick, x, y);
					
					//this sets the various colors for the rows
					if (row < 2) brick.setColor(Color.RED);
						
					if (row == 2 || row == 3) brick.setColor(Color.ORANGE);
						
					if (row == 4 || row == 5) brick.setColor(Color.YELLOW);
						
					if (row == 6 || row == 7) brick.setColor(Color.GREEN);
						
					if (row == 8 || row == 9) brick.setColor(Color.CYAN);
					
					x += BRICK_WIDTH + BRICK_SEP;
				}
				
					y += BRICK_HEIGHT + BRICK_SEP;
					
					x = 0.5;
			}
		}
		
		//creates the paddle for the game
		private void createPaddle() {
			
			double x = getWidth() / 2 - PADDLE_WIDTH / 2;
			
			double y = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
			
			paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
			
			paddle.setFilled(true);		
			
			add(paddle);

	}
		
		//adds mouse move event to track the paddle
		public void mouseMoved(MouseEvent e) {
			
			if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
				
	            paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	            
			}
	        
	    }
		
		//creates ball for the game
		private void createBall() {
			
			double x = getWidth() / 2 - BALL_RADIUS;
			
			double y = getHeight() / 2 - BALL_RADIUS;
			
			ball = new GOval (x, y, BALL_RADIUS, BALL_RADIUS);
			
			ball.setFilled(true);
			
			add(ball);
		}
	
		private void getBallVelocity(){
	        vx = rgen.nextDouble(1.0, 15.0);
	        if (rgen.nextBoolean(0.5)) vx = - vx;
	        vy = +5.0;
	    }
	    // makes the ball move
	    private void moveBall(){
	         
	        ball.move(vx, vy);
	        if (ball.getY() <= 0) vy*=-1; // reverses the y direction when the ball top hits the wall
	        if (ball.getX() + BALL_RADIUS*2 >= getWidth() || ball.getX() <= 0) vx*=-1; // reverses the x direction when the ball hits the wall
	         
	    }
	    // checks if there was a collision and, if brick, removes the colliding object
	    private void checkForCollision(){
	        GObject collider = getCollidingObject();
	        if (collider == paddle){
	            vy*=-1;
	        }
	        else if (collider != null){
	            vy*=-1;
	            remove(collider);
	            brickCounter++;
	        }
	    }
	    // captures the object with which the ball has just collide
	    private GObject getCollidingObject(){
	        if (getElementAt(ball.getX(), ball.getY()) != null)  return getElementAt(ball.getX(), ball.getY());
	        else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()) != null) return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY()); 
	        else if (getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS) != null) return getElementAt(ball.getX(), ball.getY() + 2*BALL_RADIUS);
	        else if (getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS) != null) return getElementAt(ball.getX() + 2*BALL_RADIUS, ball.getY() + 2*BALL_RADIUS);
	        else return null;
	    }
	    private void playGame(){
	        while (turn < NTURNS){
	            waitForClick();
	            getBallVelocity();
	            while (brickCounter < TOTAL_BRICKS){
	                moveBall();
	                checkForCollision();
	                pause(50);
	                if (ball.getY() == getHeight() - BALL_RADIUS*2){
	                    remove(ball);
	                    turn ++;
	                    break;
	                }
	            }
	        }   
	        printResult();
	    }
	    // prints whether the player wins or loses
	    private void printResult(){
	        // creates winner GLabel
	        result = new GLabel ("You Win!!");
	        // GLabel coordinates
	        double x = getWidth()/2 - result.getWidth()/2;
	        double y = getHeight()/2;
	         
	        // condition to win
	        if (brickCounter == TOTAL_BRICKS) {
	             
	            add (result, x, y);
	        }
	        // condition to lose
	        else if (turn == NTURNS){
	        //  creates loser GLabel
	            result = new GLabel ("You lose");
	            add (result, x, y);
	        }
	    }
	    
}
	
 

