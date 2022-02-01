package io.github.pirateducks.pathfinding;

import com.badlogic.gdx.graphics.Texture;
import io.github.pirateducks.level.LevelManager;

import java.sql.Array;
import java.util.*;

/**
 * A class used to take a level and produce pathfinding results
 */
public class PathFinder {

    private final LevelManager manager;
    private final int gradient;
    private boolean[][] tileMap;

    /**
     * NOTE: the greater the gradient, the less accurate the result is to the true result but results will be generated faster
     *
     * @param manager  The LevelManager to generate the search space from
     * @param gradient How small each tile in the tilemap should be (ie 5 game units large tiles)
     */
    public PathFinder(LevelManager manager, int gradient) {
        this.manager = manager;
        this.gradient = gradient;

        generateSearchSpace();

    }

    /**
     * Generates the tilemap that can be traversed
     */
    private void generateSearchSpace() {
        tileMap = new boolean[(int) (manager.getMap().getWidth() / gradient) + 1][(int) (manager.getMap().getHeight() / gradient) + 1];

        for (boolean[] row : tileMap) {
            Arrays.fill(row, true);
        }

        // looping through the map to check collision
        for (int x = 0; x < manager.getMap().getWidth(); x += gradient / 2) {
            for (int y = 0; y < manager.getMap().getHeight(); y += gradient / 2) {
                // if this tile is already invalid, skip the tile
                if (!tileMap[x / gradient][y / gradient]) {
                    continue;
                }

                if (manager.isOnLand(x, y)) {
                    tileMap[x / gradient][y / gradient] = false;
                }
            }
        }

    }

    /**
     * @param sourceX      the x source locaiton
     * @param sourceY      the y source location
     * @param destinationX the x destination
     * @param destinationY the y destination
     * @return the path as a list of checkpoint, or null if no path could be found
     */
    public List<Checkpoint> getPath(float sourceX, float sourceY, float destinationX, float destinationY) {

        // checking if the start or finish location is an invalid
        if (!isTraversable(sourceX, sourceY) || !isTraversable(destinationX, destinationY)) {
            return null;
        }

        // converting locations into tilemap equivelents
        Checkpoint source = new Checkpoint(sourceX, sourceY, gradient);
        Checkpoint dest = new Checkpoint(destinationX, destinationY, gradient);

        PriorityQueue<PathNode> open = new PriorityQueue<>();

        List<PathNode> close = new ArrayList<>();

        open.add(new PathNode(source, 0, dest, null));
        int count = 0;
        PathNode solutionNode = null;
        while (!open.isEmpty()) {
            count++;
            if(count > 10000){
                break;
            }
            PathNode currentNode = open.poll();
            if (currentNode.checkpoint.equals(dest)) {
                // if we have found the solution
                solutionNode = currentNode;
                break;
            }

            // looping through successor nodes
            for (PathNode successorNode : successor(currentNode)) {
                if (open.contains(successorNode)) {
                    // already in the fringe
                    continue;
                } else if (close.contains(successorNode)) {
                    // already visited
                    continue;
                } else {
                    // path cost is too great, limiting scope
                    if (successorNode.fx > 10000) {
                        continue;
                    }
                    open.add(successorNode);
                }

            }

        }

        if (solutionNode == null) {
            return null;
        }

        List<Checkpoint> checkpoints = new ArrayList<>();
        checkpoints.add(dest);
        PathNode nextNode = solutionNode;

        while (nextNode.parent != null) {
            checkpoints.add(nextNode.parent.checkpoint);
            nextNode = nextNode.parent;
        }

        Collections.reverse(checkpoints);

        return checkpoints;
    }

    /**
     * Used to lookup a location in the tilemap to check if the location is traversable
     *
     * @param x the x coord in game space
     * @param y the y coord in game space
     * @return if the location is traversable
     */
    public boolean isTraversable(float x, float y) {
        try {
            return tileMap[(int) x / gradient][(int) y / gradient];
        } catch(ArrayIndexOutOfBoundsException e){
            // duck has been attempted to spawn out of the map
            return false;
        }
    }

    /**
     * Used to get a list of all successor nodes of the provided node
     *
     * @param node The node to get the successors of
     * @return The list of successors
     */
    private List<PathNode> successor(PathNode node) {
        List<PathNode> toReturn = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {

                if (x == 0 && y == 0) {
                    // cannot travel to itself as a successor
                    continue;
                }

                float calcX = node.checkpoint.x + (x * gradient);
                float calcY = node.checkpoint.y + (y * gradient);

                if(calcX >= manager.getMap().getWidth()){
                    continue;
                }
                if(calcY >= manager.getMap().getHeight()){
                    continue;
                }

                if (isTraversable(calcX, calcY)) {
                    toReturn.add(new PathNode(new Checkpoint(calcX, calcY, gradient), (float) Math.sqrt(Math.abs(x * gradient) + Math.abs(y * gradient)), node.dest, node));
                }
            }
        }
        return toReturn;
    }

    /**
     * Used as to avoid repeated calculations and to store the checkpoint within the priority list
     */
    private class PathNode implements Comparable<PathNode> {
        float fx;
        float gx;
        Checkpoint checkpoint;
        Checkpoint dest;
        PathNode parent;

        public PathNode(Checkpoint checkpoint, float gx, Checkpoint dest, PathNode parent) {
            this.checkpoint = checkpoint;
            this.dest = dest;
            this.parent = parent;
            if (parent == null) {
                this.gx = gx;
            } else {
                this.gx = gx + parent.gx;
            }
            fx = hx(dest) + this.gx;
        }

        private float hx(Checkpoint dest) {
            return (float) Math.sqrt(Math.pow(checkpoint.x - dest.x, 2) + Math.pow(checkpoint.y - dest.y, 2));
        }

        /**
         * comparator for the priority list
         *
         * @param o the other PathNode to compare it to
         * @return The comparison of fx
         */
        @Override
        public int compareTo(PathNode o) {
            return (int) (fx - o.fx);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PathNode pathNode = (PathNode) o;
            return checkpoint.equals(pathNode.checkpoint);
        }

        @Override
        public String toString() {
            return "PathNode{" +
                    "fx=" + fx +
                    ", gx=" + gx +
                    ", checkpoint=" + checkpoint +
                    ", dest=" + dest +
                    ", parent=" + parent +
                    '}';
        }
    }

}
