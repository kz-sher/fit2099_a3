package starwars.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.Grenade;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object.
 * 
 * @author kz
 */
public class Leave extends SWAffordance {

	/**
	 * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and 
	 * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * 
	 * @param theTarget a <code>SWEntity</code> that is being held
	 * @param m the message renderer to display messages
	 */
	public Leave(SWEntityInterface theTarget, MessageRenderer m) {
		super(theTarget, m);
		priority = 1;
	}


	/**
	 * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is carrying any item already.
	 *  
	 * @author 	kz
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is holding an item, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a.getItemCarried()!=null;
	}

	/**
	 * Perform the <code>Leave</code> action by setting null to the item carried by the <code>SWActor</code>.
	 * <p>
	 * This method should only be called if the <code>SWActor a</code> is alive.
	 * 
	 * @author 	kz
	 * @param 	a the <code>SWActor</code> that is leaving the target
	 */
	@Override
	public void act(SWActor a) {
		SWEntityInterface theItem = a.getItemCarried();
		a.setItemCarried(null);
		SWLocation location = SWAction.getEntitymanager().whereIs(a);
		SWAction.getEntitymanager().setLocation(theItem, location);//add the target to the entity manager since it's now left by the SWActor
			
		//remove the leave affordance and add the take affordance 
		target.removeAffordance(this);
		target.addAffordance(new Take(theItem,this.messageRenderer));
		
		// if item left is grenade, remove the throw affordance
		if(theItem instanceof Grenade){
            for(Affordance af: target.getAffordances()){
                    if(af instanceof Throw){
                            target.removeAffordance(af);
                    }
            }
		}
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author kz
	 * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
	 */
	@Override
	public String getDescription() {
		return "leave " + target.getShortDescription();
	}

}
