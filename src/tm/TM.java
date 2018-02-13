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
                if (args.length == 4)
                {
                    String data = args[1];
                    String description = args[2];
                    String size = args[3];
                    this.describe(data,description,size);
                }
                else
                {
                   String data = args[1];
                   String description = args[2];
                   this.describe(data,description);
                }
            break;
            }
        case "SIZE":
            {
                String data = args[1];
                String size = args[2];
                this.addSize(data,size);
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
  void describe (String data, String desc, String size)
  {
      Log log = new Log();
      log.writeEntry("Describe " + data + " " + desc);
      log.writeEntry("Size " + data + " " + size);
  }
  void addSize(String data, String size)
  {
      Log log = new Log();
      log.writeEntry("Size " + data + " " + size);
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
 