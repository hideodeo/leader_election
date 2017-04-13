package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyVertex
 *
 * 頂点を表すクラス
 */
public class MyVertex {
<<<<<<< HEAD
    boolean DFSLabel=false;

    public void setDFSLabel(boolean DFSLabel) {
        this.DFSLabel = DFSLabel;
    }

    public boolean getDFSLabel() {
        return this.DFSLabel;
=======

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
>>>>>>> fcfa753ff1de835c7c6d8da2b34bbdae5a94298f
    }
}
