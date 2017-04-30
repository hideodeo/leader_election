import IO.ResultsWriter;
import algorithms.*;
import calculators.EvaluationFunctions;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import generators.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Main
 *
 * This class sets conditions and conducts simulations.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Simulation started.");
        /** parameters for simulation */
        int simulationTimes = 1;
        int initialNumOfVertex = 2, maxNumOfVertex = 8, incrementalNumOfVertex = 1;
        /** prepare a list for the num of vertexes */
        List<Integer> numOfVertexList = getVertexList(initialNumOfVertex, maxNumOfVertex, incrementalNumOfVertex);

        /** execute simulations specifying settings*/
        //execute("NWS", "BFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "Closeness", simulationTimes);
        execute("NWS", "BFS", numOfVertexList, "Random", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "OPT", simulationTimes);

        System.out.println("Simulation finished.");
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
        /** prepare lists for data collection */
        List<List<Double>> dataLists = new ArrayList<List<Double>>();
        List<Double> numOfVertex = new ArrayList<Double>();
        List<Double> valueOfObjectiveFunction = new ArrayList<Double>();

        /** collect data of the number of vertexes */
        for (int num: vertexNumIn) {
            numOfVertex.add((double) num);
        }
        dataLists.add(numOfVertex);

        /** collect data of objective function by iterating according to the number of vertexes */
        /** initialize the list for data collection */
        for (int i=0; i < vertexNumIn.size(); i++){
            valueOfObjectiveFunction.add(0.0);
        }

        /** execute simulations for leader election */
        for (int i=0; i < vertexNumIn.size(); i++) {
            System.out.println("# of vertexes: " + vertexNumIn.get(i));
            for (int j=0; j < simulationTimesIn; j++) {
                /** create graph */
                MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphNameIn, vertexNumIn.get(i)).create();

                /** get vertexes from graph */
                List<MyVertex> vertexList = new ArrayList<MyVertex>(graph.getVertices());

                /** create tree */
                MyGraph<MyVertex, MyEdge> tree = getTreeGenerator(treeNameIn, graph, vertexList.get(0)).create();

                /** create cycles */
                FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
                List<MyCycle> cycles = cyclesGenerator.create();

                /** calculate and set adjacent cycles */
                for (MyCycle cycle: cycles)
                    cycle.setAdjacentCycles(cycles);

                /** elect leaders */
                Map<MyCycle, MyVertex> leadersMap = getAlgorithm(algorithmNameIn, graph, cycles).solve();

                /** save data into list */
                valueOfObjectiveFunction.add(i, valueOfObjectiveFunction.get(i) + EvaluationFunctions.objectiveFunction(graph, cycles, leadersMap));
            }
            /** calculate average values following the number of simulation */
            double av = valueOfObjectiveFunction.get(i) / simulationTimesIn;
            valueOfObjectiveFunction.remove(i);
            valueOfObjectiveFunction.add(i, av);

            System.out.println("av values of OF: " + av);
        }
        dataLists.add(valueOfObjectiveFunction);

        /** output results into csv file */
        ResultsWriter writer = new ResultsWriter();
        String filePath = writer.getFullPath(graphNameIn, treeNameIn, algorithmNameIn);
        writer.write(dataLists, filePath, "# of vertexes", "value of objective function");
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
        else if (graphNameIn == "Lattice")
            return new LatticeGraphGenerator(vertexNumIn);
        return null;
    }

    /**
     * return tree generator according to tree name
     *
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
     *
     * @param algorithmNameIn
     * @param graphIn
     * @param cyclesIn
     * @return algorithm
     */
    private static Algorithm getAlgorithm (String algorithmNameIn, MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cyclesIn){
        if (algorithmNameIn == "SharedVertex")
            return new SharedVertexAlgorithm(graphIn, cyclesIn);
        else if (algorithmNameIn == "Closeness")
            return new ClosenessAlgorithm(graphIn, cyclesIn);
        else if (algorithmNameIn == "Random")
            return new RandomAlgorithm(graphIn, cyclesIn);
        else if (algorithmNameIn == "OPT")
            return new OPTAlgorithm(graphIn, cyclesIn);
        return null;
    }

    /**
     * return a list representing the number of vertexes
     *
     * @param initialNumOfVertex
     * @param maxNumOfVertex
     * @param incrementalNumOfVertex
     * @return
     */
    private static List<Integer> getVertexList(int initialNumOfVertex, int maxNumOfVertex, int incrementalNumOfVertex){
        List<Integer> result = new ArrayList<Integer>();
        for (int i=initialNumOfVertex; i<=maxNumOfVertex; i+=incrementalNumOfVertex) {
            result.add(i);
        }
        return result;
    }
}