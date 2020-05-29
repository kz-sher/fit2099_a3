package starwars.entities;

import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;

public interface EnterableInterface extends SWEntityInterface {

	
	public SWGrid getGrid();
	
	public void setWorldGrid(SWGrid g);
	
	public SWGrid getWorldGrid();
	
	public SWWorld getWorld();
	
	public SWLocation getEntrance();
}
