
package evaluation.tool;

import DBConnection.DBManager;
import DBConnection.MySQLconnectivity;
import DataInload.XMLInload;
import DataObject.EvalObject;
import DataObject.XMLObject;
import DataAnalysis.LineQuestion;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main execution class for the evaluation tool.
 */
public class EvaluationTool {
    private static EvalBenchOut evalBenchOut = new EvalBenchOut();

    public static void main(String[] args) {
        // Initialize XML parser and load data
        XMLInload inload = new XMLInload();
        inload.dataInload();

        // Ensure XML parsing was successful before proceeding
        XMLObject xmlData = inload.getEval(); // This is returning XMLObject
        if (xmlData == null) {
            System.out.println(" Error: XML parsing failed. No data loaded.");
            return;
        }

        // Convert XMLObject to EvalObject
        EvalObject evalData = new EvalObject(
                xmlData.getTitle(),          // Title from XML
                xmlData.getEvalType(),       // Evaluation type from XML
                xmlData.getdataPoints(),     // Data points from XML
                xmlData.getMany2Many()       // Many-to-Many relationships
        );

        // Create DBManager with transformed EvalObject
        MySQLconnectivity dbConnection = new MySQLconnectivity();
        DBManager manager = new DBManager(dbConnection);

        int evaluationId = manager.getEvaluationId(evalData.getTitle()); // Fetch ID from DB
        manager.queryQuestions(evaluationId);

        // Retrieve evaluation object
        EvalObject outFile = manager.getEval();

        //  Fix: Check if questions were retrieved before processing
        if (outFile.getQuestions().isEmpty()) {
            System.out.println(" No questions retrieved, skipping analysis.");
            return;
        }

        // Perform analysis only if questions exist
        LineQuestion linequestion = new LineQuestion(outFile);
        linequestion.findOutlier();
        outFile.addQuestion(linequestion.finMax());
        outFile.addQuestion(linequestion.findMin());
        outFile.addQuestion(linequestion.findOutlier());

        // Output results
        evalBenchOut.WriteXML(outFile);
        outFile.test();
    }
}
