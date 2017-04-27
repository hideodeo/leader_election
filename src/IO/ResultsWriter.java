package IO;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * ResultsOutputer
 *
 * 結果をcsvに吐き出すためのクラス
 */
public class ResultsWriter {
    /**
     * receive data/header and write to csv file
     * @param lists
     * @param headers
     */
    public void write(List<List<Double>> lists, String filePath, String... headers) {
        PrintWriter pw;
        try {
            FileWriter fw = new FileWriter(getFullPath(), true);
            pw = new PrintWriter(new BufferedWriter(fw));
        } catch ( IOException ex ) {
            System.out.println("ファイルの出力に失敗しました");
            return;
        }

        /** header */
        for (String h: headers){
            pw.print(h + ",");
        }
        pw.println();

        /** write data */
        for (int i = 0; i < lists.get(0).size(); i++) {
            for (List<Double> list : lists) {
                pw.print(list.get(i) + ",");
            }
            pw.println();
        }

        /** close file */
        pw.close();
    }

    /**
     * ファイルの出力先を取得する
     * @return 出力先のフルパス
     */
    public String getFullPath(String... names){
        /** ex. NWS_30_BFS_Random.csv */
        String path = "../simulationData";
        for (int i=0; i<names.length; i++){
            if (i != names.length-1)
                path += names[i] + "_";
            else
                path += names[i];
        }
        return path + ".csv";
    }
}
