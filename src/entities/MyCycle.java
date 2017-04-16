package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        assert isCycle(graph): "the graph is not cycle. Not all vertices's degree is 2. ";
        this.cycle = graph;
    }

    /**
     * サイクルサイズを返す
     * サイクルサイズ := 頂点数
     * @return サイクルサイズ
     */
    public int getSize() {
        return this.cycle.getVertexCount();
    }

    /**
     * 頂点集合をリストで返す
     * @return 頂点集合
     */
    public List<MyVertex> getVerticesList() {
        return new ArrayList<MyVertex>(this.cycle.getVertices());
    }

    private boolean isCycle(MyGraph<MyVertex, MyEdge> cycleIn){
        for (MyVertex v: cycleIn.getVertices()){
            if (cycleIn.degree(v) != 2)
                return false;
        }
        return true;
    }

    /**
     * 頂点集合をCollectionで返す
     * @return 頂点集合
     */
    public Collection<MyVertex> getVertices(){
        return cycle.getVertices();
    }
}
