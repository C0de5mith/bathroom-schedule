package com.github.c0de5mith.bathroom.analysis.data;

import com.github.c0de5mith.bathroom.BathroomEvent;

import java.util.Date;
import java.util.List;

public interface Schedule {
    List<BathroomEvent> getEvents(Date date);
}
