import org.newdawn.slick.Image;

public class Item {
	private double xPos, yPos;
	private Image img;
	private Camera cam;
	private boolean isTaken = false;
	
	public Item(double x, double y, Image img, World world) {
		this.xPos = x;
		this.yPos = y;
		this.img = img;
		this.cam = world.getCamera();
	}
	
	public double getX() {	return xPos;	}
	public double getY() {	return yPos;	}
	
	public double distFromPlayer(World world) {
		return Math.sqrt(Math.pow(world.getActivePlayer().getX() - this.getX(), 2) + 
				Math.pow(world.getActivePlayer().getY() - getY(), 2));
	}
	
	public void render() {
		img.drawCentered((float)(this.getX() - cam.getMinX()), (float)(this.getY() - cam.getMinY()));
	}
	
	public void renderAt(double x, double y) {
		img.draw((float)x , (float)y);
	}
	
	public void update(World world) {
		if (this.distFromPlayer(world) <= 50 && isTaken == false) {
			isTaken = true;
			world.getActivePlayer().obtainItem(this);
		}
	}
	
	public boolean isTaken(){
		return isTaken;
	}

	public void giveEffect(Player player) {
		
	}
 }
