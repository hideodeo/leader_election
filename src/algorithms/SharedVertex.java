package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.List;

/**
 * SharedVertex
 *
 * # of shared vertexesに基いてリーダを選ぶクラス
 */
public class SharedVertex implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public SharedVertex(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    @Override
    public List<MyVertex> create() {
        return null;
    }
}
