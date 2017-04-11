package entities;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.apache.commons.collections15.Factory;

/**
 * MyGraph
 *
 * グラフを表すクラス
 */
public class MyGraph<V, E> extends SparseGraph<V, E> {

    /**
     * ファクトリを返すクラスメソッド
     * BAGraphGenerator内で使えるようにするためにMyGraphではなくGraphを返している
     * @return MyGraphFactory
     */
    public static Factory<Graph<MyVertex, MyEdge>> getFactory() {
        return new Factory<Graph<MyVertex, MyEdge>>() {
            @Override
            public MyGraph<MyVertex, MyEdge> create() {
                return new MyGraph<MyVertex, MyEdge>();
            }
        };
    }
}
