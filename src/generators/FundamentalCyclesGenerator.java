package generators;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * FundamentalCyclesGenerator クラス
 * The generator of fundamental cycles.
 * The fundamental cycles correspond to the fundamental tie-sets
 * which is created by using tree's complimented edges.
 */
public class FundamentalCyclesGenerator implements CyclesGenerator {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> graph;
    /** ツリー（BFS/DFSにより生成）*/
    private MyGraph<MyVertex, MyEdge> tree;

    /**
     * constructor
     * @param graphIn グラフ
     * @param treeIn ツリー
     */
    public FundamentalCyclesGenerator(MyGraph<MyVertex, MyEdge> graphIn, MyGraph<MyVertex, MyEdge> treeIn) {
        this.graph = graphIn;

        /** treeがgraphのsubgraphであることを確認する*/
        Collection<MyEdge> graphEdges = graph.getEdges();
        for (MyEdge e : treeIn.getEdges()) {
            assert graphEdges.contains(e): "tree is not a sub-graph of the original graph";
        }
        this.tree = treeIn;
    }

    /**
     * サイクルのリストを返すメソッド
     * @return list of cycles
     */
    @Override
    public List<MyCycle> create() {
        List<MyCycle> list = new ArrayList<MyCycle>();

        for (MyEdge e : graph.getEdges()) {
            if (isComplimentEdge(e)) {
                MyCycle cycle = createCycle(e);
                list.add(cycle);
            }
        }

        assert checkNumOfCycles(list): "# of cycles is not correct. It should follow |E| - |V| + 1.";
        return list;
    }

    /**
     * ある辺が補木辺かどうかを調べる
     * @param  target 調査対象の辺
     * @return true if targetが補木辺
     */
    private boolean isComplimentEdge(MyEdge target) {
        return !tree.getEdges().contains(target);
    }

    /**
     * 引数で受け取った補木辺から作成される基本タイセットを返す
     * @return a fundamental cycle
     */
    private MyCycle createCycle(MyEdge complimentEdge) {
        /** 木+補木辺で構成されるグラフを作成する */
        MyGraph<MyVertex, MyEdge> subGraph = new MyGraph<MyVertex, MyEdge>();
        for (MyVertex v: tree.getVertices()){
            subGraph.addVertex(v);
        }
        for (MyEdge e: tree.getEdges()){
            subGraph.addEdge(e, tree.getEndpoints(e));
        }
        subGraph.addEdge(complimentEdge, graph.getEndpoints(complimentEdge));

        /** グラフの中から次数1の頂点を取り除くことでサイクルを抽出する */
        boolean didRemove = false;
        do{
            didRemove = false;
            for (MyVertex v : subGraph.getVertices()) {
                if (subGraph.degree(v) == 1) {
                    subGraph.removeVertex(v);
                    didRemove = true;
                    break;
                }
            }
        } while (didRemove);

        return new MyCycle(subGraph);
    }

    /**
     * check if # of cycles is |E| - |V| + 1.
     * @param list
     * @return
     */
    private boolean checkNumOfCycles(List<MyCycle> list){
        int num = graph.getEdgeCount() - graph.getVertexCount() + 1;
        if (list.size() == num)
            return true;
        else
            return false;
    }
}
