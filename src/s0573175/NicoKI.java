package s0573175;

import lenz.htw.ai4g.ai.AI;
import lenz.htw.ai4g.ai.DivingAction;
import lenz.htw.ai4g.ai.Info;
import lenz.htw.ai4g.ai.PlayerAction;

import java.awt.*;
import java.awt.geom.Path2D;

public class NicoKI extends AI {

    public NicoKI(Info info){
	    super(info);
	    enlistForTournament(573175, 573144);
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public String getName(){
        return "Abco";
    }

    //Orientation für PlayerAction
    double up = (Math.PI / 2);
    double down = -(Math.PI / 2);
    double right = (Math.PI * 2);
    double left = (-Math.PI);
    int count = 0;

    @Override
    public PlayerAction update() {

        info.getMaxVelocity();  //max. velocity
        info.getVelocity();     //current velocity

        info.getOrientation();

        info.getX(); //my position
        info.getY();

        // System.out.println("x:" + info.getX() + "    y:" +info.getY());

        Point[] pearl = info.getScene().getPearl();
        // System.out.println("x_" + pearl[1].x  +  "    y_" + pearl[1].y);

        Path2D[] obstacles = info.getScene().getObstacles();
        if (obstacles[0].contains(info.getX()+5, info.getY())) {
            //obstacle in front?

            // bewege in bestimmte richtung

            //obstacles[0].getPathIterator(null); //profi hint?
        }

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
        }

        count++;
        return null;
    }
}
