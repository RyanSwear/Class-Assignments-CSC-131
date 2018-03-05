/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tm;

import java.util.Date;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author RMswearingen
 */
public class TMModel implements ITMModel{
    
    Log log = new Log();
    public boolean startTask(String name)
    {   
        //Date date = new Date();
        log.writeEntry("Start " + name + " " + LocalDateTime.now().toString());
        // use log to write a line to a text file with the tasks start time
        return true;
    }
    
    public boolean stopTask(String name)
    {   
        //Date date = new Date();
        log.writeEntry("Stop " + name + " " + LocalDateTime.now().toString());
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
       // LinkedList<Long> starts = new LinkedList<>();
       // LinkedList<Long> stops = new LinkedList<>();
        LinkedList<LocalDateTime> starts = new LinkedList<>();
        LinkedList<LocalDateTime> stops = new LinkedList<>();
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
                starts.push(LocalDateTime.parse(tokens[2]));
            }
            else if ((tokens[0] == "Stop") && (tokens[1] == name))
            {
                stops.push(LocalDateTime.parse(tokens[2]));
            } 
            i++;
        }
        
        i = 0;
        while (i < stops.size())
        {
            totalTime += ChronoUnit.SECONDS.between(starts.pop(), stops.pop());
        }
        
        return (totalTime.toString());
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
            line = lines.pop();
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
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Describe" && tokens[1] == name)
            {
                description.concat(tokens[2] + " ");
            }
            i++;
        }
        return description;
    }
    
    public String minTimeForSize(String size)
    {
        
        String time1 = new String();
        String time2 = new String();
        time1 = "";
        Long min = 0l;
        Long seconds = 0l;
       // String description = new String();
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Size" && tokens[2] == size)
            {
                time2 = taskElapsedTime(tokens[1]);//string in seconds in Long form
                seconds = Long.parseLong(time2);
                if (min == 0l)
                {
                    min = seconds;
                }
                else if (min > seconds)
                {
                    min = seconds;
                }
            }
            i++;
        }
        time1 = seconds.toString();
        return time1;
    }
    
    public String maxTimeForSize(String size)
    {
        String time1 = new String();
        String time2 = new String();
        time1 = "";
        Long max = 0l;
        Long seconds = 0l;
       
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Size" && tokens[2] == size)
            {
                time2 = taskElapsedTime(tokens[1]);//string in seconds in Long form
                seconds = Long.parseLong(time2);
                if (max == 0l)
                {
                    max = seconds;
                }
                else if (max < seconds)
                {
                    max = seconds;
                }
            }
            i++;
        }
        time1 = seconds.toString();
        return time1;
    }
    
    public String avgTimeForSize(String size)
    {
        String time = new String();
        Long ave = 0l;
        Long seconds = 0l;
        Long counter = 0l;
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Size" && tokens[2] == size)
            {
                time = taskElapsedTime(tokens[1]);//string in seconds in Long form
                seconds = Long.parseLong(time);
                ave += seconds;
            }
            i++;
        }
        ave = ave/counter;
        time = ave.toString();
        return time;
    }
    
    public Set<String> taskNamesForSize(String size)
    {
        Set<String> tasks = new HashSet<>();
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Size" && tokens[2] == size)
            {
                tasks.add(tokens[1]);
            }
            i++;
        }
        return tasks;
    }
    
    public String elapsedTimeForAllTasks()
    {
        
        LinkedList<String> lines = new LinkedList<>();
       
        LinkedList<LocalDateTime> starts = new LinkedList<>();
        LinkedList<LocalDateTime> stops = new LinkedList<>();
        Long totalTime = 0L;
        log.read(lines);
        
        String line = new String();
        int i = 0;
        while (i < lines.size())
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0] == "Start")
            {
                starts.push(LocalDateTime.parse(tokens[2]));
            }
            else if (tokens[0] == "Stop")
            {
                stops.push(LocalDateTime.parse(tokens[2]));
            } 
            i++;
        }
        
        i = 0;
        while (i < stops.size())
        {
            totalTime += ChronoUnit.SECONDS.between(starts.pop(), stops.pop());
        }
        return totalTime.toString();
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

