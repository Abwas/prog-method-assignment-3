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
	
/** instance variable*/
	private GRect brick;
	private GRect paddle;
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setUpGame();
		
		
	}
	
		private void setUpGame() {
			createBricks();
			createPaddle();
		}
		
		private void createBricks() {
			
			double x = 0.5;
			
			double y = BRICK_Y_OFFSET;
			
			for (int row = 0; row < NBRICK_ROWS; row++) {
				
				for (int column = 0; column < NBRICKS_PER_ROW; column++) {
				
					brick = new GRect (BRICK_WIDTH, BRICK_HEIGHT);
					
					brick.setFilled(true);
					
					add(brick, x, y);
					
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
		
		private void createPaddle() {
			
			double x = getWidth() / 2 - PADDLE_WIDTH / 2;
			
			double y = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
			
			paddle = new GRect (x, y, PADDLE_WIDTH, PADDLE_HEIGHT);
			
			paddle.setFilled(true);		
			
			add(paddle);
			
			addMouseListeners();
	}
		
		public void mouseMoved(MouseEvent e) {
			
			if ((e.getX() < getWidth() - PADDLE_WIDTH / 2) && (e.getX() > PADDLE_WIDTH / 2)) {
				
	            paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	            
			}
	        
	    }
	
}
	
 

