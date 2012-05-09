package tileworld;

/**
 * Defines constants for costs.
 * 
 * @author Dennis Breuker
 */
public interface I_Cost {
    /**
     * Infinity cost. No cost is higher than this.
     */
    int INFINITY = 999999999;
    /**
     * The normal and diagonal costs for all walkable types.
     */
    int ROAD_COST = 10;
    int ROAD_DIAGONAL_COST = 14;
    int SAND_COST = 14;
    int SAND_DIAGONAL_COST = 20;
    int WATER_COST = 20;
    int WATER_DIAGONAL_COST = 28;
    int MOUNTAIN_COST = 24;
    int MOUNTAIN_DIAGONAL_COST = 34;
}
