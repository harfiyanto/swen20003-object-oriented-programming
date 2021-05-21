import java.lang.Math;
import java.util.Random;

import org.newdawn.slick.Image;

public class Monster extends Unit {
	private String nature;
	private boolean isAttacked = false;
	private int attackedTime = 0;
	private double nextX, nextY;
	private double moveCooldown = 0;
	/** Create a new Monster object. */
	public Monster(double x, double y, double s, int hp, int cd, int dmg, String name, Image unitImg, Camera cam, String nature) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the player.
		 * @param y The y coordinate of the player.
		 * @param speed The speed of the player.
		 * @param hp The max HP of the unit.
		 * @param cd The max cooldown of the unit.
		 * @param dmg the max damage of the unit.
		 */
		super(x,y,s,hp,cd,dmg,name,unitImg,cam);
		this.nature = nature;
	}
	
	public void attack(World world) {
		if (this.getCooldown() == 0) {
			world.getActivePlayer().receiveDamage(this.countDamage());
			this.setCooldown(this.getMaxCooldown());
		}
	}
	
	public double distFromPlayer(World world) {
		return Math.sqrt(Math.pow(world.getActivePlayer().getX() - this.getX(), 2) + 
				Math.pow(world.getActivePlayer().getY() - getY(), 2));
	}
	
	/** Update the unit's HP after receiving damage.
	 * @param d The damage this unit has received.
     */
	public void receiveDamage(int d) {
		if (this.getHP() > d) {
			this.setHP(this.getHP() - d);
		} else {
			this.setHP(0);
		}
		this.isAttacked = true;
		this.attackedTime = 5000;
	}
	
	@Override
	public void update(int delta, World world) {
		if (this.nature == "passive") {
			if (isAttacked) {
				if (attackedTime == 0) {
					isAttacked = false;
				} else {
					double xDist = this.getX() - world.getActivePlayer().getX();
					double yDist = this.getY() - world.getActivePlayer().getY();
					double tDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
					nextX = (xDist / tDist);
					nextY = (yDist / tDist);
					if (attackedTime < delta) {
						attackedTime = 0;
					} else {
						attackedTime -= delta;
					}
				}
			} else {
				moveCooldown += delta;
				if (moveCooldown >= 3000) {
					Random rnd = new Random();
					nextX = rnd.nextInt(3) - 1;
					nextY = rnd.nextInt(3) - 1;
					moveCooldown = 0;
				}
			}
		} else if (this.nature == "aggressive" && !isDead()) {
			if (distFromPlayer(world) <= 150 && distFromPlayer(world) >= 50) {
				double xDist = this.getX() - world.getActivePlayer().getX();
				double yDist = this.getY() - world.getActivePlayer().getY();
				double tDist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
				nextX = -(xDist / tDist);
				nextY = -(yDist / tDist);
			} else {
				nextX = 0;
				nextY = 0;
			}
			
			if (distFromPlayer(world) <= 50) {
				this.attack(world);
			}
		}
		
		if (this.getHP() == 0)
			this.setToDead();
		
		this.move(nextX, nextY, delta, world);
		
		if (this.getCooldown() > 0) {
			if ((delta) > this.getCooldown()) {
				this.setCooldown(0);
			} else {
				this.setCooldown(this.getCooldown() - delta);
			}
		}
		
	}
}
