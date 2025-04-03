/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObject;

import java.util.ArrayList;
import java.util.HashMap;
import DataObject.Question;
import DataObject.Datapoint;

/**
 *Class EvalObject holds all information about an evaluation setup
 * Information in this Class is given to the user 
 * @author Doug Taggart
 */
public class EvalObject
{

    private String title;
    private String EvalType;
    private ArrayList<Question> questions; 
    private ArrayList<Datapoint> datapoints;
    private HashMap<String, String> many2many;
    
    /**
     * Constructor for creating the EVal Object
     * @param title the name of the evaluation
     * @param EvalType the type of evaluation
     */
    public EvalObject(String title, String EvalType, ArrayList<Datapoint> datapoint, HashMap<String, String> m2m)
    {
        this.title = title;
        this.EvalType = EvalType;
        questions = new ArrayList<Question>();
        this.datapoints = datapoint;
        many2many = m2m;
        
    }
    /**
     * returns the title of the evaluation
     * 
     * @return title 
     */
    public String getTitle()
    {
        return title;
    }
    
    /**
     * Returns the type of evaluation (1 of 7)
     * 
     * @return EvalType
     */
    public String getEvalType()
    {
        return EvalType;
    }
    
    /**
     * Adds a question to th objects ArrayList of Questions
     * 
     * @param question 
     */
    public void addQuestion(Question question)
    {
        questions.add(question);
    }
    
    public HashMap<String, String> getMany2Many()
    {
        return many2many;
    }



    /**
     * returns the entire Question ArrayList
     * 
     * @return questions 
     */
    public ArrayList<Question> getQuestions()
    {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }


    public ArrayList<Datapoint> getDatapoints()
    {
        return datapoints;
    }
    
    
    /**
     * Use to print out the contents of the Eval Object
     * For Testing only
     */
    public void test()
    {
        System.out.println("title: " + this.title);
        System.out.println("Evaluation Type: " + this.EvalType);
        
        if(questions.isEmpty())
        { 
            System.out.println("Evaluation does not have any questions");
        }        
        else
        {
          for(int i =0; i < this.questions.size(); i++)
          {
              System.out.println((i+1) +": " + questions.get(i).getQuestionText());
          }
        }
        
        
    }
    
    
}
