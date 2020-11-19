package com.github.c0de5mith.bathroom.analysis.methods;

import com.github.c0de5mith.bathroom.BathroomEvent;
import com.github.c0de5mith.bathroom.OutputType;
import com.github.c0de5mith.bathroom.analysis.data.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AnalysisMethod {
    Schedule generateSchedule(Map<OutputType, Map<Date, List<BathroomEvent>>> dateListMapOne);
}