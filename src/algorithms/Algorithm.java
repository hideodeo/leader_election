package algorithms;

import entities.MyCycle;
import entities.MyVertex;

import java.util.Map;

/**
 * Algorithm
 *
 * interface for algorithm class
 */
public interface Algorithm {
    /**
     * method to elect leaders
     * @return a map of cycle and leader pairs
     */
    Map<MyCycle, MyVertex> solve();
}
