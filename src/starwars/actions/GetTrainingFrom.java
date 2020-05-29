/**
 * 
 */
package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;

/**
 * @author kz
 * <code>GetTrainingFrom</code> class allows Person A to train Person B if B is the student of A.
 */
public class GetTrainingFrom extends SWAffordance {
	
	/**The field used to check whether <code>SWActor</code> is a student of the affordance owner (mentor)*/
	private SWActor student;
	/**
	 * Constructor for the <code>GetTrainingFrom</code> Class. Will initialize the message renderer, the target, 
	 * student and set the priority of this <code>Action</code> to 1 (lowest priority is 0).
	 * @param theTarget is not used in this class
	 * @param m the message renderer to display messages
	 * @param student the <code>SWActor</code> that is being trained 
	 */
	public GetTrainingFrom(SWEntityInterface theTarget, MessageRenderer m, SWActor student) {
		// TODO Auto-generated constructor stub
		super(theTarget, m);
		priority = 1;
		this.student = student;
	}

	/**
	 * Returns if or not this <code>GetTrainingFrom</code> can be performed by the <code>SWActor a</code>.
	 * <p>
	 * This method returns true if and only if <code>a</code> is the student of this mentor already.
	 *  
	 * @author 	kz
	 * @param 	a the <code>SWActor</code> being queried
	 * @return 	true if the <code>SWActor</code> is the student, false otherwise
	 */
	@Override
	public boolean canDo(SWActor a) {
		return a == this.student;
	}

	/**
	 * Perform the <code>GetTrainingFrom</code> action by incrementing the force level of the <code>SWActor</code>
	 * <p>
	 * 
	 * @author 	kz
	 * @param 	a the <code>SWActor</code> that is the student
	 */
	@Override
	public void act(SWActor a) {
		// TODO Auto-generated method stub
		int prevForceLevel = a.getForceLevel();
		a.incrementForceLevel();
		int currForceLevel = a.getForceLevel();
		a.say("\t"+a.getShortDescription()+" says: Force Level Up! Level " + prevForceLevel + " to Level " + currForceLevel + ".");
	}

	/**
	 * A String describing what this action will do, suitable for display in a user interface
	 * 
	 * @author kz
	 * @return String comprising "train with " and the short description of the target of this <code>GetTrainingFrom</code>
	 */
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "train with " + this.target.getShortDescription();
	}

}
