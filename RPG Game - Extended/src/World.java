/* 433-294 Object Oriented Software Development

 * RPG Game Engine
 * Author: Harfiyanto Dharma Santoso, harfiyantos
 * Student ID : 772503
 */

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/** Represents the entire game world.
 * (Designed to be instantiated just once for the whole game).
 */
public class World
{
	/** Create the world map */
	private TiledMap worldmap;
	
	/** Default starting x and y values */
	private int startingX = 756;
    private int startingY = 684;
    
	/** Create a new player image */
	private Image playerimg = new Image("assets/units/player.png");
	private Image panel = new Image("assets/panel.png");
	
    /** Create a new player class */
	private Player player1 = new Player(startingX,startingY,playerimg,panel);
	
	/** Set active player */
	private Player activePlayer = player1; 
	
    /** Create a camera class */
	private Camera maincam = new Camera(player1, SCREENWIDTH, SCREENHEIGHT);
	
	
	/** Loading the Images for units and items */
	private Image elviraimg = new Image("assets/units/shaman.png");
	private Image garthimg = new Image("assets/units/peasant.png");
	private Image princeimg = new Image("assets/units/prince.png");
	private Image giantbatimg = new Image("assets/units/dreadbat.png");
	private Image zombieimg = new Image("assets/units/zombie.png");
	private Image banditimg = new Image("assets/units/bandit.png");
	private Image skeletonimg = new Image("assets/units/skeleton.png");
	private Image draelicimg = new Image("assets/units/necromancer.png");
	private Image amuletimg = new Image("assets/items/amulet.png");
	private Image swordimg = new Image("assets/items/sword.png");
	private Image tomeimg = new Image("assets/items/book.png");
	private Image elixirimg = new Image("assets/items/elixir.png");
	
	/** Create the villager class' objects */
	List<Villager> allvillagers = new ArrayList<Villager>();
	private Elvira elvira = new Elvira(738, 549, 0, 1, 0, 0,"Elvira", elviraimg, maincam);
	private Garth garth = new Garth(756, 870, 0, 1, 0, 0, "Garth",garthimg, maincam);
	private Prince prince = new Prince(467, 679, 0, 1, 0, 0, "Prince Aldric",princeimg, maincam);
	
	/** Create the item objects */
	List<Item> allitems = new ArrayList<Item>();
	Amulet amulet = new Amulet(965, 3563, amuletimg, this);
	Sword sword = new Sword(4791, 1253, swordimg, this);
	Tome tome = new Tome(546, 6707, tomeimg, this);
	Elixir elixir = new Elixir(1976, 402, elixirimg, this);
	
	/** Create monster classes */
	List<Unit> allmonsters = new ArrayList<Unit>();
	private Monster[] giantBat = new Monster[30];
	private Monster[] zombie = new Monster[38];
	private Monster[] bandit = new Monster[34];
	private Monster[] skeleton = new Monster[24];
	private Monster draelic = new Monster(2069,510, 0, 1, 0, 0,"Draelic", draelicimg, maincam, null);
	
	/** Set starting coordinates for the monsters */
	private final int batCoordinates[][] = {{1431,864},{1154,1321},{807,2315},{833,2657},
			{1090,3200},{676,3187},{836,3966},{653,4367},{1343,2731},{1835,3017},
			{1657,3954},{1054,5337},{801,5921},{560,6682},{1275,5696},{1671,5991},
			{2248,6298},{2952,6373},{3864,6695},{4554,6443},{4683,6228},{5312,6606},
			{5484,5946},{6371,5634},{5473,3544},{5944,3339},{6301,3414},{6388,1994},
			{6410,1584},{5314,274}};
	private final int zombieCoordinates[][] = {{681,3201},{691,4360},{2166,2650},
			{2122,2725},{2284,2962},{2072,4515},{2006,4568},{2385,4629},{2446,4590},
			{2517,4532},{4157,6730},{4247,6620},{4137,6519},{4234,6449},{5879,4994},
			{5954,4928},{6016,4866},{5860,4277},{5772,4277},{5715,4277},{5653,4277},
			{5787,797},{5668,720},{5813,454},{5236,917},{5048,1062},{4845,996},
			{4496,575},{3457,273},{3506,779},{3624,1192},{2931,1396},{2715,1326},
			{2442,1374},{2579,1159},{2799,1269},{2768,739},{2099,956}};
	private final int banditCoordinates[][] = {{1889,2581},{4502,6283},{5248,6581},
			{5345,6678},{5940,3412},{6335,3459},{6669,260},{6598,339},{6598,528},
			{6435,528},{6435,678},{5076,1082},{5191,1187},{4940,1175},{4760,1039},
			{4883,889},{4427,553},{3559,162},{3779,1553},{3573,1553},{3534,2464},
			{3635,2464},{3402,2861},{3151,2857},{3005,2997},{2763,2263},{2648,2263},
			{2621,1337},{2907,1270},{2331,598},{2987,394},{1979,394},{2045,693},
			{2069,1028}};
	private final int skeletonCoordinates[][] = {{1255,2924},{2545,4708},{4189,6585},
			{5720,622},{5649,767},{5291,312},{5256,852},{4790,976},{4648,401},
			{3628,1181},{3771,1181},{3182,2892},{3116,3033},{2803,2901},{2850,2426},
			{2005,1524},{2132,1427},{2242,1343},{2428,771},{2427,907},{2770,613},
			{2915,477},{1970,553},{2143,1048}};
	
