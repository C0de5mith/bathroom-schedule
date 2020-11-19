package com.github.c0de5mith.bathroom.analysis.data;

import com.github.c0de5mith.bathroom.BathroomEvent;
import com.github.c0de5mith.bathroom.OutputType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DailySchedule implements Schedule {
    private final Map<OutputType, List<Integer>> times;

    public DailySchedule(Map<OutputType, List<Integer>> times){
        this.times = times;
    }
    @Override
    public List<BathroomEvent> getEvents(Date date) {
        List<BathroomEvent> events = new ArrayList<>();
        for(OutputType type : OutputType.values()){
            List<Integer> typedTimes = times.get(type);
            for(int time : typedTimes){
                events.add(new BathroomEvent(type, date, time, 60));
            }
        }
        return events;
    }
}
