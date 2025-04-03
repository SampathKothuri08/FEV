package DBConnection;


import java.sql.*;
import java.util.*;

/**
 *
 * @author Doug Taggart
 */
public class Querying {
      
  /**
   * Performs the queries on the database
   * 
   */
    public void executeQuery ()
    {
      try{
        
        // Creates a new MySql connectivity  
        MySQLconnectivity a = new MySQLconnectivity();
        
        // Creates a new Connection with the MySql Connectivity
        Connection newConn = a.getConnection();
        
        // Testing 
        
            Statement stmt = null;
            ResultSet rs = null;
            
            // Creates a statement object with the new connection 
            stmt = newConn.createStatement();
            // Query the MySql database database table risk_factors
            // Grabs all rows in the table
            stmt.executeQuery("SELECT * FROM risk_factors");
     
            rs = stmt.getResultSet();

            rs.close();
            stmt.close();
      
        
      }
       catch(SQLException ex)
       {
           // Prints out the MySql exception
           System.out.println("SQLException: + " + ex.getMessage());
            System.out.println("SQLState: + " + ex.getSQLState());
            System.out.println("VendorError: + " + ex.getErrorCode());
       }
     
    }
}
  

    

