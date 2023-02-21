package Model;


/**
 * user class for the user table
 */
public class User {
    private final int userID;
    private String userName;
    private String password;
    private boolean active;


    /** user constructor
     * @param userID integer
     * @param userName string
     * @param password string
     * @param active boolean
     */
    public User(int userID, String userName, String password, boolean active) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.active = active;
    }

    /** get user id
     * @return int userID
     */
    public int getUserID() {
        return userID;
    }

    /** get username
     * @return string username
     */
    public String getUserName() {
        return userName;
    }


    /**
     * @return true if user is logged in
     */
    public boolean isActive() {
        return active;
    }


    /**
     * @return string of UserID and userName
     */
    public String toString() {return (Integer.toString(userID)+" "+userName);}
}
