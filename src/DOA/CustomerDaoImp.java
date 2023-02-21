package DOA;

import Model.Customer;
import Model.User;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

/**
 * Implantation of the Customer Dao design pattern for CRUD operations of the customer table
 */
public class CustomerDaoImp implements CustomerDao{

    /**
     * constructor for CustomerDaoImp
     */
    public CustomerDaoImp() {
    }

    /** implements Create method for customer table
     * @param customer
     * @return true if INSERT statement is successful
     * @throws SQLException
     */
    @Override
    public boolean addCustomer(Customer customer) throws SQLException {
        String user = DoaLists.getUsersList().stream().filter(User::isActive).findFirst().get().getUserName();
        String sqlStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Created_By, Last_Updated_By, Division_ID) " +
                "Values ('" + customer.getName() + "', '" + customer.getAddress() + "', '" + customer.getPostalCode() + "', '" + customer.getPhone() + "', '" + user + "' ,'" + user + "', " + customer.getDivisionId() + ")";
        System.out.println(sqlStatement);
        return DBQuery.insertStatement(sqlStatement);
    }

    /** Implementation of Read method for the customer table, populates customer list in DoaLists with customer objects
     * @return list of customers
     * @throws SQLException
     */
    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException {
        ResultSet rs = DBQuery.getResultSet("SELECT * FROM customers");
        while (rs.next()) {
            int customerID = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhone = rs.getString("Phone");
            int divisionID = rs.getInt("Division_ID");
            DoaLists.addCustomer(new Customer(customerID, customerName, customerAddress, customerPostalCode, customerPhone, divisionID));
            System.out.println(customerName);
        }
        return null;
    }


    /** Update method for customer table
     * @param customer object to update to customer table
     * @return true if update was successful
     * @throws SQLException
     */
    @Override
    public boolean updateCustomer(Customer customer) throws SQLException {
        LocalDateTime utc = LocalDateTime.now(ZoneOffset.UTC);
        String utcString = String.format("%d-%d-%d %d:%d:%d", utc.getYear(), utc.getMonthValue(), utc.getDayOfMonth(), utc.getHour(), utc.getMinute(), utc.getSecond());

        String user = DoaLists.getUsersList().stream().filter(User::isActive).findFirst().get().getUserName();
        String sqlStatement = "UPDATE customers " +
        "SET Customer_Name = '" + customer.getName() + "', Address = '" + customer.getAddress() + "', Postal_Code = '" + customer.getPostalCode() +
        "', Phone = '" + customer.getPhone() + "', Last_Update = '" + utcString +"', Last_Updated_By = '" + user + "', Division_ID = '" + customer.getDivisionId() + "' WHERE Customer_ID = '" + customer.getID() + "'";
        System.out.println(sqlStatement);
        return DBQuery.insertStatement(sqlStatement);
    }

    @Override
    public void deleteCustomer(Customer customer) throws SQLException {
        int customerID = customer.getID();
        String sqlStatement = "DELETE FROM customers WHERE Customer_ID = " + customerID;
        boolean b = DBQuery.insertStatement(sqlStatement);
        if (b == true)
            System.out.println(customer + " deleted");
    }
}
