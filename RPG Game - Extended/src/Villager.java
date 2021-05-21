import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/* SWEN20003 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

public abstract class Villager extends Unit{
	private boolean isTalking = false;
	private int talkingTimer = 0;
	
	public Villager (double x, double y, double s, int hp, int cd, int dmg, String name, Image unitImg, Camera cam) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the villager.
		 * @param y The y coordinate of the villager.
		 * @param speed The speed of the villager.
		 * @param hp The max HP of the villager.
		 * @param cd The max cooldown of the villager.
		 * @param dmg the max damage of the villager.
		 */
		super(x,y,s,hp,cd,dmg,name,unitImg,cam);
	}
	
	public abstract void interact(World world); 
	
	public abstract void renderText(Graphics g, Camera cam);
	
	public void update(int delta, World world) {
		if (talkingTimer > 0 && talkingTimer > delta){
			talkingTimer -= delta;
		} else {
			talkingTimer = 0;
			isTalking = false;
		}
	}
	
	public boolean getTalkingStatus() {
		return this.isTalking;
	}
	
	public int getTalkingTimer() {
		return this.talkingTimer;
	}
	
	public void setTalkingTimer(int t) {
		this.talkingTimer = t;
	}
	
	public void setTalkingStatus(boolean b) {
		this.isTalking = b;
	}
}
