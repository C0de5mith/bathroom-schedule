package com.github.c0de5mith.bathroom.io;

import com.github.c0de5mith.bathroom.BathroomEvent;
import com.github.c0de5mith.bathroom.OutputType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class InputOutput {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static interface ScannerChecker<T>{
        boolean check(T value);
        T next();
    }
    private static class InputValidator<T>{
        T getInput(String question, ScannerChecker<T> checker){
            while (true){
                System.out.println(question);
                T value = checker.next();
                if(checker.check(value)){
                    return value;
                }
                System.out.println("Invalid input");
            }
        }
    }
    public static List<BathroomEvent> getEvent(Scanner scanner){
        int type = new InputValidator<Integer>().getInput("Select a type\n1) 1\n2) 2\n3) 1 & 2", new ScannerChecker<Integer>() {
            @Override
            public boolean check(Integer value) {
                return 0 < value && value < 4;
            }

            @Override
            public Integer next() {
                try{
                    return Integer.parseInt(scanner.next());
                }catch (Exception e){
                    return -1;
                }
            }
        });
        Date date = new InputValidator<Date>().getInput("Please input a date (dd/MM/yyyy) [type n for today]", new ScannerChecker<Date>() {
            @Override
            public boolean check(Date value) {
                return value != null;
            }

            @Override
            public Date next() {
                try {
                    String s = scanner.next();
                    if(s.toLowerCase().equals("n")){
                        LocalDate d = LocalDate.now();
                        return Date.from(d.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    }
                    return dateFormat.parse(s);
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        int time = new InputValidator<Integer>().getInput("Please input 24-hour time (hour:minute) [type n for now]", new ScannerChecker<Integer>() {
            @Override
            public boolean check(Integer value) {
                System.out.println(value);
                return value >= 0;
            }

            @Override
            public Integer next() {
                try{
                    String s = scanner.next();
                    if(s.toLowerCase().equals("n")){
                        LocalDateTime t = LocalDateTime.now();
                        return t.getHour()*60+t.getMinute();
                    }
                    String[] split = s.split(":");
                    if(split.length != 2)
                        return -1;
                    int hour = Integer.parseInt(split[0]);
                    int minute = Integer.parseInt(split[1]);
                    if(hour < 0 || hour > 23 || minute < 0 || minute > 59)
                        return -1;
                    return 60*hour+minute;
                }catch (Exception e){
                    return -1;
                }
            }
        });
        int duration = new InputValidator<Integer>().getInput("Please input duration of output (seconds)", new ScannerChecker<Integer>() {
            @Override
            public boolean check(Integer value) {
                return value > 0;
            }

            @Override
            public Integer next() {
                try{
                    int dur = scanner.nextInt();
                    return dur;
                }catch (Exception e){
                    return -1;
                }
            }
        });
        List<BathroomEvent> events = new ArrayList<>();
        if(type == 1){
            events.add(new BathroomEvent(OutputType.ONE, date, time, duration));
        }
        if(type == 2){
            events.add(new BathroomEvent(OutputType.TWO, date, time, duration));
        }
        if (type == 3){
            events.add(new BathroomEvent(OutputType.ONE, date, time, duration));
            events.add(new BathroomEvent(OutputType.TWO, date, time, duration));
        }
        return events;
    }
    public static int getAction(Scanner scanner){
        int action = new InputValidator<Integer>().getInput("Select an action\n1) Input events\n2) Perform analysis\n3) Exit", new ScannerChecker<Integer>() {
            @Override
            public boolean check(Integer value) {
                return 0 < value && value < 4;
            }

            @Override
            public Integer next() {
                return scanner.nextInt();
            }
        });
        return action;
    }
}
