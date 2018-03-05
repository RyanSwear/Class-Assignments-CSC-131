/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author RMswearingen
 */
public class Task {
    String name,size = "NA";
    LinkedList<String> description = new LinkedList();
    LinkedList<Long> starTime = new LinkedList();
    LinkedList<Long> endTime = new LinkedList();
    Long totalTime  = 0L ;
    Long sTime = 0L;
    Long eTime = 0L;
    //each task has a name, description, times to start, end, a size, and the ellapsed time
    String getTotalTime()
    {

        int i = 0;
        int j = endTime.size();
        while (i < j)
        {
            sTime = this.starTime.pop();
            eTime = this.endTime.pop();
            this.totalTime += eTime - sTime;
            i++;
        }
        return (String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalTime)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
    }
    void printDes()
    {
        int i = 0;
        int j = description.size();
        while (i < j)
        {
            String line = this.description.pop();
            System.out.println(line);
            i++;
        }
    }
}
