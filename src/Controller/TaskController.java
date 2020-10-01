package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TaskController {

    UIManager ui = new UIManager();

    private Profile currentProfile;
    private Activity selectedActivity;

    @FXML private Label taskName;
    @FXML private Text taskNotes;
    @FXML private TableView<Activity> activityTable;
    @FXML private TableColumn<Activity, String> activityName;
    @FXML private TableColumn<Activity, Double> activityInitialValue;
    @FXML private TableColumn<Activity, Double> activityCompletedValue;
    @FXML private ProgressBar activityProgressBar;
    ObservableList<Activity> activityList = FXCollections.observableArrayList();

    private static Assignment assignment;
    private Task selectedTask;
    private Profile profile;

    @FXML private Button refreshTable;
    @FXML private Button editActivityButton;
    @FXML private Button backButton;
    @FXML private Button helpButton;

    public void initialize(){
        taskName.setText(selectedTask.getTaskName());

        refreshTable.setOnAction(value ->  {
            try {
                updateTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        editActivityButton.setOnAction(value ->  {
            try {
                selectedActivity = activityTable.getSelectionModel().getSelectedItem();
                if (selectedActivity != null){
                    ui.addActivity(currentProfile, assignment, selectedTask, activityList, selectedActivity);
                }
                else{
                    System.out.println("Please select an activity to edit");
                }

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

        this.setupTable();
        handleClick();
    }

    public void taskBack(ActionEvent event){
        try {
            ui.taskBack(profile, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSelectedTask(Task currentTask){
        this.selectedTask = currentTask;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public void newActivity() throws Exception {
        Stage stage = (Stage) taskNotes.getScene().getWindow();
        stage.close();
        ui.addActivity(currentProfile, assignment, selectedTask, activityList);
        updateTable();
    }

    private void updateTable(){
        activityTable.refresh();
        activityTable.setItems(activityList);
        System.out.println(activityList);
    }

    private void handleClick() {
        activityTable.setOnMouseClicked((MouseEvent event) -> {
            selectedActivity = activityTable.getSelectionModel().getSelectedItem();
            activityProgressBar.setProgress(selectedActivity.calculateActivityProgress());
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                selectedActivity = activityTable.getSelectionModel().getSelectedItem();
                try {
                    ui.addActivity(currentProfile, assignment, selectedTask, activityList, selectedActivity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setupTable() {
        PropertyValueFactory<Activity, String> aName = new PropertyValueFactory<>("activityName");
        PropertyValueFactory<Activity, Double> aInitialValue = new PropertyValueFactory<>("initialValue");
        PropertyValueFactory<Activity, Double> aCompletedValue = new PropertyValueFactory<>("completedValue");

        activityName.setCellValueFactory(aName);
        activityInitialValue.setCellValueFactory(aInitialValue);
        activityCompletedValue.setCellValueFactory(aCompletedValue);

        for (Activity a : selectedTask.getActivities()) {
            activityList.addAll(a);
        }
        for(Activity a : activityList){
            activityTable.getItems().add(a);
        }
    }
    public void setProfile(Profile profile){
        this.currentProfile = profile;
    }

    public void loadAssignment(ActionEvent event){
        try {
            ui.loadAssignment(assignment,profile, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
