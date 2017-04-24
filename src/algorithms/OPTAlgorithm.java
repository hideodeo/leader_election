package algorithms;

import entities.MyCycle;
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OPTAlgorithm
 *
 * 全組み合わせの解を生成し、最も良い解を返すクラス
 */
public class OPTAlgorithm implements Algorithm {
    /** graph */
    private MyGraph<MyVertex, MyEdge> graph;
    /** cycle set */
    private List<MyCycle> cycleList;
    /**
     * コンストラクタ
     * @param graphIn グラフ
     * @param cycleListIn ルートノード
     */
    public OPTAlgorithm(MyGraph<MyVertex, MyEdge> graphIn, List<MyCycle> cycleListIn) {
        this.graph = graphIn;
        this.cycleList = cycleListIn;
    }

    /**
     * リーダー集合を返すメソッド
     * @return map
     */
    @Override
    public Map<MyCycle, MyVertex> solve() {
        Map<MyCycle, MyVertex> resultMap = new HashMap<MyCycle, MyVertex>();
        double objectiveFunctionValue = Double.MAX_VALUE;
        return resultMap;
    }

    /**
     * http://stackoverflow.com/questions/17192796/generate-all-combinations-from-multiple-lists
     *
     * @param Lists
     * @param result
     * @param depth
     * @param current
     */
    void GeneratePermutations(List<List<Character>> Lists, List<String> result, int depth, String current)
    {
        if(depth == Lists.size())
        {
            result.add(current);
            return;
        }

        for(int i = 0; i < Lists.get(depth).size(); ++i)
        {
            GeneratePermutations(Lists, result, depth + 1, current + Lists.get(depth).get(i));
        }
    }
}
