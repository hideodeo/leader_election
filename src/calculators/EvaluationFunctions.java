package calculators;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import utils.ListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * EvaluationFunctions
 *
 * 評価関数を計算するくらす
 */
public class EvaluationFunctions {
    /**
     * 目的関数を計算する
     * obj func = 隣接しているサイクルのリーダー達との距離の総和の総和 / (サイクル数 or リーダー数)
     * @param graph グラフ
     * @param cycles サイクル
     * @param leaders リーダー
     * @return 目的関数
     */
    public static double objectiveFunction(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles, Map<MyCycle, MyVertex> leaders) {
        return 0.0;
    }

    /**
     * 隣接サイクルを返す
     * 隣接 := 頂点を少なくとも一つ共有している
     * @param cycles サイクル集合
     * @param cycle サイクル
     * @return 隣接サイクルの集合
     */
    private static List<MyCycle> getNeighborsCycles(List<MyCycle> cycles, MyCycle cycle) {
        List<MyCycle> neighbors = new ArrayList<MyCycle>();
        for(MyCycle c : cycles) {
            if(c.equals(cycle)) continue;
            List product = ListUtils.product(c.asVertexList(), cycle.asVertexList());
            if(0 < product.size()) {
                neighbors.add(c);
            }
        }
        return neighbors;
    }

    /**
     * 平均サイクルサイズを計算する
     * @param cycles サイクル集合
     * @return 平均サイクルサイズ
     */
    public static double averageCycleSize(List<MyCycle> cycles) {
        return 0.0;
    }

    /**
     * サイクルサイズの分散を計算する
     * @param cycles サイクル集合
     * @return サイクルサイズの分散
     */
    public static double varianceOfCycleSize(List<MyCycle> cycles) {
        return 0.0;
    }

    /**
     * 平均隣接サイクル数を計算する
     * @param graph グラフ
     * @param cycle サイクル
     * @return 平均隣接サイクル数
     */
    public static double averageNeighborCyclesCount(MyGraph<MyVertex, MyEdge> graph, MyCycle cycle) {
        return 0.0;
    }

    /**
     * 1リーダーあたりの平均管理サイクル数を計算する
     * @param graph グラフ
     * @param cycles サイクル集合
     * @param leaders リーダー
     * @return 1リーダーあたりの平均管理サイクル数
     */
    public static double averageLeaderCyclesCount(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles, Map<MyCycle, MyVertex> leaders) {
        return 0.0;
    }

    /**
     * 1リーダーあたりの平均管理頂点数を計算する
     * @param graph グラフ
     * @param cycles サイクル集合
     * @param leaders リーダー
     * @return 1リーダーあたりの平均管理頂点数
     */
    public static double averageLeaderVertexesCount(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles, Map<MyCycle, MyVertex> leaders) {
        return 0.0;
    }
}
