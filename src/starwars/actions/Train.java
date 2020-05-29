/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * @author kz
 *
 */
public class Train extends SWAffordance {
	
	private SWActor student;
	/**
	 * @param m
	 */
	public Train(SWEntityInterface theTarget, MessageRenderer m, SWActor student) {
		// TODO Auto-generated constructor stub
		super(theTarget, m);
		priority = 1;
		this.student = student;
	}
	
	public boolean isMentor(SWActor a){
		return a.getSymbol()=="B";
	}

	/* (non-Javadoc)
	 * @see starwars.SWActionInterface#canDo(starwars.SWActor)
	 */
	@Override
	public boolean canDo(SWActor a) {
		// TODO Auto-generated method stub
		return a == this.student;
	}

	/* (non-Javadoc)
	 * @see starwars.SWAction#act(starwars.SWActor)
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		a.incrementForceLevel();
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "train with " + this.target.getShortDescription();
	}

}
