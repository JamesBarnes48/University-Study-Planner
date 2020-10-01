package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Task  implements Serializable {
    private static final long serialVersionUID = -5L;
    private String taskName;
    private ArrayList<Activity> requirements;
    private ArrayList<Activity> activities;
    private ArrayList<Task> dependencies;
    private String taskNotes;
    private boolean isTaskComplete;
    private TaskType taskType;


    public enum TaskType {
        STUDYING,
        PROGRAMMING,
        WRITING,
        SURVEYING,
        READING,
        WATCHING,
        CREATING,
        RESEARCHING
    }

    public Task(){

    }

    public Task(String name, TaskType type){
        taskName = name;
        activities = new ArrayList<>();
        requirements = new ArrayList<>();
        dependencies = new ArrayList<>();
        isTaskComplete = false;
        taskType = type;
    }


    public ArrayList<Activity> getRequirements(){
        return requirements;
    }
    public ArrayList<Activity> getRequirementActivities(Requirement req) { return req.getActivities(); }

    public ArrayList<Activity> getActivities() { return activities; }

    public String getTaskName() {
        return taskName;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }
    public void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setTaskComplete(boolean taskComplete) {
        isTaskComplete = taskComplete;
    }

    public void setRequirements(ArrayList<Activity> requirements) {
        this.requirements = requirements;
    }

    public boolean isTaskComplete() {
        return isTaskComplete;
    }

    public Activity[] getRequirementsArray()
    {
        return this.requirements.toArray(new Activity[this.requirements.size()]);
    }

    public int requirementCount()
    {
        return requirements.size();
    }

    public boolean hasDependencies()
    {
        return dependencies.size() > 0;
    }

    public int requirementsComplete(){
        int completed = 0;
        for(Activity activity : requirements){
            if(activity.isComplete() == true){
                completed = completed + 1;
            }
        }
        return completed;
    }
    public int calculateTaskProgress(){
        if (this.requirements.size() == 0)
            return 0;
        else
            return (this.requirementsComplete() * 100) / this.requirements.size();
    }
}
