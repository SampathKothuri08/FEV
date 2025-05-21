package evaluation.tool;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class AIQuestionGenerator {

    private static final String API_KEY = "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";
    private static final String API_URL = "https://openrouter.ai/api/v1/chat/completions";

    public static String getQuestionsFromAI(String scenario, String tool) {
        String prompt = String.format(
                "Generate a JSON array of 10 short and clear evaluation questions for the scenario '%s' using the visualization tool '%s'. " +
                        "Each question should be a JSON object with two fields: 'questionText' and 'questionType'. " +
                        "The 'questionType' should be one of: 'paragraph', 'short answer', 'rating', or 'yes/no'. " +
                        "Return ONLY the JSON array. Do NOT include markdown or explanation.",
                scenario, tool
        );


        String escapedPrompt = JSONObject.quote(prompt); // ✅ escape prompt safely

        String jsonRequest = """
        {
          "model": "openai/gpt-4o",
          "messages": [
            {
              "role": "user",
              "content": %s
            }
          ],
          "max_tokens": 1500
        }
        """.formatted(escapedPrompt);

        System.out.println("📤 Sending JSON request:\n" + jsonRequest); // ✅ for debugging

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonRequest.getBytes());
            }

            StringBuilder response = new StringBuilder();
            try (Scanner scanner = new Scanner(conn.getInputStream())) {
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
            }

            return response.toString();

        } catch (Exception e) {
            System.err.println(" API Request failed:");
            e.printStackTrace();
            return null;
        }
    }
}
