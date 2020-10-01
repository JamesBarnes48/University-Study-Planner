package Model;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Date;

public class Requirement implements Serializable{
    private static final long serialVersionUID = -5L;
    private Assignment containingAssignment;
    private String requirementName;
    private String requirementNotes;
    private Double value;
    private Date endDate;
    private ArrayList<Activity> activities;
    private boolean isRequirementMet;

    public Requirement(){
        this.activities = new ArrayList();
    }

    public Requirement(String name, double value, String valueUnit){
        this.requirementName=name;
        ArrayList<Activity> activities = new ArrayList<>();
        this.isRequirementMet = false;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Assignment getContainingAssignment() {
        return containingAssignment;
    }

    public void setContainingAssignment(Assignment containingAssignment) {
        this.containingAssignment = containingAssignment;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }

    public Double getValue() {
        return value;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public String getRequirementNotes() {
        return requirementNotes;
    }

    public void setRequirementMet(boolean requirementMet) {
        isRequirementMet = requirementMet;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public void setRequirementNotes(String requirementNotes) {
        this.requirementNotes = requirementNotes;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public ArrayList<Activity> getActivities() { return activities; }
    public boolean isRequirementMet() {
        return isRequirementMet;
    }
    public void setIsRequirementMet(boolean met) {
        isRequirementMet = met;
    }

    public boolean checkIsRequirementMet(){
        isRequirementMet = true;
        for (Activity activity : this.activities){
            if(activity.isComplete() == false){
                isRequirementMet = false;
            }
        }
        return isRequirementMet;
    }

/*    public double calculateRequirementProgress(){
        return (((this.initialValue - this.remainingValue) / this.initialValue) * 100);
    }*/
}
