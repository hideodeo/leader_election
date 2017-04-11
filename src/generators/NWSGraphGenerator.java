package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.*;

/**
 * NWSGraphGenerator
 *
 * NWSグラフを作成するクラス
 * NWSグラフは円を書いた後にランダムに二点間に辺を追加することで得られるグラフ
 */
public class NWSGraphGenerator implements GraphGenerator {
    /** 辺を張る確率p */
    private double p = 0.5;

    /**
     * NWSグラフを作成して返す
     * @param vertexCount  頂点数
     * @return グラフ
     */
    @Override
    public MyGraph<MyVertex, MyEdge> create(int vertexCount) {
        MyGraph<MyVertex, MyEdge> graph = new MyGraph<MyVertex, MyEdge>();

        // 頂点の作成
        List<MyVertex> vertexes = new ArrayList<MyVertex>();
        for(int i = 0; i < vertexCount; i++) {
            MyVertex v = new MyVertex();
            graph.addVertex(v);
            vertexes.add(v);
        }

        // 円の作成
        // 0-1, 1-2, ..., (n-1)-(n)の辺を張るので繰り返しはn-1まで
        for(int i = 0; i < vertexCount-1; i++) {
            graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(i+1));
        }
        // n-0の辺を張る
        graph.addEdge(new MyEdge(), vertexes.get(vertexCount-1), vertexes.get(0));

        // ランダムに辺の作成
        for(int i = 0; i < vertexCount-1; i++) {
            for(int j = i+2; j < vertexCount; j++) {
                if(Math.random() <= this.p) {
                    graph.addEdge(new MyEdge(), vertexes.get(i), vertexes.get(j));
                }
            }
        }

        return graph;
    }

    /** グラフ作成に必要なパラメータのgetterとsetter */

    public double getPossibilityToCreateEdge() {
        return this.p;
    }

    public void setPossibilityToCreateEdge(double p) {
        assert p > 1 : "Possibility p must be in the range [0, 1]";
        this.p = p;
    }
}
