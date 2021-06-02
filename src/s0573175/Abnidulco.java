package s0573175;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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
        return "Abni Dulco";
    }

    //Orientation for PlayerAction (1st Version)
    double up = (Math.PI / 2);
    double down = -(Math.PI / 2);
    double right = (Math.PI * 2);
    double left = (-Math.PI);
    int count = 0;
    int timer = 0;

    @Override
    public PlayerAction update() {

        info.getMaxVelocity();  //max. velocity
        info.getVelocity();     //current velocity
        info.getOrientation();

        info.getX(); // position of diver
        info.getY();

        Point[] pearl = info.getScene().getPearl();
        Path2D[] obstacles = info.getScene().getObstacles();
        Point[] fishes = info.getScene().getFish();

        float[] originalPearlX = {pearl[0].x, pearl[1].x, pearl[2].x, pearl[3].x, pearl[4].x, pearl[5].x, pearl[6].x, pearl[7].x, pearl[8].x, pearl[9].x};
        float[] originalPearlY = {pearl[0].y, pearl[1].y, pearl[2].y, pearl[3].y, pearl[4].y, pearl[5].y, pearl[6].y, pearl[7].y, pearl[8].y, pearl[9].y};

        float[] tempPearlX = {pearl[0].x, pearl[1].x, pearl[2].x, pearl[3].x, pearl[4].x, pearl[5].x, pearl[6].x, pearl[7].x, pearl[8].x, pearl[9].x};
        float[] tempPearlY = {pearl[0].y, pearl[1].y, pearl[2].y, pearl[3].y, pearl[4].y, pearl[5].y, pearl[6].y, pearl[7].y, pearl[8].y, pearl[9].y};

        Arrays.sort(tempPearlX);
        Arrays.sort(tempPearlY);

        for (int i = 0; i < pearl.length; i++) {
            int n = 0;
            n = findIndex(originalPearlX, tempPearlX[i]);
            tempPearlY[i] = pearl[n].y;
        }

        float seekY = tempPearlY[info.getScore()] - info.getY();
        float seekX = tempPearlX[info.getScore()] - info.getX();

        float seekBoatY = 0 - info.getY();
        float seekBoatX = info.getScene().getShopPosition() - info.getX();

        float fleeY = seekY * (-1);
        float fleeX = seekX * (-1);

        //Für Aufgabe 4

        int i;
        int[] obstacleList = new int[obstacles.length];

        for (i=0; i < obstacles.length; i++){
            obstacleList[i] = i;
        }

        System.out.println("Obstacles: " + obstacles.length);
        System.out.println(Arrays.toString(obstacleList));
        System.out.println("Air: " + info.getAir());



        for (int obstacle : obstacleList){

            if (obstacles[obstacle].contains(info.getX(), info.getY() - 5) || obstacles[obstacle].contains(info.getX() + 10, info.getY())
                    || obstacles[obstacle].contains(info.getX(), info.getY() + 5) || obstacles[obstacle].contains(info.getX() - 10, info.getY())) {
                System.out.println("colliding");
                while (info.getY() < 100) {

                    if (info.getOrientation() > down && info.getOrientation() < up) {

                        return new DivingAction(0.5f, info.getOrientation() + 0.05f);
                    } else {

                        return new DivingAction(0.5f, info.getOrientation() - 0.05f);

                    }
                }
            }
        }


        for (i = 0; i < obstacles.length; i++) {

            if (obstacles[i].contains(info.getX(), info.getY() - 5) || obstacles[i].contains(info.getX() + 10, info.getY())
                    || obstacles[i].contains(info.getX(), info.getY() + 5) || obstacles[i].contains(info.getX() - 10, info.getY())) {
                System.out.println("colliding differently");
                while (info.getY() < 100) {
                    return new DivingAction(0.5f, info.getOrientation() + 0.05f);
                }

            }else if (obstacles[i].contains(info.getX(), info.getY() - 5) || obstacles[i].contains(info.getX() + 10, info.getY())) {
                while (info.getY() < 100) {
                    return new DivingAction(0.5f, info.getOrientation() + 0.05f);
                }

            }else{
                //return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX) - 0.1f);

                    if (info.getAir() <= 490) {
                        if (info.getY() < 0 ) {
                                return new DivingAction(0.5f, (float) up);
                            }
                        }
                    else if(tempPearlX[info.getScore()] == info.getX()) {
                        return new DivingAction(0.5f, (float) down);
                    } else if (tempPearlY[info.getScore()] != info.getY()) {
                        return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX) - 0.2f);
                    }
                }
        }
        count++;
        return null;
    }

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

    /**
     *

    System.out.println("Suche nach Perle: " + count);
        if (info.getScore() == count) {
        if (info.getY() <= pearl[info.getScore()].y+100){
            return new DivingAction(0.5f, (float) up);
        }
    } else if (pearl[info.getScore()].x < info.getX()) {
        return new DivingAction(0.5f, (float) left);
    } else if (pearl[info.getScore()].x > info.getX()) {
        return new DivingAction(0.5f, (float) right);
    } else if (pearl[info.getScore()].y != info.getY()) {
        return new DivingAction(0.5f, (float) down);

     */
}


