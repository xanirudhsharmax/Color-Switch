package colorswitch;

import java.io.Serializable;
import java.util.Random;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

public class ColorBall extends Group implements Serializable
{
    private int switchColor;

    private double xCenter;
    private double yCenter;

    private static final int SubParts;

    static
    {
        SubParts=4;
    }

    public ColorBall(double xCenter, double yCenter)
    {
        super();
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.switchColor = (new Random()).nextInt(Settings.IntersectionColors.length);

        this.construct();
    }

    public void construct()
    {
        this.getChildren().clear();

        double radius = 10;
        double[] startAngles = {0.0, 90.0, 180.0, 270.0};
        Arc[] arcs = new Arc[ColorBall.SubParts];
        for (int i = 0; i < ColorBall.SubParts; ++i)
            arcs[i] = new Arc();

        for (int i = 0; i < ColorBall.SubParts; ++i)
        {
            arcs[i].setCenterX(this.xCenter);
            arcs[i].setCenterY(this.yCenter);
            arcs[i].setRadiusX(radius);
            arcs[i].setRadiusY(radius);
            arcs[i].setStartAngle(startAngles[i]);
            arcs[i].setLength(90.37167f);
            arcs[i].setType(ArcType.ROUND);
            arcs[i].setFill(Settings.IntersectionColors[i]);
        }

        for (int i = 0; i < ColorBall.SubParts; ++i)
            this.getChildren().add(arcs[i]);
    }

    public void moveDown()
    {
        this.setTranslateY(this.getTranslateY()+GameSpace.MotionSpeed);
    }
    public void changeColors()
    {
        Node[] arcs = this.getChildren().toArray(new Node[0]);

        int i = (new Random()).nextInt(arcs.length);
        int k = 0;
        for(int j = i; j < Settings.IntersectionColors.length; ++j)
        {
            ((Arc)arcs[j]).setFill(Settings.IntersectionColors[k]);
            ++k;
        }
        for(int j = 0; j < i; ++j)
        {
            ((Arc)arcs[j]).setFill(Settings.IntersectionColors[k]);
            ++k;
        }
    }

    public void setColor(Color ignoredColor)
    {
        int chosenColor=-1;
        Random rd=new Random();
        do
            chosenColor=rd.nextInt(Settings.IntersectionColors.length);
        while(Settings.IntersectionColors[chosenColor].equals(ignoredColor));
        this.switchColor = chosenColor;
    }
    public Color getColor()
    {
        return Settings.IntersectionColors[this.switchColor];
    }

    public double[] getPosition()
    {
        Bounds boundsInScene=this.localToScene(this.getBoundsInLocal());
        return new double[]{boundsInScene.getCenterX(),boundsInScene.getCenterY()};
    }

    public void updateProperties()
    {
        double[] position=this.getPosition();
        this.xCenter=position[0];
        this.yCenter=position[1];
    }
}