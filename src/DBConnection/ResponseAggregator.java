package DBConnection;

import java.sql.*;
import java.util.Map;
import java.util.LinkedHashMap;

public class ResponseAggregator {

    private final MySQLconnectivity mysql;

    public ResponseAggregator(MySQLconnectivity mysql) {
        this.mysql = mysql;
    }

    public Map<String, Integer> getResponseDistribution(int questionId) {
        Map<String, Integer> responseCounts = new LinkedHashMap<>();

        String sql = "SELECT response_text, COUNT(*) as count " +
                "FROM responses " +
                "WHERE question_id = ? " +
                "GROUP BY response_text";

        try (Connection conn = mysql.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, questionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String response = rs.getString("response_text");
                int count = rs.getInt("count");
                responseCounts.put(response, count);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseCounts;
    }
}
