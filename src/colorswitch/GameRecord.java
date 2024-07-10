package colorswitch;

public class GameRecord extends Record
{
	private final GameSpace gameSpace;

	public GameRecord(int score, String name, GameSpace gameSpace)
	{
		super(score,name);
		this.gameSpace=gameSpace;
	}

	public GameSpace getGameSpace()
	{
		return this.gameSpace;
	}
}
