package IO;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for saving data on a temporary basis while looping for simulation times
 */
public class DataCabinet {
    private List<Double> dataList = new ArrayList<Double>();
    private int simulationTimes = 0;

    /**
     * constructor
     * @param listSize
     * @param simulationTimesIn
     */
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

    public double get(int i){
        return dataList.get(i);
    }

    public void cumulate(int i, double data){
        double buff = dataList.get(i);
        dataList.remove(i);
        dataList.add(i, buff + data);
    }

    public List<Double> getAveragedDataList(){
        List<Double> averagedDataList = new ArrayList<Double>();

        for (int i=0; i < dataList.size(); i++)
            averagedDataList.add(getAveragedValue(i));
        return averagedDataList;
    }

    public List<Double> getChangeRatioList(){
        List<Double> changeRatioDataList = new ArrayList<Double>();
        changeRatioDataList.add(0.0);
        double val = 0.0;

        for (int i=0; i < dataList.size() -1 ; i++){
            val = (getAveragedValue(i + 1) - getAveragedValue(i)) / getAveragedValue(i);
            changeRatioDataList.add(val);
        }
        return changeRatioDataList;
    }
}
