package DOA;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Contact Dao implementation as per the design pattern on tutorialspoint.com/design_pattern/data_access_object_pattern.htm
 * <p>Implements CRUD methods for the contact table</p>
 */
public class ContactDaoImpl implements ContactDao{
    /**
     * constructor for ContactDaoImpl
     */
    public ContactDaoImpl() {
    }

    /** populates contact list in DoaList with contacts from the database
     * @return list of contacts
     * @throws SQLException
     */
    @Override
    public ObservableList<Contact> getAllContacts() throws SQLException {
          ResultSet rs = DBQuery.getResultSet("Select * from contacts");
      while (rs.next()) {
          int contactID = rs.getInt("Contact_ID");
          String contactName = rs.getString("Contact_Name");
          Contact contact = new Contact(contactID, contactName);
          DoaLists.addContact(contact);
      }
        return DoaLists.getContactsList();
    }



}
