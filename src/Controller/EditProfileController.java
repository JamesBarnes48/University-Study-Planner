package Controller;

import Model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import static Model.Profile.isValid;

public class EditProfileController {
    Stage stage;
    private static ProfileController pc = new ProfileController();
    private static Profile currentProfile;
    @FXML private Button alterDetailsBtn; //button to create a profile
    @FXML private Button browseFileBtn;    //button to browse to a data file
    @FXML private TextField newName; //field to input profile's name
    @FXML private PasswordField newPassword;    //field to input password
    @FXML private PasswordField currentPassword;    //field to input password
    @FXML private TextField newDataPath;//field to input path to hub file
    boolean inputVerified = true;

    public void initialize(){
        browseFileBtn.setOnAction(value ->  {
            try {
                browseButtonClick();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        alterDetailsBtn.setOnAction(value ->  {
            try {
                alterDetailsBtnClicked();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
/*    void initData(ProfileController controller){
        this.pc = controller;
    }*/

    public void setPc(ProfileController pc) {
        EditProfileController.pc = pc;
    }

    public void setCurrentProfile(Profile selectedProfile){
        currentProfile = selectedProfile;
    }

    private void alterDetailsBtnClicked() throws Exception {

/*        if(newName.getText().trim().isBlank() ||
                newDataPath.getText().trim().isBlank() ||
                newPassword.getText().trim().isBlank()) {
            inputVerified = false;
        }*/

/*        if(inputVerified){
            pc.deleteProfile();
            //change the return of this too bool to see if it works
            Profile profile = new Profile();
            profile.setName(newName.getText());
            Hasher hashedPassW = new Hasher(){};
            hashedPassW.setHashedValue(newPassword.getText());
            profile.setPassword(hashedPassW.getHashedValue());
            File hubFile = new File(newDataPath.getText());
            Profile.InitialiseStudyProfile(profile, hubFile);
            pc.setProfile(profile);
            // assigns a real profile to the profile controller for this page
            pc.saveProfile(profile);
            //saves the profile by calling the profile serialisation method
            pc.addProfileToListView(profile);
            stage = (Stage) alterDetailsBtn.getScene().getWindow();
            stage.hide();
            System.out.println("Successfully edited");
        }
        else{
            System.out.println("Please enter all the data");
        }*/

        try {
            Hasher hasher = new Hasher() {};
            hasher.setHashedValue(currentPassword.getText());
            if (hasher.getHashedValue().equals(currentProfile.getPassword())) {
                //pc.deleteProfile();
                if (!newName.getText().trim().isBlank()) {
                    currentProfile.setName(newName.getText());
                }
                if (!newPassword.getText().trim().isBlank()) {
                    Hasher hashedPassW = new Hasher() {
                    };
                    hashedPassW.setHashedValue(newPassword.getText());
                    currentProfile.setPassword(hashedPassW.getHashedValue());
                }
                if (!newDataPath.getText().trim().isBlank()) {
                    File hubFile = new File(newDataPath.getText());
                    Profile.InitialiseStudyProfile(currentProfile, hubFile);
                }
                pc.setProfile(currentProfile);
                stage = (Stage) alterDetailsBtn.getScene().getWindow();
                stage.hide();
                System.out.println("Successfully edited");
                pc.saveProfile(currentProfile);
                //ProfileController.refreshTable();
            }
            else{
                System.out.println("Incorrect current password");
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void browseButtonClick(){
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Hub File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
        File file = fileChooser.showOpenDialog(stage);
        if(file != null) {
            if(isValid(file)) {
                newDataPath.setText(file.getPath());
            }
            else {
                System.out.println("Invalid file selected");
            }
        }
    }
}