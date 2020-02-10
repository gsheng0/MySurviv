public class Item
{
	private Location location;
	private Surviver owner = null;
	public Item(Location location)
	{
		this.location = location;
	}
	public void pickUp(Surviver owner)
	{
		if(location != null)
		{
			location = null;
			this.owner = owner;
		}
	}
}