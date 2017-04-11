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
    /** evolve()1回あたりに辺を作る個数? */
    private int numEdgesToAttach = 5;
    /** evolve()する回数 */
    private int numTimeSteps = 10;

    /**
     * BAグラフを作成する
     * @param vertexCount  頂点数
     * @return BAグラフ
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create(int vertexCount) {
        BarabasiAlbertGenerator bag = new BarabasiAlbertGenerator<MyVertex, MyEdge>(
                MyGraph.getFactory(),
                MyVertex.getFactory(),
                MyEdge.getFactory(),
                vertexCount,
                numEdgesToAttach,
                new HashSet<MyVertex>()
        );
        bag.evolveGraph(numTimeSteps);
        MyGraph<MyVertex, MyEdge> graph = (MyGraph<MyVertex, MyEdge>) bag.create();
        return graph;
    }

    /** グラフ作成に必要なパラメータのgetterとsetter */

    public int getNumEdgesToAttach() {
        return this.numEdgesToAttach;
    }

    public void setNumEdgesToAttach(int num) {
        this.numEdgesToAttach = num;
    }

    public int getNumTimeSteps() {
        return this.numTimeSteps;
    }

    public void setNumTimeSteps(int num) {
        this.numTimeSteps = num;
    }
}
