package Controller;

import Model.Profile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;

public class ProfileController {

    UIManager ui = new UIManager();

    @FXML private ListView<Profile> profileListView;
    @FXML private Button newProfileButton;
    @FXML private Button loadProfileButton;
    @FXML private Button deleteProfileButton;
    @FXML private Button alterDetailsBtn;
    @FXML private Button refreshTable;

    private Profile currentProfile;
    public Stage primaryStage;

    public void setProfile(Profile profile){
        this.currentProfile = profile;
    }

    public void initialize(){
        ChangeListener listener = (ChangeListener<Profile>) (ObservableValue<? extends Profile> observable,
                                                             Profile oldValue, Profile newValue) -> {
            currentProfile = newValue;
        };

        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            File dir = new File(System.getProperty("user.dir"));
            File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().contains(".ser"));
            if (files.length != 0) {
                for (File f : files) {
                    fin = new FileInputStream(f.getName());
                    ois = new ObjectInputStream(fin);

                    addProfileToListView((Profile) ois.readObject());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("File does not exist");
        }
        profileListView.getSelectionModel().selectedItemProperty().addListener(listener);

        newProfileButton.setOnAction(value ->  {
            try {
                openCreateProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        loadProfileButton.setOnAction(value ->  {
            try {
                openLoadProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        deleteProfileButton.setOnAction(value ->  {
            try {
                deleteProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        refreshTable.setOnAction(value ->  {
            try {
                refreshTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        alterDetailsBtn.setOnAction(value ->  {
            try {
                openEditProfile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        deleteProfileButton.setOnAction(value ->  {
            try {
                if(deleteProfile()){
                    System.out.println("File deleted successfully");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        profileListView.setCellFactory(param -> new ListCell<Profile>() {
            @Override
            protected void updateItem(Profile profile, boolean empty) {
                super.updateItem(profile, empty);

                if (empty || profile == null || profile.getName() == null) {
                    setText(null);
                } else {
                    setText(profile.getName());
                }
            }
        });
    }

    public void refreshTable(){
        profileListView.refresh();
        //profileListView.setItems(activityList);
        //System.out.println(activityList);
        //System.out.println("YA");
    }

    public void addProfileToListView(Profile studyProfile) {
            profileListView.getItems().add(studyProfile);
    }

    public void saveProfile(Profile profile) throws Exception {
            try {
                String fileName = "studyplanner" + profile.getName() + ".ser";
                //String fileName = "StudyPlanner.ser";
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(profile);
                oos.close();
                System.out.println("Successfully saved");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    public void showDashboard(Profile profile) throws Exception {
        ui.setProfile(profile);
        ui.loadDashboard(profile);
    }

    public void openEditProfile() throws Exception {
        ui.openEditProfile(this, currentProfile);
    }

    private void openCreateProfile() throws Exception {
        NewProfileController newProfileController = new NewProfileController();

        // Load in the .fxml file:
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/CreateProfileView.fxml"));
        loader.setController(newProfileController);
        newProfileController.initData(this);
        Stage stage = new Stage();
        stage.setTitle("Create New Profile");
        stage.setScene(new Scene((Pane) loader.load()));
        stage.show();
    }
    private void openLoadProfile() throws Exception {
        if (currentProfile != null) {
            loadProfileButton.getScene().getWindow().hide();

            //Call function here to open LoginVerify
            ui.openLoginVerify(currentProfile, this);

            //showDashboard(currentProfile);
        }
    }
    public boolean deleteProfile(){
        //System.out.println("TEAST");
        boolean retVal = false;
        if (currentProfile != null) {
            String fileName = "studyplanner" + currentProfile.getName() + ".ser";
            File file = new File("./" + fileName);
            try {
                if(file.delete()){ retVal = true;}
                profileListView.refresh();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        refreshTable();
        return retVal;
    }
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }
}
