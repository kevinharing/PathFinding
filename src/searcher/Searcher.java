package searcher;

import searcher.strategy.AStar;
import searcher.strategy.Dijkstra;
import searcher.strategy.Greedy;
import searcher.strategy.SearchStrategy;
import tileworld.I_TileWorld;
import tileworld.TileWorld;

/**
 * Main program for AI_P. It searches a tile world for a path from a start point
 * to an end point. The algorithms used are
 * - A* 
 * - Dijkstra 
 * - Greedy Search
 * NOTE: this is the class you have to adapt for the assignment!
 *
 * @author Dennis Breuker
 */
public class Searcher {

    /**
     * This is the main method. A number of tile worlds will be searched.
     * The method expects at least one argument.
     * The first argument denotes the number of tile worlds that will be 
     * searched. So "java -jar Search -3" searches three files, viz. i1.png, 
     * i2.png and i3.png. Instead of a number, the first argument can be a 
     * filename. So "java -jar Search myOwnFile" only searches myOwnFile.png.
     * The second argument can be left empty. If not, it should denote "show".
     * This means that the solutions will be shown on screen, using Swing.
     * So "java -jar Search -2 show" searches two tile worlds, and shows the
     * found solutions on screen.
     * For every tile world an output image is written: <filename>_a.png for A*,
     * <filename>_d.png for Dijkstra, and <filename>_g.png for Greedy Search.
     * The output text is written to System.out.
     *
     * @param args The command line options/arguments.
     */
    public static void main(String[] args) {
        String filename;
        boolean batch = true;
        int nrInputFiles;
        boolean showSolutions = false;

        if (args.length < 1) {
            System.out.println("At least one argument expected");
            printExamples();
            return;
        }

        // check which file(s) have/has to be loaded
        if (args[0].startsWith("-")) {
            String nrInputFilesStr = args[0].substring(1);
            nrInputFiles = Integer.parseInt(nrInputFilesStr);
        } else {
            batch = false;
            nrInputFiles = 1;
        }

        // check if solutions have to be shown on screen
        if (args.length == 2) {
            if ("show".equals(args[1])) {
                showSolutions = true;
            } else {
                System.out.println("Illegal second argument: " + args[1]);
                printExamples();
                return;
            }

        }

        // search all files
        for (int fileNr = 1; fileNr <= nrInputFiles; fileNr++) {
            // determine file to search
            if (batch) {
                filename = "i" + fileNr;
            } else {
                filename = args[0];
            }

            // search tile world
            ExperimentResults info = search(filename, showSolutions);

            // print the results to System.out
            printAllResults(filename, info);
        }
    }

    /**
     * Prints examples on screen of how to run the program 
     */
    private static void printExamples() {
        System.out.println("Examples:");
        System.out.println("  java -jar Search.jar -2");
        System.out.println("  java -jar Search.jar -3 show");
        System.out.println("  java -jar Search.jar testfile");
        System.out.println("  java -jar Search.jar tilefile show");
    }

    /**
     * Searches a file for the best path using three algorithms. The solutions
     * can be shown on screen.
     * NOTE: This is the method you have to adapt for the assignment!
     * Variables dijkstra, aStar and greedySearch (of type AlgorithmResutls) 
     * have to be given the correct value. In this dummy version they are 
     * all initialized to null.
     *
     * @param filename The file (without extension) to be searched.
     * @param showSolutions Indicates if solutions should be shown on screen.
     * @return All relevant statistics for the three experiments.
     */
    public static ExperimentResults search(String filename, boolean showSolutions) {
        ExperimentResults experimentResults = new ExperimentResults();
        I_TileWorld world;
        Solver solver;

        // Dijkstra
        world = new TileWorld(filename + ".png");
        solver = new Solver(world, SearchStrategy.DIJKSTRA);
        AlgorithmResults dijkstraResult = solver.solve();
        experimentResults.setDijkstra(dijkstraResult);
        world.save(filename + "_d");
        if (showSolutions) {
            world.show(filename + " Dijkstra", 10, 0, 0);
        }

        // A*
        world = new TileWorld(filename + ".png");
        solver = new Solver(world, SearchStrategy.A_STAR);
        AlgorithmResults aStarResult = solver.solve();
        experimentResults.setaStar(aStarResult);
        world.save(filename + "_a");
        if (showSolutions) {
            world.show(filename + " A*", 10, 420, 0);
        }

        // Greedy
        world = new TileWorld(filename + ".png");
        solver = new Solver(world, SearchStrategy.GREEDY);
        AlgorithmResults greedyResult = solver.solve();
        experimentResults.setGreedy(greedyResult);
        world.save(filename + "_g");
        if (showSolutions) {
            world.show(filename + " Greedy Search", 10, 0, 340);
        }

        return experimentResults;
    }

    /**
     * Prints the results for all three algorithms on System.out.
     *
     * @param filename Filename containing the world that has been searched.
     * @param info Results of the experiments for all three algorithms for that file.
     */
    public static void printAllResults(String filename, ExperimentResults info) {
        System.out.println("#######################");
        System.out.println("Testcase: " + filename);
        System.out.println("#######################");
        printAlgorithmResult("A*", info.getaStar());
        System.out.println("-------------------------------------");
        printAlgorithmResult("Dijkstra", info.getDijkstra());
        System.out.println("-------------------------------------");
        printAlgorithmResult("Greedy Search", info.getGreedySearch());
    }

    /**
     * Prints the results of one algorithm on System.out.
     *
     * @param algorithmString Name of the algorithm used (A*, Dijkstra, ...).
     * @param info Results of the algorithm.
     */
    private static void printAlgorithmResult(String algorithmString, AlgorithmResults info) {
        System.out.println(algorithmString);
        if (info == null) {
            System.out.println("No results found.");
            return;
        }
        System.out.println("#nodes: " + info.getNodesExpanded());
        System.out.println("#path cost: " + info.getBestPathCost());
        System.out.println("#time: " + info.getTime() + " nanoseconds");
    }
}
