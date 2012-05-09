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

import java.util.Comparator;
import java.util.PriorityQueue;
import searcher.Solver;
import utilities.NodeUtilities;

/**
 *
 * @author Kevin
 * 
 * Implementation of the Greedy pathfinding algorithm
 */
public class Greedy extends Strategy {
    
    public Greedy(Solver solver) {
        super(solver);
    }

    @Override
    protected final Comparator<Node> getComparator() {
        return new Comparator<Node>() {

            @Override
            public int compare(Node current, Node other) {
                

                if (current.getHeuristicCost() > other.getHeuristicCost()) {
                    
                    return 1;
                  
                } else if (current.getHeuristicCost() < other.getHeuristicCost()) {
                    
                    return -1;
                    
                } else {
                    
                    return -1;
                    
                }
            }
        };
    }
    
}
