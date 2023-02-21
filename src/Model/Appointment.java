package Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Appointment Class
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate date;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private String contact;

    /** constructor for the appointment class
     * @param appointmentID int appointment id
     * @param title string appointment title
     * @param description string appointment description
     * @param location string appointment location
     * @param type string appointment type
     * @param startTime localTime appointment start time
     * @param date localDate appointment date
     * @param endTime localTime appointment endTime
     * @param contact string appointment contact
     * @param customerID int customer id
     * @param userID int user id
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalTime startTime, LocalDate date, LocalTime endTime, String contact, int customerID, int userID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.date = date;
        this.endTime = endTime;

        this.contact = contact;
        this.customerID = customerID;
        this.userID = userID;
    }

    /** gets appointment id
     * @return appointmentID int
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /** gets title
     * @return title string
     */
    public String getTitle() {
        return title;
    }

    /** sets title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** get description
     * @return description string
     */
    public String getDescription() {
        return description;
    }

    /** sets description
     * @param description string description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** get location
     * @return location string
     */
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    /** get type
     * @return type string
     */
    public String getType() {
        return type;
    }

    /** set type
     * @param type set type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** get start time
     * @return startTime
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /** set start time
     * @param startTime local Time start time
     */
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    /** get date
     * @return LocalDate date
     */
    public LocalDate getDate() {
        return date;
    }

    /** set local date
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /** get endTime
     * @return LocalTime endTime
     */
    public LocalTime getEndTime() {
        return endTime;
    }


    /** get customer ID
     * @return int customerID
     */
    public int getCustomerID() {
        return customerID;
    }

    /** set customer ID
     * @param customerID int
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /** get user id
     * @return userID int
     */
    public int getUserID() {
        return userID;
    }

    /**set user id
     * @param userID int
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**get contact
     * @return  contact string
     */
    public String getContact() {
        return contact;
    }

    /**set contact
     * @param contact
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return string of appointment
     */
    @Override
    public String toString() {
        return
                appointmentID +
                " " + title;
    }
}
