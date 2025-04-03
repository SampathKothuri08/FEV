/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;
import DataObject.EvalObject;
import DataAnalysis.QuestionBase;

/**
 *This class handles calling the different tools for each of the vizTools
 * Only call if you are using scenario 6 and 7
 * @author Doug Taggart
 */
public class AnalysisManager 
{
    EvalObject eval;
    /**
     * 
     * @param eval the eval object returned to the user after questions are added
     * @param tool what viz tool the evaluation uses 
     */
    public AnalysisManager(EvalObject eval, String tool)
    {
        this.eval= eval;
        QuestionBase TypeQuestion;
        this.findQuestions(tool);
    }

    private void findQuestions(String tool) 
    {
        
        if(tool.equalsIgnoreCase("Line Chart"))
        {
            
        }
        
        
    }
    
    
    
    
    
    
    
    
}
