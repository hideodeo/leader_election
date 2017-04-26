import algorithms.Algorithm;
import algorithms.RandomAlgorithm;
import algorithms.SharedVertexAlgorithm;

import calculators.EvaluationFunctions;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import generators.*;

import java.util.ArrayList;
import java.util.HashMap;
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
        int initialNumOfVertex = 20, maxNumOfVertex = 100, incrementalNumOfVertex = 20;
        List<Integer> numOfVertexList = getVertexList(initialNumOfVertex, maxNumOfVertex, incrementalNumOfVertex);

        /** execute simulations specifying settings*/
        execute("NWS", "BFS", numOfVertexList, "Random", simulationTimes);
    }

    /**
     * This method executes a simulation.
     *
     * @param graphNameIn
     * @param treeNameIn
     * @param vertexNumIn
     * @param algorithmNameIn
     */
    private static void execute(String graphNameIn, String treeNameIn, List<Integer> vertexNumIn, String algorithmNameIn, int simulationTimesIn) {
        /** initialize map for data collection */
        Map<Integer, Double> dataMap = new HashMap<Integer, Double>();
        for (int num: vertexNumIn){
            dataMap.put(num, 0.0);
        }

        /** iterate according to the number of vertexes */
        for (int num: vertexNumIn) {
            /** execute simulations */
            for (int i=0; i < simulationTimesIn; i++) {
                /** create graph */
                MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphNameIn, num).create();

                /** get vertexes from graph */
                List<MyVertex> vertexList = new ArrayList<MyVertex>(graph.getVertices());

                /** create tree */
                MyGraph<MyVertex, MyEdge> tree = getTreeGenerator(treeNameIn, graph, vertexList.get(0)).create();

                /** create cycles */
                FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
                List<MyCycle> cycles = cyclesGenerator.create();

                /** elect leaders */
                Map<MyCycle, MyVertex> leadersMap = getAlgorithm(algorithmNameIn, graph, cycles).solve();

                /** save data into map */
                dataMap.put(num, dataMap.get(num) + EvaluationFunctions.objectiveFunction(graph, cycles, leadersMap));
            }
        }
        /** output results into csv file */
    }

    /**
     * return graph generator according to graph name
     *
     * @param graphNameIn
     * @param vertexNumIn
     * @return graph generator
     */
    private static GraphGenerator getGraphGenerator (String graphNameIn, int vertexNumIn){
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
    private static TreeGenerator getTreeGenerator (String treeNameIn, MyGraph<MyVertex, MyEdge> graphIn, MyVertex rootIn){
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
    private static Algorithm getAlgorithm (String algorithmNameIn, MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cyclesIn){
        if (algorithmNameIn == "SharedVertex")
            return new SharedVertexAlgorithm(graphIn, cyclesIn);
        else if (algorithmNameIn == "Random")
             return new RandomAlgorithm(graphIn, cyclesIn);
        return null;
    }

    private static List<Integer> getVertexList(int initialNumOfVertex, int maxNumOfVertex, int incrementalNumOfVertex){
        List<Integer> result = new ArrayList<Integer>();
        for (int i=initialNumOfVertex; i<=maxNumOfVertex; i+=incrementalNumOfVertex) {
            result.add(i);
        }
        return result;
    }
}