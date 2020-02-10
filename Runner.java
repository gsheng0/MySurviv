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
import javafx.scene.transform.Rotate;

public class Runner extends Application implements EventHandler<InputEvent>
{
	public static int SCREEN_X = 1500;
	public static int SCREEN_Y = 1000;
	public static int GRID_SIZE = 400;
	public static Location TOP_LEFT_CORNER = new Location(0, 0);
	public static Location CENTER = new Location(SCREEN_X/2, SCREEN_Y/2);
	public static double MOUSE_X = 0;
	public static double MOUSE_Y = 0;
	Game game;
	LocalGraphicsContext2D gc;
	AnimateObjects animate;
	Stage stage;

	Color SKIN_COLOR = Color.MOCCASIN;
	boolean W_DOWN = false;
	boolean A_DOWN = false;
	boolean S_DOWN = false;
	boolean D_DOWN = false;

	public static void main(String[] args)
	{
		launch();
	}
	public void start(Stage stage)
	{
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[1];
		//SCREEN_X = gd.getDisplayMode().getWidth();
		//SCREEN_Y = gd.getDisplayMode().getHeight();
		//CENTER = new Location(SCREEN_X/2, SCREEN_Y/2);

		Canvas canvas = new Canvas(SCREEN_X, SCREEN_Y);
		stage.setTitle("My Surviv");
		Group root = new Group();
		root.getChildren().add(canvas);

		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.addEventHandler(KeyEvent.KEY_PRESSED,this);
		scene.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		scene.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event)
			{
				//game.getPlayers().get(0).shoot();
			}
		});

		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.W)
					W_DOWN = true;
				if(event.getCode() == KeyCode.A)
					A_DOWN = true;
				if(event.getCode() == KeyCode.S)
					S_DOWN = true;
				if(event.getCode() == KeyCode.D)
					D_DOWN = true;
			}
		});

		scene.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.W)
					W_DOWN = false;
				if(event.getCode() == KeyCode.A)
					A_DOWN = false;
				if(event.getCode() == KeyCode.S)
					S_DOWN = false;
				if(event.getCode() == KeyCode.D)
					D_DOWN = false;
			}
		});

		scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
      		public void handle(MouseEvent event) {
				MOUSE_X = event.getX();
				MOUSE_Y = event.getY();
				game.getPlayers().get(0).updateAngle(event);
		  }
		});

		stage.setX(300);
		stage.setY(300);

		gc = new LocalGraphicsContext2D(canvas.getGraphicsContext2D());

		stage.show();
		this.stage = stage;

		game = new Game();
		Surviver.Builder builder = new Surviver.Builder();
		builder.centerAt(CENTER).named("Player");
		game.add(builder.build());

		builder.at(new Location(70, 70));
		game.add(builder.build());

		animate = new AnimateObjects();
		animate.start();

	}

	public class AnimateObjects extends AnimationTimer
	{
		public void handle(long now)
		{
			gc.clearRect(0, 0, SCREEN_X, SCREEN_Y);
			double anchor_x = TOP_LEFT_CORNER.getX();
			double anchor_y = TOP_LEFT_CORNER.getY();
			move();
			drawMap();

			ArrayList<Surviver> players = game.getPlayers();

			for(Surviver player : players)
			{
				Location location = player.getLocation().modify(TOP_LEFT_CORNER);
				gc.setColor(SKIN_COLOR);
				gc.fillOval(location, Surviver.SIZE);

				//System.out.println("Angle: " + player.getAngle());
				double left_angle = Math.toRadians(player.getAngle() + 45);
				double right_angle = Math.toRadians(player.getAngle() - 45);
				double radius = Surviver.SIZE/2 + Surviver.HAND_SIZE/2;
				double left_x = (radius * Math.cos(left_angle) + player.getCenter().getX() + TOP_LEFT_CORNER.getX()) - (Surviver.HAND_SIZE/2);
				double left_y = (-radius * Math.sin(left_angle) + player.getCenter().getY() + TOP_LEFT_CORNER.getY()) - (Surviver.HAND_SIZE/2);
				double right_x = (radius * Math.cos(right_angle) + player.getCenter().getX() + TOP_LEFT_CORNER.getX()) - (Surviver.HAND_SIZE/2);
				double right_y = (-radius * Math.sin(right_angle) + player.getCenter().getY() + TOP_LEFT_CORNER.getY()) - (Surviver.HAND_SIZE/2);

				Location leftFist = new Location(left_x, left_y);
				Location rightFist = new Location(right_x, right_y);

				gc.fillOval(leftFist, Surviver.HAND_SIZE);
				gc.fillOval(rightFist, Surviver.HAND_SIZE);
			}

		}
		public void drawMap()
		{
			double anchor_x = TOP_LEFT_CORNER.getX();
			double anchor_y = TOP_LEFT_CORNER.getY();

			gc.setColor(Color.LIMEGREEN);
			gc.fillRect(TOP_LEFT_CORNER, game.MAP_SIZE, game.MAP_SIZE);

			for(int i = 0; i < game.MAP_SIZE/GRID_SIZE; i++)
			{
				gc.strokeLine(anchor_x, anchor_y + i * GRID_SIZE, anchor_x + game.MAP_SIZE, anchor_y + i * GRID_SIZE);
				gc.strokeLine(anchor_x + i * GRID_SIZE, anchor_y, anchor_x + i * GRID_SIZE, anchor_y + game.MAP_SIZE);
			}

		}
		public void move()
		{
			Surviver player = game.getPlayers().get(0);

			if(A_DOWN)
			{
				player.moveX(-5);
				TOP_LEFT_CORNER.moveX(5);
			}
			if(W_DOWN)
			{
				player.moveY(-5);
				TOP_LEFT_CORNER.moveY(5);
			}
			if(S_DOWN)
			{
				player.moveY(5);
				TOP_LEFT_CORNER.moveY(-5);
			}
			if(D_DOWN)
			{
				player.moveX(5);
				TOP_LEFT_CORNER.moveX(-5);
			}

		}
	}
	public void handle(final InputEvent input)
	{
		if(input instanceof KeyEvent)
		{


		}
		else if(input instanceof MouseEvent)
		{

		}
	}
}