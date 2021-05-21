/* SWEN20003 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

import org.newdawn.slick.SlickException;

/** Represents the camera that controls our viewpoint.
 */
public class Camera
{

    /** The unit this camera is following */
    private Player unitFollow;
    
    /** The width and height of the screen */
    /** Screen width, in pixels. */
    public final int SCREENWIDTH;
    /** Screen height, in pixels. */
    public final int SCREENHEIGHT;
    /** Tile size, in pixels. */
    public static final int tilesize = 72;

    
    /** The camera's position in the world, in x and y coordinates. */
    private double xPos;
    private double yPos;

    
    public double getxPos() {
        // TO DO: Fill In
    	return xPos;
    }

    public double getyPos() {
        // TO DO: Fill In
    	return yPos;
    }

    
    /** Create a new Camera object. */
    public Camera(Player player, int screenwidth, int screenheight)
    {   
        this.unitFollow = player;
        this.SCREENWIDTH = screenwidth;
        this.SCREENHEIGHT = screenheight;

    }

    /** Update the game camera to recentre it's viewpoint around the player 
     */
    public void update()
    throws SlickException
    {
    	this.xPos = unitFollow.getX();
    	this.yPos = unitFollow.getY();
    }
    
    /** Returns the minimum x value on screen 
     */
    public double getMinX(){
    	return (this.getxPos() - SCREENWIDTH/2);
    }
    
    /** Returns the minimum x value on screen (in tiles)
     */
    public int getTiledMinX(){
    	return ((int)(this.getxPos() - SCREENWIDTH/2)%tilesize);
    }
    
    /** Returns the maximum x value on screen 
     */
    public double getMaxX(){
    	return (this.getxPos() + SCREENWIDTH/2);
    }
    
    /** Returns the minimum y value on screen 
     */
    public double getMinY(){
    	return (this.getyPos() - SCREENHEIGHT/2);
    }
    
    /** Returns the minimum y value on screen (in tiles)
     */
    public int getTiledMinY(){
    	return ((int)(this.getyPos() - SCREENWIDTH/2)%tilesize);
    }
    
    /** Returns the maximum y value on screen 
     */
    public double getMaxY(){
    	return (this.getyPos() + SCREENHEIGHT/2);
    }

    /** Tells the camera to follow a given unit. 
     */
    public void followUnit(Object unit)
    throws SlickException
    {
        // TO DO: Fill In
    }
    
}