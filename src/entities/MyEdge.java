package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyEdge
 *
 * 辺を表すクラス
 */
public class MyEdge {

    /**
     * ファクトリを返すクラスメソッド
     * GraphGeneratorで使う
     * @return MyEdgeFactory
     */
    public static Factory<MyEdge> getFactory() {
        return new Factory<MyEdge>() {
            @Override
            public MyEdge create() {
                return new MyEdge();
            }
        };
    }
}
