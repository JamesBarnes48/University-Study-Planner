package Controller;

import Model.*;
import Model.Module;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MilestoneController {
    private static Milestone currentMilestone;
    private static Profile profile;
    private static Module selectedModule;
    private static Assignment selectedAssignment;
    private UIManager ui = new UIManager();
    private ObservableList<Task> taskObservableList;

    private static ProfileController pc = new ProfileController();

    @FXML private TextField milestoneName;
    @FXML private DatePicker milestoneDeadline;
    @FXML private TextArea milestoneNotes;
    @FXML private ListView<Task> tasksListView;
    @FXML private ListView<Task> addedTasksListView;
    @FXML private Button addTaskButton;
    @FXML private Button removeTaskButton;
    @FXML private Button submitMilestoneButton;
    @FXML private Button quitButton;
    @FXML private ComboBox<Assignment> assignmentComboBox;
    @FXML private ComboBox<Module> moduleComboBox;

    private  static Stage stage;

    public MilestoneController() {
    }


    public void initialize() {

        addTaskButton.setOnAction(value -> {
            try {
                addTaskButtonClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        removeTaskButton.setOnAction(value -> {
            try {
                removeTaskButtonClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        submitMilestoneButton.setOnAction(value -> {
            try {
                handleSubmit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //submitMilestoneButton.setDisable(true);

        quitButton.setOnAction(value -> {
            try {
                handleQuit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });




        tasksListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null || task.getTaskName() == null) {
                    setText(null);
                } else {
                    setText(task.getTaskName());
                }
            }
        });
        addedTasksListView.setCellFactory(param -> new ListCell<Task>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null || task.getTaskName() == null) {
                    setText(null);
                } else {
                    setText(task.getTaskName());
                }
            }
        });
        assignmentComboBox.setCellFactory(param -> new ListCell<Assignment>() {
            @Override
            protected void updateItem(Assignment assignment, boolean empty) {
                super.updateItem(assignment, empty);

                if (empty || assignment == null || assignment.getName() == null) {
                    setText(null);
                } else {
                    setText(assignment.getName());
                }
            }
        });
        tasksListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.addChangeListeners(moduleComboBox, assignmentComboBox);

        initData();

        if(currentMilestone != null){
            taskObservableList = FXCollections.observableList(currentMilestone.getTasks());
            milestoneName.setText(currentMilestone.getMilestoneName());
            milestoneNotes.setText(currentMilestone.getMilestoneDescription());
            Date date = currentMilestone.getEndDate();
            LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
            milestoneDeadline.setValue(ld);
            addedTasksListView.setItems(taskObservableList);
        }

    }


    private void addTaskButtonClick() {
        stage = (Stage) tasksListView.getScene().getWindow();
        if(addedTasksListView.getItems().contains(tasksListView.getSelectionModel().getSelectedItem())){
            System.out.println("This task has already been added");
        }
        else{
            addedTasksListView.getItems().add(tasksListView.getSelectionModel().getSelectedItem());
        }
    }

    private void removeTaskButtonClick() {
        try{
            stage = (Stage) tasksListView.getScene().getWindow();
            Task tempTask = addedTasksListView.getSelectionModel().getSelectedItem();
            addedTasksListView.getItems().remove(tempTask);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void validateDeadline() {
    }

    public void setData(Profile currentProfile) {
        profile = currentProfile;
    }

    public void setData(Profile currentProfile, Assignment assign, Module module) {
        profile = currentProfile;
        selectedAssignment = assign;
        selectedModule = module;
    }

    public void setData(Profile currentProfile, Module module, Assignment assign) {
        profile = currentProfile;
        selectedModule = module;
        selectedAssignment = assign;
    }



    public void initData() {
        assignmentComboBox.setConverter(new StringConverter<Assignment>() {

            @Override
            public String toString(Assignment assignment) {
                return assignment.getName();
            }

            @Override
            public Assignment fromString(String string) {
                return assignmentComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });
        moduleComboBox.setConverter(new StringConverter<Module>() {

            @Override
            public String toString(Module module) {
                return module.getName();
            }

            @Override
            public Module fromString(String string) {
                return moduleComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });

        if (selectedModule != null) moduleComboBox.setValue(selectedModule);
        if (selectedAssignment != null && !selectedAssignment.getEndDate().before(new Date()))
            assignmentComboBox.setValue(selectedAssignment);
        //moduleComboBox.getItems().addAll(profile.getModules());
    }

    public void addTask(Task task) {

    }

    public void handleSubmit() throws Exception {
        /*            //Create a new Milestone:
            java.util.Date date = java.sql.Date.valueOf(this.milestoneDeadline.getValue());
            this.milestone = new Milestone(this.milestoneName.getText(), this.milestoneNotes.getText(), date);
            this.milestone.addTask((Task) this.tasksListView.getItems());
            profile.setMilestone(this.milestone);
            // =================*/

        stage = (Stage) submitMilestoneButton.getScene().getWindow();
        if (assignmentComboBox.getValue() == null || milestoneName.getText().trim().isEmpty()
                || addedTasksListView.getItems().isEmpty()) {
            System.out.println("Mandatory fields are empty");
        } else {
            try {
               // List<Task> addedTasksList = addedTasksListView.getItems();
                ObservableList<Task> addedTasksList = addedTasksListView.getItems();
                ArrayList<Task> addedTasksArrayList;
                if (addedTasksList instanceof ArrayList<?>) {
                    addedTasksArrayList = (ArrayList<Task>) addedTasksList;
                } else {
                    addedTasksArrayList = new ArrayList<>(addedTasksList);
                }

                if(currentMilestone == null){
                    Milestone newMilestone = new Milestone();
                    newMilestone.setMilestoneName(milestoneName.getText());
                    newMilestone.setMilestoneDescription(milestoneNotes.getText());
                    newMilestone.setEndDate(java.sql.Date.valueOf(milestoneDeadline.getValue()));
                    newMilestone.setTasks(addedTasksArrayList);
                    newMilestone.setBelongingAssignment(selectedAssignment);
                    currentMilestone = newMilestone;
                    selectedAssignment.setMilestone(currentMilestone);
                }
                else{
                    currentMilestone.setMilestoneName(milestoneName.getText());
                    currentMilestone.setMilestoneName(milestoneName.getText());
                    currentMilestone.setMilestoneDescription(milestoneNotes.getText());
                    currentMilestone.setEndDate(java.sql.Date.valueOf(milestoneDeadline.getValue()));
                    currentMilestone.setTasks(addedTasksArrayList);
                    currentMilestone.setBelongingAssignment(selectedAssignment);
                    selectedAssignment.setMilestone(currentMilestone);
                }

                System.out.println("Successfully added Milestone");
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        pc.setProfile(profile);
        pc.saveProfile(profile);

        //System.out.println(currentMilestone.getMilestoneName());
        //System.out.println(currentMilestone.getMilestoneDescription());
        //System.out.println(currentMilestone.getTasks());
        //ui.loadAssignment(selectedAssignment,profile);
    }


    public void handleQuit(){
        Stage stage = (Stage) this.submitMilestoneButton.getScene().getWindow();
        stage.close();
    }

    public void handleChange(){

    }

    public void addChangeListeners(ComboBox<Module> modules, ComboBox<Assignment> assignments){
        modules.valueProperty().addListener(new ChangeListener<Module>() {
            @Override
            public void changed(ObservableValue ov, Module prev, Module cur) {
                //resets value to zero so that user can't create milestone
                //with incompatible modules and assignments
                if(assignments.getValue() != null){
                    assignments.setValue(null);
                }
                tasksListView.getItems().clear();
                addedTasksListView.getItems().clear();
                milestoneName.setText(null);
                milestoneNotes.setText(null);
                ArrayList<Assignment> beforeDeadlineAssign = new ArrayList<>();
                for(Assignment assign : cur.getAssignments()){
                    if(!assign.getEndDate().before(new Date())){
                        beforeDeadlineAssign.add(assign);
                    }
                }
                assignments.getItems().setAll(beforeDeadlineAssign);
            }
        });
        assignments.valueProperty().addListener(new ChangeListener<Assignment>(){
            @Override
            public void changed(ObservableValue ov, Assignment prev, Assignment cur){
                if(cur!=null){
                    //updateDatePicker(dueDatePicker, cur);
                    tasksListView.getItems().setAll(cur.getTasks());
                    if(cur.getMilestone()!=null){
                        addedTasksListView.getItems().setAll(cur.getMilestone().getTasks());
                        milestoneName.setText(cur.getMilestone().getMilestoneName());
                        milestoneNotes.setText(cur.getMilestone().getMilestoneDescription());
                        Date date = cur.getMilestone().getEndDate();
                        LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
                        milestoneDeadline.setValue(ld);
                    }
                }
                else
                {
                    System.out.println("Null Value");
                    //taskListView.getItems().clear();
                    //addedTaskListView.getItems().clear();
                }
            }
        });
    }
}
