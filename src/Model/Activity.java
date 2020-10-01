package Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

public class Activity implements Serializable{
    private static final long serialVersionUID = -5L;
    private String activityName;                        //Name of an activity.
    private String activityNotes;                 //Notes of an activity.
    private Double weighting;                       //Value of an activity.
    private double initialValue; //Numerical value of requirement (e.g. 5) as set
    private double remainingValue; //Numerical value of requirement remaining
    private double completedValue;
    private ArrayList<Task> tasks = new ArrayList<>();
    private Task.TaskType taskType;
    private boolean isComplete;
    private int dayOfWeek;

    public Activity(){
        ArrayList<Task> tasks = new ArrayList<>();
        this.initialValue = 0;
        this.completedValue = 0;
        this.activityNotes = "";
    }

    public Activity(String NameActivity, double startValue, String NotesActivity){
        this.activityName = NameActivity;
        this.initialValue = startValue;
        this.activityNotes = NotesActivity;
        ArrayList<Task> tasks = new ArrayList<>();
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setInitialValue(double initialValue) { this.initialValue = initialValue; }

    public void setCompletedValue(double completedValue) {
        this.completedValue = completedValue;
    }

    public double getCompletedValue() {
        return completedValue;
    }

    public String getActivityNotes(){ return  activityNotes; }

    public String getActivityName() {
        return activityName;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }


    public double getInitialValue() {
        return initialValue;
    }

    public double getRemainingValue() {
        return remainingValue;
    }

    public Double getWeighting() {
        return weighting;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityNotes(String activityNotes) {
        this.activityNotes = activityNotes;
    }

    public void setWeighting(Double weighting) {
        this.weighting = weighting;
    }

    public void setTaskType(Task.TaskType taskType) {
        this.taskType = taskType;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void setRemainingValue(double remainingValue) {
        this.remainingValue = remainingValue;
    }

    public Task.TaskType getTaskType() {
        return taskType;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public double calculateActivityProgress(){
        double initialValue = this.initialValue;
        double completedValue = this.completedValue;
        double remainingValue = initialValue - completedValue;
        if(completedValue/initialValue == 0){
            return 0;
        }
        else {
            return (completedValue / initialValue);
        }
    }
}
