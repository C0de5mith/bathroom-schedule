package com.github.c0de5mith.bathroom;

import com.github.c0de5mith.bathroom.analysis.AnalyzerSettings;
import com.github.c0de5mith.bathroom.analysis.ScheduleAnalyzer;
import com.github.c0de5mith.bathroom.io.FileController;
import com.github.c0de5mith.bathroom.io.InputOutput;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        FileController.init();
        System.out.println("Bathroom Scheduler 0.1");
        System.out.println("Hello, "+System.getProperty("user.name"));
        Scanner scanner = new Scanner(System.in);
        while (true){
            int action = InputOutput.getAction(scanner);
            if(action == 1){
                List<BathroomEvent> events = InputOutput.getEvent(scanner);
                for(BathroomEvent event : events){
                    System.out.println(event.commaSeparated());
                }
                FileController.putEvents(events);
            }else if(action == 2){
                ScheduleAnalyzer.analyze(FileController.getEvents(), new AnalyzerSettings());
            }else if(action == 3){
                break;
            }
        }
        System.out.println("Happy [select output type]ing");
    }
}
