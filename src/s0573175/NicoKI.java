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
	enlistForTournament(573175);
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public String getName(){
        return "Nico";
    }

    @Override
    public PlayerAction update() {

        info.getMaxVelocity();  //max. velocity
        info.getVelocity();     //current velocity

        info.getOrientation();

        info.getX(); //my position
        info.getY();

        Point[] pearl = info.getScene().getPearl();
        System.out.println(pearl[1].x & pearl[1].y);

        Path2D[] obstacles = info.getScene().getObstacles();
        if (obstacles[0].contains(info.getX()+5, info.getY()))
            //obstacle in front?

            obstacles[0].getPathIterator(null); //profi hint?

        return new DivingAction(0.5f, (float)(-Math.PI));
    }
}
