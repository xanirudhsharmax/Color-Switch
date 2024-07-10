
package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.Group;

import java.io.Serializable;

abstract class Obstacle extends Group implements Serializable
{
    protected double obstacleSize;
    protected static boolean advanced;

    protected double xCenter;
    protected double yCenter;

    static
    {
        advanced=false;
    }

    protected Obstacle()
    {
        super();
        this.getChildren().clear();
        this.obstacleSize=GameSpace.ObstacleSize;
        this.xCenter=0;
        this.yCenter=0;
    }

    public abstract void transform();
    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+GameSpace.MotionSpeed);
    }
    public abstract void construct();

    public void updateProperties()
    {
        double[] position=this.getCentrePosition();
        this.xCenter=position[0];
        this.yCenter=position[1];
    }

    public static void enableAdvanceMode()
    {
        Obstacle.advanced=true;
    }

    public double[] getCentrePosition()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()};
    }
    public double getTopPoint()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return boundsInScene.getMinY();
    }
}