package entities;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.apache.commons.collections15.Factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * MyGraph
 *
 * グラフを表すクラス
 */
public class MyGraph<V, E> extends SparseGraph<V, E> {
    private Map<Set<MyVertex>, Integer> distanceMap = new HashMap<Set<MyVertex>, Integer>();
    private DijkstraDistance<MyVertex, MyEdge> dd;

    /**
     * constructor
     */
    public MyGraph(){
        dd = new DijkstraDistance<MyVertex, MyEdge>((Graph<MyVertex, MyEdge>) this);
    }

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

    /**
     * return distance from the map if it is previously calculated.
     * If not, calculate and add distance to the map. Then, return it.
     * @param s
     * @param t
     * @return distance
     */
    public int getDistanceBetween(MyVertex s, MyVertex t) {
        Set<MyVertex> vertexPair = new HashSet<MyVertex>();
        vertexPair.add(s);
        vertexPair.add(t);

        Integer dist = distanceMap.get(vertexPair);
        if (dist == null){
            dist = dd.getDistance(s, t).intValue();
            distanceMap.put(vertexPair, dist);
            return dist;
        }
        return dist;
    }

    /**
     * override the following four methods to initialize DijkstraDistance when topology of graph changes.
     */
    @Override
    public boolean addVertex(V vertex) {
        boolean bool = super.addVertex(vertex);
        dd.reset();

        return bool;
    }
    @Override
    public boolean addEdge(E e, V v1, V v2) {
        boolean bool = super.addEdge(e, v1, v2);
        dd.reset();

        return bool;
    }

    @Override
    public boolean removeVertex(V vertex) {
        boolean bool = super.removeVertex(vertex);
        dd.reset();

        return bool;
    }
    @Override
    public boolean removeEdge(E edge) {
        boolean bool = super.removeEdge(edge);
        dd.reset();

        return bool;
    }
}