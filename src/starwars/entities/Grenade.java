package starwars.entities;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWEntity;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.actions.Take;

/**
 * An entity that has the <code>EXPLOSIVE</code> attribute
 *
 * It will take damage and the explosion will be triggered when its hitpoints are less than or equal to zero.
 *
 *  @author kz
 *  @see {@link starwars.actions.Take}
 */

public class Grenade extends SWEntity implements ExplosiveInterface{

        /**
         * Constructor for the <code>Grenade</code> class. This constructor will,
         * <ul>
         *      <li>Initialize the message renderer for the <code>Grenade</code></li>
         *      <li>Set the short description of this <code>Grenade</code>
         *      <li>Set the long description of this <code>Grenade</code>
         *      <li>Set the hit point of this <code>Grenade</code>
         *      <li>Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken</li>
         *      <li>Add a <code>EXPLOSIVE</code> capability to this <code>Grenade</code> so it can be triggered when it is thrown</li>
         * </ul>
         *
         * @param m <code>MessageRenderer</code> to display messages.
         *
         * @see {@link starwars.actions.Take}
         * @see {@link starwars.Capability}
         */
        public Grenade(MessageRenderer m) {
                super(m);

                this.shortDescription = "a grenade";
                this.longDescription = "a grenade. Baaaa...Boommm!";
                this.hitpoints = 1; // initialize grenade's hitpoints to 1

                this.addAffordance(new Take(this, m));//add the take affordance so that the Grenade can be taken by SWActors
        }
        
        /**
         * This method will trigger the grenade to explode. 
         * <p>
         * It will give damage to those entities:
         * 	- 20 for the same location
         * 	- 10 damage for one-step away from the thrower
         * 	- 5 damage for two-step away from the thrower
         * <p>
         * The thrower will not get damaged by the grenade.
         * 
         * @param a - the thrower
         * @param actorLocation - the location of the thrower or the grenade
         */
        public void trigger(SWEntityInterface a, SWLocation actorLocation){
                EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();

                em.remove(this); // clear the grenade


                ArrayList<SWLocation> neighbours = new ArrayList<SWLocation>(); // list that stores the locations of "one-step" and "thrower"
                ArrayList<SWLocation> neighboursOfNeighbour = new ArrayList<SWLocation>(); // list that stores locations of "two-step"

                // add the location of "thrower" to the first list and explode to that location
                neighbours.add(actorLocation);
                explode(actorLocation, 20, a);

                // for every direction, get the neighbours' location and explode to their location
                for (Grid.CompassBearing d : Grid.CompassBearing.values()) {

                        SWLocation neighbourLocation = (SWLocation) actorLocation.getNeighbour(d);
                        neighbours.add(neighbourLocation);
                        explode(neighbourLocation, 10, a);

                }

                // for every location in the first list, find their neighbours and explode to their neighbours' location
                neighbours.forEach((neighbourLocation) -> {
                		if(neighbourLocation == null) {
                			return;
                		}

                        for (Grid.CompassBearing d2 : Grid.CompassBearing.values()) {

                                SWLocation neighbourLocation2 = (SWLocation) neighbourLocation.getNeighbour(d2);

                                // valid location if the location is not in either the first or second list
                                if(!(neighbours.contains(neighbourLocation2) || neighboursOfNeighbour.contains(neighbourLocation2))) {
                                        neighboursOfNeighbour.add(neighbourLocation2);
                                        explode(neighbourLocation2, 5, a);
                                }

                        }

                });


        }

        /**
         * This method will give damage to entities that are located at the same place except the thrower
         * 
         * @param neighbourLocation - one of the locations that are in the coverage of the explosion
         * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
         * @param a - the thrower
         */
        public void explode(SWLocation neighbourLocation, int damage, SWEntityInterface a){

                List<SWEntityInterface> entities = SWAction.getEntitymanager().contents(neighbourLocation);
                // if there is one or more entities in the location given, give damage to them
                if(entities != null){
                        for (SWEntityInterface e : entities) {
                                // they cannot be either the thrower or the explosive object itself
                                if(e != a && SWAction.getEntitymanager().whereIs(e) != null) {
                                        e.takeDamage(damage);
                                }
                        }
                }
        }

        /**
         * A symbol that is used to represent the Grenade on a text based user interface
         *
         * @return      A String containing a single character.
         * @see         {@link starwars.SWEntityInterface#getSymbol()}
         */
        @Override
        public String getSymbol() {
                return "G";
        }

        /**
         * Grenade are explosive, so doing damage to them will trigger them to explode
         * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
         */
        @Override
        public void takeDamage(int damage) {
                super.takeDamage(damage);

                // if the hitpoints of the grenade is less than or equal to zero, it will be triggered
                if(this.hitpoints <= 0) {

                        EntityManager<SWEntityInterface, SWLocation> em = SWAction.getEntitymanager();
                        SWLocation loc = em.whereIs(this);

                        this.trigger(null, loc); // trigger the grenade (note: null argument as no thrower)

                }


        }

}