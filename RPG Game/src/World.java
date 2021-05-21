/* 433-294 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** Create the world map */
	private TiledMap worldmap;
	
	/** Create a new player image (facing left and right) */
	private Image playerimgr = new Image("assets/units/player.png");
	private Image playerimgl = playerimgr.getFlippedCopy(true, false);
	
	/** Default starting x and y values */
	private int startingX = 756;
    private int startingY = 684;
    
    /** Default player speed, in pixels/milliseconds. */
    public double defaultpspeed = 0.25;
    /** Create a new player class */
	private Player player1 = new Player(startingX,startingY,defaultpspeed);
	/** Holds the state of the direction of the player (1 for right and -1 for left). */
	public int direction = -1;
	
	/** Active unit */
	public Object activeUnit = player1; 
	
	/** Screen width, in pixels. */
    public static final int SCREENWIDTH = 800;
    /** Screen height, in pixels. */
    public static final int SCREENHEIGHT = 600;
    /** Tile size, in pixels. */
    public static final int tilesize = 72;
    /** Extra tile to render */
    public static final int extratile = 2;
    
    
    
    /** Create a camera class */
    
	private Camera maincam = new Camera(player1, SCREENWIDTH, SCREENHEIGHT);
	
	/** Create a new World object. */
	
    public World()
    throws SlickException
    {
    	/** Constructor: Loading the map. */
        worldmap = new TiledMap("assets/map.tmx","assets");
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     */
    public void update(double dir_x, double dir_y, int delta)
    throws SlickException {
    	// Calculates the next X and Y coordinate.
    	double nextX = player1.getX()+dir_x * delta * player1.getSpeed();
    	double nextY = player1.getY()+dir_y * delta * player1.getSpeed();
    	
    	// Checks whether the next X move is blocked.
    	if (!isBlocked(nextX, player1.getY())) {
    		player1.setX(player1.getX() + dir_x * delta * player1.getSpeed());
    	} 
    	// Checks whether the next Y move is blocked.
    	if (!isBlocked(player1.getX(), nextY)) {
    		player1.setY(player1.getY() + dir_y * delta * player1.getSpeed());
    	} 
    	// Update the camera.
    	maincam.update();
    }
    
    /** Check whether the coordinate (in pixels) is blocked or out of range.
     * @param x The x coordinate that the user wants to check.
     * @param y The y coordinate that the user wants to check.
     * @return true if the coordinate is outside the map or in a blocked tile, and false otherwise.
     */
    public boolean isBlocked(double x, double y) {
    	
    	// Checks if the coordinate is outside the map. Return false if so.
    	if (x / tilesize >= worldmap.getWidth() || y / tilesize >= worldmap.getHeight()) {
    		return true;
   		}
    	// Checks if the coordinate is outside the map. Return false if so.
    	if (worldmap.getTileProperty(worldmap.getTileId((int)x/tilesize, (int)y/tilesize, 0), "block", "0") == "0") {
    		return false;
    	// Otherwise return true
    	} else {
    		return true;
    	}
    }

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	// Render the map.
    	worldmap.render(-maincam.getTiledMinX(),-maincam.getTiledMinY(),(int)maincam.getMinX()/tilesize,
    			(int)maincam.getMinY()/tilesize, RPG.SCREENWIDTH/tilesize + extratile, RPG.SCREENHEIGHT/tilesize + extratile);
    	
    	// Render Player 1.
    	// Render the player image according to the direction it's facing. 
    	if (direction == 1 && activeUnit == player1) {
    		playerimgr.drawCentered((float)(player1.getX() - maincam.getMinX()), 
    				(float)(player1.getY() - maincam.getMinY()));
    	} else if (direction == -1 && activeUnit == player1) {
    		playerimgl.drawCentered((float)(player1.getX() - maincam.getMinX()), 
    				(float)(player1.getY() - maincam.getMinY()));
    	}
    	
    	
    }
}
