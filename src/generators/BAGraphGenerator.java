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
    /** evolve()1回あたりに辺を作る個数? */
    private int numEdgesToAttach = 5;
    /** evolve()する回数 */
    private int numTimeSteps = 10;

    /**
     * コンストラクタ
     * @param vertexCount 頂点数
     */
    public BAGraphGenerator(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    /**
     * コンストラクタ
     * @param vertexCount 頂点数
     * @param numEdgesToAttach evolve1回あたりに辺を作る個数?
     * @param numTimeSteps evolve数
     */
    public BAGraphGenerator(int vertexCount, int numEdgesToAttach, int numTimeSteps) {
        this.vertexCount = vertexCount;
        this.numEdgesToAttach = numEdgesToAttach;
        this.numTimeSteps = numTimeSteps;
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
                this.vertexCount,
                this.numEdgesToAttach,
                new HashSet<MyVertex>()
        );
        bag.evolveGraph(this.numTimeSteps);
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) bag.create();
        return graph;
    }
}
