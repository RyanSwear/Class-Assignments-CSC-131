/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tm;

import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author RMswearingen
 */
public class TMModel implements ITMModel{
    
    Log log = new Log();
    public boolean startTask(String name)
    {   
        Date date = new Date();
        log.writeEntry("Start " + name + " " + date.getTime());
        // use log to write a line to a text file with the tasks start time
        return true;
    }
    
    public boolean stopTask(String name)
    {   
        Date date = new Date();
        log.writeEntry("Stop " + name + " " + date.getTime());
    // use log to write a line to a text file with the tasks end time
        return true;
    }
    
    public boolean describeTask(String name, String description)
    {
        log.writeEntry("Describe " + name + " " + description);
        return true;
    // use log to write a line to a text file with a description of the task
    }
    
    public boolean sizeTask(String name, String size)
    {   
        log.writeEntry("Size " + name + " " + size);
        return true;
    }
    
    public boolean deleteTask(String name)
    {
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        log.clearFile();
        String line = new String();
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[1] != name)
            {
                log.writeEntry(line);
            }
            i++;
        }
        return true;
    }
    
    public boolean renameTask(String oldName, String newName)
    {
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        log.clearFile();
        String line = new String();
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[1] == oldName)
            {
                tokens[1] = newName;
            }
            log.writeEntry(tokens[0] + " " + tokens[1] + " " + tokens[2]);
            i++;
        }
        return true;
    }
    
    public String taskElapsedTime(String name)
    {
       // String time = new String();
        LinkedList<String> lines = new LinkedList<>();
        LinkedList<Long> starts = new LinkedList<>();
        LinkedList<Long> stops = new LinkedList<>();
        Long totalTime = 0L;
        log.read(lines);
        
        String line = new String();
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if ((tokens[0] == "Start") && (tokens[1] == name))
            {
                starts.push(Long.parseLong(tokens[2]));
            }
            else if ((tokens[0] == "Stop") && (tokens[1] == name))
            {
                stops.push(Long.parseLong(tokens[2]));
            } 
            i++;
        }
        
        i = 0;
        while (i < stops.size())
        {
            totalTime += stops.pop() - starts.pop();
        }
        
        return (String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalTime)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
    }
    
    public String taskSize(String name)
    {
        String size =  new String();
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Size" && tokens[1] == name)
            {
                size = tokens[2];
            }
            i++;
        }
        return size;
    }
    
    public String taskDescription(String name)
    {
        String description = new String();
        return description;
    }
    
    public String minTimeForSize(String size)
    {
        String time = new String();
        return time;
    }
    
    public String maxTimeForSize(String size)
    {
        String time = new String();
        return time;
    }
    
    public String avgTimeForSize(String size)
    {
        String time = new String();
        return time;
    }
    
    public Set<String> taskNamesForSize(String size)
    {
        Set<String> tasks = new HashSet<>();
        return tasks;
    }
    
    public String elapsedTimeForAllTasks()
    {
        String time = new String();
        return time;
    }
    
    public Set<String> taskNames()
    {
        Set<String> tasks = new HashSet<>();
        return tasks;
    }
    
    public Set<String> taskSizes()
    {
        Set<String> tasks = new HashSet<>();
        return tasks;
    }
    
}

