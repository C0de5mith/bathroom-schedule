package com.github.c0de5mith.bathroom;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BathroomEvent {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public final OutputType type;
    public final Date date;
    public final int time;
    public final int duration;

    public BathroomEvent(String[] values) throws ParseException {
        type = OutputType.valueOf(values[0]);
        date = dateFormat.parse(values[1]);
        time = Integer.parseInt(values[2]);
        duration = Integer.parseInt(values[3]);
    }
    public BathroomEvent(OutputType type, Date date, int time, int duration){
        this.type = type;
        this.date = date;
        this.time = time;
        this.duration = duration;
    }

    public String commaSeparated(){
        return type.name()+","+dateFormat.format(date)+","+time+","+duration;
    }

    public String prettyPrint() {
        return dateFormat.format(date)+", "+type.name()+", "+(time/60)+":"+(time%60)+", "+duration+"s";
    }
}
