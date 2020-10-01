package Controller;
import Model.Profile;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class verifyLoginController implements Serializable {
    UIManager ui = new UIManager();
    @FXML private PasswordField inputPWord;
    @FXML private Label logInStatus;
    @FXML private Button logInBtn;
    /* on button click. verify pword. either close page and open dashboard.
     * or close page leaving the user at the profiles section*/
    private Profile userProfile;
    void initData(Profile profile){this.userProfile = profile;}

    public void initialize() {
        logInBtn.setOnAction(value ->  {
            try {
                Stage stage = (Stage) this.logInBtn.getScene().getWindow();
                if(logIn(userProfile)){
                    stage.close();
                    showDashboard(userProfile);
                }
                else{
                    //changed to error message POPUP
                    //System.out.println("Log in failed");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void showDashboard(Profile profile) throws Exception {
        ui.setProfile(profile);
        ui.loadDashboard(profile);
    }
    public boolean logIn(Profile usrProfile) throws IOException {
        Hasher hasher = new Hasher(){};
        hasher.setHashedValue(inputPWord.getText());
        if (hasher.getHashedValue().equals(userProfile.getPassword())){
            logInStatus.setText("Access authorised");
            return true;
        }
        else{
            logInStatus.setText("Password incorrect");
            return false;
        }

    }

    public void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
    }
}
