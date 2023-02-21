package Controller;

import DOA.*;
import Model.Appointment;
import Model.Contact;
import Model.User;
import Utils.AlertMessage;
import Utils.SwitchView;
import Utils.Validation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * main view controller
 */
public class Main_Controller implements Initializable {

    private ObservableList<Appointment> appointments = DoaLists.getAppointmentsList();
    @FXML
    private Text titleText;

    @FXML
    private Text MainMenuTxt;

    @FXML
    private Button addCustomerBtn;

    @FXML
    private Button modifyCustomerBtn;


    @FXML
    private Button addAppointmentBtn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button reportsBtn;

    @FXML
    private Text appointmentSchTxt;

    @FXML
    private ToggleGroup viewByTG;

    @FXML
    private RadioButton weekRadioBtn;

    @FXML
    private RadioButton allRadioBtn;

    @FXML
    private RadioButton monthRadioBtn;

    @FXML
    private DatePicker calendarSelection;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, Integer> appointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    @FXML
    private TableColumn<Appointment, Integer> locationCol;

    @FXML
    private TableColumn<Appointment, String> contactCol;

    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, LocalDate> startDateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> startTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDate> endDateCol;

    @FXML
    private TableColumn<Appointment, LocalTime> endTimeCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;


    @FXML
    private Button logoutBtn;


    /** opens addAppointment View and populates the contact and customer lists by getting the data from the data base via contactDao and customerDoa
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException, SQLException {
        if(DoaLists.getContactsList().isEmpty()){
            ContactDao contactDao = new ContactDaoImpl();
            contactDao.getAllContacts();
        }
        if(DoaLists.getCustomersList().isEmpty()){
            CustomerDao customerDao = new CustomerDaoImp();
            customerDao.getAllCustomers();
        }
        if(DoaLists.getAllUsersList().isEmpty()){
            UsersDao usersDao = new UsersDaoImpl();
            usersDao.getAllUsers();
        }
        SwitchView.addAppointmentView(event);

    }

    /** opens addAddCustomer View and populates the countries and firstLevelDivisions lists by getting the data from the data base via CountryDao and Doa
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException, SQLException {
        if(DoaLists.getCountriesList().isEmpty()){
            CountryDao countryDao = new CountryDaoImp();
            countryDao.getAllCountries();
        }
        if (DoaLists.getFirstLevelDivisionsList().isEmpty()) {
            FirstLevelDivisionDao firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
            firstLevelDivisionDao.getAllFirstLevelDivisions();
        }
        SwitchView.addCustomerView(event);
    }

    /** Logs out of application back to the login view, closes database connection
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionLogout(ActionEvent event) throws IOException {
        DoaLists.getUsersList().clear();
        SwitchView.loginPage(event);
        DoaLists.getAppointmentsList().clear();
        DBConnection.closedConnection();
    }

    /** changes views to modifyCustomer view. Populates customer, firstLevelDivision, and Countries list via their respective Doa classes
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException, SQLException {
        if (DoaLists.getCustomersList().isEmpty()){
            CustomerDao customerDao = new CustomerDaoImp();
                try {
                    customerDao.getAllCustomers();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
        }
        if (DoaLists.getFirstLevelDivisionsList().isEmpty()){
            FirstLevelDivisionDao firstLevelDivisionDao = new FirstLevelDivisionDaoImpl();
            firstLevelDivisionDao.getAllFirstLevelDivisions();
        }
        if (DoaLists.getCountriesList().isEmpty()){
            CountryDao countryDao = new CountryDaoImp();
            countryDao.getAllCountries();
        }
        SwitchView.modifyCustomerView(event);
    }

    /** changes views to modifyAppointment view. Populates customer, and contact lists via their respective Doa classes
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML void onActionModifyAppointment(ActionEvent event) throws IOException, SQLException {
        if(DoaLists.getCustomersList().isEmpty()){
            CustomerDao customerDao = new CustomerDaoImp();
            customerDao.getAllCustomers();
        }
        if(DoaLists.getContactsList().isEmpty()){
            ContactDao contactDao = new ContactDaoImpl();
            contactDao.getAllContacts();
        }
        if(DoaLists.getAllUsersList().isEmpty()){
            UsersDao usersDao = new UsersDaoImpl();
            usersDao.getAllUsers();
        }
        SwitchView.modifyAppointmentView(event);
    }

    /** when the view all radio button is clicked all of the appointments in the db can be seen in the table view in main view
     * @param event
     */
    @FXML
    void onActionViewAll(ActionEvent event) {
        appointmentTableView.setItems(appointments);
    }

