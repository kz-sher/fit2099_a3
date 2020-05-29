package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWEntity;
import starwars.actions.Exit;


/**
 * A <code>SWEntity</code> that can be used as an Exit for <code>EnterableInterface</code>s
 * 
 * @author Zhiwei
 */
public class Door extends SWEntity implements ExitableInterface{
	
	//the entity that the Door is located in
	private EnterableInterface e;
	
	/**
	 * A door. Every door is associated with an EnterableInterface. The door implements the ExitableInterface.
	 * It provides an exit for every EnterableInterface
	 * 
	 * @param m
	 *            <code>MessageRenderer</code> to display messages.
	 * @param s
	 *            the <code>EnterableInterface</code> to which this
	 *            <code>Door</code> belongs to
	 */
	public Door(MessageRenderer m, EnterableInterface s) {
		super(m);
		this.setShortDescription("the door");
		this.setLongDescription("just a plain old door");
		this.setSymbol("D");
		this.hitpoints = 100;
			
		e = s;
		this.addAffordance(new Exit(this, m));//add the Take affordance so that the blaster can be picked up
	}
	
	/**
	 * This method returns the <code>EnterableInterface</code> associated with this <code>Door</code> (the Door acts as an Exit)
	 * 
	 * @return <code>EnterableInterface</code>
	 * 			The EnterableInterface associated with this Door
	 */	
	public EnterableInterface getEnterable() {
		return e;
	}

}
