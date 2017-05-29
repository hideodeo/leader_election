package generators;

import edu.uci.ics.jung.algorithms.generators.random.BarabasiAlbertGenerator;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import java.util.HashSet;

/**
 * BAGraphGenerator
 *
 * BAグラフを作るクラス
 * 参考：
 * http://jung.sourceforge.net/doc/api/edu/uci/ics/jung/algorithms/generators/random/BarabasiAlbertGenerator.html
 */
public class BAGraphGenerator implements GraphGenerator {
    /** 頂点数 */
    private int vertexCount;
    /** 頂点数 */
    private int initVertex = 2;
    /** evolve()1回あたりに辺を作る個数 */
    private int numEdgesToAttach = 2;

    /**
     * コンストラクタ
     * @param vertexCount 頂点数
     */
    public BAGraphGenerator(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * BAグラフを作成する
     * @return BAグラフ
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create() {
        BarabasiAlbertGenerator bag = new BarabasiAlbertGenerator<MyVertex, MyEdge>(
                MyGraph.getFactory(),
                MyVertex.getFactory(),
                MyEdge.getFactory(),
                this.initVertex,
                this.numEdgesToAttach,
                new HashSet<MyVertex>()
        );
        bag.evolveGraph(vertexCount - initVertex);
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) bag.create();

//        for(MyVertex v1: graph.getVertices()){
//            if(v1.getID() == 0){
//                for(MyVertex v2:graph.getVertices()){
//                    if(v2.getID() == 1){
//                        graph.addEdge(new MyEdge(), v1, v2);
//                        break;
//                    }
//                }
//                break;
//            }
//        }

        return graph;
    }
}
