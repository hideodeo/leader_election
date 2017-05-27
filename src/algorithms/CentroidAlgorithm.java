package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * centroid algorithm
 */
public class CentroidAlgorithm  implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * constructor
     * @param graphIn graph
     * @param cycleListIn root vertex
     */
    public CentroidAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * method to elect leaders
     * @return a map of cycle and leader pairs
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();

        /** elect leader for each cycle so that it minimizes the longest distance from the leader to any other vertexes */
        for (MyCycle cycle: cycleList){
            MyVertex leader = null;
            int dist = Integer.MAX_VALUE;
            int buff = 0;
            for (MyVertex v: cycle.getVertices()){
                buff = graph.getLongestDistance(v, cycle.getVertices());

                if (leader == null)
                    leader = v;
                else if (buff < dist){
                    dist = buff;
                    leader = v;
                }
            }
            resultMap.put(cycle, leader);
        }
        return resultMap;
    }
}
