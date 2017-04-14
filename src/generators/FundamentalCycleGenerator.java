package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

/**
 * Created by Hideo on 2017/04/14.
 */
public class FundamentalCycleGenerator implements CycleGenerator {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> graph;
    /** ツリー（BFS/DFSにより生成）*/
    private MyGraph<MyVertex, MyEdge> tree;

    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param treeIn ツリー
     */
    public FundamentalCycleGenerator(MyGraph<MyVertex, MyEdge> graphIn, MyGraph<MyVertex, MyEdge> treeIn) {
        this.graph = graphIn;
        this.tree = treeIn;
    }

    /**
     * サイクルを返すメソッド
     * @return cycle
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create() {
        return null;
    }
}
