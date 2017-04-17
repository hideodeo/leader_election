package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyVertex
 *
 * 頂点を表すクラス
 */
public class MyVertex {
    /** # of adjacent cycles*/
    private int numOfAdCycles = 0;
    /** value of closeness centrality*/
    private double closenessCentrality = 0;

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

    public void setNumOfAdCycles(int numOfAdCycles) {
        this.numOfAdCycles = numOfAdCycles;
    }

    public int getNumOfAdCycles(){
        return numOfAdCycles;
    }

    public void setClosenessCentrality(double closenessCentrality) {
        this.closenessCentrality = closenessCentrality;
    }

    public double getClosenessCentrality() {
        return closenessCentrality;
    }
}
