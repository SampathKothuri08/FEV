package DBConnection;


import java.sql.*;


/**
 *The <code> MySQlconnectivity </code> class will connect our software to the new MySQL database.
 * 
 * 
 * 
 * @author Doug Taggart
 * @version 1.0
 * @since 4/15/2012
 */
public class MySQLconnectivity {

    /**
     *  Constructor for MySqlConnectivity
     */
    public MySQLconnectivity() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            //Handle the exception
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://elvis.rowan.edu:3307/taggar17?user=taggar17&password=916000771");
            // Connects to the CDC Database
            
        } catch (SQLException ex) {
            //Prints out the MySql errors
            System.out.println("SQLException: + " + ex.getMessage());
            System.out.println("SQLState: + " + ex.getSQLState());
            System.out.println("VendorError: + " + ex.getErrorCode());

        }
    }

    /**
     * Get connection returns a connection to the CDC database.
     * 
     * @return newConn is the new connection object to the CDC database.  
     */
    public Connection getConnection() {
        Connection newConn;
        try {


            newConn = DriverManager.getConnection("jdbc:mysql://elvis.rowan.edu:3307/taggar17?user=taggar17&password=916000771");
            //Connection object to our CDC database 
            
            return newConn;
        }
        catch (Exception ex) {}
        
     return null;
    }
    
}

