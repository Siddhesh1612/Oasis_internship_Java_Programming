package service;

import java.util.LinkedHashMap;
import java.util.Map;

public class TrainService {

    private final Map<String, String> trainMap;

    public TrainService() {
        trainMap = new LinkedHashMap<>();
        trainMap.put("12001", "Shatabdi Express");
        trainMap.put("12627", "Karnataka Express");
        trainMap.put("12951", "Rajdhani Express");
        trainMap.put("16382", "Kanniyakumari Express");
        trainMap.put("12723", "Telangana Express");
    }

    public String getTrainName(String trainNumber) {
        return trainMap.getOrDefault(trainNumber.trim(), "");
    }

    public boolean isValidTrainNumber(String trainNumber) {
        return trainMap.containsKey(trainNumber.trim());
    }

    public String[] getTrainNumbers() {
        return trainMap.keySet().toArray(new String[0]);
    }
}
