import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Garth extends Villager {
	private boolean swordRetrieved = false;
	private boolean tomeRetrieved = false;
	private boolean amuletRetrieved = false;
	public Garth (double x, double y, double s, int hp, int cd, int dmg, String name, Image unitImg, Camera cam) {
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
	@Override
	public void interact(World world) {
		//f( Arrays.asList("a","b","c").contains("a") )
		if (world.getActivePlayer().getInventory().contains(world.sword)) {
			swordRetrieved = true;
		}
		if (world.getActivePlayer().getInventory().contains(world.tome)) {
			tomeRetrieved = true;
		}
		if (world.getActivePlayer().getInventory().contains(world.amulet)) {
			amuletRetrieved = true;
		}
		
		if (this.getTalkingTimer() == 0) {
			this.setTalkingStatus(true);
			this.setTalkingTimer(4000);
		}
	}
	
	public void renderText(Graphics g, Camera cam) {
		if (this.getTalkingStatus()) {
			// HP bar colours
	        Color VALUE = new Color(1.0f, 1.0f, 1.0f);          // White
	        Color BAR_BG = new Color(0.0f, 0.0f, 0.0f, 0.5f);   // Black, transp
	
	        // Variables for layout
	        String text;  
	        if (amuletRetrieved) {
	        	if (swordRetrieved) {
	        		if (tomeRetrieved) {
			        	text = "You have found all the treasure I know of.";
			        } else {
			        	text = "Find the Tome of Agility, in the Land of Shadows.";
			        }
		        } else {
		        	text = "Find the Sword of Strength - cross the river and back, on the east side.";
		        }
	        } else {
	        	text = "Find the Amulet of Vitality, across the river to the west.";
	        }
	        
	        
	        double text_x, text_y;         // Coordinates to draw text
	        double bar_x, bar_y;           // Coordinates to draw rectangles
	        int bar_width, bar_height;  // Size of rectangle to draw
	
	        // Display the player's health
	        text_x = this.getX() - 35;
	        text_y = this.getY() - 60;
	
	        
	        bar_width = g.getFont().getWidth(text);
	        
	        bar_height = 15;
	        bar_x = this.getX() - bar_width/2;
	        bar_y = this.getY() - 60;
	        
	        text_x = bar_x + (bar_width - g.getFont().getWidth(text)) / 2;
	        g.setColor(BAR_BG);
	        g.fillRect((float)(bar_x - cam.getMinX()), (float)(bar_y - cam.getMinY()), bar_width, bar_height);
	        g.setColor(VALUE);
	        g.drawString(text, (float)(text_x - cam.getMinX()), (float)(text_y - cam.getMinY()));
		}
	}
}

