package Model;

/**
 * Contact class of the contact table
 */
public class Contact {
    private int contactID;
    private String contactName;

    /** contact constructor
     * @param contactID integer contact id
     * @param contactName string contact name
     */
    public Contact(int contactID, String contactName) {
        this.contactID = contactID;
        this.contactName = contactName;

    }

    /** get contactid
     * @return int contactID
     */
    public int getContactID() {
        return contactID;
    }

    /** set contactId
     * @param contactID integer
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /** get contactName
     * @return string contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName string contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }


    /** to string method to display contact in combo boxes
     * @return string contactName
     */
    @Override
    public String toString() {
        return contactName;

    }
}
