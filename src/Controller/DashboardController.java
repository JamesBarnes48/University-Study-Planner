package Controller;

import Model.*;
import Model.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Date;

public class DashboardController {

    @FXML public Label label1;
    @FXML private TableView<Assignment> approachingTable;
    @FXML private TableView<Assignment> passedTable;
    @FXML private TableColumn<Assignment, String> approachingAssignment;
    @FXML private TableColumn<Assignment, Date> approachingDeadline;
    @FXML private TableColumn<Assignment, String> passedAssignment;
    @FXML private TableColumn<Assignment, Date> passedDeadline;

    @FXML private Assignment selectedAssignment;
    @FXML private Task selectedTask;
    @FXML private Milestone selectedMilestone;
    @FXML public GridPane moduleGridPane;
    @FXML private Button saveButton;
    @FXML private Button helpButton;
    //@FXML private MenuItem openButton;
    //@FXML private CheckMenuItem darkMode;
    @FXML private AnchorPane rootPane;
    @FXML
    private CheckBox darkMode;

    private static boolean hasBeenInitialized = false;

    UIManager ui = new UIManager();
    MainController mainController = new MainController();
    ProfileController profileController = new ProfileController();
    Profile profile;
    public Stage primaryStage;

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void initialize() throws Exception {
        mainController.setProfile(profile);
        profileController.setProfile(profile);
        UIManager ui = new UIManager();
        mainController.loadDashboard(moduleGridPane, profile);
        initData();
        handleClick();
        saveButton.setOnAction(value ->  {
            try {
                profileController.saveProfile(profile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        darkMode.selectedProperty().addListener((obs, wasSelected, isSelected) ->
        {
            if (isSelected) {
                primaryStage.getScene().getRoot().setStyle("-fx-base:grey");
                UIManager.setRootStage(primaryStage);
            }
            else {
                primaryStage.getScene().getRoot().setStyle("");
                UIManager.setRootStage(primaryStage);
            }
        });
        helpButton.setOnAction(value ->  {
            try {
                ui.loadHelp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void handleClick(){
/*        approachingTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                selectedAssignment = approachingTable.getSelectionModel().getSelectedItem();
                try {
                    ui.setTempStage(primaryStage);
                    ui.loadAssignment(selectedAssignment, profile, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        passedTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                System.out.println(passedTable.getSelectionModel().getSelectedItem());
            }
        });*/
    }

    public void setProfile(Profile currentProfile){
        this.profile = currentProfile;
    }

    public void onButtonClick() throws Exception {
        //label1.setText("COLD");
        //ui.addActivity();

    }

    public void openStatistics(){
        try {
            ui.loadStatistics(profile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initData(){

        boolean allDone = false;

        PropertyValueFactory<Assignment,String> aName = new PropertyValueFactory<>("name");
        PropertyValueFactory<Assignment,Date> aDate = new PropertyValueFactory<>("endDate");

        approachingAssignment.setCellValueFactory(aName);
        approachingDeadline.setCellValueFactory(aDate);

        passedAssignment.setCellValueFactory(aName);
        passedDeadline.setCellValueFactory(aDate);

        for(Module m : profile.getModules()){
            for(Assignment a : m.getAssignments()){
                Date current = new Date();

                for(Task t : a.getTasks()){
                    if(!t.isTaskComplete()) allDone = false;
                }

                if(current.getTime() < a.getEndDate().getTime())
                    approachingTable.getItems().add(a);
                else
                    passedTable.getItems().add(a);
            }
        }

    }

}
