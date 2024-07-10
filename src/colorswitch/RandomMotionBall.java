package colorswitch;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import java.util.Random;

public class RandomMotionBall extends Circle
{
	private static int instancesCount;
	private static int instancesAnimationFinished;

	static
	{
		instancesCount=0;
		instancesAnimationFinished=0;
	}

	public RandomMotionBall(double xPosition, double yPosition, double radius, double[] bounds, final Pane gamePane)
	{
		++RandomMotionBall.instancesCount;

		Random rd=new Random();

		this.setCenterX(xPosition);
		this.setCenterY(yPosition);
		this.setRadius(radius);
		this.setFill(Settings.IntersectionColors[rd.nextInt(Settings.IntersectionColors.length)]);

		double oldX=this.getCenterX();
		double oldY=this.getCenterY();
		double newX=rd.nextDouble()*bounds[0];
		double newY=rd.nextDouble()*bounds[1];
		double transX=newX-this.getCenterX();
		double transY=newY-this.getCenterY();

		double tangent=transY/transX;
		double leftX=(newX<oldX)?newX:bounds[0]-newX;
		double leftY=(newY<oldY)?newY:bounds[1]-newY;

		if(leftX<leftY)
		{
			transX+=leftX*(transX<0?-1:1);
			transY=transX*tangent;
		}
		else
		{
			transY+=leftY*(transY<0?-1:1);
			transX=transY/tangent;
		}

		double factor1=Math.max(bounds[0],bounds[1])*1.5;
		double factor2=factor1*3;
		TranslateTransition translate=new TranslateTransition(Duration.millis(factor1+rd.nextDouble()*factor2),this);
		translate.setToX(transX);
		translate.setToY(transY);
		translate.play();
		translate.setOnFinished(finishedEvent -> {
			++RandomMotionBall.instancesAnimationFinished;
			gamePane.getChildren().remove(this);
		});

		gamePane.getChildren().add(this);
	}

	public static boolean areAllBallsAnimationFinished()
	{
		return RandomMotionBall.instancesCount==RandomMotionBall.instancesAnimationFinished;
	}
}
