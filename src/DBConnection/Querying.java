package DBConnection;

import DataObject.EvalObject;
import DataObject.Question;
import java.sql.*;
import java.util.*;

public class Querying {

    private MySQLconnectivity mySQLConnection;

    public Querying(MySQLconnectivity conn) {
        this.mySQLConnection = conn;
    }

    public EvalObject executeQuery(int evaluationId, EvalObject eval) {
        try (Connection conn = mySQLConnection.getConnection()) {
            String sql = "SELECT question_id, question_text, question_type FROM questions WHERE evaluation_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, evaluationId);

            ResultSet rs = stmt.executeQuery();
            ArrayList<Question> questions = new ArrayList<>();

            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                String questionText = rs.getString("question_text");
                String questionType = rs.getString("question_type");

                // Assuming constructor exists in Question
                Question q = new Question(questionId, evaluationId, questionText, questionType, "");
                questions.add(q);
            }

            eval.setQuestions(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return eval;
    }

    public int getEvaluationIdFromDB(String evaluationName) {
        int evaluationId = -1;
        String query = "SELECT evaluation_id FROM evaluations WHERE evaluation_name = ?";

        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, evaluationName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                evaluationId = rs.getInt("evaluation_id");
            }

        } catch (SQLException ex) {
            System.out.println(" SQL Error: " + ex.getMessage());
        }

        return evaluationId;
    }


    public void storeResponse(int evaluationId, int questionId, int userId, String responseText) {
        String insertQuery = "INSERT INTO responses (evaluation_id, question_id, user_id, response_text) VALUES (?, ?, ?, ?)";

        try (Connection conn = mySQLConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

            pstmt.setInt(1, evaluationId);
            pstmt.setInt(2, questionId);
            pstmt.setInt(3, userId);
            pstmt.setString(4, responseText);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println(" Response stored successfully!");
            } else {
                System.out.println(" No response inserted.");
            }

        } catch (SQLException ex) {
            System.out.println(" Error storing response: " + ex.getMessage());
        }
    }
}