	/** Screen width, in pixels. */
    public static final int SCREENWIDTH = 800;
    /** Screen height, in pixels. */
    public static final int SCREENHEIGHT = 600;
    /** Tile size, in pixels. */
    public static final int TILESIZE = 72;
    /** Extra tile to render */
    public static final int EXTRATILE = 2;
    
    
	/** Create a new World object. */
    public World()
    throws SlickException
    {
    	/** Constructor: Loading the map. */
        setWorldmap(new TiledMap("assets/map.tmx","assets"));
        
        /** Constructor: Creating the monster objects and adding them to monsters list.*/
        for (int i = 0; i < batCoordinates.length; i++) {
        	giantBat[i] = new Monster(batCoordinates[i][0],batCoordinates[i][1],0.20,40,0,0,"Giant Bat",giantbatimg,maincam,"passive");
        	allmonsters.add(giantBat[i]); 	}
        for (int i = 0; i < batCoordinates.length; i++) {
        	zombie[i] = new Monster(zombieCoordinates[i][0],zombieCoordinates[i][1],0.25,60,800,10,"Zombie",zombieimg,maincam,"aggressive");
        	allmonsters.add(zombie[i]); 	}
        for (int i = 0; i < banditCoordinates.length; i++) {
        	bandit[i] = new Monster(banditCoordinates[i][0],banditCoordinates[i][1],0.25,40,200,8,"Bandit",banditimg,maincam,"aggressive");
        	allmonsters.add(bandit[i]); 	}
        for (int i = 0; i < skeletonCoordinates.length; i++) {
        	skeleton[i] = new Monster(skeletonCoordinates[i][0],skeletonCoordinates[i][1],0.25,100,500,16,"Skeleton",skeletonimg,maincam,"aggressive");
        	allmonsters.add(skeleton[i]); 	}
        allmonsters.add(draelic);
        
        // Adding villagers and items into their respective lists.
        allvillagers.add(elvira);
        allvillagers.add(garth);
        allvillagers.add(prince);
        allitems.add(sword);
        allitems.add(amulet);
        allitems.add(tome);
        allitems.add(elixir);
    }

    /** Update the game state for a frame.
     * @param dir_x The player's movement in the x axis (-1, 0 or 1).
     * @param dir_y The player's movement in the y axis (-1, 0 or 1).
     * @param delta Time passed since last frame (milliseconds).
     * @param action[] Reflects the actions that are being called ([0] = Attack,
     * 			[1] = talk, [2] = run).
     */
    public void update(double dir_x, double dir_y, int delta, boolean action[])
    throws SlickException {
    	
    	// Update the player.
    	activePlayer.update(dir_x, dir_y, delta, this);
    	
    	for (Unit monster : allmonsters) {
    		monster.update(delta, this);
    	}
    	
    	for (Item item: allitems) {
    		item.update(this);
    	}
    	
    	for (Villager villager : allvillagers) {
    		villager.update(delta, this);
    	}
    	
    	if (action[0] == true) {
    		activePlayer.attack(this);
    	}
    	
    	if (action[1] == true) {
    		activePlayer.talk(this);
    	}
    	
    	if (action[2] == true)
    		activePlayer.setSpeed(activePlayer.getDefaultSpeed() * 2);
    	else 
    		activePlayer.setSpeed(activePlayer.getDefaultSpeed());
    	
    	// Update the camera.
    	maincam.update();
    }
    

    /** Render the entire screen, so it reflects the current game state.
     * @param g The Slick graphics object, used for drawing.
     */
    public void render(Graphics g)
    throws SlickException
    {
    	// Render the map.
    	getWorldmap().render(-maincam.getTiledMinX(),-maincam.getTiledMinY(),(int)maincam.getMinX()/TILESIZE,
    			(int)maincam.getMinY()/TILESIZE, RPG.SCREENWIDTH/TILESIZE + EXTRATILE, RPG.SCREENHEIGHT/TILESIZE + EXTRATILE);
    	
    	g.drawString("Inventory:" + activePlayer.getInventory(), 10, 10);
    	
    	// Render the villagers and dialogue box.
    	for (Villager v: allvillagers) {
    		v.render(g);
    		v.renderText(g,maincam);
    	}
    	
    	// Render the monsters.
    	for (Unit u:allmonsters) {
    		if (!u.isDead())
    			u.render(g);
    	}
    	
    	// Render the items.
    	for (Item i: allitems) {
    		if (!i.isTaken())
    			i.render();
    	}
    	
    	// Render the player.
    	player1.render(g, maincam);
    	
    }
    
    /** Set the world map.
     * @param worldmap The new world map.
     */
	public void setWorldmap(TiledMap worldmap) {
		this.worldmap = worldmap;
	}

    /** Get the map.
     * @return worldmap The world's map.
     */
	public TiledMap getWorldmap() {
		return worldmap;
	}
	
	 /** Get the active unit.
     * @return activePlayer The active player.
     */
	public Player getActivePlayer() {
		return activePlayer;
	}
	
	/** Get the camera.
     * @return maincam The camera.
     */
    public Camera getCamera(){
    	return maincam;
    }
}
