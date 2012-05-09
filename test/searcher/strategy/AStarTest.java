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

import org.junit.*;
import static org.junit.Assert.*;
import searcher.AlgorithmResults;
import searcher.Solver;
import tileworld.I_TileWorld;
import tileworld.TileWorld;

/**
 *
 * @author Kevin
 */
public class AStarTest {
    
    public AStarTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of solve method, of class AStar.
     */
    @Test
    public void test1() {
        System.out.println("A* Test i1");  
         
        Solver solver = new Solver(new TileWorld("i1.png"), SearchStrategy.A_STAR);
        AlgorithmResults expResult = new AlgorithmResults(580, 967);
        AlgorithmResults result = solver.solve();
        
        print(result, expResult);
        
        assertEquals("BestPathCost does not match!", expResult.getBestPathCost(), result.getBestPathCost());
    }
    
    @Test
    public void test2() {
        System.out.println("A* Test i2");  
        
        Solver solver = new Solver(new TileWorld("i2.png"), SearchStrategy.A_STAR);
        AlgorithmResults expResult = new AlgorithmResults(572, 742);
        AlgorithmResults result = solver.solve();
        
        print(result, expResult);
                
        assertEquals("BestPathCost does not match!", expResult.getBestPathCost(), result.getBestPathCost());
    }
    
    @Test
    public void test3() {
        System.out.println("A* Test i3");  
        
        Solver solver = new Solver(new TileWorld("i3.png"), SearchStrategy.A_STAR);
        AlgorithmResults expResult = new AlgorithmResults(-1, 1162);
        AlgorithmResults result = solver.solve();
        
        print(result, expResult);
                
        assertEquals("BestPathCost does not match!", expResult.getBestPathCost(), result.getBestPathCost());
    }
    
    private void print(AlgorithmResults result, AlgorithmResults expResult) {
        System.out.println("( Cost: " + result.getBestPathCost() + ", Nodes: " + result.getNodesExpanded() + 
                " ) EXPECTED -> ( Cost: " +
                expResult.getBestPathCost() + ", Nodes: " + expResult.getNodesExpanded() + " )");
    }
}
