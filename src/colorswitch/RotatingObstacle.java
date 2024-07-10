
package colorswitch;

import java.util.Random;

abstract class RotatingObstacle extends Obstacle
{
    protected double factor;
    protected double rotateAngle;

    public RotatingObstacle()
    {
        super();
        this.factor=100;
        this.rotateAngle=(new Random()).nextDouble()*360;
    }

    public void transform()
    {
        this.rotate();
    }
    private void rotate()
    {
        this.setRotate(this.getRotate()+GameSpace.TransformationSpeed*this.factor);
    }

    public void updateProperties()
    {
        super.updateProperties();
        this.rotateAngle=this.getRotate();
    }
}