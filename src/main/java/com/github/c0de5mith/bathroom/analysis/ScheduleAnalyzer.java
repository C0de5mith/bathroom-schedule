package com.github.c0de5mith.bathroom.analysis;

import com.github.c0de5mith.bathroom.BathroomEvent;
import com.github.c0de5mith.bathroom.OutputType;
import com.github.c0de5mith.bathroom.analysis.data.Schedule;
import com.github.c0de5mith.bathroom.analysis.methods.MultimodalAnalysis;

import java.util.*;

public class ScheduleAnalyzer {
    private static Comparator<BathroomEvent> timeComparator = new Comparator<BathroomEvent>() {
        @Override
        public int compare(BathroomEvent o1, BathroomEvent o2) {
            return o1.time - o2.time;
        }
    };
    public static void analyze(List<BathroomEvent> eventList, AnalyzerSettings settings){
        System.out.println("Starting analysis");
        System.out.println("Sorting "+eventList.size()+" event"+(eventList.size()>1?"s":""));
        Map<OutputType, Map<Date, List<BathroomEvent>>> dateListMap = new HashMap<>();
        for(OutputType type : OutputType.values()){
            dateListMap.put(type, new TreeMap<>());
        }
        for(BathroomEvent event : eventList){
            if(!dateListMap.get(event.type).containsKey(event.date)){
                dateListMap.get(event.type).put(event.date, new ArrayList<>());
            }
            dateListMap.get(event.type).get(event.date).add(event);
        }
        for(OutputType type : OutputType.values()){
            for(Date key : dateListMap.get(type).keySet()){
                dateListMap.get(type).get(key).sort(timeComparator);
            }
        }
        Schedule schedule = settings.analysisMethod.generateSchedule(dateListMap);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        System.out.println("Tomorrow's schedule");
        List<BathroomEvent> events = schedule.getEvents(calendar.getTime());
        for(BathroomEvent event : events){
            System.out.println(event.prettyPrint());
        }
    }
}
