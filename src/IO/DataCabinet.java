package IO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving data on a temporary basis while looping for simulation times
 */
public class DataCabinet {
    private List<Double> dataList = new ArrayList<Double>();
    private int simulationTimes = 0;

    public DataCabinet(int listSize, int simulationTimesIn){
        this.simulationTimes = simulationTimesIn;
        /** initialize the list for data collection */
        for (int i=0; i < listSize; i++){
            dataList.add(0.0);
        }
    }

    public double getAveragedValue(int i){
        return dataList.get(i) / simulationTimes;
    }

    public void cumulate(int i, double data){
        dataList.add(i, dataList.get(i) + data);
    }

    public List<Double> getAveragedDataList(){
        List<Double> averagedDataList = new ArrayList<Double>();

        for (int i=0; i < dataList.size(); i++)
            averagedDataList.add(getAveragedValue(i));
        return averagedDataList;
    }
}
