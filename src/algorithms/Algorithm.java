package algorithms;

import entities.MyVertex;
import java.util.List;

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

    List<MyVertex> create();
}
