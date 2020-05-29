/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActionInterface;
import starwars.SWActor;

/**
 * @author zhiwei
 *
 */
public class LoseHealth extends SWAction implements SWActionInterface {
	
	int amount;

	/**
	 * Constructor for the LoseHealth class. 
	 * An action that allows for a target to lose health due to natural causes
	 * 
	 * @param amount
	 * 			An integer specifying how much damage should be taken
	 * @param m
	 * 			A reference to the message renderer for outputting messages
	 */
	public LoseHealth(int amount, MessageRenderer m) {
		super(m);
		this.amount = amount;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Returns if or not a <code>SWActor a</code> can perform a <code>Move</code> command.
	 * <p>
	 * This method returns true if and only if <code>a</code> is not dead.
	 * <p>
	 * We assume that actors don't get movement commands attached to them unless they can
	 * in fact move in the appropriate direction.  If this changes, then this method will
	 * need to be altered or overridden.
	 * 
	 * @author 	ram
	 * @param 	a the <code>SWActor</code> doing the moving
	 * @return 	true if and only if <code>a</code> is not dead, false otherwise.
	 * @see 	{@link starwars.SWActor#isDead()}
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.getHitpoints() > 0;
	}

	/**
	 * Perform the <code>LoseHealth</code> action.
	 * If the actor is still alive, the actor takes damage
	 * 
	 * @param 	a the <code>SWActor</code> who takes damage
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		a.say(a.getShortDescription() + " took " + Integer.toString(this.amount) + " damage.");
		a.takeDamage(this.amount);

	}
	
	/** 
	 * Returns a string containing the description of this action
	 * 
	 * @return A string containing the description of this action
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "lose " + Integer.toString(this.amount) + " health." ;
	}
	
	/**
	 *Returns the time taken to perform this <code>LoseHealth</code> action.
	 *
	 *@return the duration of the <code>LoseHealth</code> action. Currently hard coded to return 1
	 */
	public int getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}



}
