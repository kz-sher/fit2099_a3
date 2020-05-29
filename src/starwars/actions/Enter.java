package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.EnterableInterface;


/**
 * <code>SWAffordance</code> that lets a <code>SWActor</code> enter an <code>EnterableInterface</code>.
 * 
 * @author Zhiwei
 */
public class Enter extends SWAffordance implements SWActionInterface {
	
	/**
	 * Constructor for the <code>Enter</code> Class. Will initialize the message renderer and target
	 * 
	 * @param theTarget 
	 * 					a <code>SWEntityInterface</code> that can be entered into
	 * @param m the message renderer to display messages
	 */
	public Enter(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 * Returns if or not this <code>Enter</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if the <code>SWActor</code> has force
	 *  
	 * @author 	Zhiwei
	 * @param 	a 
	 * 				the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> has the force capability, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a.hasCapability(Capability.FORCE);
	}

	/**
	 * Perform the <code>Enter</code> affordance by checking if the target can be
	 * entered from. If it can, the entrance of the corresponding EnterableInterface
	 * is used as the entry point
	 * 
	 * @author 	Zhiwei
	 * @param 	a the <code>SWActor</code> that is entering the target
	 */
	@Override
	public void act(SWActor a) {
		//can only enter a Sandcrawler right now. Can be adapted for more classes
		if(this.target instanceof EnterableInterface) {
			EnterableInterface enterable = (EnterableInterface) target;
			SWWorld world = enterable.getWorld();
			enterable.setWorldGrid(world.getGrid());
			world.setGrid(enterable.getGrid());
			
			a.say("Entering " + this.target.getShortDescription());
			
			SWWorld.getEntitymanager().setLocation(a, enterable.getEntrance());
			a.resetMoveCommands(enterable.getEntrance());

		}
	}

	@Override
	/* (non-Javadoc)
	 * @see edu.monash.fit2024.simulator.ActionInterface#getDescription()
	 */
	public String getDescription() {
		return "enter " + this.target.getShortDescription();
	}

}
