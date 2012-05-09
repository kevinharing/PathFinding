/*
 * The MIT License
 *
 * Copyright 2012 Kevin Haring <K.KevinHaring@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package searcher.strategy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import tileworld.I_Cost;
import tileworld.I_TileWorld;
import tileworld.TileType;



/**
 *
 * @author Kevin
 */
public class Node implements I_Cost {
    
    private int tentativeCost;
    private int heuristicCost;
    private boolean visited;
    private int x;
    private int y;
    private I_TileWorld tileWorld;
    private TileType tileType;
    private ArrayList<Node> straightNeighbors, diagonalNeighbors;
    private Node predecessor;
    
    private Node() {
        this.visited = false;
        this.straightNeighbors = new ArrayList<Node>();
        this.diagonalNeighbors = new ArrayList<Node>();
        this.predecessor = null;
    }
    
    public Node(I_TileWorld tileWorld, TileType tileType, int x, int y, int tentativeCost, int heuristicCost)
    {
        this();
        
        this.tileWorld = tileWorld;
        this.tileType = tileType;
        this.x = x;
        this.y = y;
        this.tentativeCost = tentativeCost;
        this.heuristicCost = heuristicCost; 
    }
    
    /**
     * 
     * @param other Node
     * @return true if straight adjacent to other, false otherwise
     */
    public boolean isStraightAdjacentTo(Node other) {
        return straightNeighbors.contains(other);
    }
    
    /**
     * 
     * @param other Node
     * @return true if diagonally adjacent to other, false otherwise
     */
    public boolean isDiagonallyAdjacentTo(Node other) {
        return diagonalNeighbors.contains(other);
    }
    
    /**
     * 
     * @param other Node
     * @return cost of traveling to other
     */
    public int calculateCostTo(Node other) {
        if(this.isDiagonallyAdjacentTo(other)) {
            
            return other.getTileType().getDiagonalCost();
            
        } else if(this.isStraightAdjacentTo(other)) {
            
            return other.getTileType().getCost();
            
        } else {
            
            return INFINITY;
            
        }       
    }

    @Override
    public String toString() {
        return "Node { (" + this.getX() + ", " + this.getY() +") cost = " + this.getTentativeCost() 
                + ", heuristic = " + this.getHeuristicCost() + ", total =" + this.getHeuristicPlusTentative() + " }";
    }
    
    /**
     * @return the tentativeCost
     */
    public int getTentativeCost() {
        return tentativeCost;
    }

    /**
     * @param tentativeCost the tentativeCost to set
     */
    public void setTentativeCost(int tentativeCost) {
        this.tentativeCost = tentativeCost;
    }

    /**
     * @return the estimation
     */
    public int getHeuristicCost() {
        return heuristicCost;
    }

    /**
     * @param value the estimation to set
     */
    public void setHeuristicCost(int value) {
        this.heuristicCost = value;
    }
    
    public int getHeuristicPlusTentative() {
        return heuristicCost + tentativeCost;
    }

    /**
     * @return the visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * @param visited the visited to set
     */
    public void setVisited(boolean visited) {
        this.visited = visited;      
    }
    
    public void drawAsVisited() {
        this.tileWorld.setTileType(x, y, TileType.PATH);
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the tileWorld
     */
    public I_TileWorld getTileWorld() {
        return tileWorld;
    }

    /**
     * @param tileWorld the tileWorld to set
     */
    public void setTileWorld(I_TileWorld tileWorld) {
        this.tileWorld = tileWorld;
    }

    /**
     * @return the tileType
     */
    public TileType getTileType() {
        return tileType;
    }

    /**
     * @param tileType the tileType to set
     */
    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    /**
     * @return the straithNeighbors
     */
    public List<Node> getStraigthNeighbors() {
        return straightNeighbors;
    }

    /**
     * @param straigthNeighbors the straithNeighbors to set
     */
    public void setStraigthNeighbors(ArrayList<Node>straigthNeighbors) {
        this.straightNeighbors = straigthNeighbors;
    }

    /**
     * @return the diagonalNeighbors
     */
    public List<Node> getDiagonalNeighbors() {
        return diagonalNeighbors;
    }

    /**
     * @param diagonalNeighbors the diagonalNeighbors to set
     */
    public void setDiagonalNeighbors(ArrayList<Node> diagonalNeighbors) {
        this.diagonalNeighbors = diagonalNeighbors;
    }
    
    /**
     * 
     * @return neighbors, both visited and unvisited, 
     * straight adjacent and diagonally adjacent
     */
    public List<Node> getNeighbors() {
        List<Node> nodes = new ArrayList<Node>(straightNeighbors);
        nodes.addAll(diagonalNeighbors);
        return nodes;
    }
    
    /**
     * 
     * @return neighbors unvisited, both straight adjacent and diagonally adjacent
     */
    public List<Node> getUnvisitedNeighbors() {
        List<Node> nodes = this.getNeighbors();

        for(Iterator it = nodes.iterator(); it.hasNext();) {
            Node node = (Node) it.next();
            
            if(node.isVisited())
                it.remove();
        }
        return nodes;
    }

    /**
     * @return the predecessor
     */
    public Node getPredecessor() {
        return predecessor;
    }

    /**
     * @param predecessor the predecessor to set
     */
    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

}
