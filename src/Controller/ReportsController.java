package Controller;

import DOA.DoaLists;
import Model.Appointment;
import Model.User;
import Utils.SwitchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Reports controller
 */
public class ReportsController implements Initializable {

    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Text byTypeTxt;

    @FXML
    private ComboBox<Month> monthComboBox;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDate> endDateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button getAppointmentNoBtn;


    @FXML
    private ComboBox<User> userComboBox;

    @FXML
    private TableView<Appointment> appointmentTable1;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol1;

    @FXML
    private TableColumn<Appointment, String> titleCol1;

    @FXML
    private TableColumn<Appointment, String> typeCol1;

    @FXML
    private TableColumn<Appointment, String> descriptionCol1;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol1;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol1;

    @FXML
    private TableColumn<Appointment, LocalDate> endDateCol1;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol1;

    @FXML
    private TableColumn<Appointment, Integer> customerIdCol1;

    /** After selecting a contact from the combo box the appointments for that contact populates in the appointmentTable
     *
     */
    @FXML
    void onActionAppointmentByContact() {
        appointments.clear();
        for (Appointment appointment : DoaLists.getAppointmentsList()){
            if(contactComboBox.getValue().equals(appointment.getContact())){
                appointments.add(appointment);
            }
        }
        appointmentTable.setItems(appointments);
        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** After selecting a user from the combo box the appointments for that user populates in the appointmentTable1 table view
     *
     * @throws NullPointerException This method was getting a NullPointerException when the view loaded, so I threw it.
     */
    @FXML
    void onActionAppointmentByUser() throws NullPointerException{
        userAppointments.clear();
        int userId = userComboBox.getValue().getUserID();
        for (Appointment appointment : DoaLists.getAppointmentsList()) {
            if(userId == appointment.getUserID()){
                userAppointments.add(appointment);
            }
        }
        appointmentTable1.setItems(userAppointments);
        appointmentIdCol1.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol1.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionCol1.setCellValueFactory(new PropertyValueFactory<>("description"));
        startDateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol1.setCellValueFactory(new PropertyValueFactory<>("date"));
        endTimeCol1.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIdCol1.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /** counts how many appointments there are by type and month
     * <p>the <b>LAMBDA</b> in the stream makes it so that I don't have to write a for loop to add the appointments to the local list</p>
     *
     */
    @FXML
    void onActionGetMonthCount() {
        ObservableList<Appointment>app = FXCollections.observableArrayList();
        String type = typeComboBox.getValue();
        Month month = monthComboBox.getValue();
        int monthInt = month.getValue();
        DoaLists.getAppointmentsList().stream().filter(appointment -> appointment.getType().equals(type) && appointment.getDate().getMonthValue() == monthInt).forEach(app::add);
        byTypeTxt.setText(String.valueOf(app.size()));
    }


    /** switches view to main view when cancel button is pushed
     * @param event on cancel button clicked
     * @throws IOException required for switching views
     */
    @FXML
    void onActionToHomeView(ActionEvent event) throws IOException {
        SwitchView.mainPage(event);
    }

    /** initializes monthList with each month, typeList with each time, and populates the typeComboBox, monthComboBox, contactComboBox, userComboBox with the appropriate data.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> typeList = FXCollections.observableArrayList();
        ObservableList<Month> monthList = FXCollections.observableArrayList(Month.JANUARY, Month.FEBRUARY, Month.MARCH, Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST, Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER);
        ObservableList<String> contactList = FXCollections.observableArrayList();
        for (Appointment a : DoaLists.getAppointmentsList()){
            String type = a.getType();
            if(!typeList.contains(type)) {
                typeList.add(type);
            }
        }
        for (Appointment a : DoaLists.getAppointmentsList()){
            String contact = a.getContact();
            if(!contactList.contains(contact)) {
                contactList.add(contact);
            }
        }
        typeComboBox.setItems(typeList);
        monthComboBox.setItems(monthList);
        contactComboBox.setItems(contactList);
        userComboBox.setItems(DoaLists.getAllUsersList());
    }
}
