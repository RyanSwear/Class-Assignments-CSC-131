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
        LinkedList<String> fileList = new LinkedList();//will load the entire file line by line to a string, the string will then be split into tokens for further disection
        LinkedList<Task> taskList = new LinkedList();//used to store each task with its appropriate information
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
                taskToSum = " ";
             }
             else{taskToSum = args[1];}
             
            this.summary(fileList, taskList, taskToSum);
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
 
  void summary(LinkedList<String> fList, LinkedList<Task> tList, String taskToSum)
  {
    Log log = new Log();
    String line = new String();
    int i = 0;
    log.read(fList);// start reading at the first line of the text file
    while (i < fList.size())
    {
        line = fList.get(i);// analyze each line
        String[] tokens = line.split(" ",3);// splits into cmd task data
        
        //add each task to task list
        if (tList.isEmpty())
        {
            Task task = new Task();
            task.name = tokens[1];
            switch (tokens[0])
            {//check the command that was entered on each line
                //enter data into the task depending on the command
                case "Start":
                        Long ts = Long.parseLong(tokens[2]);
                        task.starTime.addFirst(ts);
                        break;
                case "Stop":
                        task.endTime.addFirst(Long.parseLong(tokens[2]));
                        break;
                case "Describe": 
                        task.description.addFirst(tokens[2]);
                        break;
                case "Size":
                        task.size = tokens[2];
                        break;
                default:
                    break;
            }
                
            tList.add(task);
        }
        else
        {
            //check if task has already been inserted, check for its name
            Task task = new Task();
            int j = 0;
            boolean taskFound = false;
            while ( (j < tList.size()) && (taskFound == false))
            {
                task = tList.pollLast();
                if (task.name.equals(tokens[1]))
                {
                    switch (tokens[0])
                    {//check the command that was entered on each line
                //enter data into the task depending on the command
                        case "Start":
                            task.starTime.addLast(Long.parseLong(tokens[2]));
                            break;
                        case "Stop":
                            task.endTime.addLast(Long.parseLong(tokens[2]));
                            break;
                        case "Describe": 
                            task.description.addLast(tokens[2]);
                            break;
                        case "Size":
                            task.size = tokens[2];
                            break;
                        default:
                            break;
                    }
                    tList.push(task);
                    taskFound = true;
                }
                else
                    tList.push(task);
                j++;
            }
            if (taskFound == false)//task was not found in the list, add a new entry
            {
                 Task task2 = new Task();
                 task2.name = tokens[1];
                 switch (tokens[0])
                {
                    case "Start":
                        task2.starTime.addFirst(Long.parseLong(tokens[2]));
                        break;
                    case "Stop":
                        task2.endTime.addFirst(Long.parseLong(tokens[2]));
                        break;
                    case "Describe": 
                        task2.description.addFirst(tokens[2]);
                        break;
                    case "Size":
                        task2.size = tokens[2];
                        break;
                    default:
                        break;
                }
                
                tList.push(task2);
            }
        }
        i++;
    }
    
    //check if all tasks or just a certain task should be displayed
    if (taskToSum == " ")
    {//if no task to check was entered, display all
        int k = 0;
        int h = tList.size();
        while (k < h)
        {
            Task display = new Task();
            display = tList.pop();
            System.out.println(display.name + ": Total Time HH MM SS:  " + display.getTotalTime() + " Size: " + display.size);
            display.printDes();
            System.out.println();
            k++;
        }
    }
    else
    {
        int k = -1;
        boolean taskFound = false;
        while (k < tList.size())
        {
            Task display = new Task();
            display = tList.pop();
            if (display.name.equals(taskToSum))
            {
                //check if the current task is the one the user wants
                // if so display its summary
                taskFound = true;
                System.out.println(display.name + ": Total Time HH MM SS:  " + display.getTotalTime() + " Size: " + display.size);
                display.printDes();
                System.out.println("\n");
            }
            k++;
        }
        if (!taskFound)
        {
            //if the task the user wants is not in the text file
            //tell the user it is not in the file
            System.out.println("The wanted task cannot be found.");
        }
    }
    
  }
  
}



