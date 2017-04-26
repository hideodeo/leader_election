package IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ResultsOutputer
 *
 * 結果をcsvに吐き出すためのクラス
 */
public class ResultsOutputer {
    /** グラフの種類*/
    private String graphType = "unknown graph";
    /** 頂点数 */
    private String vertexCount = "unknown vertex count";
    /** 木の種類 */
    private String treeType = "unknown tree type";
    /** アルゴリズムの種類 */
    private String algoType = "unknown algorithm type";

    /**
     * グラフの種類を設定する
     * @param graphType グラフの種類
     */
    public void setGraphType(String graphType) {
        this.graphType = graphType;
    }

    /**
     * 頂点数を設定する
     * @param vertexCount 頂点数
     */
    public void setVertexCount(int vertexCount) {
        this.vertexCount = String.valueOf(vertexCount);
    }

    /**
     * 木の種類を設定する
     * @param treeType 木の種類
     */
    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    /**
     * アルゴリズムの種類を設定する
     * @param algoType アルゴリズムの種類
     */
    public void setAlgorithmType(String algoType) {
        this.algoType = algoType;
    }

    /**
     * ファイルに出力する
     * TODO: 出力に必要なデータを受け取る
     */
    public void write(List<List<Double>> lists) {
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(getFullPath(), true);
            pw = new PrintWriter(new BufferedWriter(fw));
        } catch ( IOException ex ) {
            System.out.println("ファイルの出力に失敗しました");
            return;
        }

        // header
        pw.print(graphType + ", " + vertexCount + ", " + treeType + ", " + algoType + ",");
        pw.println();

        // write data 
        for (int i = 0; i < lists.get(0).size(); i++) {
            for (List<Double> list : lists) {
                pw.print(list.get(i) + ",");
            }
            pw.println();
        }

        // close file
        pw.close();
    }

    /**
     * ファイルの出力先を取得する
     * @return 出力先のフルパス
     */
    private String getFullPath(){
        Date now = new Date();
        DateFormat dfYMD = new SimpleDateFormat("YYYYMMDD");
        DateFormat dfHMS = new SimpleDateFormat("hhmmss");
        // ex. 20180417T102530_NWS_30_BFS_Random.csv
        return dfYMD.format(now) + "T" + dfHMS.format(now) + "_" + graphType + "_" + vertexCount + "_" + treeType + "_" + algoType + ".csv";
    }
}
