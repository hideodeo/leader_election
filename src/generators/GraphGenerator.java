package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

/**
 * GraphGenerator
 *
 * グラフを作成するクラスのためのインターフェース
 */
public interface GraphGenerator {
    /**
     * グラフを作成して返すメソッド
     * @return  作成されたグラフ
     */
    MyGraph<MyVertex, MyEdge> create();
}
