import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Elvira extends Villager {
	private boolean healed = false;
	public Elvira (double x, double y, double s, int hp, int cd, int dmg, String name, Image unitImg, Camera cam) {
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
		if (this.getTalkingTimer() == 0) {
			if (world.getActivePlayer().getHP()<world.getActivePlayer().getMaxHP()) {
				world.getActivePlayer().setHP(world.getActivePlayer().getMaxHP());
				healed = true;
			}
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
	        if (healed) {
	        	text = "You're looking much healthier now.";
	        } else {
	        	text = "Return to me if you ever need healing.";
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
	
	public void update(int delta, World world) {
		if (this.getTalkingTimer() > 0 && this.getTalkingTimer() > delta){
			this.setTalkingTimer(this.getTalkingTimer() - delta);
		} else {
			this.setTalkingTimer(0);
			this.setTalkingStatus(false);
			this.healed = false;
		}
	}
	
}
