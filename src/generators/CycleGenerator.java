package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

/**
 * CycleGenerator
 *
 * サイクルを作るクラスのためのインターフェース
 */
public interface CycleGenerator {
    /**
     * 木を作成して返すメソッド
     * @return 木
     */
    MyGraph<MyVertex, MyEdge> create();
}
