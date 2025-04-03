/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataObject;

import java.util.AbstractList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *This Class encapsulates the information read from the XML file
 *
 *
 * @author Doug Taggart
 */
public class XMLObject {

    private String title;
    private String evalType;
    private String tools;
    private String NumQuestions;
    private ArrayList<Datapoint> datapoints;
    private HashMap<String, String> many2many;

    /**
     * Constructor for a EvalObject
     * @param t the title of the evaluation
     * @param e the type of evaluation used
     * @param tools any tools used(I.e. vizTools like a GMap
     * @param Num
     */
    public XMLObject(String t, String e, String tools, String Num) {
        title = t;
        evalType = e;
        this.tools = tools;
        NumQuestions = Num;
        datapoints = new ArrayList<Datapoint>();
        many2many = new HashMap<String, String>();
    }

    /**
     * returns the title of the evaluation
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * returns the type of evaluation
     * @return evalType
     */
    public String getEvalType() {
        return evalType;
    }

    public HashMap<String, String> getMany2Many()
    {
        return many2many;
    }

    public void addMany2Many(String label1, String label2)
    {
        this.many2many.put(label1, label2);
    }

    /**
     * returns the viz tools used in the eval
     * as a string
     * @return tools
     */
    public String getTools() {
        return tools;
    }

    /**
     * returns the number of questions requested by the user
     *
     * @return numQuestions
     */
    public String getNumQuestions() {
        return NumQuestions;
    }

    /**
     * returns the list of datapoints from the XML input file
     * as an array List
     * @return datapoints
     */
    public ArrayList<Datapoint> getdataPoints() {
        return datapoints;
    }

    /**
     * Add a datapoint to the ArrayList
     *
     * @param d
     */
    public void addDatapoints(Datapoint d) {
        this.datapoints.add(d);
    }

    /**
     * Returns a MySQL query for getting questions from the DB
     * Query takes into account the number of questions and the evaluation type
     *
     * @return query MySQL Query in String form
     */
    public String makeQueryString() {

        String query = "";

        //select question, name from vizTools join vizTool_questions on vizTools.vid=vizTool_questions.vid where vizTools.name like "%line chart";

        //Query String for user Expirence
        if (this.evalType.equalsIgnoreCase("Evaluating User Expirence") || this.evalType.equalsIgnoreCase("Evaluating User Expirence")) {
            query = "SELECT question, time, answer_type, score "
                    + " FROM vizTools join vizTool_questions "
                    + " on vizTools.vid=vizTool_questions.vid "
                    + " where vizTools.name like " + "\' %" + this.tools
                    + "\' limit " + this.NumQuestions;
        } else {
            query = "SELECT question "
                    + " FROM scenarios join scenario_questions "
                    + " on scenarios.sid=scenario_questions.sid "
                    + " where scenarios.sname like " + "%" + this.evalType
                    + " limit " + this.NumQuestions;
        }
        //System.out.println(query);
        return query;
    }
}
