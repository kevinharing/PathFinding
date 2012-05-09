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
package utilities;

import searcher.strategy.Node;
import tileworld.TileType;

/**
 *
 * @author Kevin
 */
public final class NodeUtilities {
    
    /**
     * 
     * @param current Node
     * @param other Node
     * @return true if located straight adjacent, false otherwise
     */
    public static boolean isLocatedStraightAdjacent(Node current, Node other) {
        int distanceX = calculateDistance(current.getX(), other.getX());
        int distanceY = calculateDistance(current.getY(), other.getY());
        
        return ((distanceX == 1 && distanceY == 0) || (distanceX == 0 && distanceY == 1));
    }
    
    /**
     * 
     * @param current Node
     * @param other Node
     * @return true if located diagonally adjacent, false otherwise
     */
    public static boolean isLocatedDiagonallyAdjacent(Node current, Node other) {
        int distanceX = calculateDistance(current.getX(), other.getX());
        int distanceY = calculateDistance(current.getY(), other.getY());
        
        return (distanceX == 1 && distanceY == 1);
    }
    
    /**
     * 
     * @param from Node.x or Node.y position
     * @param to Node.x or Node.y position
     * @return Calculated distance
     */
    public static int calculateDistance(int from, int to) {
        return Math.abs(from - to);
    }
    
    /*
     * 
     * @return Calculated heuristic value
     */
    public static int calculateManhattenDistance(Node current, Node other) {
        return Math.abs(other.getX() - current.getX()) + Math.abs(other.getY() - current.getY());
    }
    
    /**
     * 
     * 
     * @param current Node
     * @param other Node
     * @return Calculated heuristic value
     */
    public static int calculateCustomHeuristic(Node current, Node other) {
        int distanceX = calculateDistance(other.getX(), current.getX());
        int distanceY = calculateDistance(other.getY(), current.getY());
        int distanceDiagonal = Math.min(distanceX, distanceY);  
        
        return distanceDiagonal * TileType.ROAD_DIAGONAL_COST 
                + (distanceX + distanceY - 2 * distanceDiagonal) * TileType.ROAD_COST;    
    }
    
}
