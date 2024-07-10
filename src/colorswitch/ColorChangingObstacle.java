
package colorswitch;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.util.Random;

abstract class ColorChangingObstacle extends Obstacle
{
    private int colorIndex;

    public ColorChangingObstacle()
    {
        super();
        this.colorIndex=(new Random()).nextInt(Settings.IntersectionColors.length);
    }

    public void transform()
    {
        this.changeColor();
    }
    private void changeColor()
    {
        if(!Obstacle.advanced)
            this.swapColorOrderly();
        else
            this.swapColorRandomly();
    }

    private void swapColorOrderly()
    {
        ++this.colorIndex;
        if(this.colorIndex==Settings.IntersectionColors.length)
            this.colorIndex=0;
        for (Node part : this.getChildren().toArray(new Node[0]))
            ((Shape)part).setStroke(Settings.IntersectionColors[this.colorIndex]);
    }
    private void swapColorRandomly()
    {
        Random rd=new Random();
        Color oldColor = (Color)((Shape)this.getChildren().get(0)).getStroke();
        Color newColor =  oldColor;
        while( newColor == oldColor )
            newColor=Settings.IntersectionColors[rd.nextInt(Settings.IntersectionColors.length)];
        for (Node part : this.getChildren().toArray(new Node[0]))
            ((Shape)part).setStroke(newColor);
    }

    public void updateProperties()
    {
        super.updateProperties();
        this.colorIndex=(Settings.IntersectionColors.length+this.colorIndex-1)%Settings.IntersectionColors.length;
    }
}