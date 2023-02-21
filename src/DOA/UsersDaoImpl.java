package DOA;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Implementation of the user data access object for CRUD operations
 */
public class UsersDaoImpl implements UsersDao{


    /**
     * constructor for UsersDaoImpl
     */
    public UsersDaoImpl() {

    }


    /** created for the User appointment report
     * @return
     * @throws SQLException
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLException {
        ResultSet rs = DBQuery.getResultSet("SELECT * FROM users");
       while (rs.next()) {
           int userId = rs.getInt("User_ID");
           String userName = rs.getString("User_Name");
           String password = rs.getString("Password");

           User user = new User(userId, userName, password, false);
           DoaLists.addAllUser(user);
       }
        return DoaLists.getAllUsersList();
    }


    /** used at login
     * @param username String of the username to login
     * @param password string of the password to login
     * @return
     * @throws SQLException
     */
    @Override
    public User login(String username, String password) throws SQLException {


        //ResultSet rs = DBQuery.login("SELECT User_ID, User_Name, Password FROM users where user_name = " + "'" + username + "' and password = '" + password + "'");
        ResultSet rs = DBQuery.login("SELECT User_ID, User_Name, Password FROM users WHERE user_name = " + "'" + username + "' AND CAST(Password AS varchar(max)) = '" + password + "';");

        if(rs.next()){
            //System.out.println("found");
            int userId = rs.getInt("User_ID");
            //addUser(new User(username,password));
            User user = new User(userId, username, password, true);
            System.out.println(user);
            DoaLists.addUser(user);
            return user;

        }
        return null;
    }
}
