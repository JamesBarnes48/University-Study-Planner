package Model;

import java.util.Date;
import java.io.Serializable;

public class BasicStructure implements Serializable{
    private static final long serialVersionUID = -5L;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private boolean isDone; //Flag for if objective is complete

    public BasicStructure(){
        this.name = "";
        this.description = "";
        //this.startDate = new Date(); //Sets startDate to current date
        this.endDate = null;
        this.isDone = false;    //Objective is not met by default
    }

    public BasicStructure(String name){
        this.name = name;
    }

    public BasicStructure(String name, String description, Date startDate, Date endDate){
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate =  endDate;
        this.isDone = false;
    }

    public BasicStructure(String name, String description, Date endDate){
        this.name = name;
        this.description = description;
        this.endDate =  endDate;
        this.isDone = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getName() {return name;}
    public String getDescription() { return description;}
    public Date getEndDate() {
        return endDate;
    }
    public Date getStartDate() {
        return startDate;
    }
}
