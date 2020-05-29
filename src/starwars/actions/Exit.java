package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWWorld;
import starwars.entities.EnterableInterface;
import starwars.entities.ExitableInterface;


/**
 * <code>SWAffordance</code> that lets a <code>SWActor</code> exit an <code>EnterableInterface</code>.
 * 
 * @author Zhiwei
 */
public class Exit extends SWAffordance implements SWActionInterface {
	
	/**
	 * Constructor for the <code>Exit</code> Class. Will initialize the message renderer and target
	 * 
	 * @param theTarget a <code>SWEntityInterface</code> that is the gateway to exit through
	 * @param m the message renderer to display messages
	 */
	public Exit(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
	}

	/**
	 * Returns if or not this <code>Exit</code> can be performed by the <code>SWActor a</code>.
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
		return a.hasCapability(Capability.FORCE);
	}

	/**
	 * Perform the <code>Exit</code> affordance by checking if the target can be
	 * exited from. If it can, the location of the corresponding EnterableInterface
	 * is used as the exit point * 
	 * 
	 * @author 	Zhiwei
	 * @param 	a the <code>SWActor</code> that is leaving via the target
	 */
	@Override
	public void act(SWActor a) {
		if(this.target instanceof ExitableInterface) {
			
			ExitableInterface exit = (ExitableInterface) target;
			//get the object that is the exit point
			EnterableInterface e = exit.getEnterable();
			SWWorld world = e.getWorld();
			world.setGrid(e.getWorldGrid());
			
			a.say("Exiting " + e.getShortDescription() + " now");
			
			SWWorld.getEntitymanager().setLocation(a, world.getEntityManager().whereIs(e));
			a.resetMoveCommands(world.getEntityManager().whereIs(e));

		}	
	}

	@Override
	/* (non-Javadoc)
	 * @see edu.monash.fit2024.simulator.ActionInterface#getDescription()
	 */
	public String getDescription() {
		return "exit via " + this.target.getShortDescription();
	}

}
