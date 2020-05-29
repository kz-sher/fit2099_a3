/**
 * 
 */
package starwars.entities.actors;

import starwars.actions.LoseHealth;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.FindingOwner;

/**
 * @author zhiwei
 *
 */
public class Droid extends SWActor {
	
	private SWActor owner;
	private Direction previousDirection = null;
	
	/**
	 * Creates a Droid. Droids that have no owner will not move.
	 * Droids that have owners next to them will move towards their owners
	 * Droids that have owners on the same spot as them will not move.
	 * Droids that have an owner but the owner is not next to them or at the same 
	 * place as them will pick a random direction and move in said direction
	 * until it cannot move there anymore or until it finds its owner.
	 * If it cannot move in the specified direction anymore, it will pick another random direction
	 * to start moving again.
	 * Droids are in the same team as their owners
	 * 
	 * @param name
	 *            this droid's name. Used in displaying descriptions.
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param world
	 *            the <code>SWWorld</code> world to which this
	 *            <code>TuskenRaider</code> belongs to
	 * @param owner
	 * 			  the <code>SWActor</code> that owns this <code>Droid</code>
	 * 
	 */
	public Droid (String name, int hitpoints, MessageRenderer m, SWWorld world, SWActor owner) {
		super(owner.getTeam(), hitpoints, m, world);
		this.owner = owner;
		this.setShortDescription(name + " the Droid");
		this.setLongDescription(name + " the Droid, a soulless being created for the sole purpose of serving its master");

	}
	
	/**
	 * same as the constructor above, but without the owner input
	 * @param name
	 * @param hitpoints
	 * @param m
	 * @param world
	 */
	public Droid (String name, int hitpoints, MessageRenderer m, SWWorld world) {
		super(Team.NEUTRAL, hitpoints, m, world);
		this.owner = null;
		this.setShortDescription(name + " the Droid");
		this.setLongDescription(name + " the Droid, a soulless being created for the sole purpose of serving its master");

	}
	
	
	/**
	 * This method returns a reference to the <code>Droid</code>'s owner
	 * 
	 * @return <code>SWActor</code>
	 * 			A reference to the Droid's owner
	 */
	public SWActor getOwner() {
		return this.owner;
	}
	
	/**
	 * This method sets the <code>Droid</code>'s owner to be null (no owner)
	 * 
	 * @return <code>SWActor</code>
	 * 			A reference to the Droid's owner
	 */
	public void clearOwner() {
		this.owner = null;
	}
	
	/**
	 * This method returns the <code>Direction</code> which the <code>Droid</code> was moving towards before
	 * 
	 * @return <code>Direction</code>
	 * 			The previous direction that the Droid was moving towards.
	 */
	public Direction getPreviousDirection() {
		return previousDirection;
		
	}
	
	
	/**
	 * This method resets the <code>Direction</code> which the <code>Droid</code> was moving towards
	 * 
	 */
	public void resetPreviousDirection() {
		this.previousDirection = null;
	}
	
	/**
	 * This method sets the <code>Direction</code> which the <code>Droid</code> is moving towards 
	 * 
	 * @param <code>Direction</code> direction
	 * 		The previous direction moved by the Droid
	 */
	public void setPreviousDirection(Direction direction) {
		this.previousDirection = direction;
	}
	
	/**
	 * This method returns a <code>boolean</code> depending on whether the Droid is in Badlands
	 * 
	 * @return <code>boolean</code>
	 * 			Whether the Droid is in Badlands
	 */	
	public boolean inBadlands() {
		SWLocation location = this.world.getEntityManager().whereIs(this);
		
		if (location.getSymbol() == 'b') {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method returns false, since a Droid cannot die
	 * 
	 * @return <code>boolean</code>
	 * 			Whether the Droid is dead (always false)
	 */	

	public boolean isDead() {
		return false;
	}

	/* (non-Javadoc)
	 * @see edu.monash.fit2099.simulator.matter.Actor#act()
	 */
	@Override
	public void act() {
		// TODO Auto-generated method stub
		
		//check if droid is in badlands
		if (this.inBadlands() && this.getHitpoints() > 0) {			
			say(getShortDescription() + " is in Badlands");
			LoseHealth loseHealth = new LoseHealth(1, messageRenderer);
			scheduler.schedule(loseHealth, this, 1);
		}
		
		//get the direction of the droid
		if (owner != null && this.getHitpoints() > 0){
			Direction direction = FindingOwner.getDirection(this, this.world);
			if (direction == null) {
				say(getShortDescription() + " is heading nowhere");
			} else {
				say(getShortDescription() + " is heading " + direction + " next.");
			}
			Move myMove = new Move(direction, messageRenderer, this.world);

			scheduler.schedule(myMove, this, 1);
		}
		
		

	}


}
