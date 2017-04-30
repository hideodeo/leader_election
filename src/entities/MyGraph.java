package entities;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.*;

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
        initilizeDijkstra();
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
     * initialize DijkstraDistance. Call this right after creating a graph.
     */
    private void initilizeDijkstra(){
        dd = new DijkstraDistance<MyVertex, MyEdge>((Graph<MyVertex, MyEdge>) this);
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

        if (distanceMap.containsKey(vertexPair)) {
            return distanceMap.get(vertexPair);
        }
        else{
            int dist = dd.getDistance(s, t).intValue();
            distanceMap.put(vertexPair, dist);
            return dist;
        }
    }

    @Override
    public boolean addVertex(V vertex) {
        initilizeDijkstra();

        if(vertex == null) {
            throw new IllegalArgumentException("vertex may not be null");
        }
        if (!containsVertex(vertex)) {
            vertex_maps.put(vertex, new HashMap[]{new HashMap<V,E>(), new HashMap<V,E>(), new HashMap<V,E>()});
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean addEdge(E e, V v1, V v2) {
        initilizeDijkstra();

        return addEdge(e, v1, v2, this.getDefaultEdgeType());
    }

    @Override
    public boolean removeVertex(V vertex) {
        initilizeDijkstra();

        if (!containsVertex(vertex))
            return false;

        // copy to avoid concurrent modification in removeEdge
        Collection<E> incident = new ArrayList<E>(getIncidentEdges(vertex));

        for (E edge : incident)
            removeEdge(edge);

        vertex_maps.remove(vertex);

        return true;
    }
    @Override
    public boolean removeEdge(E edge) {
        initilizeDijkstra();

        if (!containsEdge(edge))
            return false;

        Pair<V> endpoints = getEndpoints(edge);
        V v1 = endpoints.getFirst();
        V v2 = endpoints.getSecond();

        // remove edge from incident vertices' adjacency maps
        if (getEdgeType(edge) == EdgeType.DIRECTED)
        {
            vertex_maps.get(v1)[OUTGOING].remove(v2);
            vertex_maps.get(v2)[INCOMING].remove(v1);
            directed_edges.remove(edge);
        }
        else
        {
            vertex_maps.get(v1)[INCIDENT].remove(v2);
            vertex_maps.get(v2)[INCIDENT].remove(v1);
            undirected_edges.remove(edge);
        }

        return true;
    }
}