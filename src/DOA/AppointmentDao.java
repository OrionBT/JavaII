package DOA;

import Model.Appointment;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Appointment Dao interface for CRUD operations as per the design pattern on tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 */
public interface AppointmentDao {
    /** Create method
     * @param appointment
     * @return
     * @throws SQLException
     */
    boolean addAppointment(Appointment appointment) throws SQLException;

    /** Read method
     * @return
     * @throws SQLException
     */
    ObservableList<Appointment> getAllAppointments() throws SQLException;

    /** Update method
     * @param appointment
     * @return
     * @throws SQLException
     */
    boolean updateAppointment(Appointment appointment) throws SQLException;

    /** delete method
     * @param appointment
     * @throws SQLException
     */
    void deleteAppointment(Appointment appointment) throws SQLException;
}
