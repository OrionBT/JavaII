package Utils;

import DOA.CustomerDao;
import DOA.CustomerDaoImp;
import DOA.DoaLists;
import Model.Appointment;
import Model.Customer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Instead of cluttering up the Controllers with alert message code I wrote a class for them.
 */
public class AlertMessage {

    /**
     * called when incorrect info is entered into the
     */
    public static void incorrectLogin(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
            if (Locale.getDefault().getLanguage().contains("fr")){
                alert.setContentText("Identifiant ou mot de passe incorrect.");
            }
            else {
                alert.setContentText("Incorrect username or password");
            }
        alert.showAndWait();
    }

    /** called to confirm deletion of a customer object
     * @param customer customer object
     * @return true if ok is pressed, false if cancel is pressed
     * @throws SQLException
     */
    public static boolean deleteCustomerConfirmation(Customer customer) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            if (Locale.getDefault().getLanguage().contains("fr")){
                alert.setContentText("Êtes-vous sûr de vouloir supprimer " + customer.getName() +"?");
            }
            else {
                alert.setContentText("Are you sure you wish to delete " + customer.getName() + "?");
            }
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            CustomerDao customerDao = new CustomerDaoImp();
            customerDao.deleteCustomer(customer);
            DoaLists.getCustomersList().remove(customer);
            return true;
        }
        return false;

    }

    /** is called when a customer is trying to be deleted before his appointments can be deleted
     * @param customer customer object who the user is trying to delete
     */
    public static void customerAppointment(Customer customer){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText(customer.getName() + " a un rendez-vous prévu, veuillez supprimer le rendez-vous associé avant de supprimer la fiche de ce client.");
        }
        else {
            alert.setContentText(customer.getName() + " has an appointment scheduled, " +
                    "please delete the associated appointment before deleting this customer's record.");
        }
        alert.showAndWait();
    }

    /**
     * called if fields in the modify customer view are left blank when pushing save
     */
    public static void selectCustomer(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Veuillez sélectionner un client dans la liste et entrer les valeurs correctes dans les champs de texte.");
        }
        else {
            alert.setContentText("Please Select a customer from the list and enter correct values into Text Fields");
        }
        alert.showAndWait();
    }

    /**
     * called if the filter radio buttons on the main screen are selected without selecting a date first
     */
    public static void selectDate(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Veuillez sélectionner une date dans le sélecteur de dates.");
        }
        else {
            alert.setContentText("Please select a date in the date picker. ");
        }
        alert.showAndWait();
    }

    /**
     * called by AddAppointmentController if fields are left blank when clicking save
     */
    public static void fillTextFields(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Veuillez remplir tous les champs de texte et les zones de liste déroulante avec des informations correctes.");
        }
        else {
            alert.setContentText("Please fill all text fields and combo boxes with correct information.");
        }
        alert.showAndWait();
    }

    /**
     * called if the start time for an appointment isn't before the end time of the appointment
     */
    public static void incompatibleDateTime(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Définissez l'heure de début avant l'heure de fin.");
        }
        else {
            alert.setContentText("Set the start time to be before the end time.");
        }
        alert.showAndWait();
    }

    /** called at login if the user has an appointment within 15 minutes
     * @param a appointment object coming within 15 minutes
     */
    public static void fifteenMinuteAlert(Appointment a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Vous avez un rendez-vous avec un numéro d'identification " + a.getAppointmentID() + " aujourd'hui (" + a.getDate() + ") à " + a.getStartTime());
        }
        else {
            alert.setContentText("You have and appointment with an Id number of " + a.getAppointmentID() + " today (" + a.getDate() + ") at " + a.getStartTime());
        }
        alert.showAndWait();
    }

    /**
     * called at login if the user does not have an appointment within 15 minutes
     */
    public static void noFifteenMinuteAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Vous n'avez aucun rendez-vous prévu dans les quinze minutes.");
        }
        else {
            alert.setContentText("You do not have any appointments scheduled within fifteen minutes.");
        }
        alert.showAndWait();
    }

    /**
     * called if times save for an appointment are out side of the permitted 8:00 am to 10:00 pm EST business hours
     */
    public static void estCheckAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Veuillez définir l'heure du rendez-vous entre 8 h 00 et 22 h 00 HNE");
        }
        else {
            alert.setContentText("Please set appointment time to be between 8:00 am and 10:00 pm EST");
        }
        alert.showAndWait();
    }

    /**
     * called if an appointment with that customer overlaps with another appointment with the same customer
     */
    public static void timeOverLapAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Votre heure de début ou de fin chevauche un autre rendez-vous pour ce client à cette date.");
        }
        else {
            alert.setContentText("Your start or end time overlaps with another appointment this date.");
        }
        alert.showAndWait();
    }

    /**
     * called if an appointment end or start time equals that of another appointment's end or start time with the same customer
     */
    public static void timeEqualsOverlapAlert(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if(Locale.getDefault().getLanguage().contains("fr")){
            alert.setContentText("Votre heure de début ou de fin correspond à l'heure de début ou de fin d'un autre rendez-vous pour ce client à cette date.");
        }
        else {
            alert.setContentText("Your start or end time equals the start or end time of another appointment on this date.");
        }
        alert.showAndWait();
    }

    /** called when an appointment is deleted
     * @param a appointment object
     */
    public static void appointmentCanceled(Appointment a){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Appointment number " + a.getAppointmentID() + " of type " + a.getTitle() + " canceled");
        alert.showAndWait();
    }
}
