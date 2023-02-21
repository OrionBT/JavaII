package Controller;

import DOA.AppointmentDao;
import DOA.AppointmentDaoImpl;
import DOA.DoaLists;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import Model.User;
import Utils.AlertMessage;
import Utils.SwitchView;
import Utils.TimeZoneClass;
import Utils.Validation;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.*;
import java.util.ResourceBundle;

/**
 * This class is the controller for the modify Appointment view, which updates and deletes appointments from the program/database
 */
public class ModifyAppointmentController implements Initializable {
    private int endHour;
    private int endMinute;
    private String EndTimeString;
    private int startHour;
    private int startMinute;
    private String StartTimeString;
    @FXML
    private Text modifyAppointmentTitleTxt;

    @FXML
    private TextField appointmentIDTxtField;

    @FXML
    private Text appointmentIDTxt;

    @FXML
    private TextField titleTxtField;

    @FXML
    private Text titleTxt;

    @FXML
    private TextArea descriptionTxtArea;

    @FXML
    private Text descriptionTxt;

    @FXML
    private TextField locationTxtField;

    @FXML
    private Text locationTxt;

    @FXML
    private TextField typeTxtFIeld;

    @FXML
    private Text typeTxt;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private Text contactTxt;

    @FXML
    private ComboBox<Customer> customerCombox;

    @FXML
    private Text customerTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text dateLabel;

    @FXML
    private Text startTimeLabel;

    @FXML
    private Text endTimeLabel;

    @FXML
    private Slider startHourSlider;

    @FXML
    private Slider startMinSlider;

    @FXML
    private Slider endHourSlider;

    @FXML
    private Slider endMinSlider;

    @FXML
    private Text startHourTxt;

    @FXML
    private Text startMinTxt;

    @FXML
    private Text endHourTxt;

    @FXML
    private Text endMinTxt;

    @FXML
    private Text startTimeTxt;

    @FXML
    private Text endTimeTxt;

    @FXML
    private Text startTimeZoneLabel;

    @FXML
    private Text endTimeZoneLabel;

    @FXML
    private ListView<Appointment> appointmentList;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private ComboBox<User> userComboBox;

    /** switches view back to main view
     * @param event on cancel button clicked
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
SwitchView.mainPage(event);
    }

    /** delete appointment from the db and program
     * @param event on delete button clicked
     * @throws SQLException
     */
    @FXML
    void onActionDelete(ActionEvent event) throws SQLException {
        Appointment appointment = appointmentList.getSelectionModel().getSelectedItem();
    AppointmentDao appointmentDao = new AppointmentDaoImpl();
    AlertMessage.appointmentCanceled(appointment);
        DoaLists.getAppointmentsList().remove(appointment);
        appointmentDao.deleteAppointment(appointment);
    }


    /** sets the hour of the end time of the appointment via a slider from 00 to 23
     * @param event dragging the end hour slider
     */
    @FXML
    void onDragEndHour(MouseEvent event) {
        endHour = (int) endHourSlider.getValue();
        EndTimeString = String.format("%02d:%02d:00", endHour, endMinute);
        endTimeTxt.setText(EndTimeString);
    }

    /** sets the minute of the end time of the appointment via a slider from 00 to 59
     * @param event dragging the end minute slider
     */
    @FXML
    void onDragEndMin(MouseEvent event) {
        endMinute = (int) endMinSlider.getValue();
        EndTimeString = String.format("%02d:%02d:00", endHour, endMinute);
        endTimeTxt.setText(EndTimeString);
    }

    /** sets the hour of the start time of the appointment via a slider from 00 to 23
     * @param event dragging the start hour slider
     */
    @FXML
    void onDragStartHour(MouseEvent event) {
        startHour = (int) startHourSlider.getValue();
        StartTimeString = String.format("%02d:%02d:00", startHour, startMinute);
        startTimeTxt.setText(StartTimeString);
    }

    /** sets the minute of the start time of the appointment via a slider from 00 to 59
     * @param event dragging the start minute slider
     */
    @FXML
    void onDragStartMin(MouseEvent event) {
        startMinute = (int) startMinSlider.getValue();
        StartTimeString = String.format("%02d:%02d:00", startHour, startMinute);
        startTimeTxt.setText(StartTimeString);
    }

