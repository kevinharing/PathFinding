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
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import searcher.AlgorithmResults;
import searcher.Solver;
import tileworld.I_Cost;
import tileworld.I_TileWorld;
import utilities.NodeUtilities;

/**
 *
 * @author Kevin
 */
public abstract class Strategy implements I_Cost {
    
    private I_TileWorld tileWorld;
    private Node[][] grid;
    private PriorityQueue<Node> unsettledNodes;
    
    public Strategy(Solver solver) {
        this.tileWorld = solver.getTileWorld();
        this.grid = solver.getGrid();      
        this.unsettledNodes = new PriorityQueue<Node>(grid.length * grid[1].length, this.getComparator());
    }
    
    /**
     * 
     * Solve the problem(i.e. find the path)
     * 
     * @return the result
     */
    public AlgorithmResults solve() {
        int nodesExpanded = 0;       
        Node start = getStartNode();
        Node end = getEndNode();
        
        setup(start, end);
        
        unsettledNodes.add(start);
      
        while(!unsettledNodes.isEmpty()) {
            
            Node current = unsettledNodes.poll();
            
            if(current.equals(end)) {
                return processResults(current, nodesExpanded);
            }
            
            for(Node other : current.getUnvisitedNeighbors()) {
                       
                unsettledNodes.remove(other);
                int potentialCost = current.getTentativeCost() 
                        + current.calculateCostTo(other);

                if(other.getTentativeCost() >= potentialCost) {

                    other.setTentativeCost(potentialCost);
                    other.setPredecessor(current);

                }
                unsettledNodes.add(other);

            }
            
            current.setVisited(true);
            nodesExpanded++;
        
        }
        return new AlgorithmResults(-1, nodesExpanded);
    }
    
    /**
     * 
     * @return the start Node 
     */
    private Node getStartNode() {
        return grid[tileWorld.findStartX()][tileWorld.findStartY()];
    }
    
    /**
     * 
     * @return the end/destination Node
     */
    private Node getEndNode() {
        return grid[tileWorld.findEndX()][tileWorld.findEndY()];
    }
    
    /**
     * 
     * Processes the result. This includes printing the path taken onto the image,
     * setting the solution path, setting the path cost and setting the number of nodes
     * expanded.
     * 
     * @param end Node
     * @param nodesExpanded number of nodes expanded
     * @return the result
     */
    private AlgorithmResults processResults(Node end, int nodesExpanded) {
        
        AlgorithmResults results = new AlgorithmResults();
        List<Node> solutionPath = new ArrayList<Node>();        
        Node node = end;
        
        while(node != null) {
            node.drawAsVisited();
            solutionPath.add(node);
            node = node.getPredecessor();
        }
        
        results.setSolutionPath((ArrayList<Node>)solutionPath);
        results.setBestPathCost(end.getTentativeCost());
        results.setNodesExpanded(nodesExpanded);
        
        return results;
    }
    
    /**
     * 
     * Set the tentative cost of the start Node to 0.
     * Set the tentative cost of the other Nodes to INFINITY.
     * Set the heuristic value of the Nodes.
     * 
     * @param start
     * @param end 
     */
    private void setup(Node start, Node end) {
        
        for(int currentY = 0; currentY < grid[1].length; ++currentY) { // Iterate over Y-Axis
            
            for(int currentX = 0; currentX < grid.length; ++currentX) { // Iterate over X-Axis
                
                Node current = grid[currentX][currentY];
                
                if(current.equals(start)) {

                    current.setTentativeCost(0);

                } else {
                    
                    current.setTentativeCost(INFINITY);
                    
                }
                
                current.setHeuristicCost(NodeUtilities.calculateCustomHeuristic(current, end));

            }
        }
        
    }
    
    /**
     * 
     * @return the Comparator
     */
    protected abstract Comparator<Node> getComparator();
    
}
