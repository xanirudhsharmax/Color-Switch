package colorswitch;

import javafx.scene.paint.Color;

class SpecialStar extends Star
{
	public SpecialStar(double xPosition,double yPosition)
	{
		super(xPosition, yPosition);
		this.setFill(Color.RED);
		this.setScore(5);
	}
}
