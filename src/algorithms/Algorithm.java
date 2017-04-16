package algorithms;

import entities.MyCycle;
import entities.MyVertex;

import java.util.Map;

/**
 * Algorithm
 *
 * algorithmクラスのためのインターフェース
 */
public interface Algorithm {
    /**
     * リーダーを選出するメソッド
     * @return リーダー集合のリスト
     */

    Map<MyCycle, MyVertex> solve();
}
