package Controller;

import DOA.*;
import Model.User;
import Utils.AlertMessage;
import Utils.SwitchView;
import Utils.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controls the Login view
 */
public class Login_Controller implements Initializable {

    @FXML
    private TextField usernameTxtBox;

    @FXML
    private Text usernameTxt;

    @FXML
    private Text passwordTxt;

    @FXML
    private Button resetBtn;

    @FXML
    private Button loginBtn;


    @FXML
    private Text loginTxt;

    @FXML
    private PasswordField passwordTxtBox;

    @FXML
    private Text timeZoneTxt;


    /** sends data to database to see if info entered into the login box is valid
     * @param event on login button clicked
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionLogin(ActionEvent event) throws SQLException, IOException {
        UsersDao usersDao = new UsersDaoImpl();
        String name = usernameTxtBox.getText();
        String password = passwordTxtBox.getText();
        String filename = "login_activity.txt", loginAttempts;
        FileWriter fWriter = new FileWriter(filename, true);
        PrintWriter outputFile = new PrintWriter(fWriter);
        if (usersDao.login(name, password) != null){
            AppointmentDao appointmentDao = new AppointmentDaoImpl();
            appointmentDao.getAllAppointments();
            loginAttempts = name + " logged in on " + String.valueOf(LocalDate.now()) + " at " + String.valueOf(LocalTime.now()) + " in Timezone of " + ZoneId.systemDefault().getId() + " and login was successful";
            outputFile.println(loginAttempts);
            outputFile.close();
            User user = DoaLists.getUsersList().stream().filter(User::isActive).findFirst().get();
            SwitchView.mainPage(event);
            Validation.appointmentAlert(user);
        }
        else {
            //loginAttempts = name + " attempted to login on " + String.valueOf(LocalDate.now()) + " at " + String.valueOf(LocalTime.now()) + " in Timezone of " + ZoneId.systemDefault().getId() + " and was not successful";
           // outputFile.println(loginAttempts);
           // outputFile.close();
            AlertMessage.incorrectLogin();
        }

    }

    /** clears username and password fields when clicked
     * @param event on reset button clicked
     */
    @FXML
    void onActionResetFields(ActionEvent event) {
        usernameTxtBox.clear();
        passwordTxtBox.clear();
    }

    /**
     * @param url
     * @param resourceBundle translates all the text to french if the default language is french
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String zone = ZoneId.systemDefault().getId();
        timeZoneTxt.setText(zone);

        resourceBundle = ResourceBundle.getBundle("Languages_fr");
        if (Locale.getDefault().getLanguage().contains("fr")) {
            Locale.setDefault(new Locale("fr"));
            loginTxt.setText(resourceBundle.getString("Login"));
            usernameTxt.setText(resourceBundle.getString("Username"));
            passwordTxt.setText(resourceBundle.getString("Password"));
            loginBtn.setText(resourceBundle.getString("Login"));
            resetBtn.setText(resourceBundle.getString("Reset"));
        }
    }
}
