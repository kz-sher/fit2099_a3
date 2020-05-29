package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWEntityInterface;

/**
 * <code>SWAffordance</code> that lets a <code>SWActor</code> exit an <code>EnterableInterface</code>.
 * Similar to exit, but the canDo method is different.
 * 
 * @author Zhiwei
 */
public class SpecialExit extends Exit {

	/**
	 * Constructor for the <code>Exit</code> Class. Will initialize the message renderer and target
	 * 
	 * @param theTarget a <code>SWEntityInterface</code> that is the gateway to exit through
	 * @param m the message renderer to display messages
	 */
	public SpecialExit(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Returns if or not this <code>Exit</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if the <code>SWActor</code> that acts as the gateway to the outside world is dead
	 * This is useful for Boss levels, where the Boss has to be dead before you can escape
	 *  
	 * @author 	Zhiwei
	 * @param 	a 
	 * 				the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> target is dead, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		if (this.target instanceof SWActor) {
			SWActor boss = (SWActor) target;
			return boss.isDead();
		} else {
			return true;
		}
	}

}
