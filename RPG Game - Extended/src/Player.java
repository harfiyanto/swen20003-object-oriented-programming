import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/* SWEN20003 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

/** Represents the player(s).
 */
public class Player extends Unit {
	private static final double DEFAULTSPEED = 0.25;
	private static final int DEFAULTMAXHP = 100;
	private static final int DEFAULTMAXCOOLDOWN = 600;
	private static final int DEFAULTDAMAGE = 26;
	private final int respawnX = 756;
    private final int respawnY = 684;
    private int regenCooldown = 2000;
    private boolean isDamaged = false;
    private int damagedCooldown = 0;
	private Image right, left, panel;
	private List<Item> inventory = new ArrayList<Item>();
	
	/** Create a new Player object. */
	public Player(double x, double y, Image right, Image panel) {
		/** Constructor: Set the values of xPos, yPos and speed. 
		 * @param x The x coordinate of the player.
		 * @param y The y coordinate of the player.
		 * @param speed The speed of the player.
		 * @param hp The max HP of the unit.
		 * @param cd The max cooldown of the unit.
		 * @param dmg the max damage of the unit.
		 */
		super(x,y);
		this.setSpeed(DEFAULTSPEED);
		this.right = right;
		this.left = right.getFlippedCopy(true, false);
		this.panel = panel;
		this.setHP(100);
		this.setMaxHP(DEFAULTMAXHP);
		this.setDamage(26);
		this.setMaxCooldown(600);
	}
	
	public void attack(World world) {
		if (this.getCooldown() == 0) {
			if (!this.nearbyMonsters(world.allmonsters).isEmpty()) {
				for (Unit monster : this.nearbyMonsters(world.allmonsters)) {
					monster.receiveDamage(this.countDamage());
					this.setCooldown(this.getMaxCooldown());
				}
			}
		}
	}
	
	public double calculateDist(double x, double y) {
		return Math.sqrt(Math.pow(x - this.getX(), 2) + Math.pow(y - this.getY(), 2));
	}
	
	public int getDefaultDamage() {
		return DEFAULTDAMAGE;
	}
	
	public int getDefaultMaxHP() {
		return DEFAULTMAXHP;
	}
	
	public int getDefaultMaxCooldown() {
		return DEFAULTMAXCOOLDOWN;
	}
	
	public double getDefaultSpeed() {
		return DEFAULTSPEED;
	}
	
	public List<Item> getInventory() {
		return inventory;
	}
	
	public void obtainItem(Item item) {
		inventory.add(item);
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
		this.damagedCooldown = 50;
	}
	
	/** Update the unit's state
	 * @param delta The amount of time that has passed (in ms).
	 */
	public void update(double dir_x, double dir_y, int delta, World world) {
		for (Item item : inventory) {
			item.giveEffect(this);
		}
		
		this.move(dir_x, dir_y, delta, world);
		
		if (this.getHP() == 0)
			this.setToDead();
		if (this.isDead()) {
			this.setX(respawnX);
			this.setY(respawnY);
			this.setToLive();
		}
		if (this.regenCooldown <= 0) {
			if (this.getHP() < this.getMaxHP()) {
				this.setHP(this.getHP() + 1);
			}
			this.regenCooldown = 2000;
		} else {
			this.regenCooldown -= delta;
		}
		
		if (this.getCooldown() > 0) {
			if ((delta) > this.getCooldown()) {
				this.setCooldown(0);
			} else {
				this.setCooldown(this.getCooldown() - delta);
			}
		}
		
		if (this.damagedCooldown > 0) {
			this.isDamaged = true;
			if ((delta) > this.damagedCooldown) {
				this.damagedCooldown = 0;
			} else {
				this.damagedCooldown -= delta;
			}
		} else {
			this.isDamaged = false;
		}
	}
	
	public void render(Graphics g, Camera maincam) {
		if (this.getDir() == 1) {
    		right.drawCentered((float)(this.getX() - maincam.getMinX()), 
    				(float)(this.getY() - maincam.getMinY()));
    	} else if (this.getDir() == -1) {
    		left.drawCentered((float)(this.getX() - maincam.getMinX()), 
    				(float)(this.getY() - maincam.getMinY()));
    	}
		this.renderPanel(g);
		if (isDamaged) {
			Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp
			g.setColor(BAR);
	        g.fillRect(0, 0, 800, 600);
		}
	}
	
	/** Renders the player's status panel.
     * @param g The current Slick graphics context.
     */
    public void renderPanel(Graphics g)
    {
        // Panel colours
        Color LABEL = new Color(0.9f, 0.9f, 0.4f);          // Gold
        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.8f);   // Black, transp
        Color BAR = new Color(0.8f, 0.0f, 0.0f, 0.8f);      // Red, transp

        // Variables for layout
        String text;                // Text to display
        int text_x, text_y;         // Coordinates to draw text
        int bar_x, bar_y;           // Coordinates to draw rectangles
        int bar_width, bar_height;  // Size of rectangle to draw
        int hp_bar_width;           // Size of red (HP) rectangle
        int inv_x, inv_y;           // Coordinates to draw inventory item

        double health_percent;       // Player's health, as a percentage

        // Panel background image
        panel.draw(0, RPG.SCREENHEIGHT - RPG.PANELHEIGHT);

        // Display the player's health
        text_x = 15;
        text_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 25;
        g.setColor(LABEL);
        g.drawString("Health:", text_x, text_y);
        text = this.getHP() + "/" + this.getMaxHP();

        bar_x = 90;
        bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 20;
        bar_width = 90;
        bar_height = 30;
        health_percent = (double)this.getHP() / (double)this.getMaxHP();
        hp_bar_width = (int) (bar_width * health_percent);
        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);
        g.setColor(BAR);
        g.fillRect(bar_x, bar_y, hp_bar_width, bar_height);
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's damage and cooldown
        text_x = 200;
        g.setColor(LABEL);
        g.drawString("Damage:", text_x, text_y);
        text_x += 80;
        text = String.valueOf(getDamage());
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);
        text_x += 40;
        g.setColor(LABEL);
        g.drawString("Rate:", text_x, text_y);
        text_x += 55;
        text = String.valueOf(getCooldown());
        g.setColor(VALUE);
        g.drawString(text, text_x, text_y);

        // Display the player's inventory
        g.setColor(LABEL);
        g.drawString("Items:", 420, text_y);
        bar_x = 490;
        bar_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT + 10;
        bar_width = 288;
        bar_height = bar_height + 20;
        g.setColor(BAR_BG);
        g.fillRect(bar_x, bar_y, bar_width, bar_height);

        inv_x = 490;
        inv_y = RPG.SCREENHEIGHT - RPG.PANELHEIGHT
            + ((RPG.PANELHEIGHT - 72) / 2);
        // for (each item in the player's inventory)
        for (Item item : inventory) {
        	item.renderAt(inv_x, inv_y);
        	// Render the item to (inv_x, inv_y)
            inv_x += 72;
        }
    }

    public void talk(World world) {
    	for (Villager i: world.allvillagers) {
			if (calculateDist(i.getX(), i.getY()) <= 50) {
				i.interact(world);
			}
		}
    }
    
    /** Builds a list of nearby monsters.
     * @param units The list of monsters in the world.
     * @return nearbyunits The list of monsters nearby.
     */
    public List<Unit> nearbyMonsters(List<Unit> units) {
		List<Unit> nearbyunits = new ArrayList<Unit>();
		for (Unit i: units) {
			if (calculateDist(i.getX(), i.getY()) <= 50) {
				nearbyunits.add(i);
			}
		}
		return nearbyunits;
	}
	
}
