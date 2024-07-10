package colorswitch;

import javafx.scene.paint.Color;

class Settings
{
    public static final double DesiredSceneWidth;
    public static final double DesiredSceneHeight;

    public static final long TimeDelay; // in ms

    public static double TransformationSpeed;
    public static long ObstacleTransformationSpeed; //Special Case
    public static final double MotionSpeed; // in pixels
    public static final double Gravity; // in pixels

    public static final Color[] IntersectionColors;

    public static final int ObstaclesCount;
    public static final double ObstacleSize;

    public static final int BrokenBallsCount;

    public static final int MinimumEntitiesCount;
    public static final double EntitiesGap;

    static
    {
        DesiredSceneWidth = 500;
        DesiredSceneHeight = 650;

        TimeDelay = 20_000_000L; // 20 ms

        TransformationSpeed = 0.05;
        MotionSpeed = 5;
        Gravity = MotionSpeed * 0.15;

        IntersectionColors = new Color[]{Color.RED,Color.YELLOW,Color.MAGENTA,Color.AQUAMARINE};

        ObstacleTransformationSpeed = 3 * (long)(TimeDelay*TransformationSpeed)/(long)1e5;
        ObstaclesCount = 6; //update according to number of obstacles available //check children of Obstacle class
        ObstacleSize = 150;

        BrokenBallsCount = 100;

        MinimumEntitiesCount = 5;
        EntitiesGap = 400;
    }
}
