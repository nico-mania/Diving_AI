package s0573175;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

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
//    double down = -(Math.PI / 2);
//    double right = (Math.PI * 2);
//    double left = (-Math.PI);
//    int count = 0;

    // obstacles[0] --> little sand dot
    // obstacles[1] --> sand ground

    @Override
    public PlayerAction update() {

        info.getMaxVelocity();  //max. velocity
        info.getVelocity();     //current velocity

        info.getOrientation();

        info.getX(); // position of diver
        info.getY();

        System.out.println("x:" + info.getX() + "    y:" +(-info.getY()));

        Point[] pearl = info.getScene().getPearl();
        // System.out.println("x_" + pearl[1].x  +  "    y_" + pearl[1].y);

        Path2D[] obstacles = info.getScene().getObstacles();
        if (obstacles[1].contains(info.getX(), info.getY()-5)) {
            // obstacle in front?

            if(info.getY() < info.getY()+10)
                return new DivingAction(0.5f, (float) up/2);

//          obstacles[1].getPathIterator(null); //profi hint?
        }

        System.out.println(obstacles[1].getCurrentPoint());

        // Orientation for PlayerAction (2nd Version)
        float directionY = pearl[info.getScore()].y - info.getY();
        float directionX = pearl[info.getScore()].x - info.getX();

        return new DivingAction(0.5f, (float) Math.atan2(directionY, directionX));

        // First Version

//        System.out.println("Searching for pearl: " + count);
//        if (info.getScore() == count) {
//            if (info.getY() <= pearl[info.getScore()].y+100){
//                return new DivingAction(0.5f, (float) up);
//            }
//        } else if (pearl[info.getScore()].x < info.getX()) {
//            return new DivingAction(0.5f, (float) left);
//        } else if (pearl[info.getScore()].x > info.getX()) {
//            return new DivingAction(0.5f, (float) right);
//        } else if (pearl[info.getScore()].y != info.getY()) {
//            return new DivingAction(0.5f, (float) down);
//        }

    }
}