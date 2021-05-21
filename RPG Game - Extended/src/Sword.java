import org.newdawn.slick.Image;

public class Sword extends Item {
	public Sword(double x, double y, Image img, World world) {
		super(x,y,img,world);
	}
	
	/** Gives the player its effect.
	 * @param p The active player.
	 */
	public void giveEffect(Player p) {
		if (p.getDamage() == p.getDefaultDamage()) {
			p.setDamage(p.getDefaultDamage() + 30);
		}
	}
}
