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
 * 全組み合わせの解を生成し、最も良い解を返すクラス
 */
public class OPTAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public OPTAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * リーダー集合を返すメソッド
     * @return map
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        /** initialization */
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();
        double objectiveFunctionValue = Double.MAX_VALUE;

        /** select best solution by comparing all combinations of leaders */
        selectBestSolution(cycleList, resultMap, 0, new HashMap<MyCycle, MyVertex>(), objectiveFunctionValue);

        return resultMap;
    }

    /**
     *
     * @param cycles
     * @param resultMap
     * @param depth
     * @param current
     * @param objectiveFunctionValue
     */
    private void selectBestSolution(List<MyCycle> cycles, Map<MyCycle, MyVertex> resultMap, int depth, Map<MyCycle, MyVertex> current, double objectiveFunctionValue)
    {
        if(depth == cycles.size())
        {
            double val = EvaluationFunctions.objectiveFunction(graph, cycles, current);
            if (val < objectiveFunctionValue) {
                resultMap.putAll(current);
                objectiveFunctionValue = val;
            }
            return;
        }
        for(MyVertex v: cycles.get(depth).getVertices()){
            current.put(cycles.get(depth), v);
            selectBestSolution(cycles, resultMap, depth + 1, current, objectiveFunctionValue);
        }
        System.out.println("test");
    }

    private void selectBestSolution1(List<MyCycle> cycles, Map<MyCycle, MyVertex> resultMap, int depth, Map<MyCycle, MyVertex> current, double objectiveFunctionValue)
    {
        for (int i=0; i<cycles.size(); i++){
            for (int j=0; j<cycles.get(i).getVertexCount(); j++){

            }
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
