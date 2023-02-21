package Model;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.*;

/**
 * this the first model class used in the application
 * the main(String[] args) is here
 */
public class Login extends Application {

    /** opens the first view model
     * @param primaryStage the first view
     * @throws Exception ioException
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/Login_View.fxml"));
        primaryStage.setTitle("Appointment Management System");
        primaryStage.setScene(new Scene(root, 634, 436));
        primaryStage.show();
    }


    /** main method is here
     * @param args
     * @throws SQLException
     */
    public static void main(String[] args) throws SQLException {

        launch(args);
    }
}
