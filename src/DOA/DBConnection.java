package DOA;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;
import java.util.Scanner;

/**
 * class is called to connect to the database
 */
public class DBConnection {
    // JDBC URL parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":sqlserver:";
    private static final String ipAddress = "//bradc195.database.windows.net:1433;database=javaII:1433";

    // JDBC URL
    //private static final String jdbcURL = protocol + vendorName + ipAddress;

    private static final String password = Password.getPassword();
    private static final String username = Password.getUsername(); // Username
    private static final String jdbcURL = "jdbc:sqlserver://bradc195.database.windows.net:1433;database=Java2;user=" + username + "@bradc195;password=" + password + ";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    // Driver Interface reference
    private static final String MYSQLJDBCDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static Connection conn = null;





    //private static final String password = ""; // Password

    /** starts the connection to the db
     * @return Connection
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);

            conn = DriverManager.getConnection(jdbcURL);
            System.out.println("Connection successful");
            return conn;
        }
        catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * closes the connection to the db
     */
    public static void closedConnection() {
        try {
            conn.close();
            System.out.println("Connection closed");
        }
        catch (SQLException e){
            System.out.println("Error" + e.getMessage());
        }
    }

}
