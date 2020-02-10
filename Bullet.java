public class Bullet
{
	public static final double SPEED = 9.6;
	private double x_speed = 0.0;
	private double y_speed = 0.0;
	private Location location;
	public Bullet(Location player, Location mouse)
	{
		location = player;
		double distance = player.distanceFrom(mouse);
		double time = distance/SPEED;

		double x_diff = mouse.getX() - player.getX();
		double y_diff = mouse.getY() - player.getY();

		x_speed = x_diff/time;
		y_speed = y_diff/time;

	}
	public Bullet(Location player, double x_speed, double y_speed)
	{
		location = player;
		this.x_speed = x_speed;
		this.y_speed = y_speed;
	}
	public double getXSpeed() { return x_speed; }
	public double getYSpeed() { return y_speed; }
	public Location getLocation() { return location; }
}
