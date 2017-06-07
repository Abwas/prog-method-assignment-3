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
	
/** Brick color array */
	private static final Color[] BRICK_COLOR_ARRAY = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		
		double x = 0.5;
		
		double y = getHeight() - (BRICK_HEIGHT + BRICK_Y_OFFSET) * 9;
		
		for (int row = 0; row < NBRICK_ROWS; row++) {
			
			createRow (x, y, (BRICK_WIDTH + BRICK_SEP)); 
			
			y -= (BRICK_HEIGHT + BRICK_SEP);
		}
	}
	
	
	private void createRow (double x, double y, int NBRICKS_PER_ROW) {
		
		for (int i = 0; i < NBRICKS_PER_ROW; i++ ) {
			
			createBrick (x + i * (BRICK_WIDTH + BRICK_SEP), y);
			
		}
	}
	
	private void createBrick (double x, double y) {
		
		GRect brick = new GRect (x, y, BRICK_WIDTH, BRICK_HEIGHT);
		
		add(brick);
	}
}
