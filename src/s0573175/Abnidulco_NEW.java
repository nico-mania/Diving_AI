package s0573175;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Comparator;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

public class Abnidulco_NEW extends AI {

    //// LEVEL ////
    private Point[] pearls;
    private Path2D[] obstacles;

    //// SCENE INFORMATION ////
    private int groundObstacle;
    private int nextPearl;
    private int previousScore;
    private int[] obstacleList;
    private int obsI;

    //// DIVER CONTOLLER ////
    private float diverSpeed;
    private float direction;

    //// STARTING POINT ////
    private Boolean makeInitialization = true;
    private Boolean goLeft = true;
    private Boolean saveTime = true;
    long startTime = 0;

    public Abnidulco_NEW(Info info) {
        super(info);
        enlistForTournament(573175, 573144);
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public String getName() {
        return "Abni Dulco NEW";
    }

    public void Initialize() {
        pearls = info.getScene().getPearl();
        obstacles = info.getScene().getObstacles();

        Arrays.sort(pearls, new Comparator<Point>() {
            public int compare(Point a, Point b) {
                int xComp = Integer.compare(a.x, b.x);
                if(xComp == 0)
                    return Integer.compare(a.y, b.y);
                else
                    return xComp;
            }
        });

        for(int i = 0; i < obstacles.length; i++){
            if(obstacles[i].contains(500, -700)) {
                groundObstacle = i;
                System.out.print(i);
            }
        }

        int o;
        obstacleList = new int[obstacles.length];

        for (o=0; o < obstacles.length; o++){
            obstacleList[o] = o;
        }
        System.out.print(Arrays.toString(obstacleList));


        diverSpeed = info.getMaxAcceleration();

    }

    @Override
    public PlayerAction update() {

        info.getMaxVelocity();    //maximum velocity
        info.getVelocity();    //current velocity

        info.getOrientation();

        info.getX();    //meine Position
        info.getY();

        // to initialize the scene once
        if(makeInitialization){
            Initialize();
            makeInitialization = false;
        }

        // pick up pearls and marking them
        if (info.getScore() > previousScore){
            previousScore++;
            pearls = takePearl();
            nextPearl = 0;
        }


        if(saveTime){
            long time = System.currentTimeMillis() / 1000;
            startTime = time;
            saveTime = false;
        }

        long currentTime = (System.currentTimeMillis() / 1000)  - startTime;
        //System.out.println(currentTime);




        // When obstacles are there:

        // When no obstacles:

        if(info.getAir() < 500){
            if(info.getScore() == 9){
                direction = seekPearl(pearls[0].x, pearls[0].y, info.getX(), info.getY());
            }else {
                direction = (float) Math.PI / 2;
            }
            }else {
            if (goLeft) {
                if (currentTime < 20) {
                    //System.out.println("go left");
                    direction = (float) -Math.PI;
                } else {
                    goLeft = false;
                }
            } else {

                direction = seekPearl(pearls[0].x, pearls[0].y, info.getX(), info.getY());
            }
        }

        for (obsI = 0; obsI < obstacles.length; obsI++) {

            if (obsI == groundObstacle){
                continue;
            }

            if (obstacles[obsI].contains(info.getX(), info.getY() - 5) || obstacles[obsI].contains(info.getX() + 10, info.getY())
                    || obstacles[obsI].contains(info.getX(), info.getY() + 5) || obstacles[obsI].contains(info.getX() - 10, info.getY())) {
                System.out.println("obstacle rest");
                direction = info.getOrientation() - 0.05f;

            }else if(obstacles[groundObstacle].contains(info.getX(), info.getY() - 5) || obstacles[groundObstacle].contains(info.getX() + 10, info.getY())
                        || obstacles[groundObstacle].contains(info.getX(), info.getY() + 5) || obstacles[groundObstacle].contains(info.getX() - 10, info.getY())) {
                    System.out.println("big boi");
                    direction = info.getOrientation() + 0.05f;

                }

            }


        nextPearl++;
        //System.out.println(direction);
        return new DivingAction(diverSpeed, direction);
    }

    public float seekPearl(int goalX, int goalY, int startX, int startY) {
        int x = goalX - startX;
        int y = goalY - startY;
        double direction = Math.atan2(y,x);

        return (float) direction;
    }

    public Point[] takePearl() {

        Point[] remainingPearls = new Point[pearls.length-1];

        double minDistanceToDiver = Double.MAX_VALUE;
        int nearestPearlIndex = 0;

        for (int i = 0; i < pearls.length; i++) {

            double distance = Point.distance(pearls[i].x,pearls[i].y, info.getX(), info.getY());
            if(distance < minDistanceToDiver) {
                minDistanceToDiver = distance;
                nearestPearlIndex = i;
            }
        }

        for (int i = 0, j = 0; i < pearls.length; i++) {
            if (i != nearestPearlIndex) {
                remainingPearls[j++] = pearls[i];
            }
        }

        return remainingPearls;
    }


}
