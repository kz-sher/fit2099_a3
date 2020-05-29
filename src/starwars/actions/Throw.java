package starwars.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWAction;
import starwars.SWActor;
import starwars.SWAffordance;
import starwars.SWEntityInterface;
import starwars.SWLocation;
import starwars.entities.ExplosiveInterface;
import starwars.entities.Grenade;

/**
 * <code>SWAction</code> that lets a <code>SWActor</code> throw an object.
 *
 * @author kz
 */
public class Throw extends SWAffordance {

        /**
         * Constructor for the <code>Throw</code> Class. Will initialize the message renderer, the target and
         * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
         *
         * @param theTarget a <code>SWEntity</code> that is being held
         * @param m the message renderer to display messages
         */
        public Throw(SWEntityInterface theTarget, MessageRenderer m) {
                super(theTarget, m);
                priority = 1;
        }


        /**
         * Returns if or not this <code>Throw</code> can be performed by the <code>SWActor a</code>.
         * <p>
         * This method returns true if and only if <code>a</code> is carrying any item already.
         *
         * @author      kz
         * @param       a the <code>SWActor</code> being queried
         * @return      true if the <code>SWActor</code> is holding an item, false otherwise
         */
        @Override
        public boolean canDo(SWActor a) {
                return a.getItemCarried()!=null;
        }

        /**
         * Perform the <code>Throw</code> action by setting null to the item carried by the <code>SWActor</code>. 
         * This is similar to leave affordance but it will make entities that have EXPLOSIVE capability explode.
         * <p>
         * This method should only be called if the <code>SWActor a</code> is holding something .
         *
         * @author      kz
         * @param       a the <code>SWActor</code> that is going to throw the item
         */
        @Override
        public void act(SWActor a) {
                SWEntityInterface theItem = a.getItemCarried();
                a.setItemCarried(null);
                SWLocation actorLocation = SWAction.getEntitymanager().whereIs(a);
                SWAction.getEntitymanager().setLocation(theItem, actorLocation);
                
                //remove the throw affordance
                target.removeAffordance(this);
           
                System.out.println(a.getShortDescription()+" is throwing a grenade");
                
                //if the previously held item is not explosive, then remove the leave affordance and add the take affordance
                if(!(theItem instanceof ExplosiveInterface)) {

                        for(Affordance af: target.getAffordances()){
                                if(af instanceof Leave){
                                        target.removeAffordance(af);
                                }
                        }

                        target.addAffordance(new Take(theItem,this.messageRenderer));

                }
                else{ //else it is ExplosiveInterface

                        //in this case, grenade is the class which has trigger() method
                        //in the future, it could be a superclass called 'Bomb' having subclasses like grenade
                        ((ExplosiveInterface) theItem).trigger(a, actorLocation);

                }

        }

        /**
         * A String describing what this action will do, suitable for display in a user interface
         *
         * @author kz
         * @return String comprising "throw " and the short description of the target of this <code>Throw</code>
         */
        @Override
        public String getDescription() {
                return "throw " + target.getShortDescription();
        }

}