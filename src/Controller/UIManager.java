package Controller;

import Model.*;
import Model.Module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.*;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
    private static Parent rootParent;
    private static Stage rootStage;
    private Parent tempParent;
    private Stage tempStage;
    private static Profile profile;
    private static Assignment selectedAssignment;
    private static Module selectedModule;
    private Task selectedTask;
    private MainController mc = new MainController();
    private ProfileController staticProfileController;

    public static List<Stage> stageval = new ArrayList<Stage>();
    public static List<String>fxmlval = new ArrayList<String>();

    public static void setRootParent(Parent parent){ rootParent = parent; }
    public static void setRootStage(Stage stage){ rootStage = stage; }
    public void setTempStage(Stage stage){ tempStage = stage; }
    public void setProfile(Profile selectedProfile) { profile = selectedProfile; }

    public static void setSelectedAssignment(Assignment selectedAssignment) {
        UIManager.selectedAssignment = selectedAssignment;
    }

    /*  Create new scene of CreateActivityView
     */
    public void addActivity(Profile currentProfile, Assignment currentAssignment, Task currentTask,
                            ObservableList<Activity> activityList) throws Exception {
        CreateActivityController createActivityController = new CreateActivityController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateActivityView.fxml"));

        //createActivityController.setProfile(currentProfile);
        createActivityController.setProfile(profile);
        createActivityController.setAssignment(currentAssignment);
        //createActivityController.setPc(staticProfileController);
        //System.out.println(selectedTask.getTaskName());
        createActivityController.setTask(currentTask);
        createActivityController.setActivityList(activityList);
        loader.setController(createActivityController);
        Stage stage = new Stage();
        stage.setTitle("New Activity");
        stage.setScene(new Scene( loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }
    public void addActivity(Profile currentProfile, Assignment currentAssignment, Task currentTask,
                            ObservableList<Activity> activityList, Activity selectedActivity) throws Exception {
        CreateActivityController createActivityController = new CreateActivityController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateActivityView.fxml"));

        //createActivityController.setProfile(currentProfile);
        createActivityController.setProfile(profile);
        createActivityController.setAssignment(currentAssignment);
        //createActivityController.setPc(staticProfileController);
        //System.out.println(selectedTask.getTaskName());
        createActivityController.setTask(currentTask);
        createActivityController.setActivityList(activityList);
        createActivityController.setExistingActivity(selectedActivity);
        loader.setController(createActivityController);
        Stage stage = new Stage();
        stage.setTitle("New Activity");
        stage.setScene(new Scene( loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    /*  Change scene to chosen Module
     */
    public void loadModule(Profile currentProfile, Module module) throws Exception{
        ModuleController mc = new ModuleController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Module.fxml"));


        selectedModule = module;
        mc.setModule(module);
        mc.setProfile(currentProfile);
        loader.setController(mc);
        Stage stage = new Stage();
        stage.setTitle(module.getName());
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    /*  Change scene to chosen Module
     */
    public void loadModule(Profile currentProfile, Module module, Event event) throws Exception{
        ModuleController mc = new ModuleController();
        mc.setModule(selectedModule);
        mc.setProfile(currentProfile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Module.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        loader.setController(mc);
        //Scene viewScene = new Scene(viewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }
        //window.setScene(viewScene);
        window.show();
    }

    /*  Change scene to chosen Module
     */
    public void loadModule(Profile currentProfile, Module module, Stage window) throws Exception{
        ModuleController mc = new ModuleController();
        mc.setModule(selectedModule);
        mc.setProfile(currentProfile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Module.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        //mc.setupTable();
        //mc.handleClick();
        loader.setController(mc);
        //Scene viewScene = new Scene(viewParent);
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }
        //window.setScene(viewScene);
        window.show();
    }


/*    public void loadAssignment(Assignment assignment, Profile currentProfile) throws Exception{

        AssignmentController assignmentController = new AssignmentController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Assignment.fxml"));


        selectedAssignment = assignment;
        assignmentController.setSelectedAssignment(assignment);
        assignmentController.setProfile(currentProfile);
        loader.setController(assignmentController);
        Stage stage = new Stage();
        stage.setTitle(selectedAssignment.getName());
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }*/

    public void loadAssignment(Assignment assignment, Profile currentProfile, Event event) throws Exception{

        AssignmentController assignmentController = new AssignmentController();
        selectedAssignment = assignment;
        assignmentController.setSelectedAssignment(assignment);
        assignmentController.setProfile(currentProfile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Assignment.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        loader.setController(assignmentController);
        //Scene viewScene = new Scene(viewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }
        //window.setScene(viewScene);
        window.show();
    }

    //opens gantt chart from assignment controller
    public void loadGanttChart(Assignment assignment, Profile currentProfile) throws Exception{
        AssignmentController assignmentController = new AssignmentController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Assignment.fxml"));

        selectedAssignment = assignment;
        assignmentController.setSelectedAssignment(assignment);
        assignmentController.setProfile(currentProfile);
        loader.setController(assignmentController);
        Stage stage = new Stage();
        assignmentController.initGanttChart(stage, assignment, rootStage);
    }


/*    public void loadTask(Task task, Profile currentProfile, Assignment currentAssignment, ActionEvent event) throws IOException {
        TaskController taskController = new TaskController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Task.fxml"));

        selectedTask = task;
        taskController.setSelectedTask(task);
        taskController.setProfile(currentProfile);
        taskController.setAssignment(currentAssignment);
        loader.setController(taskController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }*/


    public void loadTask(Task task, Profile currentProfile, Assignment currentAssignment, ActionEvent event) throws IOException{
        TaskController taskController = new TaskController();
        taskController.setProfile(currentProfile);
        taskController.setSelectedTask(task);
        taskController.setAssignment(currentAssignment);

        selectedAssignment = currentAssignment;

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Task.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        loader.setController(taskController);
        //Scene viewScene = new Scene(viewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }
        //window.setScene(viewScene);
        window.show();
    }
    public void loadMilestone(Profile currentProfile, Assignment selectedAssignment) throws IOException {
        MilestoneController milestoneController = new MilestoneController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateMilestoneView.fxml"));

        milestoneController.setData(currentProfile, selectedAssignment, selectedModule);
        loader.setController(milestoneController);
        Stage stage = new Stage();
        stage.setTitle("Edit Milestone");
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    /*  Change scene to Dashboard
     */
    public void loadDashboard(Profile currentProfile) throws Exception{
        DashboardController dc = new DashboardController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Dashboard.fxml"));

        dc.setProfile(currentProfile);
        loader.setController(dc);
        Stage stage = new Stage();
        stage.setTitle("Dashboard");
        stage.setScene(new Scene((Pane) loader.load()));
        dc.setPrimaryStage(stage);
        stage.show();
    }

    public void openPreviousScene() throws IOException {
        Stage stage = stageval.get(stageval.size() - 1);
        String instance = fxmlval.get(fxmlval.size() - 1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource(instance));
        loader.setController(mc);
        Parent parent = FXMLLoader.load(getClass().getResource(instance));
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void loadStatistics(Profile currentProfile) throws IOException{
        StatisticsController statisticsController = new StatisticsController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Statistics.fxml"));

        statisticsController.setProfile(currentProfile);
        loader.setController(statisticsController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    public void loadRequirement(Profile currentProfile, Assignment currentAssignment) throws IOException{
        RequirementController requirementController = new RequirementController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateRequirementView.fxml"));

        requirementController.setProfile(currentProfile);
        requirementController.setCurrentAssignment(currentAssignment);
        //requirementController.setCurrentModule(selectedModule);
        loader.setController(requirementController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }
    public void loadRequirement(Profile currentProfile, Assignment currentAssignment,
                                ArrayList<Activity> requirementsArrayList) throws IOException{
        RequirementController requirementController = new RequirementController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateRequirementView.fxml"));

        requirementController.setProfile(currentProfile);
        requirementController.setCurrentAssignment(currentAssignment);
        requirementController.setRequirementArrayList(requirementsArrayList);
        loader.setController(requirementController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }

    public void loadProfileSelection(Profile currentProfile) throws IOException {
        ProfileController profileController = new ProfileController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/StudyPlannerProfile.fxml"));

        profileController.setProfile(currentProfile);
        loader.setController(profileController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void loadNewAccount() throws IOException {
        NewProfileController createAccountController = new NewProfileController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateAccountView.fxml"));

        loader.setController(createAccountController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void loadLogin() throws IOException {
        NewProfileController createAccountController = new NewProfileController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Login.fxml"));

        loader.setController(createAccountController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void statsBack(Profile currentProfile, ActionEvent event) throws IOException{
        DashboardController dashboardController = new DashboardController();
        dashboardController.setProfile(currentProfile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Dashboard.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        loader.setController(dashboardController);
        //Scene viewScene = new Scene(viewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }
        //window.setScene(viewScene);
        window.show();
    }

    public void taskBack(Profile currentProfile, ActionEvent event) throws Exception {
        AssignmentController assignmentController = new AssignmentController();
        assignmentController.setProfile(currentProfile);
        assignmentController.setSelectedAssignment(selectedAssignment);
        //loadAssignment(selectedAssignment, currentProfile);

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Assignment.fxml"));
        //Parent viewParent = FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
        loader.setController(assignmentController);
        //Scene viewScene = new Scene(viewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //window.setScene(viewScene);
        //Stage stage = new Stage();
        window.setScene(new Scene(loader.load()));
        try
        {
            window.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            window.getScene().getRoot().setStyle("");
        }

        //window.setScene(viewScene);
        window.show();
    }

    public void openLoginVerify(Profile selectedProfile, ProfileController pc) throws Exception {
        verifyLoginController loginController = new verifyLoginController();
        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Login.fxml"));
        profile = selectedProfile;
        loginController.setUserProfile(selectedProfile);

        staticProfileController = pc;

        loader.setController(loginController);
        Stage stage = new Stage();
        stage.setTitle("Verify Login");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }

    public void openEditProfile(ProfileController profileController, Profile currentProfile) throws Exception {
        EditProfileController c = new EditProfileController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/EditProfile.fxml"));
        c.setPc(profileController);
        c.setCurrentProfile(currentProfile);
        loader.setController(c);
        Stage stage = new Stage();
        stage.setTitle("Edit Profile");
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }

    public void loadHelp() throws IOException{
        HelpController helpController = new HelpController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Help.fxml"));

        loader.setController(helpController);
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        try
        {
            stage.getScene().getRoot().setStyle(rootStage.getScene().getRoot().getStyle());
        }
        catch(Exception e)
        {
            stage.getScene().getRoot().setStyle("");
        }
        stage.show();
    }


}
