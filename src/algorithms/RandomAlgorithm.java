package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.*;

/**
 * Random
 *
 * Generates 100 solutions and choose the best one.
 */
public class RandomAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public RandomAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * リーダー集合を返すメソッド
     * @return map
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        Map<MyCycle, MyVertex> map = new HashMap<MyCycle, MyVertex>();
        Random rnd = new Random();

        for (MyCycle c: cycleList){
            List<MyVertex> list = new ArrayList<MyVertex>(c.getVertices());
            int leaderIndex = rnd.nextInt(list.size());

            map.put(c, list.get(leaderIndex));
        }
        return map;
    }
}
