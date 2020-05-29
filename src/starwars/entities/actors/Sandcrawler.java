package starwars.entities.actors;

import java.util.List;

import edu.monash.fit2099.gridworld.GridRenderer;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Enter;
import starwars.actions.GetTrainingFrom;
import starwars.actions.Move;
import starwars.entities.Door;
import starwars.entities.EnterableInterface;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;
import starwars.swinterfaces.SWGridTextInterface;


/**
 * A <code>SWActor</code> that goes around the world looking for <code>Droid</code>s to eat
 * 
 * @author Zhiwei
 */
public class Sandcrawler extends SWActor implements EnterableInterface{
	
	//Grid referring to the internals of the Sandcrawler
	private SWGrid myGrid;
	
	//a reference to the entrance/exit of the sandcrawler (coordinates 0,0)
	private SWLocation entrance;
	
	//storing the world grid that is temporarily replaced 
	private SWGrid worldGrid;
	
	//the Sandcrawler's path of movement
	private Patrol path;
	
	//boolean to check whether the sandcrawler should move this turn
	private boolean moveNow;

	/**
	 *A sandcrawler moves in the same way as Ben Kenobi, but only moves every second turn
	 *If a sandcrawler finds a droid in its location, the droid is taken inside the sandcrawler.
	 *A sandcrawler has a door that can be entered by any actor with force ability that is in the same
	 *location as the sandcrawler. When the actor enters the sandcrawler, the actor moves to the interior
	 *of the sandcrawler, which is a grid of four locations.
	 *All the droids taken by the sandcrawler are located at the Sandcrawler's entrance

	 *One of the insides of the Sandcrawler has a door that can be user by any actor with force ability
	 *Exiting the door results in the actor being returned to the location in which the sandcrawler is located.
	 * 
	 * @param team
	 *            this Sandcrawler's team.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>Sandcrawler</code> belongs to
	 * @param moves
	 * 			  the <code>Direction</code>s that represent the <code>Sandcrawler's</code> pattern of movement
	 * 
	 */	
	public Sandcrawler(Team team, int hitpoints, MessageRenderer m, SWWorld world, Direction [] moves) {

		super(team, hitpoints, m, world);
		
		//path describes the Sandcrawler's movement
		path = new Patrol(moves);
		moveNow = false;
		
		SWLocation.SWLocationMaker factory = SWLocation.getMaker();
		//create a Grid of size 2*2
		myGrid = new SWGrid(2,2,factory);
		
		//Make this Sandcrawler an Enterable
		this.addAffordance(new Enter(this, m));
		
		this.setShortDescription("Mr Sandcrawler");
		this.setLongDescription("Mr Sandcrawler just wants to collect shiny bots");
		
		SWLocation loc;
		
		//set the description for each grid
		for (int row=0; row < myGrid.getHeight(); row++) {
			for (int col=0; col < myGrid.getWidth(); col++) {
				loc = myGrid.getLocationByCoordinates(col, row);
				loc.setLongDescription("Sandcrawler (" + col + ", " + row + ")");
				loc.setShortDescription("Sandcrawler (" + col + ", " + row + ")");
				loc.setSymbol('.');				
			}
		}
		
		//set the entrance
		entrance = myGrid.getLocationByCoordinates(0, 0);
		
		//set the grid for the outside world
		this.setWorldGrid(world.getGrid());
		
		//place a door at the entrance
		SWWorld.getEntitymanager().setLocation(new Door(m, this), entrance);
		
	}
	
	/**
	 * This method returns a reference to the <code>Sandcrawler</code>'s entrance
	 * 
	 * @return <code>SWLocation</code>
	 * 			A reference to the Sandcrawler's entrance
	 */
	public SWLocation getEntrance() {
		return entrance;
	}

	/* (non-Javadoc)
	 * @see edu.monash.fit2099.simulator.matter.Actor#act()
	 */
	@Override
	public void act() {
		
		//get the current location of the Sandcrawler and all of the entities in its location
		SWLocation sandcrawlerLocation = this.world.getEntityManager().whereIs(this);
		List<SWEntityInterface> entities = this.world.getEntityManager().contents(sandcrawlerLocation);
		
		//if there is a droid at the Sandcrawler's location, take it into the inside of the Sandcrawler (grid)
		for (SWEntityInterface e : entities) {
			//can be changed to adopt more classes
			if(e instanceof Droid) {
				Droid droid = (Droid) e;
				this.say(this.getShortDescription() + " has taken " + droid.getShortDescription());
				SWWorld.getEntitymanager().setLocation(e, entrance);
				droid.resetMoveCommands(entrance);
				//once a droid is taken, it has no owner and it is a neutral entity
				droid.clearOwner();
				droid.setTeam(Team.NEUTRAL);
			}
			
		}
		
		//if the sandcrawler is supposed to move at this turn, we move, else we just set the boolean to let the sandcrawler move the next turn		
		if (moveNow) {
			//use the same path as Ben
			Direction newdirection = path.getNext();
			say(getShortDescription() + " moves " + newdirection);
			Move myMove = new Move(newdirection, messageRenderer, world);
			moveNow = false;
			scheduler.schedule(myMove, this, 1);
		} else {
			moveNow = true;
		}
		

		
	}
	
	/**
	 * This method returns a <code>SWGrid</code> of the <code>Sandcrawler</code>'s insides
	 * 
	 * @return <code>SWGrid</code>
	 * 			The Grid representing the internals of the Sandcrawler.
	 */	
	public SWGrid getGrid() {
		return myGrid;
	}

	/**
	 * This method sets the <code>SWGrid</code> which the <code>Sandcrawler</code> is in 
	 * 
	 * @param g 
	 * 			The <code>SWGrid</code> where the <code>Sandcrawler</code> is in
	 */
	//setting the 'world' grid, which is the main grid of the outside world
	public void setWorldGrid(SWGrid g) {
		worldGrid = g;
	}
	
	/**
	 * This method returns the <code>SWGrid</code> where the <code>Sandcrawler</code> is
	 * 
	 * @return <code>SWGrid</code>
	 * 			The Grid representing the outside world where the Sandcrawler is in
	 */	
	//getting the 'world' grid, which is the grid of the outside world
	public SWGrid getWorldGrid() {
		return worldGrid;
	}
	
	/**
	 * This method returns the <code>SWWorld</code> where the <code>Sandcrawler</code> is in
	 * 
	 * @return <code>SWWorld</code>
	 * 			The SWWorld where the Sandcrawler is in.
	 */	
	public SWWorld getWorld() {
		return this.world;
	}
	

}
