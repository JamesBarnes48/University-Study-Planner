package Model;

import Controller.MainController;

import java.io.IOException;
import java.util.HashSet;
import java.io.Serializable;

public class Module implements Serializable{
    private static final long serialVersionUID = -5L;
    private String name; //Model.Module name
    private String code; //Model.Module code
    private String moduleDescription;
    HashSet<Assignment> assignments; //Using hashset so no duplicate assignments

    //Default constructor
    public Module(){
        this.name = "";
        this.code = "";
        this.moduleDescription = "";
        this.assignments = new HashSet<>();
    }

    //Constructor with arguments
    public Module(String name, String code, String moduleDescription){
        this.name = name;
        this.code = code;
        this.moduleDescription = moduleDescription;
        this.assignments = new HashSet<>();
    }

    public String getName(){
        return name;
    }
    public String getCode(){
        return code;
    }

    public String getModuleDescription() {
        return moduleDescription;
    }

    public HashSet<Assignment> getAssignments(){
        return assignments;
    }
    public int getNoOfAssignments()
    {
        return this.assignments.size();
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCode(String code){
        this.code = code;
    }

    public void setModuleDescription(String moduleDescription) {
        this.moduleDescription = moduleDescription;
    }

    public void setAssignments(HashSet<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addAssignment(Assignment assignment){
        this.assignments.add(assignment);
    }

    @Override
    public String toString(){
        return this.name;
    }

    //Calculate percentage (0-100) of how much of the module is complete
/*    public int calculateModuleProgress()
    {
        if (this.assignments.size() == 0)
            return 0;

        int sum = 0;
        for (Assignment assignment : this.assignments)
            sum = sum + assignment.calculateAssignmentProgress();
        return sum / this.assignments.size();
    }*/

}
