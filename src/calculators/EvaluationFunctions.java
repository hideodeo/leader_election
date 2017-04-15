package calculators;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.List;

/**
 * EvaluationFunctions
 *
 * 評価関数を計算するくらす
 */
public class EvaluationFunctions {
    /** グラフ */
    private MyGraph<MyVertex, MyEdge> graph;
    /** サイクル集合 */
    private List<MyCycle> cycles;

    /**
     * コンストラクタ
     * @param graph グラフ
     * @param cycles サイクル集合
     */
    public EvaluationFunctions(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles) {
        this.graph = graph;
        this.cycles = cycles;
    }

    /**
     * 目的関数を計算する
     * obj func = 隣接しているサイクルのリーダー達との距離の総和の総和 / (サイクル数 or リーダー数)
     * @return 目的関数
     */
    public double objectiveFunction() {
        return 0.0;
    }

    /**
     * 平均サイクルサイズを計算する
     * @return 平均サイクルサイズ
     */
    public double averageCycleSize() {
        return 0.0;
    }

    /**
     * サイクルサイズの分散を計算する
     * @return サイクルサイズの分散
     */
    public double varianceOfCycleSize() {
        return 0.0;
    }

    /**
     * 平均隣接サイクル数を計算する
     * @return 平均隣接サイクル数
     */
    public double averageNeighborCyclesCount() {
        return 0.0;
    }

    /**
     * 1リーダーあたりの平均管理サイクル数を計算する
     * @return 1リーダーあたりの平均管理サイクル数
     */
    public double averageLeaderCyclesCount() {
        return 0.0;
    }

    /**
     * 1リーダーあたりの平均管理頂点数を計算する
     * @return 1リーダーあたりの平均管理頂点数
     */
    public double averageLeaderVertexesCount() {
        return 0.0;
    }
}
