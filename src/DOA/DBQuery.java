package DOA;

import java.sql.*;

/**
 * class for the queries to be processed
 */
public class DBQuery {

    private static PreparedStatement statement; //Statement reference
    private static Connection conn;

    /**
     * @return the connection made when logging in
     */
    public static Connection getConn() {
        return conn;
    }

    /**
     * @param conn set the connection made at login
     */
    public static void setConn(Connection conn) {
        DBQuery.conn = conn;
    }

    /**
     * @param sqlLogin login sql query, checks if username and password exist
     * @return returns result set data to be processed
     * @throws SQLException
     */
    public static ResultSet login(String sqlLogin) throws SQLException{
       Connection conn = DBConnection.startConnection();
       setConn(conn);
       setPreparedStatement(conn, sqlLogin);
        PreparedStatement ps = getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
        return rs;
    }

    /**
     * @param conn the connection made at login
     * @param sqlStatement the sql
     * @throws SQLException
     */
    // Create Statement Object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException
    {
        statement = conn.prepareStatement(sqlStatement);
    }

    /** gets prepared statement
     * @return statement object
     */
    // Return Statement object
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }

    /** retrieves data from the database
     * @param sqlStatement
     * @throws SQLException
     */
    public static ResultSet getResultSet(String sqlStatement) throws SQLException {
        Connection conn = getConn();

        setPreparedStatement(conn, sqlStatement);
        PreparedStatement ps = getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
        return rs;
    }
    /** Updates, inserts, deletes data in the database
     * @param sqlStatement
     * @throws SQLException
     */
    public static boolean insertStatement(String sqlStatement) throws SQLException{

        setPreparedStatement(getConn(), sqlStatement);
        PreparedStatement ps = getPreparedStatement();
        try{
        ps.execute();}
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        if (ps.getUpdateCount() == 1){
            return true;
        }
        else return false;
    }
}