    /** When a date is selected with the date picker, you can see all the appointments which have the same month and year value as that selected date
     * @param event
     */
    @FXML
    void onActionViewByMonth(ActionEvent event) {
        try {
            ObservableList<Appointment> filterMonth = FXCollections.observableArrayList();
            int month = calendarSelection.getValue().getMonthValue();
            int year = calendarSelection.getValue().getYear();
            for (Appointment a : DoaLists.getAppointmentsList()) {
                if (a.getDate().getMonthValue() == month && a.getDate().getYear() == year)
                    filterMonth.add(a);
            }
            appointmentTableView.setItems(filterMonth);
        }
        catch (NullPointerException e){
            AlertMessage.selectDate();
        }
    }

    /** When a date is selected with the date picker, you can see all the appointments which happen during the same week as the date selected.
     * @param event
     */
    @FXML
    void onActionViewByWeek(ActionEvent event) {
        try {
            ObservableList<Appointment> filterWeek = FXCollections.observableArrayList();
            int year = calendarSelection.getValue().getYear();
            int month = calendarSelection.getValue().getMonthValue();
            WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);
            TemporalField weekOfMonth = weekFields.weekOfMonth();
            LocalDate day = calendarSelection.getValue();
            int wom = day.get(weekOfMonth);
            for (Appointment a : DoaLists.getAppointmentsList()) {
                if (a.getDate().getYear() == year && a.getDate().get(weekOfMonth) == wom && a.getDate().getMonthValue() == month) {
                    filterWeek.add(a);
                }
            }
            appointmentTableView.setItems(filterWeek);
        }
        catch (NullPointerException e){
            AlertMessage.selectDate();
        }

    }

    /** switches view to Reports View and updates all Users list to be used in the view
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void onActionViewReports(ActionEvent event) throws IOException, SQLException {
        DoaLists.getAllUsersList().clear();
        UsersDao usersDao = new UsersDaoImpl();
        usersDao.getAllUsers();
        SwitchView.reportsView(event);
    }

    /** sets the values in the appointmentTableView and it's columns
     * @param url
     * @param resourceBundle sets all the text to french if Locale is french (before I knew that I was only supposed to do that to the login page)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        appointmentTableView.setItems(appointments);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        resourceBundle = ResourceBundle.getBundle("Languages_fr");
        if (Locale.getDefault().getLanguage().contains("fr")){
            //Table
            appointmentIDCol.setText(resourceBundle.getString("AppointmentID"));
            titleCol.setText(resourceBundle.getString("Title"));
            descriptionCol.setText(resourceBundle.getString("Description"));
            locationCol.setText(resourceBundle.getString("Location"));
            contactCol.setText(resourceBundle.getString("Contact"));
            typeCol.setText(resourceBundle.getString("Type"));
            startDateCol.setText(resourceBundle.getString("StartDate"));
            startTimeCol.setText(resourceBundle.getString("StartTime"));
            endTimeCol.setText(resourceBundle.getString("EndTime"));
            endDateCol.setText(resourceBundle.getString("EndDate"));
            customerIDCol.setText(resourceBundle.getString("CustomerID"));
            //everything else
            logoutBtn.setText(resourceBundle.getString("Logout"));
            titleText.setText(resourceBundle.getString("AppointmentManagementSystem"));
            MainMenuTxt.setText(resourceBundle.getString("MainMenu"));
            addCustomerBtn.setText(resourceBundle.getString("AddCustomer"));
            modifyCustomerBtn.setText(resourceBundle.getString("ModifyCustomer"));
            addAppointmentBtn.setText(resourceBundle.getString("AddAppointment"));
            modifyAppointmentBtn.setText(resourceBundle.getString("ModifyAppointment"));
            reportsBtn.setText(resourceBundle.getString("Reports"));
            appointmentSchTxt.setText(resourceBundle.getString("AppointmentSchedule"));
            weekRadioBtn.setText(resourceBundle.getString("ViewbyWeek"));
            monthRadioBtn.setText(resourceBundle.getString("ViewbyMonth"));
            allRadioBtn.setText(resourceBundle.getString("ViewAll"));

        }

    }
}
