//Author: Ryan Swearingen
//Class:  CSC 131 section 4
//Assignment: Task Manager Week 2
package tm;

import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
public class TM {

    
    public static void main(String[] args) {
        
    TM tm = new TM();
    tm.appMain(args);
    }
    void appMain(String[] args)
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
            this.start(data);
            break;
            }
        case "STOP":
            {
            String data = args[1];
            this.stop(data);
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
            String data = args[1];
            String description = args[2];
            this.describe(data,description);
            break;
            }
        default:
        {
            System.out.println("Please enter a valid command: start, stop, describe, summary");
            break;
        }
    }
    }
    
  }
  void start(String data)
  {
    Log log = new Log();
    Date date = new Date();
    log.writeEntry("Start " + data + " " + date.getTime());
    // use log to write a line to a text file with the tasks start time
  }
  void stop(String data)
  {
    Log log = new Log();
    Date date = new Date();
    log.writeEntry("Stop " + data + " " + date.getTime());
    // use log to write a line to a text file with the tasks end time
  }
  void describe(String data, String desc)
  {
    Log log = new Log();
    log.writeEntry("Describe " + data + " " + desc);
    // use log to write a line to a text file with a description of the task
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
                        task.starTime = Long.parseLong(tokens[2]);
                case "Stop":
                        task.endTime = Long.parseLong(tokens[2]);
                case "Describe": 
                        task.description = tokens[2];
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
                            task.starTime = Long.parseLong(tokens[2]);
                        case "Stop":
                            task.endTime = Long.parseLong(tokens[2]);
                        case "Describe": 
                            task.description = tokens[2];
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
                        task2.starTime = Long.parseLong(tokens[2]);
                    case "Stop":
                        task2.endTime = Long.parseLong(tokens[2]);
                    case "Describe": 
                        task2.description = tokens[2];
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
        int k = -1;
        while (k < tList.size())
        {
            Task display = new Task();
            display = tList.pop();
            System.out.println(display.name + ": Total Time HH MM SS:" + display.getTotalTime() + " " + display.description);
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
                System.out.println(display.name + ": Total Time HH MM SS:" + display.getTotalTime() + " " + display.description);
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
class Log{
  
  void writeEntry(String line)
  {
    try{//establish a stream to the desired file and write the data to it on a single line
      PrintWriter writer = new PrintWriter(new FileWriter("TM.txt",true)); 
      writer.println(line);
      writer.close();
    }
    catch (IOException exception){//send report to user if the program cant find the file
      System.out.println("Could not open file.");
    }
  }
 void read(LinkedList<String> list)
  {//read the entire file into a Linked List of strings ine by line
      String string = new String();
    try{
       FileReader file = new FileReader("TM.txt");
       BufferedReader reader = new BufferedReader(file);
       string = reader.readLine();
       while (string != null)
       {
        list.add(string);
        string = reader.readLine();
       }
    }
    catch (IOException exception){
      System.out.println("Could not open file.");
    }
  }
}

class Task{
    String name, description;
    long starTime,endTime,totalTime;
    //each task has a name, description, times to start, end, and the ellapsed time
    String getTotalTime()
    {
        if (starTime == 0 || endTime == 0)
        {
            return " 00:00:00";
        }
        else{// create a time stamp in hours::minutes::seconds as a string
            totalTime = endTime - starTime;
            return (String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(totalTime),
                    TimeUnit.MILLISECONDS.toMinutes(totalTime)-TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(totalTime)),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(totalTime))));
        }
    }
}
 