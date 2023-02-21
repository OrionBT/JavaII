package Utils;

import DOA.DoaLists;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;

/**
 * Validation class to keep the code organized
 */
public class Validation {
    /** Checks to see if a customer has an appointment and returns true if customer has an appointment
     * @param customer object being checked
     * @return true if customer has an appointment
     */
    public static boolean customerHasAppointment(Customer customer){
        for (Appointment a : DoaLists.getAppointmentsList()){
            if(a.getCustomerID() == customer.getID()){
                return true;
            }
        }
        return false;
    }

    /**  checks to see if the start time is before the end time
     * @param startEstTime local start time converted to est
     * @param endEstTime local end time converted to est
     * @return true if the start time is before the end time
     */
    public static boolean datesCompatible(LocalDateTime startEstTime, LocalDateTime endEstTime){
       if (startEstTime.isBefore(endEstTime))
       {return true;}
       else
       {AlertMessage.incompatibleDateTime();
       return false;}
    }

    /** checks to see if the user has an appointment within 15 minutes of logging in
     * <p><b>LAMBDA</b> expression in the stream used to prevent me from needing to use a for loop to add appointments relevant to that user to the local Observable List</p>
     * @param user user object being passed in to check
     */
    public static void appointmentAlert(User user){
        ObservableList<Appointment> appList = FXCollections.observableArrayList();
        DoaLists.getAppointmentsList().stream().filter(appointment -> appointment.getUserID() == user.getUserID()).forEach(appList::addAll);
        try {
            Appointment a = appList.stream().filter(appointment -> appointment.getDate().equals(LocalDate.now()) && LocalTime.now().isBefore(appointment.getStartTime())
                    && LocalTime.now().plusMinutes(15).isAfter(appointment.getStartTime())).findAny().get();
            AlertMessage.fifteenMinuteAlert(a);
        }
        catch (NoSuchElementException e){
            AlertMessage.noFifteenMinuteAlert();
        }

    }

    /** checks if the dates scheduled in the addAppointmentView or modifyAppointmentView are between the allotted business hours of 8 am and 10 pm est
     * @param startEstTime local start time converted to est
     * @param endEstTime local end time converted to est
     * @return
     */
    public static boolean timeIntervalCheck(LocalDateTime startEstTime, LocalDateTime endEstTime){

        if (
                startEstTime.isBefore(endEstTime) &&
                startEstTime.toLocalTime().isAfter(LocalTime.of(7, 59)) &&
                endEstTime.toLocalTime().isBefore(LocalTime.of(22, 1)) &&
                startEstTime.toLocalDate().equals(endEstTime.toLocalDate())
        )
        { return true;}
        else
        { AlertMessage.estCheckAlert();
        return false;}

    }

    /** Checks all appointments that the customer has currently in the DB to see if the appointment being scheduled overlaps with existing appointments
     * @param a Appointment object being checked for over lap
     * @return false if there is and over lap, true if there is not an overlap
     */
    public static boolean overLappingAppointment(Appointment a){
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        LocalTime aStartTime = a.getStartTime();
        LocalTime aEndTime = a.getEndTime();
        //app.getCustomerID() == a.getCustomerID() &&
        for (Appointment app : DoaLists.getAppointmentsList()){
            if (app.getDate().equals(a.getDate()) && app.getAppointmentID() != a.getAppointmentID()) {
                appointments.add(app);
            }
        }
        for (Appointment appointment : appointments) {
            LocalTime appListStartTime = appointment.getStartTime();
            LocalTime appListEndTime = appointment.getEndTime();

              if (aStartTime.isAfter(appListStartTime) && aEndTime.isBefore(appListEndTime)) {
                AlertMessage.timeOverLapAlert();
                  System.out.println("1");
                return false;
            }
              else if (aStartTime.isBefore(appListStartTime) && aEndTime.isAfter(appListEndTime)){
                  AlertMessage.timeOverLapAlert();
                  System.out.println("2");
                  return false;
              }
              else if (aEndTime.isAfter(appListStartTime) && aEndTime.isBefore(appListEndTime)){
                  AlertMessage.timeOverLapAlert();
                  System.out.println("3");
                  return false;
              }
              else if (aStartTime.isAfter(appListStartTime) && aStartTime.isBefore(appListEndTime)){
                  AlertMessage.timeOverLapAlert();
                  System.out.println("4");
                  return false;
              }
              else if (aStartTime.equals(appListStartTime) || aEndTime.equals(appListEndTime)){
                  AlertMessage.timeEqualsOverlapAlert();
                  System.out.println("5");
                  return false;
              }
              else if (aStartTime.equals(appListEndTime) || aEndTime.equals(appListStartTime)){
                  AlertMessage.timeEqualsOverlapAlert();
                  System.out.println("6");
                  return false;
              }
                else {
                  return true;
              }
        }
        return true;
    }

}
