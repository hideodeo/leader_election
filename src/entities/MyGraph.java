package entities;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import org.apache.commons.collections15.Factory;

import java.util.*;

/**
 * MyGraph
 *
 * グラフを表すクラス
 */
public class MyGraph<V, E> extends SparseGraph<V, E> {
    private Map<Set<MyVertex>, Integer> distanceMap = new HashMap<Set<MyVertex>, Integer>();

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
     * 頂点間の距離を計算する
     * @param s ソースノード
     * @param t 宛先ノード
     * @return distance
     */
    private int getDistance(MyVertex s, MyVertex t) {
        DijkstraDistance<MyVertex, MyEdge> dd = new DijkstraDistance<MyVertex, MyEdge>((Graph<MyVertex, MyEdge>) this);
        return dd.getDistance(s, t).intValue();
    }

    /**
     * set distances between all vertex pairs & save them into a map
     *
     * "vertexPair has only one element when vertexes are equal because it is Set"
     */
    public void setDistancesBetweenAllPairs()
    {
        List<MyVertex> vertexes = new ArrayList(this.getVertices());
        for (int i=0; i<vertexes.size(); i++){
            for (int j=i; j<vertexes.size(); j++){
                /** set vertex pair */
                Set<MyVertex> vertexPair = new HashSet<MyVertex>();
                vertexPair.add(vertexes.get(i));
                vertexPair.add(vertexes.get(j));
                /** add pair to distanceMap with distance*/
                distanceMap.put(vertexPair, getDistance(vertexes.get(i), vertexes.get(j)));
            }
        }
    }

    /**
     * return distance from previously
     * @param s
     * @param t
     * @return distance
     */
    public int getDistanceFromMap(MyVertex s, MyVertex t) {
        Set<MyVertex> vertexPair = new HashSet<MyVertex>();
        vertexPair.add(s);
        vertexPair.add(t);

        return distanceMap.get(vertexPair);
    }
}