package Controller;

import Model.*;
import Model.Module;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class AssignmentController {

    private Stage previousStage;

    @FXML private Label assignmentLabel;
    @FXML private Text assignmentDescription;
    @FXML private Button editMilestone;
    @FXML private Button setRequirementsButton;
    @FXML private Button viewGanttButton;
    @FXML private Button buttonReading;
    @FXML private Button buttonWriting;
    @FXML private Button buttonProgramming;
    @FXML private Button buttonWatching;
    @FXML private Button buttonSurveying;
    @FXML private Button buttonCreating;
    @FXML private Button buttonResearching;
    @FXML private ProgressBar milestoneProgressBar;

    @FXML private Button goToPreviousScene;
    @FXML private Button helpButton;

    private Profile profile;
    private Module selectedModule;
    private static Assignment selectedAssignment;
    UIManager ui = new UIManager();

    Button[] arrayButtons = new Button[7];

    public void setPreviousStage(Stage stage){ this.previousStage = stage; }

    public void initialize(){
        assignmentLabel.setText(selectedAssignment.getName());
        assignmentDescription.setText(selectedAssignment.getDescription());

        //NEW
        viewGanttButton.setOnAction(value ->  {
            try {
                ui.loadGanttChart(selectedAssignment, profile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        setRequirementsButton.setOnAction(value ->  {
            try {
                ui.loadRequirement(profile, selectedAssignment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        editMilestone.setOnAction(value ->  {
            try {
                ui.loadMilestone(profile, selectedAssignment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        helpButton.setOnAction(value ->  {
            try {
                ui.loadHelp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

/*        goToPreviousScene.setOnAction(value ->  {
            try {
                ui.loadModule(profile, selectedModule);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });*/

        arrayButtons[0] = buttonReading;
        arrayButtons[1] = buttonWriting;
        arrayButtons[2] = buttonProgramming;
        arrayButtons[3] = buttonWatching;
        arrayButtons[4] = buttonSurveying;
        arrayButtons[5] = buttonCreating;
        arrayButtons[6] = buttonResearching;

        buttonReading.setDisable(true);
        buttonWriting.setDisable(true);
        buttonProgramming.setDisable(true);
        buttonWatching.setDisable(true);
        buttonSurveying.setDisable(true);
        buttonCreating.setDisable(true);
        buttonResearching.setDisable(true);

        for(Task t : selectedAssignment.getTasks()){
            for(Button btn : arrayButtons){
                String buttonText = btn.getText();
                if(t.getTaskType().toString().equalsIgnoreCase(buttonText)){
                    btn.setDisable(false);
                }
            }
        }

        if(selectedAssignment.getMilestone() != null) {
            milestoneProgressBar.setProgress(selectedAssignment.getMilestone().calculateMilestoneProgress());
        }
        //milestoneProgressBar.setProgress(0.5);

    }

    //creates gantt chart
    public void initGanttChart(Stage stage, Assignment assignment, Stage previousStage) {
        stage.setTitle("Gantt Chart");

        String[] rows = new String[] {"Studying", "Programming", "Reading", "Writing", "Surveying", "Watching", "Creating", "Researching"};

        //initialise axes
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        //initialise gantt chart
        final GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis, yAxis);

        // set options for axes
        xAxis.setLabel("Time Spent on Assignment (Hours)");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setMinorTickCount(4);
        yAxis.setLabel("Task");
        yAxis.setTickLabelFill(Color.BLACK);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(rows)));

        //set options for chart
        chart.setTitle("Task Gantt Chart");
        chart.setLegendVisible(false);
        chart.setBlockHeight(30);
        String curRow;

        //populate chart
        //get list of tasks for assignment
        ArrayList<Task> assignmentTasks = assignment.getTasks();
        for(int i = 0; i < assignmentTasks.size(); i++)
        {
            System.out.println(assignmentTasks.get(i).getTaskName());
        }
        //initialise series on chart
        XYChart.Series series1 = new XYChart.Series();
        XYChart.Series series2 = new XYChart.Series();
        XYChart.Series series3 = new XYChart.Series();
        XYChart.Series series4 = new XYChart.Series();
        XYChart.Series series5 = new XYChart.Series();
        XYChart.Series series6 = new XYChart.Series();
        XYChart.Series series7 = new XYChart.Series();
        XYChart.Series series8 = new XYChart.Series();

        //iterate through each task in assignment and add to chart if present
        for(int i = 0; i < assignmentTasks.size(); i++)
        {
            Task aTask = assignmentTasks.get(i);

            //calculating total length of all activities for aTask
            int taskLength = 0;

            for(int j = 0; j < aTask.getActivities().size(); j++)
            {
                Activity curActivity = aTask.getActivities().get(j);
                double initVal = curActivity.getInitialValue();
                taskLength = taskLength + (int)initVal;
            }

            //--SERIES 1 - STUDYING--//
            curRow = rows[0];
            if(aTask.getTaskType() == Task.TaskType.STUDYING)
            {
                series1.getData().add(new XYChart.Data(0, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 2 - PROGRAMMING--//
            curRow = rows[1];
            if(aTask.getTaskType() == Task.TaskType.PROGRAMMING)
            {
                series2.getData().add(new XYChart.Data(3, curRow, new GanttChart.ExtraData(taskLength, "status-green")));

            }

            //--SERIES 3 - READING--//
            curRow = rows[2];
            if(aTask.getTaskType() == Task.TaskType.READING)
            {
                series3.getData().add(new XYChart.Data(0.5, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 4 - WRITING--//
            curRow = rows[3];
            if(aTask.getTaskType() == Task.TaskType.WRITING)
            {
                series4.getData().add(new XYChart.Data(2, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 5 - SURVEYING--//
            curRow = rows[4];
            if(aTask.getTaskType() == Task.TaskType.SURVEYING)
            {
                series5.getData().add(new XYChart.Data(6, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 6 - WATCHING--//
            curRow = rows[5];
            if(aTask.getTaskType() == Task.TaskType.WATCHING)
            {
                series6.getData().add(new XYChart.Data(1, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 7 - CREATING--//
            curRow = rows[6];
            if(aTask.getTaskType() == Task.TaskType.CREATING)
            {
                series7.getData().add(new XYChart.Data(1.5, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }

            //--SERIES 8 - RESEARCHING--//
            curRow = rows[7];
            if(aTask.getTaskType() == Task.TaskType.RESEARCHING)
            {
                series8.getData().add(new XYChart.Data(0, curRow, new GanttChart.ExtraData(taskLength, "status-green")));
            }
        }


        //adds everything to the chart and applies stylesheet
        chart.getData().addAll(series1, series2, series3, series4, series5, series6, series7, series8);
        chart.getStylesheets().add(getClass().getResource("../Content/ganttchart.css").toExternalForm());

        //displays chart to screen
        Scene scene = new Scene(chart, 620, 350);
        stage.setScene(scene);
        try
        {
            stage.getScene().getRoot().setStyle(previousStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    public void onBackClick(ActionEvent event) throws Exception {
        ui.loadModule(profile, selectedModule, event);
    }

    public AssignmentController(){}
    public AssignmentController(Task t){ enableButton(t); }

    public Module getSelectedModule() {
        return selectedModule;
    }

    public Assignment getSelectedAssignment() {
        return selectedAssignment;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setUi(UIManager ui) {
        this.ui = ui;
    }

    public void setProfile(Profile currentProfile){
        this.profile = currentProfile;
    }

    public void enableButton(Task t){
        for(Button btn : arrayButtons){
            if(t.getTaskType().name().equalsIgnoreCase(btn.getText())){
                btn.setDisable(false);
            }
        }
    }

    public void onTaskClick(ActionEvent event) throws IOException {
        if(selectedAssignment.getMilestone()!=null){
            milestoneProgressBar.setProgress(selectedAssignment.getMilestone().calculateMilestoneProgress());
        }
        UIManager ui = new UIManager();
        Button button = (Button) event.getSource();
        String buttonText = button.getText();
        switch (buttonText) {
            case "Reading":
                System.out.println("Reading");
                ui.loadTask(selectedAssignment.getTasks().get(0), profile, selectedAssignment, event);
                break;
            case "Writing":
                System.out.println("Writing");
                ui.loadTask(selectedAssignment.getTasks().get(1), profile, selectedAssignment, event);
                break;
            case "Programming":
                System.out.println("Programming");
                ui.loadTask(selectedAssignment.getTasks().get(2), profile, selectedAssignment, event);
                break;
            case "Watching":
                System.out.println("Watching");
                ui.loadTask(selectedAssignment.getTasks().get(3), profile, selectedAssignment, event);
                break;
            case "Surveying":
                System.out.println("Surveying");
                ui.loadTask(selectedAssignment.getTasks().get(4), profile, selectedAssignment,event);
                break;
            case "Creating":
                System.out.println("Creating");
                ui.loadTask(selectedAssignment.getTasks().get(5), profile, selectedAssignment,event);
                break;
            case "Researching":
                System.out.println("Researching");
                ui.loadTask(selectedAssignment.getTasks().get(6), profile, selectedAssignment,event);
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }

    }

    public void setSelectedAssignment(Assignment assignment){
        this.selectedAssignment = assignment;
    }

}
