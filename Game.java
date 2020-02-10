import java.util.ArrayList;
public class Game
{
	public final static int MAP_SIZE = 20000;
	ArrayList<Surviver> players = new ArrayList<>();

	public Game()
	{
	}
	public ArrayList<Surviver> getPlayers() { return players; }
	public void add(Surviver player)
	{
		players.add(player);
	}
}