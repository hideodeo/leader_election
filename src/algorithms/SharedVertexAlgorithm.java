package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SharedVertex
 *
 * # of shared vertexesに基いてリーダを選ぶクラス
 */
public class SharedVertexAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public SharedVertexAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
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

        /** vertexを共有する隣接サイクル数の計算*/
        for (MyCycle cycle: cycleList){
            for (MyVertex v: cycle.getVertices()){
                v.setNumOfAdCycles(v.getNumOfAdCycles() + 1);
            }
        }

        /** サイクルごとにリーダーを選出*/
        for (MyCycle cycle: cycleList){
            MyVertex leader = new MyVertex();
            for (MyVertex v: cycle.getVertices()){
                if (leader == null)
                    leader = v;
                else if (v.getNumOfAdCycles() > leader.getNumOfAdCycles()){
                    leader = v;
                }
            }
            resultMap.put(cycle, leader);
        }
        return resultMap;
    }
}
