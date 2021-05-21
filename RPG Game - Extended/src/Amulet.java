import org.newdawn.slick.Image;

/** Represents the Amulet item in the world.
 */
public class Amulet extends Item {
	public Amulet(double x, double y, Image img, World world) {
		super(x,y,img,world);
	}
	/** Gives the player its effect.
	 * @param p The active player.
	 */
	public void giveEffect(Player p) {
		if (p.getMaxHP() == p.getDefaultMaxHP()) {
			p.setMaxHP(p.getDefaultMaxHP() + 80);
			p.setHP(p.getHP() + 80);
		}
	}
}
