package calculators;

import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;
import utils.ListUtils;

import java.util.*;

/**
 * EvaluationFunctions
 *
 * 評価関数を計算するクラス
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
        double sumOfLengths = 0;
        for(MyCycle cycle : cycles) {
            MyVertex leader = leaders.get(cycle);
            List<MyCycle> neighbors = getNeighborsCycles(cycles, cycle);
            // 隣接サイクルとの距離の総和を計算する
            for(MyCycle neighbor : neighbors) {
                MyVertex neighborsLeader = leaders.get(neighbor);
                sumOfLengths += getLengthBetween(leader, neighborsLeader, graph);
            }
        }

        // サイクル数を計算
        int cyclesCount = leaders.values().size();
        // リーダー数を計算
        // Setは重複を許さないので自然に重複しているリーダーが消えるはず
//        Set<MyVertex> leaderVertexes = (Set<MyVertex>) leaders.values();
//        int leadersCount = leaderVertexes.size();

        return sumOfLengths/cyclesCount;
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
     * 頂点間の距離を計算する
     * @param s ソースノード
     * @param t 宛先ノード
     * @param graph グラフ
     * @return 距離
     */
    private static int getLengthBetween(MyVertex s, MyVertex t, MyGraph<MyVertex, MyEdge> graph) {
        DijkstraDistance<MyVertex, MyEdge> dd = new DijkstraDistance<MyVertex, MyEdge>(graph);
        return dd.getDistance(s, t).intValue();
    }

    /**
     * 平均サイクルサイズを計算する
     * @param cycles サイクル集合
     * @return 平均サイクルサイズ
     */
    public static double averageCycleSize(List<MyCycle> cycles) {
        double sum = 0.0;
        for(MyCycle c : cycles) {
            sum += c.getSize();
        }
        return sum / cycles.size();
    }

    /**
     * サイクルサイズの分散を計算する
     * @param cycles サイクル集合
     * @return サイクルサイズの分散
     */
    public static double varianceOfCycleSize(List<MyCycle> cycles) {
        double average = averageCycleSize(cycles);
        double sum = 0.0;
        for(MyCycle c : cycles) {
            double diff = average - c.getSize();
            sum += diff * diff;
        }
        return sum / cycles.size();
    }

    /**
     * 平均隣接サイクル数を計算する
     * @param graph グラフ
     * @param cycles サイクル集合
     * @return 平均隣接サイクル数
     */
    public static double averageNeighborCyclesCount(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles) {
        double sum = 0.0;
        for(MyCycle c : cycles) {
            List<MyCycle> neighbors = getNeighborsCycles(cycles, c);
            sum += neighbors.size();
        }
        return sum / cycles.size();
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
