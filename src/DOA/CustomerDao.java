package DOA;

import Model.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * customer data access object interface as per Doa design pattern for CRUD operations
 */
public interface CustomerDao {
    /** Create method for customer table
     * @param customer
     * @return true if customer added successfully
     * @throws SQLException
     */
    boolean addCustomer(Customer customer) throws SQLException;

    /** Read method for customer table
     * @return list of customers
     * @throws SQLException
     */
    ObservableList<Customer> getAllCustomers() throws SQLException;

    /** Update method for customer table
     * @param customer
     * @return true if update is successful
     * @throws SQLException
     */
    boolean updateCustomer(Customer customer) throws SQLException;

    /** Delete method from customer table
     * @param customer
     * @throws SQLException
     */
    void deleteCustomer(Customer customer) throws SQLException;

}
