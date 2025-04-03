package DBConnection;

import DataObject.EvalObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DBManager {
    private final MySQLconnectivity dbConnection;
    private EvalObject evaluation;

    // Constructor
    public DBManager(MySQLconnectivity connection) {
        this.dbConnection = connection;
        this.evaluation = new EvalObject("", "", new ArrayList<>(), new HashMap<>());
// Ensures evaluation is initialized
    }

    /**
     * Fetches evaluation ID based on the evaluation name
     *
     * @param evaluationName The name of the evaluation to find
     * @return evaluationId or -1 if not found
     */
    public int getEvaluationId(String evaluationName) {
        int evaluationId = -1;
        String query = "SELECT evaluation_id FROM evaluations WHERE evaluation_name = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, evaluationName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                evaluationId = rs.getInt("evaluation_id");
            }
        } catch (SQLException ex) {
            System.out.println(" SQL Error while fetching evaluation ID: " + ex.getMessage());
        }

        return evaluationId;
    }

    /**
     * Retrieves the evaluation object with questions
     *
     * @return EvalObject containing all evaluation data
     */
    public EvalObject getEval() {
        if (this.evaluation == null) {
            System.out.println("⚠ Warning: No evaluation data available.");
            this.evaluation = new EvalObject(
                    "",                          // title
                    "",                          // evaluation type
                    new ArrayList<>(),           // datapoints
                    new HashMap<>()              // many2many
            );
            // Return empty EvalObject to avoid null errors
        }
        return this.evaluation;
    }

    /**
     * Fetches questions based on evaluation ID
     *
     * @param evaluationId The ID of the evaluation
     */
    public void queryQuestions(int evaluationId) {
        String query = "SELECT question_id, question_text, question_type FROM questions WHERE evaluation_id = ?";

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, evaluationId);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(rs.getString("question_text"));
            }

            if (questions.isEmpty()) {
                System.out.println("⚠ No questions retrieved for evaluation ID: " + evaluationId);
            } else {
                System.out.println("✅ Successfully retrieved " + questions.size() + " questions.");
            }

        } catch (SQLException ex) {
            System.out.println(" SQL Error while fetching questions: " + ex.getMessage());
        }
    }
}
