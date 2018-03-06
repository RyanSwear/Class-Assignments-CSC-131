//Author: Ryan Swearingen
//Class:  CSC 131 section 4
//Assignment: Task Manager Sprint 2 Feb 13
package tm;

import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
public class TM {

    TMModel tmModel = new TMModel();
    public static void main(String[] args) {
        
    TM tm = new TM();
    tm.run(args);
    }
    void run(String[] args)
    {
        if(args.length == 0)
        {
            System.out.println("Please enter execute program with the following format:  JAVA TM <cmd> <task> <data>");
            return;
        }
        else if ( args.length > 0)
        {
            String cmd = args[0].toUpperCase();
       
            //check the command entered and execute the appropriate method
            switch (cmd)
            {
            case "START":
                {
                String data = args[1];
                tmModel.startTask(data);
                break;
                }
            case "STOP":
                {
                String data = args[1];
                tmModel.stopTask(data);
                break;
                }
            case "SUMMARY":
                {
                String taskToSum = new String();// will be used to determine if user wants to summarize a particular task or not
                if (args.length == 1)
                {
                    this.summary();
                }
                else
                {
                    taskToSum = args[1];
                    this.summary(taskToSum);
                }
                break;
                }
            case "DESCRIBE":
                {
                    if (args.length == 4)
                    {
                        String data = args[1];
                        String description = args[2];
                        String size = args[3];
                        tmModel.describeTask(data,description);
                        tmModel.sizeTask(data, size);
                    }
                    else
                    {
                        String data = args[1];
                        String description = args[2];
                        tmModel.describeTask(data,description);
                    }
                break;
            }
            case "SIZE":
                {
                    String data = args[1];
                    String size = args[2];
                    tmModel.sizeTask(data,size);
                    break;
                }
            default:
            {
                System.out.println("Please enter a valid command: start, stop, describe, size, summary");
                break;
            }
            }
        }   
    
    }
 
  void summary ()
  {
      Set<String> x = tmModel.taskNames();
      String[] tasks = x.toArray(new String[x.size()]);
      //display each task and its characteristics
      for (int i = 0; i < tasks.length; i++)
      {
          System.out.println("Task Name: " + tasks[i] + " Total Time:" + tmModel.taskElapsedTime(tasks[i]));
          System.out.println("Description: " + tmModel.taskDescription(tasks[i]));
          System.out.println("Size: " + tmModel.taskSize(tasks[i]));
          System.out.println();
      }
      //display time statistics
      Set<String> y = tmModel.taskSizes();
      String[] sizes = y.toArray(new String[x.size()]);
      for (int i = 0; i < sizes.length; i++)
      {
          System.out.println("Size: " + sizes[i] + " Min Time: " + tmModel.minTimeForSize(sizes[i]) + " seconds  Max Time: " + tmModel.maxTimeForSize(sizes[i]) + " seconds");
          System.out.println("Average Time: " + tmModel.avgTimeForSize(sizes[i]) + " seconds " + "\n");
          Set<String> tasksWSize = tmModel.taskNamesForSize(sizes[i]);
          System.out.println("Tasks with this size: " + tasksWSize + "\n");
      }
      //display total time
      System.out.println("Total Time For All Tasks: " + tmModel.elapsedTimeForAllTasks() + " seconds");
  }
  void summary(String taskToSum)
  {
          System.out.println("Task Name: " + taskToSum + " Total Time:" + tmModel.taskElapsedTime(taskToSum));
          System.out.println("Description: " + tmModel.taskDescription(taskToSum));
          System.out.println("Size: " + tmModel.taskSize(taskToSum));
          System.out.println();
  }

}

