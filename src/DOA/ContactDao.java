package DOA;

import Model.Contact;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Contact Dao design pattern as per tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 */
public interface ContactDao {
    /** Read method is the only CRUD method required for this object
     * @return
     * @throws SQLException
     */
    ObservableList<Contact> getAllContacts() throws SQLException;


}
