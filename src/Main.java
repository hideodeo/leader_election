import algorithms.Algorithm;
import algorithms.RandomAlgorithm;
import algorithms.SharedVertexAlgorithm;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import generators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Hideo on 17/04/11.
 *
 */
public class Main {
    public static void main(String[] args) {
        /** parameters for simulation */
        int simulationTimes = 10;

        /** excute simulations */
        for (int i=0; i < simulationTimes; i++) {
            /** set number of vertexes */
            for (int vertexNum=20; i<=100; i+=20) {
                execute("NWS", "BFS", vertexNum, "Random");
            }
        }
    }

    /**
     * This method executes a simulation.
     *
     * @param graphNameIn
     * @param treeNameIn
     * @param vertexNumIn
     * @param algorithmNameIn
     */
    public static void execute(String graphNameIn, String treeNameIn, int vertexNumIn, String algorithmNameIn) {
        /** create graph */
        MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphNameIn, vertexNumIn).create();

        /** get vertexes from graph */
        List<MyVertex> vertexList = new ArrayList<MyVertex>(graph.getVertices());

        /** create tree */
        MyGraph<MyVertex, MyEdge> tree = getTreeGenerator(treeNameIn, graph, vertexList.get(0)).create();

        /** create cycles */
        FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
        List<MyCycle> cycles = cyclesGenerator.create();

        /** elect leaders */
        Map<MyCycle, MyVertex> leadersMap = getAlgorithm(algorithmNameIn, graph, cycles).solve();
    }

    /**
     * return graph generator according to graph name
     *
     * @param graphNameIn
     * @param vertexNumIn
     * @return graph generator
     */
    public static GraphGenerator getGraphGenerator (String graphNameIn, int vertexNumIn){
        if (graphNameIn == "NWS")
            return new NWSGraphGenerator(vertexNumIn);
        else if (graphNameIn == "BA")
            return new BAGraphGenerator(vertexNumIn);
        return null;
    }

    /**
     * return tree generator according to tree name
     * @param treeNameIn
     * @param rootIn
     * @return tree generator
     */
    public static TreeGenerator getTreeGenerator (String treeNameIn, MyGraph<MyVertex, MyEdge> graphIn, MyVertex rootIn){
        if (treeNameIn == "BFS")
            return new BFSTreeGenerator(graphIn, rootIn);
        else if (treeNameIn == "DFS")
            return new DFSTreeGenerator(graphIn, rootIn);
        return null;
    }

    /**
     * return algorithm instance according to algorithm name
     * @param algorithmNameIn
     * @param graphIn
     * @param cyclesIn
     * @return algorithm
     */
    public static Algorithm getAlgorithm (String algorithmNameIn, MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cyclesIn){
        if (algorithmNameIn == "SharedVertex")
            return new SharedVertexAlgorithm(graphIn, cyclesIn);
        else if (algorithmNameIn == "Random")
             return new RandomAlgorithm(graphIn, cyclesIn);
        return null;
    }
}