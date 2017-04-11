package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.ArrayList;
import java.util.List;

/**
 * LatticeGraphGenerator
 *
 * Latticeグラフを作成するクラス
 *
 */
public class LatticeGraphGenerator implements GraphGenerator{

    /**
     * Latticeグラフを作成して返す
     * 前提：受け取る頂点数は必ずx^2の形
     * @param vertexCount  頂点数
     * @return Latticeグラフ
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create(int vertexCount) {
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();

        // 頂点の作成・追加
        List<MyVertex> vertexes = new ArrayList<MyVertex>();
        for(int i = 0; i < vertexCount ; i++) {
            MyVertex v = new MyVertex();
            graph.addVertex(v);
            vertexes.add(v);
        }

        int latticeSize = (int) Math.sqrt(vertexCount);

        // 最初に一行目を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        // 1   2   3
        // + - + - +
        for(int i = 0; i < latticeSize-1; i++) {
            graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(i+1));
        }

        // 2行目以降を作る
        // ex) latticeSize = 3のとき、以下の辺を作る
        //      0   1   2
        // row0 +   +   +
        //      |   |   |
        // row1 + - + - +
        //      3   4   5
        //
        // ※一番左（baseVertex）は latticeSize * rowの値
        for(int row = 1; row < latticeSize; row++) {
            int baseVertex = latticeSize * row;
            for(int i = 0; i < latticeSize-1; i++) {
                graph.addEdge(new MyEdge(), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i-latticeSize)); // 上の頂点と接続
                graph.addEdge(new MyEdge(), vertexes.get(baseVertex+i), vertexes.get(baseVertex+i+1)); // 右の頂点と接続
            }
            // 上の頂点と接続. 一番右の頂点は右に頂点は無いので上だけ.
            graph.addEdge(new MyEdge(), vertexes.get(baseVertex+(latticeSize-1)), vertexes.get(baseVertex+-1));
        }

        return graph;
    }
}
