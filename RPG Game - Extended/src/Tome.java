import org.newdawn.slick.Image;

public class Tome extends Item {
	public Tome(double x, double y, Image img, World world) {
		super(x,y,img,world);
	}
	
	/** Gives the player its effect.
	 * @param p The active player.
	 */
	public void giveEffect(Player p) {
		if (p.getMaxCooldown() == p.getDefaultMaxCooldown()) {
			p.setMaxCooldown(p.getDefaultMaxCooldown() - 300);
			if (p.getCooldown() > p.getMaxCooldown()) {
				p.setCooldown(p.getMaxCooldown());
			}
		}
	}
}
