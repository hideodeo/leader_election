import IO.DataCabinet;
import IO.ResultsWriter;
import algorithms.*;
import calculators.EvaluationFunctions;
import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
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
        System.out.println("---------------------------------------------");
        /** parameters for simulation */
        int simulationTimes = 200;
        int initialNumOfVertex = 20, maxNumOfVertex = 20, incrementalNumOfVertex = 10;
        int maxNumOfManagedVertex = 100;
        /** prepare a list for the num of vertexes */
        List<Integer> numOfVertexList = getVertexList(initialNumOfVertex, maxNumOfVertex, incrementalNumOfVertex);

        /** execute simulations specifying settings*/
        execute("NWS", "BFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("Lattice", "BFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "Closeness", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "Random", simulationTimes);
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
        int numVertex = vertexNumIn.size();
        DataCabinet objectiveFunctionCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfEdgeCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet diameterCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet densityCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfCyclesCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet cycleSizeCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfadjCyclesCabinet = new DataCabinet(numVertex, simulationTimesIn);

        /** collect data of the number of vertexes */
        for (int num: vertexNumIn) {
            numOfVertex.add((double) num);
        }
        dataLists.add(numOfVertex);

        /** execute simulations for leader election */
        for (int i=0; i < numVertex; i++) {
            System.out.println("|V|: " + vertexNumIn.get(i));
            for (int j=0; j < simulationTimesIn; j++) {
                /** create graph */
                MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphNameIn, vertexNumIn.get(i)).create();
                numOfEdgeCabinet.cumulate(i, graph.getEdgeCount());
                diameterCabinet.cumulate(i, DistanceStatistics.diameter(graph));
                densityCabinet.cumulate(i, graph.getDensity());

                /** get vertexes from graph */
                List<MyVertex> vertexList = new ArrayList<MyVertex>(graph.getVertices());

                /** create tree */
                MyGraph<MyVertex, MyEdge> tree = getTreeGenerator(treeNameIn, graph, vertexList.get(0)).create();

                /** create cycles */
                FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
                List<MyCycle> cycles = cyclesGenerator.create();
                numOfCyclesCabinet.cumulate(i, cycles.size());
                cycleSizeCabinet.cumulate(i, EvaluationFunctions.averageCycleSize(cycles));

                /** calculate and set adjacent cycles */
                for (MyCycle cycle: cycles)
                    cycle.setAdjacentCycles(cycles);
                numOfadjCyclesCabinet.cumulate(i, EvaluationFunctions.averageNeighborCyclesCount(cycles));

                /** elect leaders */
                Map<MyCycle, MyVertex> leadersMap = getAlgorithm(algorithmNameIn, graph, cycles).solve();

                /** save data into cabinets */
                objectiveFunctionCabinet.cumulate(i, EvaluationFunctions.objectiveFunction(graph, cycles, leadersMap));
            }
            System.out.println("OF value              : " + objectiveFunctionCabinet.getAveragedValue(i));
            System.out.println("|E|                   : " + numOfEdgeCabinet.getAveragedValue(i));
            System.out.println("diameter              : " + diameterCabinet.getAveragedValue(i));
            System.out.println("density               : " + densityCabinet.getAveragedValue(i));
            System.out.println("# of cycles           : " + numOfCyclesCabinet.getAveragedValue(i));
            System.out.println("av size of cycles     : " + cycleSizeCabinet.getAveragedValue(i));
            System.out.println("# of adjacent cycles  : " + numOfadjCyclesCabinet.getAveragedValue(i));
            System.out.println("---------------------------------------------");
        }
        dataLists.add(objectiveFunctionCabinet.getAveragedDataList());

        /** output results into csv file */
        ResultsWriter writer = new ResultsWriter();
        String filePath = writer.getFullPath(graphNameIn, treeNameIn, algorithmNameIn);
        writer.write(dataLists, filePath, "# of vertexes", "value of objective function");
    }

    /**
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