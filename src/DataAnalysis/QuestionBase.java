/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;

import DataObject.Question;


/**
 * All VizTool Specific Questions extend this class
 * This Class has the base methods for finding:
 * Outlier Questions
 * Recommendation Questions
 * Min Questions
 * Max Questions
 * 
 * @author Dooug Taggart
 */
public abstract class QuestionBase
{
    
    /**
     * 
     * @return a question about a max value on the graph
     */
    abstract Question finMax( );
    
    /**
     * 
     * @return a question about a minimum value on the graph   
     */
    abstract Question  findMin();
    
    /**
     * 
     * @return a question about an outlier 
     */
    abstract Question findOutlier();
    
    /**
     * 
     * 
     * @return a question asking a user to recommend something  
     */
    abstract Question findRecomendation(); 
    
    /**
     * 
     * @return a question about exploring the graph 
     */
    abstract Question exploration();
    
    
    abstract String modifyQuestion(String s, int num, String l);
    
    
}
