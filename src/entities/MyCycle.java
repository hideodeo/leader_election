package entities;

/**
 * MyCycle
 *
 * サイクルを表すクラス
 */
public class MyCycle {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> cycle;

    /**
     * コンストラクタ
     */
    public MyCycle(){

    }

    public boolean isCycle(MyGraph<MyVertex, MyEdge> cycleIn){
        for (MyVertex v: cycleIn.getVertices()){
            if (cycleIn.inDegree(v) != 2)
                return false;
        }
        return true;
    }
}
