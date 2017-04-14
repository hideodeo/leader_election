package generators;

import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import java.util.List;

/**
 * CycleGenerator
 *
 * サイクルを作るクラスのためのインターフェース
 */
public interface CyclesGenerator {
    /**
     * サイクルの集合を作成してリストで返すメソッド
     * @return サイクル集合のリスト
     */
    List<MyGraph<MyVertex, MyEdge>> create();
}
