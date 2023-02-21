package Model;

/**
 * customer class for the customer table
 */
public class Customer {
    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    /** constructor
     * @param ID customer id
     * @param name customer name
     * @param address customer address
     * @param postalCode customer postal code
     * @param phone customer phone number
     * @param divisionId customer state/province id
     */
    public Customer(int ID, String name, String address, String postalCode, String phone, int divisionId) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return  "["+ID+"] "+name;
    }

    /** get customer id
     * @return customer id
     */
    public int getID() {
        return ID;
    }

    /** set customer id
     * @param ID customer id
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /** customer name
     * @return string name
     */
    public String getName() {
        return name;
    }


    /** get address
     * @return address string
     */
    public String getAddress() {
        return address;
    }


    /** get postal code
     * @return string postal code
     */
    public String getPostalCode() {
        return postalCode;
    }


    /** get customer phone number
     * @return customer phone
     */
    public String getPhone() {
        return phone;
    }


    /** get division id
     * @return state/province id
     */
    public int getDivisionId() {
        return divisionId;
    }


}
