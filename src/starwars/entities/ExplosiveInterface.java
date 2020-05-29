package starwars.entities;

import starwars.SWEntityInterface;
import starwars.SWLocation;

public interface ExplosiveInterface {
	public void trigger(SWEntityInterface a, SWLocation actorLocation);
}
