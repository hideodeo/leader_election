package generators;

import edu.uci.ics.jung.graph.event.GraphEvent;
import entities.MyGraph;
import entities.MyVertex;
import entities.MyEdge;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hideo on 2017/04/11.
 *
 * DFSTreeGenerator
 *
 * DFS treeを作成するクラス
 */
public class DFSTreeGenerator implements TreeGenerator {

    MyGraph<MyVertex, MyEdge> graph;
    ArrayList<GraphEvent.Edge> Tree;
    MyVertex rootVertex;
    int count = 0;
    boolean label = false;

    @Override
    public void create(MyGraph graphIn) {
        graph = graphIn;

        for (MyVertex v : graph.getVertices()) {
            v.setDFSLabel(false);
        }

        for(MyVertex v: graph.getVertices()){
            search(v);
            break;
        }

        if (count == graph.getVertexCount()) label = true;
        else label = false;
    }

    public Collection<GraphEvent.Edge> getTree() {
        return Tree;
    }

    public void search(MyVertex v) {
        count++;
        v.setDFSLabel(true);
        for (MyVertex w : graph.getNeighbors(v)) {

            if (!w.getDFSLabel()) {
                search(w);
            }
        }
    }
}
