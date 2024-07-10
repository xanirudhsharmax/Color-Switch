
package colorswitch;

import javafx.geometry.Bounds;
import javafx.scene.shape.Polygon;
import javafx.scene.effect.Glow;
import javafx.scene.paint.Color;

import java.io.Serializable;

class Star extends Polygon implements Serializable
{
    private final static int pointsCount;
    private final static double[] adjustmentsX;
    private final static double[] adjustmentsY;

    private int score;
    private double xCenter;
    private double yCenter;
    private final double scalingFactor;

    static
    {
        pointsCount =10;
        adjustmentsX=new double[]{0.0,1.5,5.5,2.0,3.5,0.0,-3.5,-2.0,-5.5,-1.5};
        adjustmentsY=new double[]{-5.0,-1.5,-1.5,1,5.0,2.0,5.0,1,-1.5,-1.5};
    }

    public Star(double xCenter,double yCenter)
    {
        super();
        this.scalingFactor=2;
        this.score=1;
        this.xCenter=xCenter;
        this.yCenter=yCenter;

        this.construct();
    }

    public void construct()
    {
        Double[] pointsXY=new Double[2*Star.pointsCount];
        for(int i = 0; i<Star.pointsCount; ++i)
        {
            double x=Star.adjustmentsX[i]*this.scalingFactor;
            double y=Star.adjustmentsY[i]*this.scalingFactor;
            pointsXY[2*i]=x;
            pointsXY[2*i+1]=y;
        }
        this.getPoints().clear();
        this.getPoints().addAll(pointsXY);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(0.5);
        this.setFill(Color.YELLOW);
        this.setEffect(new Glow(0.5));

        this.translatePosition(this.xCenter,this.yCenter);
    }

    public void setScore(int s)
    {
        this.score=s;
    }
    public int getScore()
    {
        return this.score;
    }

    public double[] getPosition()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()};
    }

    public void translatePosition(double xPosition,double yPosition)
    {
        Double[] pointsXY=new Double[2*Star.pointsCount];
        int i=0;
        for(Double point : this.getPoints())
        {
            pointsXY[i]=point;
            ++i;
        }
        this.getPoints().clear();
        for(i=0;i<pointsXY.length;++i)
        {
            if((i&1)==0)
                pointsXY[i]+= xPosition;
            else
                pointsXY[i]+= yPosition;
        }
        this.getPoints().addAll(pointsXY);
    }

    public void moveDown()
    {
        this.translatePosition(0,GameSpace.MotionSpeed);
    }

    public void updateProperties()
    {
        double[] position=this.getPosition();
        this.xCenter=position[0];
        this.yCenter=position[1];
    }
}