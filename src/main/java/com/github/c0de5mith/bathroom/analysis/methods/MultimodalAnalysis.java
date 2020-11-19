package com.github.c0de5mith.bathroom.analysis.methods;

import com.github.c0de5mith.bathroom.BathroomEvent;
import com.github.c0de5mith.bathroom.OutputType;
import com.github.c0de5mith.bathroom.analysis.data.DailySchedule;

import java.util.*;

public class MultimodalAnalysis implements AnalysisMethod {
    private double[] getCountStatistics(Map<Date, List<BathroomEvent>> dateListMap){
        double average = 0;
        double iterations = 0;
        for(Date key : dateListMap.keySet()){
            List<BathroomEvent> events = dateListMap.get(key);
            average*=iterations/(iterations+1);
            iterations++;
            average+=events.size()/iterations;
        }
        double variance = 0;
        iterations = 0;
        for(Date key : dateListMap.keySet()){
            List<BathroomEvent> events = dateListMap.get(key);
            variance*=iterations/(iterations+1);
            iterations++;
            variance+=Math.pow(events.size()-average, 2)/iterations;
        }
        return new double[]{average, variance};
    }
    private List<Integer> getTimes(Map<Date, List<BathroomEvent>> dateListMap, int number){
        List<Double> times = new ArrayList<>();
        for(int i = 0; i < number; i++){
            times.add(0.0);
        }
        int iterations = 0;
        for(Date key : dateListMap.keySet()){
            List<BathroomEvent> events = dateListMap.get(key);
            iterations++;
            for(int i = 0; i < number && i < events.size(); i++){
                times.set(i,times.get(i)*((iterations - 1)/iterations)+
                        events.get(i).time/(iterations));
            }
        }
        List<Integer> result = new ArrayList<>();
        for(double time : times){
            result.add((int)time);
        }
        return result;
    }
    @Override
    public DailySchedule generateSchedule(Map<OutputType, Map<Date, List<BathroomEvent>>> dateListMap) {
        Map<OutputType, List<Integer>> timeMap = new HashMap<>();
        for(OutputType type : OutputType.values()){
            double[] countStatistics = getCountStatistics(dateListMap.get(type));
            System.out.println("Events of type "+type.name()+" average "+countStatistics[0]+" times per day with a variance of "+countStatistics[1]);
            System.out.println("Expecting "+(int)Math.ceil(countStatistics[0]+countStatistics[1]/2)+" events for type "+type);
            timeMap.put(type, getTimes(dateListMap.get(type), (int)Math.ceil(countStatistics[0]+countStatistics[1]/2)));
        }
        return new DailySchedule(timeMap);
    }
}
