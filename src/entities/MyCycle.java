package entities;

/**
 * MyCycle
 *
 * サイクルを表すクラス
 */
public class MyCycle {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> cycle;

    /**
     * コンストラクタ
     */
    public MyCycle(MyGraph<MyVertex, MyEdge> graph){
        assert isCycle(graph): "the graph is not cycle";
        this.cycle = graph;
    }

    private boolean isCycle(MyGraph<MyVertex, MyEdge> cycleIn){
        for (MyVertex v: cycleIn.getVertices()){
            if (cycleIn.degree(v) != 2)
                return false;
        }
        return true;
    }
}
