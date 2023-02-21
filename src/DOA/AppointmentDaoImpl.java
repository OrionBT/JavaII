package DOA;

import Model.Appointment;
import Model.Contact;
import Model.User;
import Utils.TimeZoneClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * AppointmentDao implementation class as per the design pattern on tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 * <p>Implements CRUD methods for the appointment table</p>
 */
public class AppointmentDaoImpl implements AppointmentDao{

    /**
     * constructor for appointmentDaoImpl
     */
    public AppointmentDaoImpl() {
    }

    /**The <b>LAMBDA</b> in the stream for the integer contact id was made so that I could get the contactID integer without making a for loop.
     * @param a appointment object being added to the database, the start and end time are converted from local timezone to UTC here
     * @return true if appointment is successfully added
     * @throws SQLException
     */
    @Override
    public boolean addAppointment(Appointment a) throws SQLException {
        LocalDate localDate = a.getDate();
        LocalTime localStartTime = a.getStartTime();
        LocalTime localEndTime = a.getEndTime();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localStartZDT = ZonedDateTime.of(localDate, localStartTime, localZoneId); // tells program what timezone user is in
        ZonedDateTime localEndZDT = ZonedDateTime.of(localDate, localEndTime, localZoneId); //
        Instant localStartToUTCInstant = localStartZDT.toInstant(); // converts input to UTC
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant localEndToUTCInstant = localEndZDT.toInstant();
            String startDateTime = localStartToUTCInstant.atZone(ZoneId.of("UTC")).format(formatter);
            String endDateTime = localEndToUTCInstant.atZone(ZoneId.of("UTC")).format(formatter);
        String user = DoaLists.getUsersList().stream().filter(User::isActive).findFirst().get().getUserName();
        int contactID = DoaLists.getContactsList().stream().filter(c -> c.getContactName().equals(a.getContact())).findFirst().get().getContactID();

        String sqlStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, [End], Created_By, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                + "Values ('" + a.getTitle() + "', '" + a.getDescription() + "', '" + a.getLocation() + "', '" + a.getType() + "', '" + startDateTime
                                + "', '" + endDateTime + "', '" + user + "', '" + user + "', '" + a.getCustomerID() + "', '" + a.getUserID() + "', '" + contactID + "')";
        System.out.println(sqlStatement);
        return DBQuery.insertStatement(sqlStatement);
    }

    /** populates the appointment list in the DoaLists class with all the appointments in the db
     * <p>The <b>Lambda</b> in the String named "contact" stream is used to avoid making a large for loop to find the proper contact name which matches to
     * this appointment.</p>
     * @return an ObservableList of appointment objects
     * @throws SQLException
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException {


        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        ContactDao contactDao = new ContactDaoImpl();
        for (Contact c : contactDao.getAllContacts()) {
            contacts.add(c);
        }
        ResultSet rs = DBQuery.getResultSet("SELECT * FROM appointments");
        while (rs.next()) {
            int id = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            ZonedDateTime startZDT_UTC = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.of("UTC"));
                ZonedDateTime startZDT = startZDT_UTC.withZoneSameInstant(TimeZoneClass.getLocalZoneID());
                LocalDate date = startZDT.toLocalDate();
                LocalTime startTime = startZDT.toLocalTime();

           // LocalDate date = rs.getTimestamp("Start").toLocalDateTime().toLocalDate();
           // LocalTime startTime = rs.getTimestamp("Start").toLocalDateTime().toLocalTime();
            ZonedDateTime endZDT_UTC = ZonedDateTime.of(rs.getTimestamp("End").toLocalDateTime(), ZoneId.of("UTC"));
                ZonedDateTime endZDT = endZDT_UTC.withZoneSameInstant(TimeZoneClass.getLocalZoneID());
                LocalTime endTime = endZDT.toLocalTime();
            //LocalTime endTime = rs.getTimestamp("End").toLocalDateTime().toLocalTime();
            int contactID = rs.getInt("Contact_ID");
            int customerID = rs.getInt("Customer_ID");
            int userID = rs.getInt("User_ID");
            String contact = contacts.stream().filter(con -> con.getContactID() == contactID).findFirst().get().getContactName();
//            for (Contact c : contacts){
//                if (contactID == c.getContactID()){
//                    String contact = c.getContactName();
//                }
//            }
            Appointment appointment = new Appointment(id, title, description, location, type, startTime, date, endTime, contact, customerID, userID );
            DoaLists.addAppointment(appointment);


        }
            return DoaLists.getAppointmentsList();

    }

    /** The <b>LAMBDA</b> in the stream for the integer contact id was made so that I could get the contactID integer without making a for loop.
     * @param a the appointment object being updated to the data base, the time is converted from local time zone to UTC here
     * @return true if update was successful
     * @throws SQLException
     */
    @Override
    public boolean updateAppointment(Appointment a) throws SQLException {
        LocalDate localDate = a.getDate();
        LocalTime localStartTime = a.getStartTime();
        LocalTime localEndTime = a.getEndTime();
        ZoneId localZoneId = ZoneId.systemDefault();
        ZonedDateTime localStartZDT = ZonedDateTime.of(localDate, localStartTime, localZoneId); // tells program what timezone user is in
        ZonedDateTime localEndZDT = ZonedDateTime.of(localDate, localEndTime, localZoneId); //
        Instant localStartToUTCInstant = localStartZDT.toInstant(); // converts input to UTC
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Instant localEndToUTCInstant = localEndZDT.toInstant();
            String startDateTime = localStartToUTCInstant.atZone(ZoneId.of("UTC")).format(formatter);
            String endDateTime = localEndToUTCInstant.atZone(ZoneId.of("UTC")).format(formatter);
        LocalDateTime utc = LocalDateTime.now(ZoneOffset.UTC);
        String utcString = String.format("%d-%d-%d %d:%d:%d", utc.getYear(), utc.getMonthValue(), utc.getDayOfMonth(), utc.getHour(), utc.getMinute(), utc.getSecond());
        int contactID = DoaLists.getContactsList().stream().filter(c -> c.getContactName().equals(a.getContact())).findFirst().get().getContactID();
        String user = DoaLists.getUsersList().stream().filter(User::isActive).findFirst().get().getUserName();

        //Timestamp startTimeStamp = Timestamp.valueOf(startDateTime);
        String sqlStatement = "UPDATE appointments " +
                "SET Title = '" + a.getTitle() + "', Description = '" + a.getDescription() + "', Location = '" + a.getLocation() +
                "', Type = '" + a.getType() + "', Start = '" + startDateTime + "', [End] = '" + endDateTime + "', Last_Updated_By = '" + user +
                "', Customer_ID = '" + a.getCustomerID() + "', User_ID = '" + a.getUserID() + "', Contact_ID = '" + contactID + "' WHERE Appointment_ID = '" + a.getAppointmentID() + "'";
        System.out.println(sqlStatement);
        return DBQuery.insertStatement(sqlStatement);
    }

    /** deletes appointment
     * @param appointment object being deleted from the database
     * @throws SQLException
     */
    @Override
    public void deleteAppointment(Appointment appointment) throws SQLException {
        int appointmentID = appointment.getAppointmentID();
        String sqlStatement = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentID;
        boolean b = DBQuery.insertStatement(sqlStatement);
    }
}
