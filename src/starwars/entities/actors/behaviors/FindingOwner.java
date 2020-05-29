/**
 * 
 */
package starwars.entities.actors.behaviors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.space.Location;
import starwars.SWActor;
import starwars.SWLocation;
import starwars.SWWorld;
import starwars.entities.actors.Droid;

/**
 * @author zhiwei
 *
 */
public class FindingOwner {
	
	/**
	 * This method returns where the Droid should go depending on whether its owner is next to him/in neighbouring locations/far away
	 * If the Droid's owner is far next to the Droid, null is returned.
	 * If the Droid's owner is in a neighbouring location to the Droid, the direction to that location is returned;
	 * If the Droid's owner is far away, the method checks whether the Droid has been moving towards a certain direction
	 * - if it has, it continues to move in that direction if possible
	 * 		- if not possible, it finds a random direction to move towards
	 * - if it hasn't been moving, it picks a random direction to move towards
	 * 
	 * @param droid
	 * 			A reference to the <code>Droid</code> that needs to move
	 * @param world
	 * 			A reference to the <code>SWWorld</code> of the <code>Droid</code>
	 * @return <code>Direction</code>
	 * 			The correct direction that the <code>Droid</code> should be moving towards
	 */	
	
	public static Direction getDirection(Droid droid, SWWorld world) {
		SWActor owner = droid.getOwner();
		
		SWLocation droidLocation = world.getEntityManager().whereIs(droid);
		SWLocation ownerLocation = world.getEntityManager().whereIs(owner);
		
		//if droid is at the owner's location
		if (droidLocation == ownerLocation) {
			droid.resetPreviousDirection();
			return null;
		}

		//if owner is right next to the droid
		for(Direction direction : Grid.CompassBearing.values()) {
			Location neighbour = droidLocation.getNeighbour(direction);
			
			if (neighbour == ownerLocation) {
				droid.resetPreviousDirection();
				return direction;
			}		
		}
		
		//if droid was moving in a direction before and can continue moving in that direction, continue in that direction
		if (droid.getPreviousDirection() != null && SWWorld.getEntitymanager().seesExit(droid, droid.getPreviousDirection())) {
			return droid.getPreviousDirection();
		}
		
		//choose a random direction
		ArrayList<Direction> possibledirections = new ArrayList<Direction>();

		// build a list of available directions
		for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
			if (SWWorld.getEntitymanager().seesExit(droid, d)) {
				possibledirections.add(d);
			}
		}

		Direction heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
		
		droid.setPreviousDirection(heading);
		return heading;

		
	}

}
