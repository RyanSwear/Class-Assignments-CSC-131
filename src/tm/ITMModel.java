/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tm;
import java.util.Set;
/**
 *
 * @author RMswearingen
 */
public interface ITMModel {
     // set information in our model
    //
    boolean startTask(String name);
    boolean stopTask(String name);
    boolean describeTask(String name, String description);
    boolean sizeTask(String name, String size);
    boolean deleteTask(String name);
    boolean renameTask(String oldName, String newName);

    // return information about our tasks
    //
    String taskElapsedTime(String name);
    String taskSize(String name);
    String taskDescription(String name);

    // return information about some tasks
    //
    String minTimeForSize(String size);
    String maxTimeForSize(String size);
    String avgTimeForSize(String size);

    Set<String> taskNamesForSize(String size);

    // return information about all tasks
    //
    String elapsedTimeForAllTasks();
    Set<String> taskNames();
    Set<String> taskSizes();
}
