package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

/**
 * TreeGenerator
 *
 * 木を作るクラスのためのインターフェース
 */
public interface TreeGenerator {
    /**
     * 木を作成して返すメソッド
     * @return 木
     */
    MyGraph<MyVertex, MyEdge> create();
}
