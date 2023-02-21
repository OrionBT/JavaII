package DOA;

import Model.User;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * Data Access Object for users for CRUD operations
 */
public interface UsersDao {


    /**
     * @return Observable List of users
     * @throws SQLException
     */
         ObservableList<User> getAllUsers() throws SQLException;

    /**
     * @param username String of the username to login
     * @param password string of the password to login
     * @return whether the username and password exist
     * @throws SQLException
     */
         User login(String username, String password) throws SQLException;
}

