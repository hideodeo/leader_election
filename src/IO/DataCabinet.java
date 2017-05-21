package IO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving data on a temporary basis while looping for simulation times
 */
public class DataCabinet {
    private List<Double> dataList = new ArrayList<Double>();
    private int simulationTimes = 0;

    public DataCabinet(int simulationTimesIn){
        this.simulationTimes = simulationTimesIn;
    }

    public double getAveragedValue(int i){
        return dataList.get(i) / simulationTimes;
    }

    public void add(double data){
        dataList.add(data);
    }

    public void add(int i, double data){
        dataList.add(i, data);
    }

    public List<Double> averagedDataList(){
        List<Double> averagedDataList = new ArrayList<Double>();

        for (int i=0; i < dataList.size(); i++)
            averagedDataList.add(getAveragedValue(i));
        return averagedDataList;
    }
}
