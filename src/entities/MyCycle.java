package entities;

import utils.ListUtils;

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
    /** 隣接サイクルの集合 */
    private List<MyCycle> adjacentCycles;

    /**
     * constructor
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
    public int getVertexCount() {
        return this.cycle.getVertexCount();
    }

    /**
     * 頂点集合をCollectionで返す
     * @return 頂点集合
     */
    public Collection<MyVertex> getVertices(){
        return cycle.getVertices();
    }

    /**
     * 頂点集合をリストで返す
     * @return 頂点集合
     */
    public List<MyVertex> getVerticesList() {
        return new ArrayList<MyVertex>(this.cycle.getVertices());
    }

    /**
     * get adjacent cycles
     * @return
     */
    public List<MyCycle> getAdjacentCycles() {
        return adjacentCycles;
    }

    /**
     *
     * @param cycles
     */
    public void setAdjacentCycles(List<MyCycle> cycles) {
        this.adjacentCycles = getNeighborsCycles(cycles, this);
    }

    /**
     * 隣接サイクルを返す
     * 隣接 := 頂点を少なくとも一つ共有している
     * @param cycles サイクル集合
     * @param cycle サイクル
     * @return 隣接サイクルの集合
     */
    private static List<MyCycle> getNeighborsCycles(List<MyCycle> cycles, MyCycle cycle) {
        List<MyCycle> neighbors = new ArrayList<MyCycle>();
        for(MyCycle c : cycles) {
            if(c.equals(cycle)) continue;
            List product = ListUtils.product(c.getVerticesList(), cycle.getVerticesList());
            if(0 < product.size()) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }

    /**
     * check if a graph is cycle
     * @param cycleIn
     * @return boolean
     */
    private boolean isCycle(MyGraph<MyVertex, MyEdge> cycleIn){
        for (MyVertex v: cycleIn.getVertices()){
            if (cycleIn.degree(v) != 2)
                return false;
        }
        return true;
    }
}
