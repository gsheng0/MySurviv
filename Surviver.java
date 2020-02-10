import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.media.AudioClip;
import java.net.URL;
import javafx.application.Application;
import java.awt.geom.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import java.util.Scanner;
import javafx.stage.Screen;
import javafx.scene.control.Button;
import java.awt.event.MouseListener;
import java.awt.MouseInfo;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.scene.text.*;

public class Surviver
{
	private int health = 100;
	private int armor = 0;
	private String name = "";
	private Location location;
	private double speed;
	private double angle = 0.0;
	public static final double SIZE = 100.0;
	public static double DEFAULT_SPEED = 4.5;
	public static double HAND_SIZE = 25.0;
	private Surviver()
	{
	}
	public void turn(double change) { angle += change; }
	public void setAngle(double angle) { this.angle = angle; }
	public double getAngle() { return angle; }
	public int getHealth() { return health; }
	public int getArmor() { return armor; }
	public String getName() { return name; }
	public Location getLocation() { return location; }
	public double getSpeed() { return speed; }
	public void updateAngle(MouseEvent event)
	{
		double adjusted_x = event.getX() - Runner.CENTER.getX();
		double adjusted_y = Runner.CENTER.getY() - event.getY();
		angle = Util.processAngle(Math.toDegrees(Math.atan(adjusted_y/adjusted_x)), adjusted_x, adjusted_y);
	}
	public void move(int x, int y)
	{
		location.move(x, y);
	}
	public void moveX(int x)
	{
		location.moveX(x);
	}
	public void moveY(int y)
	{
		location.moveY(y);
	}
	public Location getCenter() { return new Location(location.getX() + (SIZE/2.0), location.getY() + (SIZE/2.0)); }
	public String toString() { return "" + name; }
	public void pickUp(Item item)
	{
		item.pickUp(this);
	}

	public static class Builder
	{
		private int health = 100;
		private int armor = 0;
		private String name = "";
		private Location location;
		private double speed = DEFAULT_SPEED;
		public Builder()
		{
		}

		public Builder hasHealth(int health)
		{
			this.health = health;
			return this;
		}

		public Builder hasArmor(int armor)
		{
			this.armor = armor;
			return this;
		}

		public Builder named(String name)
		{
			this.name = name;
			return this;
		}

		public Builder at(Location location)
		{
			this.location = location.clone();
			return this;
		}
		public Builder centerAt(Location location)
		{
			this.location = location.clone().modify(new Location(-SIZE/2, -SIZE/2));
			return this;
		}

		public Builder speedOf(double speed)
		{
			this.speed = speed;
			return this;
		}

		public Surviver build()
		{
			Surviver surviver = new Surviver();
			surviver.health = health;
			surviver.armor = armor;
			surviver.name = name;
			surviver.location = location;
			surviver.speed = speed;
			return surviver;
		}
	}

}