package Controller;

import Model.*;
import Model.Module;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequirementController {
    private static Profile profile;
    private static Assignment currentAssignment;
    private static Module currentModule;
    private ObservableList<Activity> activityObservableList;
    private ArrayList<Activity> requirementArrayList;
    private Activity tempActivity;
    private static Requirement currentRequirement;

    private UIManager ui = new UIManager();
    private static ProfileController pc = new ProfileController();

    @FXML private ComboBox<Assignment> assignmentComboBox;
    @FXML private ComboBox<Module> moduleComboBox;
    @FXML private TextField requirementName;
    @FXML private TextArea requirementDescription;
    @FXML private ListView<Activity> availableActivitiesListView;
    @FXML private ListView<Activity> addedActivitiesListView;
    @FXML private DatePicker dueDatePicker;
    @FXML private Button updateButton;
    @FXML private Button cancelButton;
    @FXML private Button addActivityButton;
    @FXML private Button removeActivityButton;

    private static Stage stage;

    public RequirementController() {
    }

    public void initialize()
    {
        updateButton.setOnAction(value ->  {
            try {
                onSubmitClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(value ->  {
            try {
                handleQuit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        addActivityButton.setOnAction(value ->  {
            try {
                addActivityToAddedList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        removeActivityButton.setOnAction(value ->  {
            try {
                removeActivityfromAddedList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        availableActivitiesListView.setCellFactory(param -> new ListCell<Activity>() {
            @Override
            protected void updateItem(Activity activity, boolean empty) {
                super.updateItem(activity, empty);

                if (empty || activity == null || activity.getActivityName() == null) {
                    setText(null);
                } else {
                    setText(activity.getActivityName());
                }
            }
        });
        addedActivitiesListView.setCellFactory(param -> new ListCell<Activity>() {
            @Override
            protected void updateItem(Activity activity, boolean empty) {
                super.updateItem(activity, empty);

                if (empty || activity == null || activity.getActivityName() == null) {
                    setText(null);
                } else {
                    setText(activity.getActivityName());
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

        availableActivitiesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //addedActivitiesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        this.addChangeListeners(moduleComboBox, assignmentComboBox);

        initData();

        for(Task task : currentAssignment.getTasks()){
            for(Activity activity : task.getActivities()){
                availableActivitiesListView.getItems().addAll(activity);
            }
        }

        if(currentRequirement != null){
            System.out.println("SILLY");
            activityObservableList = FXCollections.observableList(currentRequirement.getActivities());
            requirementName.setText(currentRequirement.getRequirementName());
            requirementDescription.setText(currentRequirement.getRequirementNotes());
            addedActivitiesListView.setItems(activityObservableList);
            Date date = currentRequirement.getEndDate();
            LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
            dueDatePicker.setValue(ld);
        }

    }

    public void setRequirementArrayList(ArrayList<Activity> requirementArrayList) {
        this.requirementArrayList = requirementArrayList;
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
/*        moduleComboBox.setConverter(new StringConverter<Module>() {

            @Override
            public String toString(Module module) {
                return module.getName();
            }

            @Override
            public Module fromString(String string) {
                return moduleComboBox.getItems().stream().filter(ap ->
                        ap.getName().equals(string)).findFirst().orElse(null);
            }
        });*/

        if (currentModule != null) moduleComboBox.setValue(currentModule);
        if (currentAssignment != null && !currentAssignment.getEndDate().before(new Date()))
            assignmentComboBox.setValue(currentAssignment);
        //availableActivitiesListView.getItems().setAll();
    }

    public void setCurrentModule(Module module){
        currentModule = module;
    }

    public void setRequirementName(TextField name) {
        this.requirementName = name;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setCurrentAssignment(Assignment currentAssignment) {
        this.currentAssignment = currentAssignment;
    }

    public ArrayList<Activity> getRequirementArrayList() {
        return requirementArrayList;
    }

    private void onSubmitClick() throws Exception {
        stage = (Stage) updateButton.getScene().getWindow();
        try{
            ObservableList<Activity> addedActivitiesList = addedActivitiesListView.getItems();
            ArrayList<Activity> addedActivitiesArrayList;
            if (addedActivitiesList instanceof ArrayList<?>) {
                addedActivitiesArrayList = (ArrayList<Activity>) addedActivitiesList;
            } else {
                addedActivitiesArrayList = new ArrayList<>(addedActivitiesList);
            }

            if(currentRequirement == null){
                Requirement newRequirement = new Requirement();
                //System.out.println(milestoneName.getText());
                //System.out.println(milestoneNotes.getText());
                newRequirement.setRequirementName(requirementName.getText());
                //newRequirement.setRequirementNotes(requirementDescription.getText());
                newRequirement.setActivities(addedActivitiesArrayList);
                newRequirement.setContainingAssignment(currentAssignment);
                newRequirement.setEndDate(java.sql.Date.valueOf(dueDatePicker.getValue()));
                currentRequirement = newRequirement;
                currentAssignment.setRequirements(currentRequirement);
            }
            else{
                currentRequirement.setRequirementName(requirementName.getText());
                //currentRequirement.setRequirementNotes(requirementDescription.getText());
                currentRequirement.setActivities(addedActivitiesArrayList);
                currentRequirement.setContainingAssignment(currentAssignment);
                currentRequirement.setEndDate(java.sql.Date.valueOf(dueDatePicker.getValue()));
                currentAssignment.setRequirements(currentRequirement);
            }

            //requirementArrayList.addAll(addedActivitiesArrayList);


            System.out.println("Successfully added Requirement");
            System.out.println(currentAssignment.getRequirements().getActivities());
            stage.close();

        }
        catch(Exception e){
            System.out.println("A different error occurred");
        }

        pc.setProfile(profile);
        pc.saveProfile(profile);
    }

    private void addActivityToAddedList(){
        stage = (Stage) availableActivitiesListView.getScene().getWindow();
        tempActivity = availableActivitiesListView.getSelectionModel().getSelectedItem();
        if(addedActivitiesListView.getItems().contains(tempActivity)){
            System.out.println("This activity has already been added");
        }
        else{
            addedActivitiesListView.getItems().add(tempActivity);
        }
    }

    private void removeActivityfromAddedList(){
        stage = (Stage) availableActivitiesListView.getScene().getWindow();
        tempActivity = addedActivitiesListView.getSelectionModel().getSelectedItem();
        addedActivitiesListView.getItems().remove(tempActivity);
    }

    private void handleQuit(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void addChangeListeners(ComboBox<Module> modules, ComboBox<Assignment> assignments){
/*        modules.valueProperty().addListener(new ChangeListener<Module>() {
            @Override
            public void changed(ObservableValue ov, Module prev, Module cur) {
                //resets value to zero so that user can't create milestone
                //with incompatible modules and assignments
                if(assignments.getValue() != null){
                    assignments.setValue(null);
                }
                availableActivitiesListView.getItems().clear();
                addedActivitiesListView.getItems().clear();
                requirementName.setText(null);
                requirementDescription.setText(null);
                ArrayList<Assignment> beforeDeadlineAssign = new ArrayList<>();
                for(Assignment assign : cur.getAssignments()){
                    if(!assign.getEndDate().before(new Date())){
                        beforeDeadlineAssign.add(assign);
                    }
                }
                assignments.getItems().setAll(beforeDeadlineAssign);
            }
        });*/

        assignments.valueProperty().addListener(new ChangeListener<Assignment>(){
            @Override
            public void changed(ObservableValue ov, Assignment prev, Assignment cur){
                if(cur!=null){
                    //updateDatePicker(dueDatePicker, cur);
                    availableActivitiesListView.getItems().setAll(cur.getActivities());
                    if(cur.getMilestone()!=null){
                        addedActivitiesListView.getItems().setAll(cur.getRequirements().getActivities());
                        requirementName.setText(cur.getRequirements().getRequirementName());
                        requirementDescription.setText(cur.getRequirements().getRequirementNotes());
                        Date date = cur.getMilestone().getEndDate();
                        LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
                        dueDatePicker.setValue(ld);
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