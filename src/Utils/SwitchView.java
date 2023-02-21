package Utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This class is to prevent the controller classes from getting cluttered with switching view code
 */
public class SwitchView {
    private static Stage stage;
    private static Parent scene;

    /** swithes to mainPage
     * @param event on click button
     * @throws IOException
     */
    public static void mainPage(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/Main_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to login page
     * @param event on click button
     * @throws IOException
     */
    public static void loginPage(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/Login_View.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to addCustomer view
     * @param event on click button
     * @throws IOException
     */
    public static void addCustomerView(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/AddCustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to modify customer view
     * @param event on click button
     * @throws IOException
     */
    public static void modifyCustomerView(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/ModifyCustomerView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to addAppointmentView
     * @param event on button clicked
     * @throws IOException
     */
    public static void addAppointmentView(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/AddAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to modifyAppointmentView
     * @param event on button clicked
     * @throws IOException
     */
    public static void modifyAppointmentView(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/ModifyAppointmentView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** switches to reportsView
     * @param event on button clicked
     * @throws IOException
     */
    public static void reportsView(ActionEvent event) throws IOException{
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SwitchView.class.getResource("/View/ReportsView.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
