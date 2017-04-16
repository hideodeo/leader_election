package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.*;

/**
 * Random
 *
 * Generates 100 solutions and choose the best one.
 */
public class RandomAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public RandomAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * リーダー集合を返すメソッド
     * @return map
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        Random rnd = new Random();
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();
        int objectiveFunctionValue = Integer.MAX_VALUE;

        /** generation of 100 solutions*/
        for (int i=0;i<100;i++) {
            Map<MyCycle, MyVertex> leadersMap = new HashMap<MyCycle, MyVertex>();

            for (MyCycle c : cycleList) {
                List<MyVertex> list = new ArrayList<MyVertex>(c.getVertices());
                int leaderIndex = rnd.nextInt(list.size());

                leadersMap.put(c, list.get(leaderIndex));
            }

            /** comparison of 100 solutions*/
            int val = EvaluationFunctions.objectiveFunction(graph, cycleList, leadersMap);

            if (val < objectiveFunctionValue) {
                resultMap = leadersMap;
                objectiveFunctionValue = val;
            }
        }
        return resultMap;
    }
}
