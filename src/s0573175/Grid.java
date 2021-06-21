package s0573175;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.*;

public class Grid {

    public Tile[][] tiles;          // for importing tiles into the grid with its costs

    public int width;               // size of the game, seperated by width and height
    public int height;
    public double scale;            // scale for making the grid (more specific the tile size) rougher or finer

    public Grid (int gameWidth, int gameHeight, Path2D[] gameObstacles, double scale){
        this.scale = scale;
        width = (int) (gameWidth * scale);
        height = (int) (gameHeight * scale);

        tiles = new Tile[width][height];
        var walkableMap = getWalkableMap(gameObstacles);

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                tiles[x][y] = new Tile(walkableMap[x][y], new Point((int)(x/scale), (int)(-y/scale)), x, y);
            }
        }
    }

    public Point[] findPath(Point startPoint, Point endPoint){
        var startTile = tiles[(int) (startPoint.x * scale)][(int) (-startPoint.y * scale)];
        var endTile = tiles[(int) (endPoint.x * scale)][(int) (-endPoint.y * scale)];

        var openTiles = new PriorityQueue<>(Comparator.comparing(Tile::fCost));
        var closedTiles = new HashSet<Tile>();

        openTiles.add(startTile);

        while (openTiles.size() > 0){
            var currentTile = openTiles.remove();

            closedTiles.add(currentTile);

            if(currentTile == endTile){
                return retracePath(startTile, endTile);
            }

            for(var neighbour : getNeighbours(currentTile)){
                if(!neighbour.walkable || closedTiles.contains(neighbour)){
                    continue;
                }

                var newCostToNeighbour = currentTile.gCost + calculateDistance(currentTile, neighbour);
                if(newCostToNeighbour < currentTile.gCost || !openTiles.contains(neighbour)){
                    neighbour.gCost = newCostToNeighbour;
                    neighbour.hCost = calculateDistance(neighbour, endTile);
                    neighbour.parent = currentTile;
                    if(!openTiles.contains(neighbour)){
                        openTiles.add(neighbour);
                    }
                }
            }
        }
        return null;
    }

    public Boolean[][] getWalkableMap(Path2D[] obstalces){

        Boolean[][] walkableMap = new Boolean[width][height];

        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                walkableMap[x][y] = true;
                for(Path2D obstacle : obstalces){
                    if(obstacle.contains(new Point((int)(x/scale), (int)(-y/scale)))){
                        walkableMap[x][y] = false;
                        break;
                    }
                }
            }
        }

        // Set neighbours of unwalkable tiles (obstacles) also to false to avoid the diver to get stuck again
        for (int x = 1; x < width-1; x++){
            for (int y = 1; y < height-1; y++){
                if(!walkableMap[x][y]){
                    for (int i = x - 1; i <= x; i++){
                        for (int j = y - 1; j <= y; j++){
                            if(walkableMap[i][j]) {
                                walkableMap[i][j] = false;
                            }
                        }
                    }
                }
            }
        }
        return walkableMap;
    }

    public int calculateDistance (Tile tileStart, Tile tileEnd){
        int distanceX = Math.abs(tileStart.gridX - tileEnd.gridX);
        int distanceY = Math.abs(tileStart.gridY - tileEnd.gridY);

        if(distanceY < distanceX){
            return 14 * distanceY + 10 * (distanceX - distanceY);
        }

        return 14 * distanceX + 10 * (distanceY - distanceX);

    }

    public ArrayList<Tile> getNeighbours (Tile currentTile){

        ArrayList<Tile> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++){
            for (int y = -1; y <= 1; y++){

                if(x == 0 && y == 0){
                    continue;
                }

                int checkX = currentTile.gridX + x;
                int checkY = currentTile.gridY + y;

                if(checkX >= 0 && checkX < width && checkY >= 0 && checkY < height){
                    neighbours.add(tiles[checkX][checkY]);
                }
            }
        }
        return neighbours;
    }

    public Point[] retracePath(Tile startTile, Tile endTile){
        var path = new ArrayList<Tile>();
        var currentTile = endTile;

        while (currentTile != startTile){
            path.add(currentTile);
            currentTile = currentTile.parent;
        }

        Collections.reverse(path);
        return simplifyPath(path);
    }

    public Point[] simplifyPath(ArrayList<Tile> path){
        var pathPoints = new ArrayList<Point>();
        var directionOld = new Point(0,0);

        for (int i = 1; i < path.size(); i++){
            var directionNew = new Point(path.get(i - 1).gridX - path.get(i).gridX, path.get(i - 1).gridY - path.get(i).gridY);
            if(directionNew != directionOld){
                pathPoints.add(path.get(i).gamePosition);
            }
            directionOld = directionNew;
        }

        var pathPointsArray = new Point[pathPoints.size()];
        pathPoints.toArray(pathPointsArray);
        return pathPointsArray;

    }

}
