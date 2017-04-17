package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClosenessCentrality
 */
public class ClosenessAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public ClosenessAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * リーダー集合を返すメソッド
     * @return map
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();

        /** calculate closeness centrality for each vertex*/
        for (MyVertex v: graph.getVertices())
            v.setClosenessCentrality(caluculateClosenessCentrality(v));

        /** サイクルごとにリーダーを選出*/
        for (MyCycle cycle: cycleList){
            MyVertex leader = null;
            for (MyVertex v: cycle.getVertices()){
                if (leader == null)
                    leader = v;
                else if (leader.getClosenessCentrality() < v.getClosenessCentrality()){
                    leader = v;
                }
            }
            resultMap.put(cycle, leader);
        }
        return resultMap;
    }

    /**
     * closeness centralityを計算するメソッド
     * @param v vertex
     * @return value of closeness centrality
     */
    public double caluculateClosenessCentrality(MyVertex v){
        double denominator = 0;
        double numerator = 0;

        for (MyVertex i: graph.getVertices()){
            if (i != v) {
                denominator += graph.getDistanceBetween(v, i);
            }
        }
        numerator = graph.getVertexCount() - 1;
        return numerator / denominator;
    }
}
