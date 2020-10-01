package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.io.Serializable;

public class Milestone implements Serializable{
    private static final long serialVersionUID = -5L;
    private Assignment belongingAssignment;
    private String milestoneName; //Model.Module name
    private String milestoneDescription;
    private Date endDate;
    private ArrayList<Task> tasks; //List of tasks that MUST be completed for this milestone to be achieved
    private boolean isMilestoneComplete;

    //Default constructor
    public Milestone(){
        this.tasks = new ArrayList();
    }

    public Milestone(String name, String description, Date endDate, ArrayList<Task> tasksArrayList){
        this.milestoneName = name;
        this.milestoneDescription = description;
        this.endDate = endDate;
        this.tasks = tasksArrayList;
    }
    public Milestone(String name, String description, Date endDate){
        this.milestoneName = name;
        this.milestoneDescription = description;
        this.endDate = endDate;
        this.tasks = new ArrayList();
    }

    public boolean checkIfComplete(){
        this.isMilestoneComplete = true;
        for(Task t : this.getTasks()){
            if(!t.isTaskComplete()){
                this.isMilestoneComplete = false;
            }
        }
        return isMilestoneComplete;
    }

    public void setBelongingAssignment(Assignment belongingAssignment) {
        this.belongingAssignment = belongingAssignment;
    }

    public Assignment getBelongingAssignment() {
        return belongingAssignment;
    }

    public boolean isMilestoneComplete() {
        return isMilestoneComplete;
    }

    public void setMilestoneComplete(boolean milestoneComplete) {
        isMilestoneComplete = milestoneComplete;
    }

    public void removeTask(Task task)
    {
        this.tasks.remove(task);
    }
    public void replaceTasks(Collection<Task> tasks)
    {
        this.tasks.clear();
        this.tasks.addAll(tasks);
    }

    public void setMilestoneDescription(String milestoneDescription) {
        this.milestoneDescription = milestoneDescription;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMilestoneDescription() {
        return this.milestoneDescription;
    }

    public void addTask(Task task)
    {
        this.tasks.add(task);
    }
    public boolean containsTask(Task task)
    {
        return this.tasks.contains(task);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setMilestoneName(String milestoneName) {
        this.milestoneName = milestoneName;
    }

    public String getMilestoneName() {
        return milestoneName;
    }

    public Date getEndDate() {
        return this.endDate;
    }


    public int calculateMilestoneProgress(){
        int count = 0;
        int total = tasks.size();
        for(Task task : tasks){
            System.out.println(task.getTaskName());
            System.out.println(task.calculateTaskProgress());
            if(task.isTaskComplete()){
                count = count + 1;
            }
        }
        return ((count/total));
    }

}
