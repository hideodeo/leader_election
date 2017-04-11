package entities;

/**
 * MyVertex
 *
 * 頂点を表すクラス
 */
public class MyVertex {
    boolean DFSLabel=false;

    public void setDFSLabel(boolean DFSLabel) {
        this.DFSLabel = DFSLabel;
    }

    public boolean getDFSLabel() {
        return this.DFSLabel;
    }
}
