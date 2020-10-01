package Controller;

import Model.Assignment;
import Model.Module;
import Model.Profile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Date;

public class ModuleController {

    @FXML
    private TableView<Assignment> assignmentTable;
    @FXML
    private TableColumn<Assignment, String> assignmentName;
    @FXML
    private TableColumn<Assignment, Double> assignmentWeighting;
    @FXML
    private TableColumn<Assignment, Date> assignmentDeadline;
    @FXML
    private Text moduleDescription;
    @FXML
    private Label moduleTitle;
    @FXML
    private Assignment selectedAssignment;
    @FXML
    private ProgressBar assignmentProgress;
    private static Stage primaryStage;
    @FXML private Button helpButton;
    private Profile profile;
    private static Module selectedModule;

    UIManager ui = new UIManager();

    public void setModule(Module module) {
        this.selectedModule = module;
    }
    public void setProfile(Profile currentProfile) { this.profile = currentProfile; }

    public void initialize() {
        moduleTitle.setText(selectedModule.getName());
        moduleDescription.setText(selectedModule.getModuleDescription());

        /*
        goToPreviousScene.setOnAction(value ->  {
            try {
                ui.openPreviousScene();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        */

        setupTable();
        handleClick();

        helpButton.setOnAction(value ->  {
            try {
                ui.loadHelp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    public void clickAssignment() {
        Assignment cur = assignmentTable.getSelectionModel().selectedItemProperty().get();
    }

    private void updateAssignmentProgress(Assignment cur) {
        try {
            assignmentProgress.setProgress(cur.calculateAssignmentProgress());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupTable() {

        PropertyValueFactory<Assignment, String> aName = new PropertyValueFactory<>("name");
        PropertyValueFactory<Assignment, Double> aWeighting = new PropertyValueFactory<>("weightingPercentage");
        PropertyValueFactory<Assignment, Date> aDate = new PropertyValueFactory<>("endDate");

        assignmentName.setCellValueFactory(aName);
        assignmentWeighting.setCellValueFactory(aWeighting);
        assignmentDeadline.setCellValueFactory(aDate);

        ObservableList<Assignment> assignmentsList = FXCollections.observableArrayList();

        for (Assignment a : selectedModule.getAssignments()) {
            assignmentsList.addAll(a);
        }
        for(Assignment a : assignmentsList){
            assignmentTable.getItems().add(a);
        }
    }

    public void handleClick() {
        assignmentTable.setOnMouseClicked((MouseEvent event) -> {
            selectedAssignment = assignmentTable.getSelectionModel().getSelectedItem();
            updateAssignmentProgress(selectedAssignment);
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                //System.out.println(approachingTable.getSelectionModel().getSelectedItem());
                selectedAssignment = assignmentTable.getSelectionModel().getSelectedItem();
                try {
                    ui.loadAssignment(selectedAssignment, profile, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

