package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.List;

/**
 * Created by Hideo on 2017/04/14.
 */
public class FundamentalCyclesGenerator implements CyclesGenerator {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> graph;
    /** ツリー（BFS/DFSにより生成）*/
    private MyGraph<MyVertex, MyEdge> tree;

    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param treeIn ツリー
     */
    public FundamentalCyclesGenerator(MyGraph<MyVertex, MyEdge> graphIn, MyGraph<MyVertex, MyEdge> treeIn) {
        this.graph = graphIn;
        this.tree = treeIn;
    }

    /**
     * サイクルを返すメソッド
     * @return cycle
     */
    @Override
    public List<MyGraph<MyVertex, MyEdge>> create() {
        MyGraph<MyVertex, MyEdge> cycle = new MyGraph<MyVertex, MyEdge>();
        return null;
    }
}
