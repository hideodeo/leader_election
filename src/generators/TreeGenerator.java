package generators;

<<<<<<< HEAD
import entities.MyGraph;

/**
 * Created by Hideo on 2017/04/11.
 *
 * TreeGenerator
 *
 * ツリーを作成するクラスのためのインターフェース
 */
public interface TreeGenerator {
    /**
     * ツリーを作成して返すメソッド
     * @param graphIn
     */
    void create(MyGraph graphIn);
=======
import entities.MyEdge;
import entities.MyGraph;
import entities.MyVertex;

/**
 * TreeGenerator
 *
 * 木を作るクラスのためのインターフェース
 */
public interface TreeGenerator {
    /**
     * 木を作成して返すメソッド
     * @return 木
     */
    MyGraph<MyVertex, MyEdge> create();
>>>>>>> fcfa753ff1de835c7c6d8da2b34bbdae5a94298f
}
