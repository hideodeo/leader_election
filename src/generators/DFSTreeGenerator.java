package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * DFSTreeGenerator
 *
 * DFS treeを作成するクラス
 */
public class DFSTreeGenerator implements TreeGenerator {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> graph;
    /** ルートノード（探索の始点）*/
    private MyVertex root;

    /**
     * constructor
     * @param graph グラフ
     * @param root ルートノード
     */
    public DFSTreeGenerator(MyGraph<MyVertex, MyEdge> graph, MyVertex root) {
        assert graph.containsVertex(root) : "the root vertex is not contained in the graph";

        this.graph = graph;
        this.root = root;
    }

    /**
     * DFS木を返すメソッド
     * @return DFS tree
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create() {
        MyGraph<MyVertex, MyEdge> tree = new MyGraph<MyVertex, MyEdge>();
        Deque<MyVertex> stack = new ArrayDeque<MyVertex>();
        // ある頂点が探索済みかどうかを判別するために探索済みの頂点を保存するリスト
        List<MyVertex> visitedVertexes = new ArrayList<MyVertex>();

        tree.addVertex(root);
        visitedVertexes.add(root);
        stack.offerFirst(root);

        while (!stack.isEmpty()) {
            MyVertex r = stack.pollFirst();
            // vの隣接点のうち、未探索のノードを木に追加し探索済みにする
            for(MyVertex v : this.graph.getNeighbors(r)) {
                if(!visitedVertexes.contains(v)) {
                    tree.addVertex(v);
                    MyEdge e = this.graph.findEdge(r, v);
                    tree.addEdge(e, r, v);
                    visitedVertexes.add(v);
                    stack.offerFirst(v);
                }
            }
        }

        // 後片付け（必要かと言われれば要らない）
        stack.clear();
        visitedVertexes.clear();

        return tree;
    }
}
