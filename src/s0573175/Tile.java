package s0573175;

import java.awt.Point;

public class Tile {
    public boolean walkable;        // checking if obstales or not
    public Point gamePosition;    // position inside game => x and y axis

    public int gridX;               // grid axis
    public int gridY;

    public int gCost;               // cost from start/current(Diver) to end(Pearls)
    public int hCost;               // cost from end(Pearls) to start/current(Diver)

    public Tile parent;             // parent required to reference previous steps to retrace from end to start

    public Tile(boolean isWalkable, Point currGamePosition, int currGridX, int currGridY){
        walkable = isWalkable;
        gamePosition = currGamePosition;
        gridX = currGridX;
        gridY = currGridY;
    }

    public int fCost(){
        return gCost + hCost;
    }

}
