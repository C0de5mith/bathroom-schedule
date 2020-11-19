package com.github.c0de5mith.bathroom.io;

import com.github.c0de5mith.bathroom.BathroomEvent;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileController {
    private static File file;

    public static void init() throws IOException {
        file = new File("bathroom.csv");
        if(!file.exists()){
            System.out.println("Creating new file");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write("type,date,time,duration\n");
            writer.flush();
            writer.close();
        }
    }
    public static List<BathroomEvent> getEvents() throws IOException {
        System.out.println("Reading events from file");
        List<BathroomEvent> events = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        reader.readLine();
        String line = reader.readLine();
        while (line != null){
            String[] values = line.replaceAll(" ", "").split(",");
            if(values.length == 4){
                try{
                    BathroomEvent event = new BathroomEvent(values);
                    events.add(event);
                }catch (Exception e){
                    System.out.println("Uh oh, corrupted line.");
                }
            }
            line = reader.readLine();
        }
        return events;
    }
    public static void putEvents(List<BathroomEvent> events) throws IOException {
        System.out.println("Writing "+events.size()+" event"+(events.size()>1?"s":""));
        FileWriter writer = new FileWriter(file,true);
        for(BathroomEvent event:events){
            writer.append(event.commaSeparated()+"\n");
        }
        writer.flush();
        writer.close();
    }
}