    /** sets all the values in the text fields, combo boxes, sliders, texts, and text areas to the proper data of the appointment selected in the list view
     * <p>the <b>LAMBDA</b> in the stream for the contact object makes so that I don't have to write and enhanced for loop to match the contact name string in the
     * appointment object selected to the contactName string in an object in the appointment Doalist.getContactsList</p>
     * <p> the <b>LAMBDA</b> in the stream for the customer object makes it so that I don't have to write and enhanced for loop to match the customerInt in the
     * appointment object selected to the customerInt in the DoaList.getCustomersList()</p>
     * <p>The <b>LAMBDA</b> in the stream for the user object makes it so that I don't have to write a for loop to match the userID integer in the appointment
     * object with the correct user object in the DoaList.getAllUsersList</p>
     * @param event on select appointment
     */
    @FXML
    void onSelectAppointment(MouseEvent event) {
        Appointment a = appointmentList.getSelectionModel().getSelectedItem();
        String contactString = a.getContact();
        Contact contact = DoaLists.getContactsList().stream().filter(c -> c.getContactName().equals(contactString)).findFirst().get();
        int customerInt = a.getCustomerID();
        Customer customer = DoaLists.getCustomersList().stream().filter(i -> i.getID() == customerInt).findFirst().get();
        appointmentIDTxtField.setText(String.valueOf(a.getAppointmentID()));
        titleTxtField.setText(a.getTitle());
        descriptionTxtArea.setText(a.getDescription());
        locationTxtField.setText(a.getLocation());
        contactComboBox.setValue(contact);
        typeTxtFIeld.setText(a.getType());
        datePicker.setValue(a.getDate());
        startHourSlider.setValue(a.getStartTime().getHour());
        startMinSlider.setValue(a.getStartTime().getMinute());
        startTimeTxt.setText(a.getStartTime() +":00");
        StartTimeString = a.getStartTime() + ":00";
        customerCombox.setValue(customer);
        //endDatePicker.setValue(a.getEndDate());
        endHourSlider.setValue(a.getEndTime().getHour());
        endMinSlider.setValue(a.getEndTime().getMinute());
        endTimeTxt.setText(a.getEndTime() +":00");
        EndTimeString = a.getEndTime() + ":00";
        User user = DoaLists.getAllUsersList().stream().filter(u -> u.getUserID() == a.getUserID()).findAny().get();
        userComboBox.setValue(user);

    }

    /** creates a new appointment object and sends the object to the database via update command in the appointmentDao
     * <p> clears appointments List in DoaList and refreshes it with the new data from the db</p>
     * @param event clicking the save button
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event)  throws SQLException, IOException{
        LocalDate date = datePicker.getValue();
        LocalTime startTime = LocalTime.parse(StartTimeString);
        LocalTime endTime = LocalTime.parse(EndTimeString);
        ZoneId localZoneID = ZoneId.systemDefault();
        ZoneId nycZoneID = ZoneId.of("America/New_York");
        ZonedDateTime localStartZDT = ZonedDateTime.of(date, startTime, localZoneID);
        ZonedDateTime localEndZDT = ZonedDateTime.of(date, endTime, localZoneID);
        Instant localStartToUTC = localStartZDT.toInstant();
        Instant localEndToUTC = localEndZDT.toInstant();
            LocalDateTime startESTDateTime = localStartToUTC.atZone(nycZoneID).toLocalDateTime();
            LocalDateTime endESTDateTime = localEndToUTC.atZone(nycZoneID).toLocalDateTime();



            try {
                int ID = Integer.parseInt(appointmentIDTxtField.getText());
                String title = titleTxtField.getText();
                String description = descriptionTxtArea.getText();
                String location = locationTxtField.getText();
                String type = typeTxtFIeld.getText();
                String contact = contactComboBox.getSelectionModel().getSelectedItem().toString();
                int customer = customerCombox.getSelectionModel().getSelectedItem().getID();
                int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();
                Appointment appointment = new Appointment(ID, title, description, location, type, startTime, date, endTime, contact, customer, userID);
                if(Validation.overLappingAppointment(appointment) && Validation.datesCompatible(startESTDateTime, endESTDateTime) && Validation.timeIntervalCheck(startESTDateTime, endESTDateTime)){
                    DoaLists.getAppointmentsList().clear();
                    AppointmentDao ad = new AppointmentDaoImpl();
                    if (ad.updateAppointment(appointment)) {
                        DoaLists.getAllUsersList().clear();
                        DoaLists.getContactsList().clear();
                        ad.getAllAppointments();
                        SwitchView.mainPage(event);
                    }
                }
            }
            catch (NullPointerException e) {
                AlertMessage.fillTextFields();
            }
    }

    /** populates appointmentList, contactComboBox, customerCombo box, and sets the time zone labels to the local time zone
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appointmentList.setItems(DoaLists.getAppointmentsList());
        contactComboBox.setItems(DoaLists.getContactsList());
        customerCombox.setItems(DoaLists.getCustomersList());
        startTimeZoneLabel.setText(String.valueOf(TimeZoneClass.getLocalZoneID()));
        endTimeZoneLabel.setText(String.valueOf(TimeZoneClass.getLocalZoneID()));
        userComboBox.setItems(DoaLists.getAllUsersList());
    }
}
