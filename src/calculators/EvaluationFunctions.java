package calculators;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.*;

/**
 * EvaluationFunctions
 *
 * class to calculate evaluation functions
 */
public class EvaluationFunctions {
    /**
     * 目的関数を計算する
     * obj func = （隣接サイクルのリーダー達との距離の総和 / 隣接サイクル数の総和）/ 全サイクル数
     * or
     * obj func = 隣接サイクルのリーダー達との距離の総和 / 全サイクル数
     * @param graph グラフ
     * @param cycles サイクル
     * @param leaders リーダー
     * @return 目的関数
     */
    public static double objectiveFunction(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles, Map<MyCycle, MyVertex> leaders) {
        double sumOfLengths = 0;
        double result = 0;
        /**for(MyCycle cycle : cycles) {
            MyVertex leader = leaders.get(cycle);
            List<MyCycle> neighbors = cycle.getAdjacentCycles();
            // 隣接サイクルとの距離の総和を計算する
            for(MyCycle neighbor : neighbors) {
                MyVertex neighborsLeader = leaders.get(neighbor);
                sumOfLengths += graph.getDistanceBetween(leader, neighborsLeader);
            }
            result += sumOfLengths / neighbors.size();
            sumOfLengths = 0;
        }**/
        for(MyCycle cycle : cycles) {
            MyVertex leader = leaders.get(cycle);
            List<MyCycle> neighbors = cycle.getAdjacentCycles();
            // 隣接サイクルとの距離の総和を計算する
            for(MyCycle neighbor : neighbors) {
                MyVertex neighborsLeader = leaders.get(neighbor);
                sumOfLengths += graph.getDistanceBetween(leader, neighborsLeader);
            }
            result += sumOfLengths;
            sumOfLengths = 0;
        }

        // サイクル数を計算
        int cyclesCount = cycles.size();
        result /= (cyclesCount * 2);
        return result;
    }

    /**
     * リーダー数を計算. Setは重複を許さないので自然に重複しているリーダーが消えるはず
     * @param leaders
     * @return
     */
    public static int countLeaderVertexes(Map<MyCycle, MyVertex> leaders){
        Set<MyVertex> leaderVertexes = new HashSet<MyVertex>(leaders.values());
        return leaderVertexes.size();
    }

    /**
     * 平均サイクルサイズを計算する
     * @param cycles サイクル集合
     * @return 平均サイクルサイズ
     */
    public static double averageCycleSize(List<MyCycle> cycles) {
        double sum = 0.0;
        for(MyCycle c : cycles) {
            sum += c.getVertexCount();
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
            double diff = average - c.getVertexCount();
            sum += diff * diff;
        }
        return sum / cycles.size();
    }

    /**
     * サイクルサイズの標準偏差を計算する
     * @param cycles サイクル集合
     * @return サイクルサイズの標準偏差
     */
    public static double standardDeviationOfCycleSize(List<MyCycle> cycles) {
      double variance = varianceOfCycleSize(cycles);
      return Math.sqrt(variance);
    }

    /**
     * 平均隣接サイクル数を計算する
     * @param cycles サイクル集合
     * @return 平均隣接サイクル数
     */
    public static double averageNeighborCyclesCount(List<MyCycle> cycles) {
        double sum = 0.0;
        for(MyCycle c : cycles) {
            List<MyCycle> neighbors = c.getAdjacentCycles();
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
        // キーがリーダー、値が管理しているサイクル数となるマップを作成する
        Map<MyVertex, Integer> manageCyclesMap = new HashMap<MyVertex, Integer>();
        for(Map.Entry<MyCycle, MyVertex> entry : leaders.entrySet()) {
            // keyがなければnullを返すので0を代入してエラーを防ぐ
            Integer manageCycleCount = manageCyclesMap.get(entry.getValue());
            if (manageCycleCount == null) {
                manageCycleCount = 0;
            }
            manageCyclesMap.put(entry.getValue(), ++manageCycleCount);
        }

        // 平均管理サイクル数を計算する
        int leadersCount = manageCyclesMap.keySet().size();
        double sum = 0.0;
        for(Integer cyclesCount : manageCyclesMap.values()) {
            sum += cyclesCount;
        }
        return sum / leadersCount;
    }

    /**
     * 1リーダーあたりの平均管理頂点数を計算する
     * @param graph グラフ
     * @param cycles サイクル集合
     * @param leaders リーダー
     * @return 1リーダーあたりの平均管理頂点数
     */
    public static double averageLeaderVertexesCount(MyGraph<MyVertex, MyEdge> graph, List<MyCycle> cycles, Map<MyCycle, MyVertex> leaders) {
        // キーがリーダー、値が管理している頂点集合となるマップを作成する
        Map<MyVertex, Set<MyVertex>> manageVertexesMap = new HashMap<MyVertex, Set<MyVertex>>();
        for(Map.Entry<MyCycle, MyVertex> entry : leaders.entrySet()) {
            Set<MyVertex> vertexes = manageVertexesMap.get(entry.getValue());
            if(vertexes == null) {
                vertexes = new HashSet<MyVertex>();
            }
            for(MyVertex v : entry.getKey().getVerticesList()) {
                vertexes.add(v);
            }
            manageVertexesMap.put(entry.getValue(), vertexes);
        }

        // 平均管理頂点数を計算する
        int leadersCount = manageVertexesMap.keySet().size();
        double sum = 0.0;
        for(Set<MyVertex> vertexes : manageVertexesMap.values()) {
            sum += vertexes.size();
        }
        return sum / leadersCount;
    }

    public static double clusteringCoefficient(MyGraph graph) {
        double total = 0.0;
        for (Object v : graph.getVertices()) {
            // Cumulate local clustering coefficient of vertex v.
            int possible = graph.degree(v) * (graph.degree(v) - 1);
            int actual = 0;
            for (Object u : graph.getNeighbors(v)) {
                for (Object w : graph.getNeighbors(v)) {
                    if (graph.getNeighbors(w).contains(u))
                        actual++;
                }
            }
            if (possible > 0) {
                total += 1.0 * actual / possible;
            }
        }
        return total / graph.getVertexCount();
    }
}
