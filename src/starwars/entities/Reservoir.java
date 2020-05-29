package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.actions.Attack;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 * 
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "W"</li>
	 *	<li>Set the hit point of this <code>Reservoir</code> to 40</li>
	 * </ul>
	 * 
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m) {
		super(m);
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);
		
		SWAffordance attack = new Attack(this, m);
		this.addAffordance(attack); // add the attack affordance to allow it to be attackable
		
		this.setLongDescription("a water reservoir, full of cool, clear, refreshing water"); 
		this.setShortDescription("a water reservoir");
		this.setSymbol("W");
		this.hitpoints = 40; // initialize reservoirs's hitpoints to 40
	}

	@Override 
	public String getShortDescription() {
		return shortDescription;
	}
	
	public String getLongDescription() {
		return longDescription;
	}
	
	/**
	 * This method will reduce <code>Reservoir</code>'s <code>hitpoints</code>
	 * and then change the description and symbol when its hitpoints are reduced to a certain amount
	 * <p>
	 *  if the <code>Reservoir</code>'s <code>hitpoints</code> after taking the damage is zero or less, it changes this <code>Reservoir</code>'s <code>longDescription</code> to
	 * "the wreckage of a water reservoir, surrounded by slightly damp soil", this <code>Reservoir</code>'s <code>shortDescription</code> to
	 * "the wreckage of a water reservoir" and its symbol to "X".
	 * <p>
	 *  if the <code>Reservoir</code>'s <code>hitpoints</code> after taking the damage is twenty or less but more than zero, it changes this <code>Reservoir</code>'s <code>longDescription</code> to
	 * "a damaged water reservoir, leaking slowly", this <code>Reservoir</code>'s <code>shortDescription</code> to
	 * "a damaged water reservoir" and its symbol to "V".
	 * <p>
	 * 
	 * @param 	damage - the amount of <code>hitpoints</code> to be reduced
	 */
	@Override
	public void takeDamage(int damage){
		super.takeDamage(damage);
		
		if (this.getHitpoints() <= 0) {
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("the wreckage of a water reservoir, surrounded by slightly damp soil");
			this.setSymbol("X");
		}
		else if (this.getHitpoints() < 20) {
			this.setShortDescription("a damaged water reservoir");
			this.setLongDescription("a damaged water reservoir, leaking slowly");
			this.setSymbol("V");
		}
	}
	
}
