package generators;

import entities.MyCycle;

import java.util.List;

/**
 * CycleGenerator
 *
 * サイクルを作るクラスのためのインターフェース
 */
public interface CyclesGenerator {
    /**
     * サイクルの集合を作成してリストで返すメソッド
     * @return サイクル集合のリスト
     */
    List<MyCycle> create();
}
