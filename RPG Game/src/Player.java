/* SWEN20003 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

public class Player {
	private double xPos, yPos, speed;
	
	/** Create a new Player object. */
	Player(double x, double y, double s) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the player.
		 * @param y The y coordinate of the player.
		 * @param speed The speed of the player.
		 */
		this.xPos = x;
		this.yPos = y;
		this.speed = s;
	}
	
	/** Returns the x coordinate of the player unit.  
     */
	public double getX() {
		return xPos;
	}
	
	/** Returns the y coordinate of the player unit.  
     */
	public double getY() {
		return yPos;
	}
	
	/** Returns the speed of the player unit.  
     */
	public double getSpeed() {
		return speed;
	}
	
	/** Set the x position of the player.
	 * @param x The x coordinate to which the previous value is changed.
     */
	public void setX(double x) {
		this.xPos = x;
	}
	
	/** Set the y position of the player.
	 * @param y The y coordinate to which the previous value is changed.
     */
	public void setY(double y) {
		this.yPos = y;
	}
}
