public class Location
{
	private double x = 0;
	private double y = 0;
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public double getX() { return x; }
	public double getY() { return y; }
	public void moveX(double change)
	{
		x += change;
	}
	public void moveY(double change)
	{
		y += change;
	}
	public void move(double xChange, double yChange)
	{
		x += xChange;
		y += yChange;
	}
	public Location move(Location other)
	{
		move(other.getX(), other.getY());
		return this;
	}
	public Location clone() { return new Location(x, y); }
	public double distanceFrom(Location other)
	{
		double other_x = other.getX();
		double other_y = other.getY();

		return Math.sqrt((other_x * other_x) + (other_y * other_y));
	}
	public Location modify(Location other) { return new Location(x + other.getX(), y + other.getY()); }
	public Location modify(double change) { return new Location(x + change, y + change); }
	public String toString() { return "(" + x + ", " + y + ")"; }
}