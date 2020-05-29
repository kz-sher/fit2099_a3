package starwars.entities;

import starwars.SWEntityInterface;

public interface ExitableInterface extends SWEntityInterface {
	public EnterableInterface getEnterable();
}
