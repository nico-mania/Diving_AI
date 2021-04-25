package s0573175;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Arrays;

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

            float[] tempPearlX = {pearl[0].x, pearl[1].x, pearl[2].x, pearl[3].x, pearl[4].x, pearl[5].x, pearl[6].x, pearl[7].x, pearl[8].x, pearl[9].x};
            float[] tempPearlY = {pearl[0].y, pearl[1].y, pearl[2].y, pearl[3].y, pearl[4].y, pearl[5].y, pearl[6].y, pearl[7].y, pearl[8].y, pearl[9].y};

            Arrays.sort(tempPearlX);
            Arrays.sort(tempPearlY);

            float seekY = tempPearlY[info.getScore()] - info.getY();
            float seekX = tempPearlX[info.getScore()] - info.getX();

            float fleeY = info.getY() - pearl[info.getScore()].y;
            float fleeX = info.getX() - tempPearlX[info.getScore()];

            if (obstacles[1].contains(info.getX(), info.getY()-10)) {
                int count = 0;
                while (info.getY() < 550) {
                    // obstacle in front?
                    return new DivingAction(0.5f, info.getOrientation()+0.1f);
                }
            }else if (obstacles[0].contains(info.getX()+10, info.getY()+15) || obstacles[0].contains(info.getX()+10, info.getY()-15) ) {
                int count = 0;
                while (info.getY() < 550) {
                    // obstacle in front?
                    return new DivingAction(0.5f, info.getOrientation()+0.1f);
                }
            }else{
//          System.out.println("Searching for pearl: " + count);
            if (info.getScore() == count) {
                if (info.getY() <= info.getY()-250){
                    return new DivingAction(0.5f, (float)up/2);
                }
            } else if (tempPearlX[info.getScore()] < info.getX()) {
                return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
            } else if (tempPearlX[info.getScore()] > info.getX()) {
                return new DivingAction(0.5f, (float) Math.atan2(seekY, seekX));
            } else {
                return new DivingAction(0.5f, (float) down);
            }
        }
        count++;
        return null;
    }
}

