import Controller.*;
import Model.Profile;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
/*        FXMLLoader loader = new FXMLLoader();
        DashboardController dc = new DashboardController();
        dc.setPrimaryStage(primaryStage);
        loader.setLocation(getClass().getResource("View/Dashboard.fxml"));
        loader.setController(dc);
        Parent homeRoot = loader.load();*/

        FXMLLoader loader = new FXMLLoader();
        ProfileController pc = new ProfileController();
        pc.setPrimaryStage(primaryStage);
        loader.setLocation(getClass().getResource("View/StudyPlannerProfile.fxml"));
        loader.setController(pc);
        Parent homeRoot = loader.load();

/*        FXMLLoader loader = new FXMLLoader();
        LoginController lc = new LoginController();
        lc.setPrimaryStage(primaryStage);
        loader.setLocation(getClass().getResource("View/Login.fxml"));
        loader.setController(lc);
        Parent homeRoot = loader.load();*/

        //Parent homeRoot = FXMLLoader.load(getClass().getResource("View/Dashboard.fxml"));
        primaryStage.setTitle("Study Planner");
        //primaryStage.setScene(new Scene(root, 300, 275));

        //primaryStage.setHeight(500);
        //primaryStage.setWidth(400);

        Scene home = new Scene(homeRoot);
        primaryStage.setScene(home);

        //UIManager.setRootParent(homeRoot);
        //UIManager.setRootStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        launch(args);

    }
}