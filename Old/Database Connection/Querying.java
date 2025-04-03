/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Risk.Analysis;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Alex Waltz Doug Taggart
 */
public class Querying {
    
  // Stores the sample data from the database  
  private static ArrayList sampleData = new ArrayList();
  // Stores the training data created from the sample data
  private static ArrayList trainingData = new ArrayList();
  // Testing data
  private static ArrayList testData = new ArrayList();
      
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

   
          // Pulls the next row from the database
           while (rs.next()) 
           {
               // New Row
              Row r = new Row(); 
              
              // Stores State Codes
              dataPoint d =  new dataPoint(rs.getInt("State_FIPS_Code"), "State_FIPS_Code");         
              r.addDataPoint(d);
              //Stored Countyt Codes
              d = new dataPoint(rs.getInt("County_FIPS_Code"), "County_FIPS_Code");
              r.addDataPoint(d);
              //Stores no exercise
              d = new dataPoint(rs.getDouble("No_Exercise"), "No_Exercise");
              r.addDataPoint(d);
              //Stores few fruit veggies
              d = new dataPoint(rs.getDouble("Few_Fruit_Veg"), "Few_Fruit_Veg" );
              r.addDataPoint(d);
              //Stores obesity
              d = new dataPoint(rs.getDouble("Obesity"), "Obesity");
              r.addDataPoint(d);
              //Stores high blood pressure
              d= new dataPoint(rs.getDouble("High_Blood_Pres"), "High_Blood_Pres");
              r.addDataPoint(d);
              //Stores smoker
              d = new dataPoint(rs.getDouble("Smoker"), "Smoker");
              r.addDataPoint(d);
              //Stores diabetes
              d = new dataPoint(rs.getDouble("Diabetes"), "Diabetes");
              r.addDataPoint(d);
              //Stores uninsured
              d = new dataPoint(rs.getDouble("Uninsured"), "Uninsured");
              r.addDataPoint(d);
              //Stores Primary Care Physian Availability Rate
              d = new dataPoint(rs.getDouble("Prim_Care_Phys_Rate"), "Prim_Care_Phys_Rate");
              r.addDataPoint(d);
              //Stores Dentist availability Rate
              d = new dataPoint(rs.getDouble("Dentist_Rate"), "Dentist_Rate");
              r.addDataPoint(d);
              //Stores Community Health Center availability rate
              d = new dataPoint(rs.getDouble("Community_Health_Center_Ind"), "Community_Health_Center_Ind");
              r.addDataPoint(d);
              //add the row object to the sample data list
              r.setObese();
              sampleData.add(r); 
            }
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
  
   /**
    * Returns the data being stores in the database  
    * 
    * @return sampleData is the data stored in the database
    */
    public static ArrayList getData()
    {   
        return sampleData;
    }
    
    /**
     * Creates the training data set used in creating the tree/info gains
     * 
     * @return  trainingData is the newly created training data set
     */
    public static ArrayList createTrainingData() {
        if (trainingData.isEmpty()) 
        {   
            //Pulls the data    
            ArrayList temp = Querying.getData();
            
            //Randomizes the data in temp
            Collections.shuffle(temp);
            
            
            // Adds the randomized data
            
            double tempSize = temp.size() *.9;
            
            for (int i = 0; i < tempSize-1; i++) {
                trainingData.add(temp.remove(0));            
            }
            
            
            System.out.println("training Data " + trainingData.size() );
            
            // testing
            testData = temp;
            
            return trainingData;
        } 
        else 
        {
            return trainingData;
        }
    }
    
    /**
     * 
     * @return testData is the  
     */
    public static ArrayList getTestData()
    {
        return testData;
    }
    
}
