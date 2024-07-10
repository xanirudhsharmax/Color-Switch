package colorswitch;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

abstract class ColorSwappingObstacle extends Obstacle
{
	private int colorIndex;

	public ColorSwappingObstacle()
	{
		super();
		this.colorIndex=(new Random()).nextInt(Settings.IntersectionColors.length);
	}

	public void transform()
	{
		this.swapColors();
	}
	private void swapColors()
	{
		if(!Obstacle.advanced)
			this.swapColorsOrderly();
		else
			this.swapColorsRandomly();
	}

	private void swapColorsOrderly()
	{
		++this.colorIndex;
		if(this.colorIndex==Settings.IntersectionColors.length)
			this.colorIndex=0;
		int count=0;
		for (Node part : this.getChildren().toArray(new Node[0]))
		{
			int index=(this.colorIndex+count)%Settings.IntersectionColors.length;
			((Shape)part).setStroke(Settings.IntersectionColors[index]);
			++count;
		}
	}
	private void swapColorsRandomly()
	{
		ArrayList<Color> colors=new ArrayList<>(Settings.IntersectionColors.length);
		colors.addAll(Arrays.asList(Settings.IntersectionColors));
		Random rd=new Random();
		for (Node part : this.getChildren().toArray(new Node[0]))
		{
			int colorIndex=rd.nextInt(colors.size());
			Color newColor=colors.get(colorIndex);
			colors.remove(colorIndex);
			((Shape) part).setStroke(newColor);
		}
	}

	public void updateProperties()
	{
		super.updateProperties();
		this.colorIndex=(Settings.IntersectionColors.length+this.colorIndex-1)%Settings.IntersectionColors.length;
	}
}
