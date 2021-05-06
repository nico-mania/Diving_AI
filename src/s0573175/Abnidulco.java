package s0573175;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.lang.Object;
public class Abnidulco extends AI {

    public Abnidulco(Info info){
        super(info);
        enlistForTournament(573175, 573144);
    }
    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
    @Override
    public String getName(){
        return "Abnidulco";
    }

    //Orientation for PlayerAction (1st Version)
    double up = (Math.PI / 2);
    double down = -(Math.PI / 2);
    double right = (Math.PI * 2);
    double left = (-Math.PI);
    int count = 0;
    float direction = 0;
    // obstacles[0] --> little sand dot
    // obstacles[1] --> sand ground

    @Override
    public PlayerAction update() {
        info.getMaxVelocity();  //max. velocity
        info.getVelocity();     //current velocity
        info.getOrientation();
        info.getX(); // position of diver
        info.getY();
        System.out.println("x:" + info.getX() + "    y:" + (-info.getY()));
        System.out.println("Angle:" + info.getOrientation());
        Point[] pearl = info.getScene().getPearl();
        Path2D[] obstacles = info.getScene().getObstacles();
        float[] originalPearlX = {pearl[0].x, pearl[1].x, pearl[2].x, pearl[3].x, pearl[4].x, pearl[5].x, pearl[6].x, pearl[7].x, pearl[8].x, pearl[9].x};
        float[] originalPearlY = {pearl[0].y, pearl[1].y, pearl[2].y, pearl[3].y, pearl[4].y, pearl[5].y, pearl[6].y, pearl[7].y, pearl[8].y, pearl[9].y};
        float[] tempPearlX = {pearl[0].x, pearl[1].x, pearl[2].x, pearl[3].x, pearl[4].x, pearl[5].x, pearl[6].x, pearl[7].x, pearl[8].x, pearl[9].x};
        float[] tempPearlY = {pearl[0].y, pearl[1].y, pearl[2].y, pearl[3].y, pearl[4].y, pearl[5].y, pearl[6].y, pearl[7].y, pearl[8].y, pearl[9].y};
        Arrays.sort(tempPearlX);
        Arrays.sort(tempPearlY);

        for (int i = 0; i < pearl.length; i++){
            int n = 0;
            n = findIndex(originalPearlX, tempPearlX[i]);
            tempPearlY[i] = pearl[n].y;
        }
//            System.out.println("X: " + tempPearlX[0] + " Y:" + tempPearlY[0]);
//            System.out.println("X: " + tempPearlX[1] + " Y:" + tempPearlY[1]);
//            System.out.println("X: " + tempPearlX[2] + " Y:" + tempPearlY[2]);
//            System.out.println("X: " + tempPearlX[3] + " Y:" + tempPearlY[3]);
//            System.out.println("X: " + tempPearlX[4] + " Y:" + tempPearlY[4]);
//            System.out.println("X: " + tempPearlX[5] + " Y:" + tempPearlY[5]);
//            System.out.println("X: " + tempPearlX[6] + " Y:" + tempPearlY[6]);
//            System.out.println("X: " + tempPearlX[7] + " Y:" + tempPearlY[7]);
//            System.out.println("X: " + tempPearlX[8] + " Y:" + tempPearlY[8]);
//            System.out.println("X: " + tempPearlX[9] + " Y:" + tempPearlY[9]);
//            System.out.println("Obstacle 1: " + obstacles[0].getCurrentPoint());
//            System.out.println("Obstacle 2: " + obstacles[1].getCurrentPoint());
//            System.out.println("Obstacle 3: " + obstacles[2].getCurrentPoint());
//            System.out.println("Obstacle 4: " + obstacles[3].getCurrentPoint());
//            System.out.println("Obstacle 5: " + obstacles[4].getCurrentPoint());

        float seekY = tempPearlY[info.getScore()] - info.getY();
        float seekX = tempPearlX[info.getScore()] - info.getX();
        float fleeY = seekY * (-1);
        float fleeX = seekX * (-1);
        //Für Aufgabe 2
        //int[] obstacleNumbers = {0,1,2,3,4};
        if (obstacles[4].contains(info.getX(), info.getY()-5) || obstacles[4].contains(info.getX()+10, info.getY())) {
            int count = 0;
            while (info.getY() < 500) {
                // obstacle in front?
                return new DivingAction(0.5f,  info.getOrientation() + 0.05f);
            }
        }else if (obstacles[2].contains(info.getX(), info.getY()-5) || obstacles[2].contains(info.getX()+10, info.getY())
                || obstacles[2].contains(info.getX(), info.getY()+5) || obstacles[2].contains(info.getX()-10, info.getY())) {
            int count = 0;
            while (info.getY() < 100) {
                // obstacle in front?
                return new DivingAction(0.5f, info.getOrientation()+0.05f);
            }
        }else if (obstacles[1].contains(info.getX(), info.getY()-5) || obstacles[1].contains(info.getX()+10, info.getY())
                || obstacles[1].contains(info.getX(), info.getY()+5) || obstacles[1].contains(info.getX()-10, info.getY())) {
            int count = 0;
            while (info.getY() < 100) {
                // obstacle in front?
                return new DivingAction(0.5f, info.getOrientation()+0.05f);
            }
        }else {

//              System.out.println("Searching for pearl: " + count);
//              if (info.getScore() == count) {
//                  if (info.getY() <= info.getY()-50){
//                      return new DivingAction(0.5f, (float)up/2);
//                  }
//              } else if (tempPearlX[info.getScore()] < info.getX()) {
//                  return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
//              } else if (tempPearlX[info.getScore()] > info.getX()) {
//                  return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
//              } else {
//                  return new DivingAction(0.5f, (float) down);
//              }
            return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
        }
        count++;
        return null;
    }

//            if (obstacles[1].contains(info.getX(), info.getY()-10)) {
//                int count = 0;
//                while (info.getY() < 550) {
//                    // obstacle in front?
//                    return new DivingAction(0.5f, info.getOrientation()+0.1f);
//                }
//            }else if (obstacles[0].contains(info.getX()+10, info.getY()+15) || obstacles[0].contains(info.getX()+10, info.getY()-15) ) {
//                int count = 0;
//                while (info.getY() < 550) {
//                    // obstacle in front?
//                    return new DivingAction(0.5f, info.getOrientation()+0.1f);
//                }
//
//            }else{
////          System.out.println("Searching for pearl: " + count);
//            if (info.getScore() == count) {
//                if (info.getY() <= info.getY()-250){
//                    return new DivingAction(0.5f, (float)up/2);
//                }
//            } else if (tempPearlX[info.getScore()] < info.getX()) {
//                return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
//            } else if (tempPearlX[info.getScore()] > info.getX()) {
//                return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
//            } else {
//                return new DivingAction(0.5f, (float) down);
//            }
//        }
//        count++;
//        return null;


    // Source: https://www.geeksforgeeks.org/find-the-index-of-an-array-element-in-java/
    public static int findIndex(float arr[], float t)
    {
        // if array is Null
        if (arr == null) {
            return -1;
        }
        // find length of array
        int len = arr.length;
        int i = 0;
        // traverse in the array
        while (i < len) {
            // if the i-th element is t
            // then return the index
            if (arr[i] == t) {
                return i;
            }
            else {
                i = i + 1;
            }
        }
        return -1;
    }
}


