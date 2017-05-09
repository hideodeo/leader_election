package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SharedVertex
 *
 * class to select leaders based on # of shared vertexes
 */
public class SharedVertexAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * constructor
     * @param graphIn graph
     * @param cycleListIn root vertex
     */
    public SharedVertexAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
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

        /** calculate # of adjacent cycles which share v */
        for (MyVertex v : graph.getVertices()){
            v.setNumOfAdCycles(0);
        }

        for (MyCycle cycle: cycleList){
            for (MyVertex v: cycle.getVertices()){
                v.setNumOfAdCycles(v.getNumOfAdCycles() + 1);
            }
        }

        /** elect leader for each cycle*/
        for (MyCycle cycle: cycleList){
            MyVertex leader = null;
            for (MyVertex v: cycle.getVertices()){
                if (leader == null)
                    leader = v;
                else if (leader.getNumOfAdCycles() < v.getNumOfAdCycles())
                    leader = v;
            }
            resultMap.put(cycle, leader);
        }
        return resultMap;
    }
}
