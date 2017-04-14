package entities;

import org.apache.commons.collections15.Factory;

/**
 * MyCycle
 *
 * サイクルを表すクラス
 */
public class MyCycle {

    /**
     * ファクトリを返すクラスメソッド
     * @return MyCycleFactory
     */
    public static Factory<MyCycle> getFactory() {
        return new Factory<MyCycle>() {
            @Override
            public MyCycle create() {
                return new MyCycle();
            }
        };
    }
}
