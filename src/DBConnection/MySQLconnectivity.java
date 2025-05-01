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
