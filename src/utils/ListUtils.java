package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * ListUtils
 *
 * リストに関するutilityクラス
 */
public class ListUtils {
    /**
     * 二つのリストの論理積を返す
     * @param l1 リスト1
     * @param l2 リスト2
     * @return l1 ∩ l2
     */
    public static List product(List l1, List l2) {
        List<Object> result = new ArrayList<Object>();
        for(Object a : l1) {
            for(Object b : l2) {
                if(a.equals(b)) {
                    result.add(a);
                    l1.remove(a);
                    l2.remove(b);
                }
            }
        }
        return result;
    }
}
