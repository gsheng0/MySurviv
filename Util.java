public class Util
{
	public static double processAngle(double angle, double x, double y)
	{
		if(x < 0)
			angle += 180;
		else if(x >= 0 && y <= 0)
			angle += 360;


		return angle;
	}
}