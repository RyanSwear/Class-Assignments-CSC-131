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

/**
 *
 * @author RMswearingen
 */
public class Log {
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
 void clearFile()
    {
        try{
        FileWriter fw = new FileWriter("TM.txt", false); 
        PrintWriter pw = new PrintWriter(fw, false);
        pw.flush();
        pw.close();
        fw.close();
        }
        catch (IOException exception){System.out.println("Could not open file.");}
    }
}
