import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/* SWEN20003 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

public class Unit {
	
	private double xPos, yPos, speed;
	private int HP, maxHP, cooldown = 0, maxCooldown, damage, direction = -1;
	private boolean isDead = false;
	private Camera cam;
	private String unitName;
	private Image right, left;
	
	public Unit(double x, double y, double s, int hp, int cd, int dmg, String name, Image right, Camera cam) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the unit.
		 * @param y The y coordinate of the unit.
		 * @param s The speed of the unit.
		 * @param hp The max HP of the unit.
		 * @param cd The max cooldown of the unit.
		 * @param dmg the max damage of the unit.
		 */
		this.setX(x);
		this.setY(y);
		this.setSpeed(s);
		this.setHP(hp);
		this.setMaxHP(hp);
		this.setMaxCooldown(cd);
		this.setDamage(dmg);
		this.unitName = name;
		this.right = right;
		this.left = right.getFlippedCopy(true, false);
		this.cam = cam;
	}
	
	public Unit(double x, double y) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the player.
		 * @param y The y coordinate of the player.
		 */
		this.setX(x);
		this.setY(y);
	}
	
	/** Calculate a random damage the unit inflicts.
     */
	public int countDamage () {
		Random rnd = new Random();
		int damage = rnd.nextInt(this.damage);
		return damage;
	}
	
	/** Returns the x coordinate of the unit.  
     */
	public double getX() {
		return xPos;
	}
	
	/** Returns the y coordinate of the unit.  
     */
	public double getY() {
		return yPos;
	}
	
	/** Returns the speed of the unit.  
     */
	public double getSpeed() {
		return speed;
	}
	
	/** Returns the direction of the unit.  
     */
	public int getDir() {
		return direction;
	}
	
	/** Returns the HP of the unit.  
     */
	public int getHP() {
		return HP;
	}
	
	/** Returns the max HP of the unit.  
     */
	public int getMaxHP() {
		return maxHP;
	}
	
	/** Returns the direction of the unit.  
     */
	public int getDamage() {
		return damage;
	}
	
	
	/** Returns the direction of the unit.  
     */
	public int getCooldown() {
		return cooldown;
	}
	
	/** Returns the max cooldown of the unit.  
     */
	public int getMaxCooldown() {
		return maxCooldown;
	}
	
	/** Returns the camera of the unit.  
     */
	public Camera getCamera() {
		return cam;
	}
	
	/** Check whether the coordinate (in pixels) is blocked or out of range.
     * @param x The x coordinate that the user wants to check.
     * @param y The y coordinate that the user wants to check.
     * @param w The current world object.
     * @return true if the coordinate is outside the map or in a blocked tile, and false otherwise.
     */
	public boolean isBlocked(double x, double y, World w) {
    	// Checks if the coordinate is outside the map. Return false if so.
    	if (x / World.TILESIZE >= w.getWorldmap().getWidth() || y / World.TILESIZE >= w.getWorldmap().getHeight()) {
    		return true;
   		}
    	// Checks if the coordinate is outside the map. Return false if so.
    	if (w.getWorldmap().getTileProperty(w.getWorldmap().getTileId((int)x/World.TILESIZE, (int)y/World.TILESIZE, 0), "block", "0") == "0") {
    		return false;
    	// Otherwise return true
    	} else {
    		return true;
    	}
    }
	
	/** Returns the state of the unit. (dead : true, alive : false)  
     */
	public boolean isDead() {
		return this.isDead;
	}
	
	/** Takes care of updating the unit's coordinate.
     * @param x The change of the unit's x coordinate.
     * @param y The change of the unit's y coordinate.
     * @param delta Time passed since last frame (milliseconds).
     * @param w The current world object.
     * @return true if the coordinate is outside the map or in a blocked tile, and false otherwise.
     */
	public void move(double x, double y, int delta, World w) {
		// Calculates the next X and Y coordinate.
    	double nextX = this.getX() + x * delta * this.getSpeed();
    	double nextY = this.getY() + y * delta * this.getSpeed();
    	
    	// Change the direction the unit is facing accordingly
    	if (x > 0) {
    		this.setDir(1);
    	}
    	if (x < 0) {
    		this.setDir(-1);
    	}
    	
    	// Checks whether the next X move is blocked.
    	if (!isBlocked(nextX, this.getY(), w)) {
    		this.setX(this.getX() + x * delta * this.getSpeed());
    	} 
    	// Checks whether the next Y move is blocked.
    	if (!isBlocked(this.getX(), nextY, w)) {
    		this.setY(this.getY() + y * delta * this.getSpeed());
    	} 
	}
	
	/** Update the unit's HP after receiving damage.
	 * @param d The damage this unit has received.
     */
	public void receiveDamage(int d) {
		if (this.HP > d) {
			this.HP = this.HP - d;
		} else {
			this.HP = 0;
		}
	}
	
	/** Render the unit's image.
	 * @param g Graphics processor.
     */
	public void render(Graphics g) {
		// Render the unit's image
		// unitImg.drawCentered((float)(this.getX() - cam.getMinX()), (float)(this.getY() - cam.getMinY()));
		if (this.getDir() == 1) {
    		right.drawCentered((float)(this.getX() - cam.getMinX()), 
    				(float)(this.getY() - cam.getMinY()));
    	} else if (this.getDir() == -1) {
    		left.drawCentered((float)(this.getX() - cam.getMinX()), 
    				(float)(this.getY() - cam.getMinY()));
    	}
		
		// HP bar colours
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.5f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
        
        // Variables for layout
        String text;                // Text to display
        double text_x, text_y;         // Coordinates to draw text
        double bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        double health_percent;       // Player's health, as a percentage

        // Display the unit's name.
        text = this.unitName;
        text_y = this.getY() - 45;

        // Calculate the widths of bg bar and health bar
        if (g.getFont().getWidth(text) > 70) {
        	bar_width = g.getFont().getWidth(text);
        } else {
        	bar_width = 70;
        }
        bar_height = 15;
        bar_x = this.getX() - bar_width/2;
        bar_y = this.getY() - 45;
        health_percent = (double) this.getHP() / (double)this.getMaxHP();
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        
        // Render the texts and bars
        g.setColor(BAR_BG);
        g.fillRect((float)(bar_x - cam.getMinX()), (float)(bar_y - cam.getMinY()), bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect((float)(bar_x - cam.getMinX()), (float)(bar_y - cam.getMinY()), hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, (float)(text_x - cam.getMinX()), (float)(text_y - cam.getMinY()));

	}
	
	/** Set the x position of the unit.
	 * @param x The x coordinate to which the previous value is changed.
     */
	public void setX(double x) {
		this.xPos = x;
	}
	
	/** Set the y position of the unit.
	 * @param y The y coordinate to which the previous value is changed.
     */
	public void setY(double y) {
		this.yPos = y;
	}
	
	/** Set the speed of the unit.
	 * @param y The speed to which the previous value is changed.
     */
	public void setSpeed(double s) {
		this.speed = s;
	}
	
	/** Set the direction of the unit.
	 * @param y The speed to which the previous value is changed.
     */
	public void setDir(int d) {
		this.direction = d;
	}
	
	/** Set the max HP of the unit.
	 * @param hp The max HP of the unit.
     */
	public void setMaxHP(int hp) {
		this.maxHP = hp;
	}
	
	/** Set the max HP of the unit.
	 * @param hp The max HP of the unit.
     */
	public void setHP(int hp) {
		this.HP = hp;
	}
	
	/** Set the max Cooldown of the unit.
	 * @param hp The max cooldown of the unit.
     */
	public void setMaxCooldown(int cd) {
		this.maxCooldown= cd;
	}
	
	/** Set the cooldown of the unit.
	 * @param c The cooldown to which the previous value is changed.
     */
	public void setCooldown(int c) {
		this.cooldown = c;
	}
	
	/** Set the Camera.
	 * @param c The camera that is being used.
     */
	public void setCamera(Camera cam) {
		this.cam = cam;
	}
	
	/** Set the damage of the unit.
	 * @param c The damage to which the previous value is changed.
     */
	public void setDamage(int d) {
		this.damage = d;
	}
	
	
	/** Set the the unit's state to dead.
     */
	public void setToDead() {
		this.HP = 0;
		this.isDead = true;
	}
	
	/** Set the the unit's state to alive.
     */
	public void setToLive() {
		this.HP = this.maxHP;
		this.isDead = false;
	}	

	/** Update the unit's state
	 * @param delta The amount of time that has passed (in ms).
	 * @param world The current world object.
	 */
	public void update(int delta, World world) {
		if (this.cooldown > 0) {
			if ((delta) > this.cooldown) {
				this.cooldown = 0;
			} else {
				this.cooldown -= delta;
			}
		}
	}
	
}
