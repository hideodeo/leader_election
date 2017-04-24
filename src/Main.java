import algorithms.RandomAlgorithm;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import generators.BFSTreeGenerator;
import generators.FundamentalCyclesGenerator;
import generators.NWSGraphGenerator;

import java.util.*;

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
                excute("NWS", "BFS", vertexNum, "Random");
            }
        }
    }

    public static void excute(String graphNameIn, String treeNameIn, int vertexNumIn, String algorithmNameIn) {
        /** initialize graph, tree, cycle list and leader map*/
        MyGraph<MyVertex, MyEdge> graph;
        List<MyVertex> vertexList;
        MyGraph<MyVertex, MyEdge> tree;
        List<MyCycle> cycles;
        Map<MyCycle, MyVertex> leadersMap;

        if (graphNameIn == "NWS"){

            NWSGraphGenerator NWSGenerator = new NWSGraphGenerator(vertexNumIn);
            graph = NWSGenerator.create();
            vertexList = new ArrayList<MyVertex>(graph.getVertices());

            BFSTreeGenerator BFSTreeGenerator = new BFSTreeGenerator(graph, vertexList.get(0));
            tree = BFSTreeGenerator.create();

            FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
            cycles = cyclesGenerator.create();

            RandomAlgorithm raAl = new RandomAlgorithm(graph, cycles);
            leadersMap = raAl.solve();

        }
    }
}