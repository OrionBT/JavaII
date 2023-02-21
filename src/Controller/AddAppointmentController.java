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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.ResourceBundle;

/**
 * Adds a new Appointment
 */
public class AddAppointmentController implements Initializable {
    private int endHour = 0;
    private int endMinute = 0;
    private String EndTimeString = String.format("%02d:%02d:00", endHour, endMinute);
    private int startHour = 0;
    private int startMinute = 0;
    private String StartTimeString = String.format("%02d:%02d:00", startHour, startMinute);
    @FXML
    private TextField appointmentIDTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextArea descriptionTextArea;

    @FXML
    private TextField locationTextField;

    @FXML
    private Text AppointmenIDLabel;

    @FXML
    private Text TitleLabel;

    @FXML
    private Text descriptionLabel;

    @FXML
    private Text locationLabel;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private Text contactLabel;

    @FXML
    private TextField typeTxtField;

    @FXML
    private Text typeLabel;

    @FXML
    private Text dateTxt;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text startTimeLabel;


    @FXML
    private Text endTimeLabel;

    @FXML
    private Button saveAppointmentBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Text addAppointmentText;

    @FXML
    private Slider startMinSlider;

    @FXML
    private Text startTimeTxt;

    @FXML
    private Text endTimeTxt;

    @FXML
    private Slider startHourSlider;

    @FXML
    private Slider endHourSlider;

    @FXML
    private Slider endMinSlider;

    @FXML
    private Text hourTxt;

    @FXML
    private Text minuteTxt;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private Text customerTxt;

    @FXML
    private Text hourTxt1;

    @FXML
    private Text minuteTxt1;

    @FXML
    private Text startTimeZoneLabel;

    @FXML
    private Text endTimeZoneLabel;

    @FXML
    private ComboBox<User> userComboBox;


    /** switches From add appointment view to main view
     * @param event on button click
     * @throws IOException
     */
    @FXML
    void onActionCancel(ActionEvent event) throws IOException {
        SwitchView.mainPage(event);
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

    /** creates a new appointment object and sends the object to the database
     * <p> clears appointments List in DoaList and refreshes it with the new data from the db</p>
     * @param event clicking the save button
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onActionSave(ActionEvent event) throws SQLException, IOException {
        LocalDate date = datePicker.getValue();
        LocalTime startTime = LocalTime.parse(StartTimeString);
        LocalTime endTime = LocalTime.parse(EndTimeString);
        ZoneId localZoneID = ZoneId.systemDefault();
        ZoneId nycZoneID = ZoneId.of("America/New_York");
        ZonedDateTime localStartZDT = ZonedDateTime.of(date, startTime, localZoneID);
        ZonedDateTime localEndZDT = ZonedDateTime.of(date, endTime, localZoneID);
        Instant localStartToUTC = localStartZDT.toInstant();
        Instant localEndToUTC = localEndZDT.toInstant();
            LocalDateTime startESTDateTime = localStartToUTC.atZone(nycZoneID).toLocalDateTime();//converts Start time to EST to send to validation
            LocalDateTime endESTDateTime = localEndToUTC.atZone(nycZoneID).toLocalDateTime();//converts End time to EST to send to validation
        {
            try {
                String title = titleTextField.getText();
                String description = descriptionTextArea.getText();
                String location = locationTextField.getText();
                String type = typeTxtField.getText();
                String contact = contactComboBox.getSelectionModel().getSelectedItem().toString();
                int customer = customerComboBox.getSelectionModel().getSelectedItem().getID();
                int userID = userComboBox.getSelectionModel().getSelectedItem().getUserID();

                Appointment appointment = new Appointment(0, title, description, location, type, startTime, date, endTime, contact, customer, userID);
                if(Validation.datesCompatible(startESTDateTime, endESTDateTime) && Validation.timeIntervalCheck(startESTDateTime, endESTDateTime) && Validation.overLappingAppointment(appointment)) {
                    DoaLists.getAppointmentsList().clear();
                    AppointmentDao ad = new AppointmentDaoImpl();
                    if (ad.addAppointment(appointment)) {
                        DoaLists.getAllUsersList().clear();
                        DoaLists.getContactsList().clear();
                        ad.getAllAppointments();
                        SwitchView.mainPage(event);
                    }
                }
            } catch (NullPointerException e) {
                            AlertMessage.fillTextFields();
                        }
        }


    }


    /** sets the values in the comb boxes and the time zone labels
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactComboBox.setItems(DoaLists.getContactsList());
        customerComboBox.setItems(DoaLists.getCustomersList());
        startTimeZoneLabel.setText(String.valueOf(TimeZoneClass.getLocalZoneID()));
        endTimeZoneLabel.setText(String.valueOf(TimeZoneClass.getLocalZoneID()));
        Appointment a = DoaLists.getAppointmentsList().get(DoaLists.getAppointmentsList().size()-1);
        int i = a.getAppointmentID() + 1;
        appointmentIDTextField.setText(String.valueOf(i));
        userComboBox.setItems(DoaLists.getAllUsersList());
    }
}
