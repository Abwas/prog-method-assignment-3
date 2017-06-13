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
	
/** Animation delay or pause time between ball moves */   
    private static final int DELAY = 25;
	
/** instance variable*/
	private GRect brick;
	private GRect paddle;
	private GOval ball;
	private GObject collider;
	private GLabel Winner;
	private GLabel gameOver;
	private double vx;
    private double vy;
    private RandomGenerator rgen = RandomGenerator.getInstance();
    AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
    private int brickCounter = 100;
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		for(int i = 0; i < NTURNS; i++) {
			
            setUpGame();
            playGame();
            bounceClip.play();
            
            if(brickCounter == 0) {
            	
                ball.setVisible(false);
                
                printWinner();
                
                break;
            }
            
            if(brickCounter > 0) removeAll();
        }
		
        if(brickCounter > 0) printGameOver();
      
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
			
			addMouseListeners();

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
		
		private void playGame() {
			
	        waitForClick();
	        bounceClip.play();
	        getBallVelocity();
	        
	        while (true) {
	            moveBall();
	            if (ball.getY() >= getHeight()) {
	                break;
	            }
	            if(brickCounter == 0) {
	                break;
	            }
	        }
	    }
	 
	    private void getBallVelocity() {
	    	
	        vy = 4.0;
	        vx = rgen.nextDouble(1.0, 3.0);
	        if (rgen.nextBoolean(0.5)) vx = -vx; 
	 
	    }
	 
	    private void moveBall() {
	    	
	        ball.move(vx, vy);
	        
	        //check for walls
	        if ((ball.getX() - vx <= 0 && vx < 0 ) || (ball.getX() + vx >= (getWidth() - BALL_RADIUS * 2) && vx > 0)) vx = -vx;
	 
	        //check for colliding objects
	        collider = getCollidingObject();
	        if (collider == paddle) {
	 
	        	if(ball.getY() >= getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 && ball.getY() < getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT - BALL_RADIUS * 2 + 4) {
	                vy = -vy;    
	            }
	        	
	        } else if (collider != null) {
	            remove(collider); 
	            brickCounter--;
	            vy = -vy;
	        }
	        pause(DELAY);
	    }
	 
	    private GObject getCollidingObject() {
	 
	        if((getElementAt(ball.getX(), ball.getY())) != null) {
	        	
	             return getElementAt(ball.getX(), ball.getY());
	             
	          }
	        
	        else if (getElementAt( (ball.getX() + BALL_RADIUS * 2), ball.getY()) != null ){
	        	
	             return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY());
	             
	          }
	        
	        else if(getElementAt(ball.getX(), (ball.getY() + BALL_RADIUS * 2)) != null ){
	        	
	             return getElementAt(ball.getX(), ball.getY() + BALL_RADIUS * 2);
	             
	          }
	        
	        else if(getElementAt((ball.getX() + BALL_RADIUS * 2), (ball.getY() + BALL_RADIUS * 2)) != null ){
	        	
	             return getElementAt(ball.getX() + BALL_RADIUS * 2, ball.getY() + BALL_RADIUS * 2);
	             
	          }
	        //returns null if there are no objects present
	        else {
	             return null;
	          }
	 
	    }
	 
	    private void printGameOver() {
	    	
	        gameOver = new GLabel ("You Lose!", getWidth() / 2, getHeight()/2);
	        gameOver.move(-gameOver.getWidth() / 2, -gameOver.getHeight());
	        gameOver.setColor(Color.RED);
	        add(gameOver);
	        
	    }
	 
	    private void printWinner() {
	    	
	        Winner = new GLabel ("You Win!!", getWidth() / 2, getHeight() / 2);
	        Winner.move(-Winner.getWidth()/2, -Winner.getHeight());
	        Winner.setColor(Color.BLUE);
	        add (Winner);
	        
	    }
	
}
	
 

