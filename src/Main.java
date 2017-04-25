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
                excute("NWS", "BFS", vertexNum, "Random");
            }
        }
    }

    public static void excute(String graphNameIn, String treeNameIn, int vertexNumIn, String algorithmNameIn) {
        /** initialization */
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();
        List<MyVertex> vertexList;
        MyGraph<MyVertex, MyEdge> tree = new MyGraph<MyVertex, MyEdge>();
        List<MyCycle> cycles;
        Map<MyCycle, MyVertex> leadersMap;

        /** create graph */
        if (graphNameIn == "NWS") {
            NWSGraphGenerator NWSGenerator = new NWSGraphGenerator(vertexNumIn);
            graph = NWSGenerator.create();
        }
        else if (graphNameIn == "BA"){
            BAGraphGenerator BAGenerator = new BAGraphGenerator(vertexNumIn);
            graph = BAGenerator.create();
        }

        /** get vertexes from graph */
        vertexList = new ArrayList<MyVertex>(graph.getVertices());

        /** create tree */
        if (treeNameIn == "BFS") {
            BFSTreeGenerator BFSTreeGenerator = new BFSTreeGenerator(graph, vertexList.get(0));
            tree = BFSTreeGenerator.create();
        }
        else if (treeNameIn == "DFS"){
            DFSTreeGenerator DFSTreeGenerator = new DFSTreeGenerator(graph, vertexList.get(0));
            tree = DFSTreeGenerator.create();
        }

        /** create cycles */
        FundamentalCyclesGenerator cyclesGenerator = new FundamentalCyclesGenerator(graph, tree);
        cycles = cyclesGenerator.create();

        /** elect leaders */
        if (algorithmNameIn == "SharedVertex") {
            SharedVertexAlgorithm al = new SharedVertexAlgorithm(graph, cycles);
            leadersMap = al.solve();
        }
        else if (algorithmNameIn == "Random") {
            RandomAlgorithm al = new RandomAlgorithm(graph, cycles);
            leadersMap = al.solve();
        }
    }
}