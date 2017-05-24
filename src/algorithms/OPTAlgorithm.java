package algorithms;

import calculators.EvaluationFunctions;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OPTAlgorithm
 *
 * class to return an optimal solution by generating all combination
 */
public class OPTAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /** variable for selecting optimal solution */
    private double objectiveFunctionValue = Double.MAX_VALUE;

    /**
     * constructor
     * @param graphIn graph
     * @param cycleListIn root vertex
     */
    public OPTAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * method to elect leaders
     * @return a map of cycle and leader pairs
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        /** initialization */
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();

        /** select best solution by comparing all combinations of leaders */
        long allCombinationsCount = 1;
//        System.out.print("Each cycle size = ");
        for (MyCycle cycle: cycleList) {
            allCombinationsCount *= cycle.getVertices().size();
//            System.out.print(cycle.getVertices().size() + ", ");
        }
//        System.out.println();
//        System.out.println("All patterns = " + allCombinationsCount);
        selectBestSolution(cycleList, resultMap, 0, new HashMap<MyCycle, MyVertex>());

        return resultMap;
    }

    /**
     *
     * @param cycles
     * @param resultMap
     * @param depth
     * @param current
     */
    private void selectBestSolution(List<MyCycle> cycles, Map<MyCycle, MyVertex> resultMap, int depth, Map<MyCycle, MyVertex> current)
    {
        if(depth == cycles.size())
        {
//            System.out.println("Find leaf");
            double val = EvaluationFunctions.objectiveFunction(graph, cycles, current);
            if (val < objectiveFunctionValue) {
                resultMap.putAll(current);
                objectiveFunctionValue = val;
//                System.out.println("Update best solution");
            }
            return;
        }
        for(MyVertex v: cycles.get(depth).getVertices()){
            current.put(cycles.get(depth), v);
            selectBestSolution(cycles, resultMap, depth + 1, current);
        }
    }


    /**
     * generate all solutions and add them to a list of maps
     *
     * http://stackoverflow.com/questions/17192796/generate-all-combinations-from-multiple-lists
     *
     * @param cycles
     * @param result
     * @param depth
     * @param current
     */
    /**private void generateAllCombinationOfLeaders1(List<MyCycle> cycles, List<Map<MyCycle, MyVertex>> result, int depth, Map<MyCycle, MyVertex> current)
    {
        if(depth == cycles.size())
        {
            result.add(current);
            return;
        }

        for (MyVertex v: cycles.get(depth).getVertices()){
            current.put(cycles.get(depth), v);
            selectBestSolution(cycles, result, depth + 1, current);
        }
    }*/
}
