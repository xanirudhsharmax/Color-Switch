package colorswitch;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Record implements Serializable
{
	private final int score;
	private final String name;
	private final LocalDateTime timeStamp;

	public Record(int score, String name)
	{
		this.score=score;
		this.name=name;
		this.timeStamp=LocalDateTime.now();
	}

	public int getScore()
	{
		return this.score;
	}
	public String getName()
	{
		return this.name;
	}
	public LocalDateTime getTimeStamp()
	{
		return this.timeStamp;
	}
}
