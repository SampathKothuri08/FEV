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

    
    
    
    
