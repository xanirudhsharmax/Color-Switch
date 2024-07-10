
package colorswitch;

import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;

public class CircularRotatingObstacle extends RotatingObstacle
{

    private static final int SubParts;

    static
    {
        SubParts =4;
    }

    public CircularRotatingObstacle(double xCenter,double yCenter)
    {
        super();
        this.xCenter=xCenter;
        this.yCenter=yCenter;

        this.construct();
    }

    public void construct()
    {
        this.getChildren().clear();

        double radius = this.obstacleSize/2;
        double[] startAngles = {0,90,180,270};

        Arc[] arcs=new Arc[CircularRotatingObstacle.SubParts];
        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
            arcs[i]=new Arc();

        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
        {
            arcs[i].setCenterX(this.xCenter);
            arcs[i].setCenterY(this.yCenter);
            arcs[i].setRadiusX(radius);
            arcs[i].setRadiusY(radius);
            arcs[i].setStartAngle(startAngles[i]);
            arcs[i].setLength(90);
            arcs[i].setType(ArcType.OPEN);
            arcs[i].setStroke(Settings.IntersectionColors[i]);
            arcs[i].setFill(Color.TRANSPARENT);
            arcs[i].setStrokeWidth(10);
        }

        for(int i = 0; i<CircularRotatingObstacle.SubParts; ++i)
            this.getChildren().add(arcs[i]);

        this.setRotate(this.rotateAngle);
    }
}