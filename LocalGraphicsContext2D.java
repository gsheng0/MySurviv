import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
public class LocalGraphicsContext2D
{
	GraphicsContext g;
	public LocalGraphicsContext2D(GraphicsContext g)
	{
		this.g = g;
	}

	public void setColor(Color color)
	{
		g.setFill(color);
	}

	public void fillRect(Location location, double x, double y)
	{
		g.fillRect(location.getX(), location.getY(), x, y);
	}
	public void fillRect(double x, double y, double xSize, double ySize)
	{
		g.fillRect(x, y, xSize, ySize);
	}

	public void clearRect(Location location, double x, double y)
	{
		g.clearRect(location.getX(), location.getY(), x, y);
	}
	public void clearRect(double x, double y, double xSize, double ySize)
	{
		g.clearRect(x, y, xSize, ySize);
	}

	public void fillOval(Location location, double x, double y)
	{
		g.fillOval(location.getX(), location.getY(), x, y);
	}
	public void fillOval(Location location, double r)
	{
		g.fillOval(location.getX(), location.getY(), r, r);
	}

	public void strokeLine(double x1, double y1, double x2, double y2)
	{
		g.strokeLine(x1, y1, x2, y2);
	}
	public void strokeLine(Location point1, Location point2)
	{
		g.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
	}

	public void fillCircle(Circle circle)
	{
		fillOval(new Location(circle.getCenterX() - circle.getRadius()/2, circle.getCenterY() - circle.getRadius()/2), circle.getRadius());
	}

	public void setFill(Color color)
	{
		g.setFill(color);
	}
}