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
//public class MySQLconnectivity {
//
//    /**
//     *  Constructor for MySqlConnectivity
//     */
//    public MySQLconnectivity() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//        } catch (Exception ex) {
//            //Handle the exception
//        }
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:mysql://elvis.rowan.edu:3306/taggar17?user=taggar17&password=916000771");
//            // Connects to the CDC Database
//
//        } catch (SQLException ex) {
//            //Prints out the MySql errors
//            System.out.println("SQLException: + " + ex.getMessage());
//            System.out.println("SQLState: + " + ex.getSQLState());
//            System.out.println("VendorError: + " + ex.getErrorCode());
//
//        }
//    }
//
//    /**
//     * Get connection returns a connection to the CDC database.
//     *
//     * @return newConn is the new connection object to the CDC database.
//     */
//    public Connection getConnection() {
//        Connection newConn;
//        try {
//
//
//            newConn = DriverManager.getConnection("jdbc:mysql://elvis.rowan.edu:3306/taggar17?user=taggar17&password=916000771");
//            //Connection object to our CDC database
//
//            return newConn;
//        }
//        catch (Exception ex) {}
//
//     return null;
//    }
//
//}
//



// My code



import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLconnectivity {

    public Connection getConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config.properties"));  // Load DB credentials

            String url = properties.getProperty("DB_URL");
            String user = properties.getProperty("DB_USER");
            String password = properties.getProperty("DB_PASSWORD");

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);

        } catch (IOException e) {
            System.out.println(" Failed to load config.properties!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println(" MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Database connection failed: " + e.getMessage());
        }

        return null;
    }
}
