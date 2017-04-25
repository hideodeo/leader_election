package algorithms;

import calculators.EvaluationFunctions;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.ArrayList;
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
        List<Map<MyCycle, MyVertex>> allCombinationOfLeaders = new ArrayList<Map<MyCycle, MyVertex>>();
        Map<MyCycle, MyVertex> currentMap = new HashMap<MyCycle, MyVertex>();
        double objectiveFunctionValue = Double.MAX_VALUE;

        /** enumerate all solution */
        generateAllCombinationOfLeaders(cycleList, allCombinationOfLeaders, 0, currentMap);

        /** select best solution by comparing all*/
        for (Map<MyCycle, MyVertex> leadersMap: allCombinationOfLeaders){
            double val = EvaluationFunctions.objectiveFunction(graph, cycleList, leadersMap);

            if (val < objectiveFunctionValue) {
                resultMap = leadersMap;
                objectiveFunctionValue = val;
            }
        }

        return resultMap;
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
    private void generateAllCombinationOfLeaders(List<MyCycle> cycles, List<Map<MyCycle, MyVertex>> result, int depth, Map<MyCycle, MyVertex> current)
    {
        if(depth == cycles.size())
        {
            result.add(current);
            return;
        }

        for (MyVertex v: cycles.get(depth).getVertices()){
            current.put(cycles.get(depth), v);
            generateAllCombinationOfLeaders(cycles, result, depth + 1, current);
        }
    }
}
