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
        // use log to write a line to a text file with the tasks start time
        return log.writeEntry("Start " + name + " " + LocalDateTime.now().toString());
    }
    
    public boolean stopTask(String name)
    {       
    // use log to write a line to a text file with the tasks end time
        return log.writeEntry("Stop " + name + " " + LocalDateTime.now().toString());
    }
    
    public boolean describeTask(String name, String description)
    {
        return log.writeEntry("Describe " + name + " " + description);
    // use log to write a line to a text file with a description of the task
    }
    
    public boolean sizeTask(String name, String size)
    {   
        return log.writeEntry("Size " + name + " " + size);
    }
    
    public boolean deleteTask(String name)
    {
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        log.clearFile();
        String line = new String();
        int i = 0;
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[1].compareTo(name) != 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[1].compareTo(oldName) == 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if ((tokens[0].compareTo("Start") == 0) && (tokens[1].compareTo(name) == 0))
            {
                starts.push(LocalDateTime.parse(tokens[2]));
            }
            else if ((tokens[0].compareTo("Stop") == 0) && (tokens[1].compareTo(name) == 0))
            {
                stops.push(LocalDateTime.parse(tokens[2]));
            } 
            i++;
        }
        
        i = 0;
        j = stops.size();
        while (i < j)
        {
            long calc = ChronoUnit.SECONDS.between(starts.pop(), stops.pop());
            totalTime += calc;
            i++;
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0 && tokens[1].compareTo(name) == 0)
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
        description = "";
        String line = new String();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Describe") == 0 && tokens[1].compareTo(name) == 0)
            {
                description = description.concat(tokens[2] + " ");
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0 && tokens[2].compareTo(size) == 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0 && tokens[2].compareTo(size) == 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0 && tokens[2].compareTo(size) == 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0 && tokens[2].compareTo(size) == 0)
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
        int j = lines.size();
        while (i < j)
        {
            line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Start") == 0)
            {
                starts.push(LocalDateTime.parse(tokens[2]));
            }
            else if (tokens[0].compareTo("Stop") == 0)
            {
                stops.push(LocalDateTime.parse(tokens[2]));
            } 
            i++;
        }
        
        i = 0;
        j = stops.size();
        while (i < j)
        {
            totalTime += ChronoUnit.SECONDS.between(starts.pop(), stops.pop());
        }
        return totalTime.toString();
    }
    
    public Set<String> taskNames()
    {
        Set<String> tasks = new HashSet<>();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        int j = lines.size();
        while (i < j)
        {
            String line = lines.pop();
            String[] tokens = line.split(" ", 3);
            tasks.add(tokens[1]);
            i++;
        }
        return tasks;
    }
    
    public Set<String> taskSizes()
    {
        Set<String> tasks = new HashSet<>();
        LinkedList<String> lines = new LinkedList<>();
        log.read(lines);
        int i = 0;
        int j = lines.size();
        while (i < j)
        {
            String line = lines.pop();
            String[] tokens = line.split(" ", 3);
            if (tokens[0].compareTo("Size") == 0)
            {
                tasks.add(tokens[2]);
            }
            i++;
        }
        return tasks;
    }
    
}

