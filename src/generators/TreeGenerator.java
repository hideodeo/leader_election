package generators;

import entities.MyGraph;

/**
 * Created by Hideo on 2017/04/11.
 *
 * TreeGenerator
 *
 * ツリーを作成するクラスのためのインターフェース
 */
public interface TreeGenerator {
    /**
     * ツリーを作成して返すメソッド
     * @param graph
     */
    void create(MyGraph graph);
}
