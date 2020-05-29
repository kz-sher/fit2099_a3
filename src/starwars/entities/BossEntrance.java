package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Enter;
import starwars.entities.actors.Bomberman;
import starwars.entities.actors.behaviors.Patrol;


/**
 * Entrance to a Boss level
 * 
 * @author Zhiwei
 */
public class BossEntrance extends SWEntity implements EnterableInterface {
	
	//Grid referring to the internals of the boss level
	private SWGrid myGrid;
	
	//a reference to the entrance/exit of the Boss level (coordinates 0,0)
	private SWLocation entrance;
	
	//storing the world grid that is temporarily replaced 
	private SWGrid worldGrid;

	private SWWorld world;
	
	/**
	 * Constructor that creates an entrance to the Boss level.
	 * 
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>BossEntrance</code> belongs to
	 * 
	 */	
	public BossEntrance(MessageRenderer m, SWWorld world) {
		super(m);
		this.world = world;
		
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		//create a Grid of size 4*4
		myGrid = new SWGrid(4,4,factory);
		
		this.addAffordance(new Enter(this, m));
		
		this.setShortDescription("Boss Dungeon");
		this.setLongDescription("The entrance to the Boss Dungeon.. do you have what it takes to get out?");
		
		SWLocation loc;
		
		//set the description for each grid
		for (int row=0; row < myGrid.getHeight(); row++) {
			for (int col=0; col < myGrid.getWidth(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Boss Dungeon (" + col + ", " + row + ")");
				loc.setShortDescription("Boss Dungeon (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		//set the entrance
		entrance = myGrid.getLocationByCoordinates(0, 0);
		
		//set the grid for the outside world
		this.setWorldGrid(world.getGrid());
		
		//place a door at the entrance
		SWWorld.getEntitymanager().setLocation(new Bomberman(Team.EVIL, 100, m, world, this), entrance);
		// TODO Auto-generated constructor stub
	}

	/**
	 * This method returns a <code>SWGrid</code> of the <code>BossEntrance</code>'s insides
	 * 
	 * @return <code>SWGrid</code>
	 * 			The Grid representing the internals of the Boss dungeon.
	 */	
	@Override
	public SWGrid getGrid() {
		// TODO Auto-generated method stub
		return this.myGrid;
	}

	/**
	 * This method sets the <code>SWGrid</code> which the <code>BossEntrance</code> is in 
	 * 
	 * @param g 
	 * 			The <code>SWGrid</code> where the <code>BossEntrance</code> is in
	 */
	@Override
	public void setWorldGrid(SWGrid g) {
		// TODO Auto-generated method stub
		this.worldGrid = g;

	}
	
	/**
	 * This method returns the <code>SWGrid</code> where the <code>BossEntrance</code> is
	 * 
	 * @return <code>SWGrid</code>
	 * 			The Grid representing the outside world where the BossEntrance is in
	 */	
	@Override
	public SWGrid getWorldGrid() {
		// TODO Auto-generated method stub
		return this.worldGrid;
	}

	/**
	 * This method returns the <code>SWWorld</code> where the <code>BossEntrance</code> is in
	 * 
	 * @return <code>SWWorld</code>
	 * 			The SWWorld where the BossEntrance is in.
	 */	
	@Override
	public SWWorld getWorld() {
		// TODO Auto-generated method stub
		return this.world;
	}

	@Override
	
	
	/**
	 * This method returns a reference to the <code>BossDungeon</code>'s entrance (in this case it is just the location at coordinate 0,0)
	 * 
	 * @return <code>SWLocation</code>
	 * 			A reference to the Boss Dungeon entrance
	 */
	public SWLocation getEntrance() {
		// TODO Auto-generated method stub
		return entrance;
	}

}
