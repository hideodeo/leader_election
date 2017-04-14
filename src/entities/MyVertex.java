package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyVertex
 *
 * 頂点を表すクラス
 */
public class MyVertex {
    /**
     * ファクトリを返すクラスメソッド
     * GraphGeneratorで使う
     * @return MyVertexFactory
     */
    public static Factory<MyVertex> getFactory() {
        return new Factory<MyVertex>() {
            @Override
            public MyVertex create() {
                return new MyVertex();
            }
        };
    }
}
