/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAnalysis;
import DataObject.*;





/**
 *
 * @author Doug Taggart
 */
//public class LineQuestion extends QuestionBase
//{
//
//    private EvalObject eval;
//
//    public LineQuestion(EvalObject eval)
//    {
//        this.eval = eval;
//    }
//
//
//     /**
//     * The orig question object
//     *
//     * @return a question about a max value on the graph
//     */
//    @Override
//    public Question finMax()
//    {
//        double max = eval.getDatapoints().get(0).getValueY();
//
//        //itterate through all of the datapoints find the min
//        for(int i =1; i < eval.getDatapoints().size(); i++)
//        {
//
//            if( eval.getDatapoints().get(i).getValueY() > max)
//            {
//                max = eval.getDatapoints().get(i).getValueY();
//            }
//        }
//        //create a new question object
//        Question newQuestion = new Question("Find the maxium value on the Graph", 30, "s", 0);
//        newQuestion.setAnswer(new Double(max).toString());
//        return newQuestion;
//    }
//
//    /**
//     *
//     * @return a question about a minimum value on the graph
//     */
//    @Override
//    public Question  findMin()
//    {
//        double min = eval.getDatapoints().get(0).getValueY();
//
//        //itterate through all of the datapoints find the min
//        for(int i =1; i < eval.getDatapoints().size(); i++)
//        {
//
//            if( eval.getDatapoints().get(i).getValueY() < min)
//            {
//                min = eval.getDatapoints().get(i).getValueY();
//            }
//        }
//        //create a new question object
//        Question newQuestion = new Question("Find the mininum value on the Graph", 30, "s", 0);
//        newQuestion.setAnswer(new Double(min).toString());
//        return newQuestion;
//    }
//
//    /**
//     *
//     * @return a question about an outlier
//     */
//    @Override
//    public Question findOutlier()
//    {
//        //sort the list
//       boolean done = false;
//       int i = 1;
//       while(i < eval.getDatapoints().size() && !done)
//            {
//                done = true;//gets changed IF a swap occurs
//                for(int j = 0; j< eval.getDatapoints().size() - (i); j++)
//                {
//                    if(eval.getDatapoints().get(j).getValueY() > eval.getDatapoints().get(j+1).getValueY())
//                    {
//
//                        eval.getDatapoints().add(j, eval.getDatapoints().remove(j+1));
//
//
//
//
//                        done = false;//a swap happened so we're not done yet
//                    }
//                }
//                i++;
//            }
//
//
//            double median = ( eval.getDatapoints().get(eval.getDatapoints().size()/2).getValueY() + eval.getDatapoints().get(1 + eval.getDatapoints().size()/2).getValueY() )/2;
//
//            int q1 = eval.getDatapoints().size()/4;
//            int q3 = 3*(q1);
//
//            double iqr = eval.getDatapoints().get(q3).getValueY() - eval.getDatapoints().get(q1).getValueY();
//
//            String answer = "";
//
//            double upperRange = eval.getDatapoints().get(q3).getValueY() + (1.5*iqr);
//            double lowerRange = eval.getDatapoints().get(q1).getValueY() - (1.5*iqr);
//
//            for(int k = 0; k < eval.getDatapoints().size(); k ++)
//            {
//
//                //if > q3+1.5*iqr
//                //if < q1-1.5*iqr
//                //its an outlier
//
//
//                if( eval.getDatapoints().get(k).getValueY() < lowerRange  || eval.getDatapoints().get(k).getValueY() >  upperRange)
//                {//this is an outlier
//                    answer = answer + " " + eval.getDatapoints().get(k).getValueX().toString();
//                }
//
//            }
//
//        //create a new question object
//        Question newQuestion = new Question("Find any outliers.", 45, "s", 0);
//
//        if(answer.isEmpty())
//        {
//            answer = "No Outliers";
//        }
//
//
//        newQuestion.setAnswer(answer);
//        return newQuestion;
//    }
//
//    /**
//     *
//     *
//     * @return a question asking a user to recommend something
//     */
//    @Override
//    public Question findRecomendation()
//    {
//        //nothing to do here
//        return null;
//    }
//
//    /**
//     *
//     * @return a question about exploring the graph
//     */
//    @Override
//    public Question exploration()
//    {
//        return null;
//    }
//
//    @Override
//    String modifyQuestion(String s, int num, String l)
//    {
//        s = s.substring(0, s.indexOf("blank"));
//
//
//
//        return s;
//
//    }


import DataObject.*;

/**
 * Handles the creation of questions related to line graphs.
 */
public class LineQuestion extends QuestionBase {

    private EvalObject eval;

    public LineQuestion(EvalObject eval) {
        this.eval = eval;
    }

    @Override
    public Question finMax() {
        double max = eval.getDatapoints().get(0).getValueY();

        for (int i = 1; i < eval.getDatapoints().size(); i++) {
            if (eval.getDatapoints().get(i).getValueY() > max) {
                max = eval.getDatapoints().get(i).getValueY();
            }
        }

        return new Question(0, 0, "Find the maximum value on the graph", "short_answer", "2025-03-16");
    }

    @Override
    public Question findMin() {
        double min = eval.getDatapoints().get(0).getValueY();

        for (int i = 1; i < eval.getDatapoints().size(); i++) {
            if (eval.getDatapoints().get(i).getValueY() < min) {
                min = eval.getDatapoints().get(i).getValueY();
            }
        }

        return new Question(0, 0, "Find the minimum value on the graph", "short_answer", "2025-03-16");
    }

    @Override
    public Question findOutlier() {
        return new Question(0, 0, "Find any outliers.", "short_answer", "2025-03-16");
    }

    @Override
    public Question findRecomendation() {
        return null;
    }

    @Override
    public Question exploration() {
        return null;
    }

    @Override
    String modifyQuestion(String s, int num, String l) {
        return s.substring(0, s.indexOf("blank"));
    }
}

    
    
    
    
