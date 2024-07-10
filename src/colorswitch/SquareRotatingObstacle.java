package colorswitch;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class SquareRotatingObstacle extends RotatingObstacle
{
    private static final int SubParts;

    static
    {
        SubParts = 4;
    }

    public SquareRotatingObstacle(double xCenter,double yCenter)
    {
        super();
        this.xCenter=xCenter;
        this.yCenter=yCenter;

        this.construct();
    }

    public void construct()
    {
        this.getChildren().clear();

        double dimension = this.obstacleSize;
        double[][] sideDimensions = new double[SquareRotatingObstacle.SubParts][4];
        sideDimensions[0] = new double[]{ this.xCenter-dimension/2 , this.yCenter-dimension/2 , this.xCenter+dimension/2 , this.yCenter-dimension/2 };
        sideDimensions[1] = new double[]{ this.xCenter+dimension/2 , this.yCenter-dimension/2 , this.xCenter+dimension/2 , this.yCenter+dimension/2 };
        sideDimensions[2] = new double[]{ this.xCenter+dimension/2 , this.yCenter+dimension/2 , this.xCenter-dimension/2 , this.yCenter+dimension/2 };
        sideDimensions[3] = new double[]{ this.xCenter-dimension/2 , this.yCenter+dimension/2 , this.xCenter-dimension/2 , this.yCenter-dimension/2 };

        Line[] sides = new Line[SquareRotatingObstacle.SubParts];
        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
            sides[i]=new Line( sideDimensions[i][0] , sideDimensions[i][1] , sideDimensions[i][2] , sideDimensions[i][3] );

        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
        {
            sides[i].setStroke(Settings.IntersectionColors[i]);
            sides[i].setFill(Color.TRANSPARENT);
            sides[i].setStrokeWidth(10);
        }

        for(int i = 0 ; i < SquareRotatingObstacle.SubParts ; ++i)
            this.getChildren().add(sides[i]);

        this.setRotate(this.rotateAngle);
    }
}
