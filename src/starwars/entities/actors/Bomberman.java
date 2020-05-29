package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.actions.SpecialExit;
import starwars.entities.EnterableInterface;
import starwars.entities.ExitableInterface;
import starwars.entities.Grenade;

import java.util.ArrayList;
import java.util.Random;


/**
 * A boss <code>SWActor</code>.  Its <code>act()</code> method
 * randomly creates and explodes grenades and makes the SWActor move randomly
 * 
 * @author Zhiwei
 */

public class Bomberman extends SWActor implements ExitableInterface {
	
	private EnterableInterface e;
	
	/**
	 * Constructor for the <code>Bomberman</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the team which the Boss is in</li>
	 * 	<li>Initialize its hitpoints</li>
	 * 	<li>Initialize the message renderer for the <code>Player</code></li>
	 * 	<li>Initialize the world for this <code>Player</code></li>
	 * 	<li>Initialize the EnterableInterface associated with this <code>Bomberman</code></li>
	 * </ul>
	 * 
	 * @param team the <code>Team</code> to which the this <code>Player</code> belongs to
	 * @param hitpoints the hit points of this <code>Player</code> to get started with
	 * @param m <code>MessageRenderer</code> to display messages.
	 * @param world the <code>SWWorld</code> world to which this <code>Player</code> belongs to
	 * @param e the <code>EnterableInterface</code> associated with this Bomberman
	 * 
	 */
	public Bomberman(Team team, int hitpoints, MessageRenderer m, SWWorld world, EnterableInterface e) {
		super(team, hitpoints, m, world);
		this.e = e;
		
		this.setShortDescription("Bomberman");
		this.setLongDescription("Bomberman - he's going to bomb you!");
		this.setSymbol("B");
		
			
		this.addAffordance(new SpecialExit(this, m));//add the Take affordance so that the blaster can be picked up
	}

	/**
	 * This method first creates a grenade and explodes it at the location of the Bomberman,
	 * then moves the Bomberman in a random direction
	 */
	public void act() {
		// TODO Auto-generated method stub
		Grenade grenade = new Grenade(this.messageRenderer);
        SWAction.getEntitymanager().setLocation(grenade, SWWorld.getEntitymanager().whereIs(this));

        grenade.trigger(this, SWWorld.getEntitymanager().whereIs(this));
        
        
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();

		// build a list of available directions
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(this, d)) {
				possibledirections.add(d);
			}
		}

		Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
		say(getShortDescription() + "is heading " + heading + " next.");
		Move myMove = new Move(heading, messageRenderer, world);

		scheduler.schedule(myMove, this, 1);
        
	}

	/**
	 * This method returns the <code>EnterableInterface</code> associated with this <code>Bomberman</code> (the Bomberman acts as an Exit)
	 * 
	 * @return <code>EnterableInterface</code>
	 * 			The EnterableInterface associated with this Bomberman
	 */	
	@Override
	public EnterableInterface getEnterable() {
		// TODO Auto-generated method stub
		return e;
	}

}
