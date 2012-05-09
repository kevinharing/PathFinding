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
package searcher;

import searcher.strategy.*;
import tileworld.I_Cost;
import tileworld.I_TileWorld;
import tileworld.TileType;
import utilities.NodeUtilities;

/**
 *
 * @author Kevin
 */
public final class Solver implements I_Cost {
    
    private Strategy strategy;
    private Node[][] grid;
    private I_TileWorld tileWorld;
    
    public Solver(I_TileWorld tileWorld, SearchStrategy strategy) {
        this.setTileWorld(tileWorld);
        this.initialize();
        this.setStrategy(strategy);
    }
    
    /**
     * Solve the problem(i.e. find the path)
     * 
     * @return the result
     */
    public AlgorithmResults solve() { 
        long timeStart = System.nanoTime();
        
        AlgorithmResults results = strategy.solve();
        results.setTime(System.nanoTime() - timeStart);
        
        return results;
    }
    
    /**
     * 
     * Initialize the Solver instance
     * 
     */
    public void initialize() {
        this.generateGrid();
        this.fillNodes();
    }
    
    /**
     * 
     * Generate the grid of Nodes
     * 
     */
    private void generateGrid() {
        this.setGrid(new Node[tileWorld.getWidth()][tileWorld.getHeight()]);    
            
        for(int currentY = 0; currentY < tileWorld.getHeight(); ++currentY) { 

            for(int currentX = 0; currentX < tileWorld.getWidth(); ++currentX) {
                
                Node current = new Node(tileWorld, tileWorld.getTileType(currentX, currentY), 
                        currentX, currentY, INFINITY, INFINITY);
                grid[currentX][currentY] = current;
            
            }

        }     
    }
    
    /**
     * 
     * Fill the Nodes with both the straight and diagonally adjacent Nodes
     * 
     */
    private void fillNodes() {
        for(int currentY = 0; currentY < grid[1].length; ++currentY) { 
            
            for(int currentX = 0; currentX < grid.length; ++currentX) { 
                
                Node current = grid[currentX][currentY];
                
                for(int otherY = 0; otherY < grid[1].length; ++otherY) { 

                    for(int otherX = 0; otherX < grid.length; ++otherX) { 

                        Node other = grid[otherX][otherY];
                        if(other.getTileType() == TileType.NONWALKABLE) {
                            
                            continue;
                            
                        } else if(NodeUtilities.isLocatedDiagonallyAdjacent(current, other)) {
                            
                            current.getDiagonalNeighbors().add(other);
                            
                        } else if(NodeUtilities.isLocatedStraightAdjacent(current, other)) {
                            
                            current.getStraigthNeighbors().add(other);
                            
                        }

                    }
                }
            }
        }
    }
    
    /**
     * @return the strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy the strategy to set
     */
    public void setStrategy(SearchStrategy strategy) {
        switch(strategy) {
            case A_STAR:
                this.strategy = new AStar(this);
                break;
            case DIJKSTRA:
                this.strategy = new Dijkstra(this);
                break;
            case GREEDY:
                this.strategy = new Greedy(this);
                break;
        }
    }

    /**
     * @return the grid
     */
    public Node[][] getGrid() {
        return grid;
    }

    /**
     * @param grid the grid to set
     */
    public void setGrid(Node[][] grid) {
        this.grid = grid;
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
    
}
