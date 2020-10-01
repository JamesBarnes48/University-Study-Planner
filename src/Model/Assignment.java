package Model;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Assignment extends BasicStructure implements Serializable{
    private static final long serialVersionUID = -5L;
    private String assignmentTitle;
    private ArrayList<Task> tasks;               //List of tasks
    private ArrayList<Activity> activities;
    private Requirement requirements;
    private Milestone milestone;
    private Double weightingPercentage;          //What percentage is the assignment worth
    private AssignmentType type;                           //Coursework or Exam
    private Module module;

    public enum AssignmentType {
        COURSEWORK,
        EXAM
    }

    public Assignment(String name, String description, Date startDate, Date endDate, Double weighting,
                      AssignmentType assignmentType, Module module){
        super(name, description, startDate, endDate);
        this.weightingPercentage = weighting;
        //this.tasks = new ArrayList<>();
        Task readingTask = new Task("Reading", Task.TaskType.READING);
        Task writingTask = new Task("Writing", Task.TaskType.WRITING);
        Task programmingTask = new Task("Programming", Task.TaskType.PROGRAMMING);
        Task watchingTask = new Task("Watching", Task.TaskType.WATCHING);
        Task surveyingTask = new Task("Surveying", Task.TaskType.SURVEYING);
        Task creatingTask = new Task("Creating", Task.TaskType.CREATING);
        Task researchingTask = new Task("Researching", Task.TaskType.RESEARCHING);
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        taskArrayList.add(readingTask);
        taskArrayList.add(writingTask);
        taskArrayList.add(programmingTask);
        taskArrayList.add(watchingTask);
        taskArrayList.add(surveyingTask);
        taskArrayList.add(creatingTask);
        taskArrayList.add(researchingTask);
        this.tasks = taskArrayList;
        //this.milestone = new Milestone();
        //this.requirements = new ArrayList<>();
        this.activities = new ArrayList<>();
        this.type = assignmentType;
        this.module = module;
    }
    public Assignment(String name, String description, Date startDate, Date endDate, Double weighting,
                      AssignmentType assignmentType){
        super(name, description, startDate, endDate);
        this.weightingPercentage = weighting;
        Task readingTask = new Task("Reading", Task.TaskType.READING);
        Task writingTask = new Task("Writing", Task.TaskType.WRITING);
        Task programmingTask = new Task("Programming", Task.TaskType.PROGRAMMING);
        Task watchingTask = new Task("Watching", Task.TaskType.WATCHING);
        Task surveyingTask = new Task("Surveying", Task.TaskType.SURVEYING);
        Task creatingTask = new Task("Creating", Task.TaskType.CREATING);
        Task researchingTask = new Task("Researching", Task.TaskType.RESEARCHING);
        ArrayList<Task> taskArrayList = new ArrayList<Task>();
        taskArrayList.add(readingTask);
        taskArrayList.add(writingTask);
        taskArrayList.add(programmingTask);
        taskArrayList.add(watchingTask);
        taskArrayList.add(surveyingTask);
        taskArrayList.add(creatingTask);
        taskArrayList.add(researchingTask);
        this.tasks = taskArrayList;
        //this.milestone = new Milestone();
        this.activities = new ArrayList<>();
        //this.requirements = new ArrayList<>();
        this.type = assignmentType;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }

    public Double getWeightingPercentage() {
        return weightingPercentage;
    }
    public AssignmentType getType(){
        return type;
    }
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public Milestone getMilestone() {
        return milestone;
    }
    public Requirement getRequirements() {
        return requirements;
    }

    public void addTask(Task task){
        tasks.add(task);
    }
    public void addMilestone(Task milestoneTask){
        milestone.addTask(milestoneTask);
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public void setRequirements(Requirement requirements) {
        this.requirements = requirements;
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    public void setMilestoneArray(ArrayList<Task> milestones) {
        this.milestone.replaceTasks(milestones);
    }
    public void setWeighting(Double weighting) {
        this.weightingPercentage = weighting;
    }

    public int calculateAssignmentProgress(){
        int sum = 0;
        int total = 0;
        for (Task task : this.tasks)
        {
            if (task.getRequirements().size() > 0)
            {
                sum += task.calculateTaskProgress();
                total++;
            }
        }
        if(sum == 0 || total==0){
            return 0;
        }
        return sum / total;
    }
}
