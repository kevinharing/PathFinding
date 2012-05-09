package searcher;

import java.util.ArrayList;
import searcher.strategy.Node;

/**
 * Class containing all results of the experiments of one algorithm (e.g., A*).
 * 
 * @author Dennis Breuker
 */
public class AlgorithmResults {
    private int bestPathCost;
    private int nodesExpanded;
    private long time;
    /**
     * Integer list containing the indices of the tiles in the best path.
     */
    private ArrayList<Node> solutionPath = new ArrayList<Node>();

    public AlgorithmResults() {
        this(0, 0);
    }

    /**
     * This constructor immediately initializes the class attributes.
     * 
     * @param bestPathCost The cost of the best path
     * @param nodesExpanded Number of expanded nodes
     */
    public AlgorithmResults(int bestPathCost, int nodesExpanded) {
        this.bestPathCost = bestPathCost;
        this.nodesExpanded = nodesExpanded;
    }

    public int getBestPathCost() {
        return bestPathCost;
    }

    public void setBestPathCost(int cost) {
        this.bestPathCost = cost;
    }

    public int getNodesExpanded() {
        return nodesExpanded;
    }

    public void setNodesExpanded(int nodesExpanded) {
        this.nodesExpanded = nodesExpanded;
    }

    public ArrayList<Node> getSolutionPath() {
        return solutionPath;
    }

    public void setSolutionPath(ArrayList<Node> solutionPath) {
        this.solutionPath = solutionPath;
    }
    
    /**
     * 
     * Print the solution path
     * 
     */
    public void printSoluctionPath() {
        System.out.println("\n------");
        System.out.println("SOLUTION PATH:");
        System.out.println("------\n");
        for(Node node : solutionPath) {
            System.out.println(node);
        }
        System.out.println("------\n");
    }

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }
}
