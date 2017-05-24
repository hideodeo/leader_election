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

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Main
 *
 * This class sets conditions and conducts simulations.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Simulation started.");
        System.out.println("-------------------------------------------------");
        /** parameters for simulation */
        int simulationTimes = 300;
        int initialNumOfVertex = 30, maxNumOfVertex = 30, incrementalNumOfVertex = 10;
        /** prepare a list for the num of vertexes */
        List<Integer> numOfVertexList = getVertexList(initialNumOfVertex, maxNumOfVertex, incrementalNumOfVertex);

        /** execute simulations specifying settings */
        /** experiment on different number of vertexes */
        //execute("NWS", "BFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("NWS", "DFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("Lattice", "BFS", numOfVertexList, "SharedVertex", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "Closeness", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "Random", simulationTimes);
        //execute("NWS", "BFS", numOfVertexList, "OPT", simulationTimes);
        //execute("NWS", "DFS", numOfVertexList, "OPT", simulationTimes);
        /** experiment on different root vertexes */
        int a = 16;
        String[] treeNameList = {"BFS", "DFS"};
        execute("NWS", a, treeNameList, "OPT");

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
        System.out.println("● Experiment on different number of vertexes");
        System.out.println("Condition: " + graphNameIn + ", " + treeNameIn + ", " + algorithmNameIn);

        /** prepare lists for data collection */
        int numVertex = vertexNumIn.size();
        List<List<Double>> dataLists = new ArrayList<List<Double>>();
        List<Double> numOfVertex = new ArrayList<Double>();
        /** prepare cabinets for data collection */
        DataCabinet objectiveFunctionCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfEdgeCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet diameterCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet densityCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfCyclesCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet cycleSizeCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet cycleSizeSTDCabinet = new DataCabinet(numVertex, simulationTimesIn);
        DataCabinet numOfadjCyclesCabinet = new DataCabinet(numVertex, simulationTimesIn);

        /** collect data of the number of vertexes */
        for (int num: vertexNumIn) {
            numOfVertex.add((double) num);
        }
        dataLists.add(numOfVertex);

        /** execute simulations for leader election */
        for (int i=0; i < numVertex; i++) {
            System.out.println("Graph Feature");
            System.out.println("  |V|                   : " + vertexNumIn.get(i));
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

                /** cumulate data in each cabinet */
                numOfEdgeCabinet.cumulate(i, graph.getEdgeCount());
                diameterCabinet.cumulate(i, DistanceStatistics.diameter(graph));
                densityCabinet.cumulate(i, graph.getDensity());
                numOfCyclesCabinet.cumulate(i, cycles.size());
                cycleSizeCabinet.cumulate(i, EvaluationFunctions.averageCycleSize(cycles));
                cycleSizeSTDCabinet.cumulate(i, EvaluationFunctions.standardDeviationOfCycleSize(cycles));
                numOfadjCyclesCabinet.cumulate(i, EvaluationFunctions.averageNeighborCyclesCount(cycles));
                objectiveFunctionCabinet.cumulate(i, EvaluationFunctions.objectiveFunction(graph, cycles, leadersMap));
            }
            /** print simulation data */
            System.out.println("  |E|                   : " + numOfEdgeCabinet.getAveragedValue(i));
            System.out.println("  diameter              : " + diameterCabinet.getAveragedValue(i));
            System.out.println("  density               : " + densityCabinet.getAveragedValue(i));
            System.out.println("Cycle Distribution Feature");
            System.out.println("  # of cycles           : " + numOfCyclesCabinet.getAveragedValue(i));
            System.out.println("  av size of cycles     : " + cycleSizeCabinet.getAveragedValue(i));
            System.out.println("  std of size of cycles : " + cycleSizeSTDCabinet.getAveragedValue(i));
            System.out.println("  # of adjacent cycles  : " + numOfadjCyclesCabinet.getAveragedValue(i));
            System.out.println("Evaluation Function");
            System.out.println("  OF value              : " + objectiveFunctionCabinet.getAveragedValue(i));
            System.out.println("-------------------------------------------------");
        }
        dataLists.add(objectiveFunctionCabinet.getAveragedDataList());

        /** output results into csv file */
        ResultsWriter writer = new ResultsWriter();
        String filePath = writer.getFullPath(graphNameIn, treeNameIn, algorithmNameIn);
        writer.write(dataLists, filePath, "# of vertexes", "value of objective function");
    }

    private static void execute(String graphNameIn, int numVertexIn, String[] treeNameListIn, String algorithmNameIn) {
        System.out.println("● Experiment on different root vertexes");
        final String algorithmName = algorithmNameIn;

        /** prepare list for data collection */
        List<List<Double>> dataLists = new ArrayList<List<Double>>();

        /** create graph */
        final MyGraph<MyVertex, MyEdge> graph = getGraphGenerator(graphNameIn, numVertexIn).create();

        for (String treeName0: treeNameListIn) {
            final String treeName = treeName0;
            System.out.println("Condition: " + graphNameIn + ", " + treeName + ", " + algorithmName);

            /** prepare thread pool for multi-thread programming */
            ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            Collection<Callable<Void>> processes = new LinkedList<Callable<Void>>();
            /** prepare list for data collection */
            final List<Double> objectiveFunction = new ArrayList<Double>();
            /** use all vertexes in graph as root*/
            for (MyVertex v0 : graph.getVertices()) {
                /** preparation for multi-thread programming */
                final MyVertex v = v0;
                processes.add(new Callable<Void>() {
                    @Override
                    public Void call() {
                        /** create tree */
                        MyGraph<MyVertex, MyEdge> tree = getTreeGenerator(treeName, graph, v).create();

                        /** create cycles */
                        FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
                        List<MyCycle> cycles = cyclesGenerator.create();

                        /** calculate and set adjacent cycles */
                        for (MyCycle cycle : cycles)
                            cycle.setAdjacentCycles(cycles);

                        /** elect leaders */
                        Map<MyCycle, MyVertex> leadersMap = getAlgorithm(algorithmName, graph, cycles).solve();
                        double OF = EvaluationFunctions.objectiveFunction(graph, cycles, leadersMap);
                        objectiveFunction.add(OF);

                        System.out.println("Graph Feature");
                        System.out.println("  |V|                   : " + graph.getVertexCount());
                        System.out.println("  |E|                   : " + graph.getEdgeCount());
                        System.out.println("Cycle Distribution Feature");
                        System.out.println("  # of cycles           : " + cycles.size());
                        System.out.println("  av size of cycles     : " + EvaluationFunctions.averageCycleSize(cycles));
                        System.out.println("  std of size of cycles : " + EvaluationFunctions.standardDeviationOfCycleSize(cycles));
                        System.out.println("  # of adjacent cycles  : " + EvaluationFunctions.averageNeighborCyclesCount(cycles));
                        System.out.println("OF value              : " + OF);
                        System.out.println("-------------------------------------------------");

                        return null;
                    }
                });
            }
            try {
                threadPool.invokeAll(processes);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                threadPool.shutdown();
            }

            double totalOF = 0.0;
            for (double e : objectiveFunction)
                totalOF += e;
            System.out.println("av OF value           : " + totalOF / objectiveFunction.size());
            System.out.println("=================================================");
            dataLists.add(objectiveFunction);
        }

        /** output results into csv file */
        ResultsWriter writer = new ResultsWriter();
        String filePath = writer.getFullPath(graphNameIn, algorithmName);
        writer.write(dataLists, filePath, "BFS", "DFS");
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