package Controller;

import Model.Activity;
import Model.Assignment;
import Model.Profile;
import Model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Calendar;

public class CreateActivityController {

    // Lists:
    @FXML private ListView<Task> tasks;
    @FXML private TextField NameActivity;
    @FXML private TextField initialValueTextField;
    @FXML private TextField completedValueTextField;
    @FXML private TextArea NotesActivity;

    @FXML private Button submitButton;
    @FXML private Button cancelButton;
    @FXML private CheckBox completeCheckBox;


    ObservableList<Activity> activityList;

    private Task selectedTask;
    private static Assignment selectedAssignment;
    private static Module selectedModule;
    private Activity existingActivity;
    private UIManager ui = new UIManager();
    private Profile profile;

    private static ProfileController pc = new ProfileController();

    private void onSubmitClick(ActionEvent event) throws Exception {
        if (!NameActivity.getText().trim().isBlank() && !initialValueTextField.getText().trim().isBlank()
        && !completedValueTextField.getText().trim().isBlank()) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (existingActivity == null) {
                Activity activity = new Activity();
                try {
                    activity.setActivityName(NameActivity.getText());
                    activity.setActivityNotes(NotesActivity.getText());
                    activity.setInitialValue(Double.parseDouble(initialValueTextField.getText()));
                    if (completedValueTextField.getText().trim().isEmpty() ||
                            completedValueTextField.getText().trim().isBlank()) {
                        activity.setCompletedValue(0);
                        activity.setComplete(completeCheckBox.isSelected());
                    }
                    activity.setCompletedValue(Double.parseDouble(completedValueTextField.getText()));
                    activity.setComplete(completeCheckBox.isSelected());
                    activity.setDayOfWeek(dayOfWeek);
                    activity.addTask(selectedTask);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number");
                } catch (Exception e) {
                    System.out.println("A different error occurred");
                }
                System.out.println(selectedTask.getActivities());
                selectedTask.addActivity(activity);
                //System.out.println(activity.getActivityName());
                System.out.println(selectedTask.getActivities());
                Stage stage = (Stage) this.submitButton.getScene().getWindow();
                stage.close();
                ui.loadTask(selectedTask, profile, selectedAssignment, event);
                //ui.loadTask(selectedTask, profile, selectedAssignment);
            } else {
                try {
                    selectedTask.removeActivity(existingActivity);
                    existingActivity.setActivityName(NameActivity.getText());
                    existingActivity.setActivityNotes(NotesActivity.getText());
                    existingActivity.setInitialValue(Double.parseDouble(initialValueTextField.getText()));
                    existingActivity.setCompletedValue(Double.parseDouble(completedValueTextField.getText()));
                    existingActivity.setComplete(completeCheckBox.isSelected());
                    existingActivity.addTask(selectedTask);
                    existingActivity.setDayOfWeek(dayOfWeek);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number");
                } catch (Exception e) {
                    System.out.println("A different error occurred");
                }
                selectedTask.addActivity(existingActivity);
                Stage stage = (Stage) this.submitButton.getScene().getWindow();
                stage.close();
                //ui.loadTask(selectedTask, profile, selectedAssignment, event);
                //ui.loadTask(selectedTask, profile, selectedAssignment);
            }
        }
        else {
            System.out.println("Please enter mandatory data");
        }
        pc.setProfile(profile);
        pc.saveProfile(profile);

    }

    public static void setPc(ProfileController pc) {
        CreateActivityController.pc = pc;
    }

    public void setActivityList(ObservableList<Activity> activityList) {
        this.activityList = activityList;
    }

    public void setExistingActivity(Activity activity){
        this.existingActivity = activity;
    }

    public void initialize()
    {
        submitButton.setOnAction(value ->  {
            try {
                onSubmitClick(value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(value ->  {
            try {
                Stage stage = (Stage) this.cancelButton.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

/*        for(Task.TaskType taskType : Task.TaskType.values()){
            taskChoiceBox.getItems().addAll(taskType);
        }

        this.addChangeListeners(assignmentChoiceBox);

        assignmentChoiceBox.setValue(selectedAssignment);
        if(selectedAssignment != null && !selectedAssignment.getEndDate().
                before(new Date())) assignmentChoiceBox.setValue(selectedAssignment);*/

        //System.out.println(selectedAssignment.getName());

        if(existingActivity != null){
            NameActivity.setText(existingActivity.getActivityName());
            initialValueTextField.setText(String.valueOf(existingActivity.getInitialValue()));
            completedValueTextField.setText(String.valueOf(existingActivity.getCompletedValue()));
            completeCheckBox.setSelected(existingActivity.isComplete());
            NotesActivity.setText(existingActivity.getActivityNotes());
        }
        //assignmentChoiceBox.getItems().addAll(se);
        //moduleChoiceBox.getItems().addAll(profile.getModules());
    }

    public void setAssignment(Assignment assignment){
        this.selectedAssignment = assignment;
    }

    public void setProfile(Profile currentProfile){
        this.profile = currentProfile;
    }

    public void setTask(Task task){
        this.selectedTask = task;
    }

    public void addChangeListeners(ChoiceBox<Assignment> assignments){
        assignments.valueProperty().addListener(new ChangeListener<Assignment>(){
            @Override
            public void changed(ObservableValue ov, Assignment prev, Assignment cur){
                if(cur!=null){
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
