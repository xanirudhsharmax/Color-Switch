package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class PlusRotatingObstacle extends RotatingObstacle
{
	private double xShift;
	private static final int SubParts;

	static
	{
		SubParts = 4;
	}

	public PlusRotatingObstacle(double xCenter,double yCenter)
	{
		super();
		this.xCenter=xCenter;
		this.yCenter=yCenter;

		this.construct();
	}

	public void construct()
	{
		this.getChildren().clear();

		this.xShift=this.obstacleSize/2;

		double dimension = (3*this.obstacleSize)/4;
		double[][] sideDimensions = new double[PlusRotatingObstacle.SubParts][4];
		sideDimensions[0] = new double[]{ this.xCenter-dimension+this.xShift , this.yCenter , this.xCenter+this.xShift , this.yCenter };
		sideDimensions[1] = new double[]{ this.xCenter+this.xShift , this.yCenter-dimension , this.xCenter+this.xShift , this.yCenter };
		sideDimensions[2] = new double[]{ this.xCenter+this.xShift , this.yCenter , this.xCenter+dimension+this.xShift , this.yCenter };
		sideDimensions[3] = new double[]{ this.xCenter+this.xShift , this.yCenter , this.xCenter+this.xShift , this.yCenter+dimension };

		Line[] sides = new Line[PlusRotatingObstacle.SubParts];
		for(int i = 0 ; i < PlusRotatingObstacle.SubParts ; ++i)
			sides[i]=new Line( sideDimensions[i][0] , sideDimensions[i][1] , sideDimensions[i][2] , sideDimensions[i][3] );

		for(int i = 0 ; i < PlusRotatingObstacle.SubParts ; ++i)
		{
			sides[i].setStroke(Settings.IntersectionColors[i]);
			sides[i].setFill(Color.TRANSPARENT);
			sides[i].setStrokeWidth(10);
		}

		for(int i = 0 ; i < PlusRotatingObstacle.SubParts ; ++i)
			this.getChildren().add(sides[i]);

		this.setRotate(this.rotateAngle);
	}

	public double[] getCentrePosition()
	{
		Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
		return new double[]{boundsInScene.getCenterX()-this.xShift,boundsInScene.getCenterY()};
	}
}
