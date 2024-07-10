package colorswitch;

import java.io.Serializable;
import java.util.ArrayList;

public class StatsRecord implements Serializable
{
	private final int gamesPlayed;
	private final double averageScore;
	private final int totalStarsCollected;
	private final int highScore;
	private final ArrayList<String> savedGames;

	public StatsRecord(int gamesPlayed,double averageScore,int totalStarsCollected,int highScore,ArrayList<String> savedGames)
	{
		this.gamesPlayed=gamesPlayed;
		this.averageScore=averageScore;
		this.totalStarsCollected=totalStarsCollected;
		this.highScore=highScore;
		this.savedGames=savedGames;
	}

	public int getGamesPlayed()
	{
		return this.gamesPlayed;
	}
	public double getAverageScore()
	{
		return this.averageScore;
	}
	public int getTotalStarsCollected()
	{
		return this.totalStarsCollected;
	}
	public int getHighScore()
	{
		return this.highScore;
	}
	public ArrayList<String> getSavedGames()
	{
		return this.savedGames;
	}
}
