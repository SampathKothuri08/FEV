package GUI;

import DBConnection.MySQLconnectivity;
import evaluation.tool.AIQuestionGenerator;
import org.json.JSONArray;
import org.json.JSONObject;

import DBConnection.Querying;
import DataObject.EvalObject;
import DataObject.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class input extends JFrame {
    private JComboBox<String> scenarioDropdown, visualizationDropdown, evaluationDropdown;
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JButton fetchButton, submitButton, uploadImageButton;
    private JLabel imageLabel, imagePathLabel;

    private Querying querying;
    private HashMap<String, Integer> scenarioMap = new HashMap<>();

    public input() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Evaluation System");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        querying = new Querying(new MySQLconnectivity());

        // Dropdown Panel
        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Select Evaluation"));

        scenarioDropdown = new JComboBox<>();
        visualizationDropdown = new JComboBox<>();
        evaluationDropdown = new JComboBox<>();

        selectionPanel.add(createLabeledRow("Select Scenario:", scenarioDropdown));
        selectionPanel.add(Box.createVerticalStrut(5));
        selectionPanel.add(createLabeledRow("Select Visualization Tool:", visualizationDropdown));
        selectionPanel.add(Box.createVerticalStrut(5));
        selectionPanel.add(createLabeledRow("Evaluation:", evaluationDropdown));
        selectionPanel.add(Box.createVerticalStrut(10));

        // Image Section
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(500, 300));
        imagePathLabel = new JLabel();

        uploadImageButton = new JButton("Choose Image");
        uploadImageButton.addActionListener(this::chooseImage);

        selectionPanel.add(new JLabel("Visualization Preview:"));
        selectionPanel.add(imageLabel);
        selectionPanel.add(uploadImageButton);
        selectionPanel.add(imagePathLabel);

        // Buttons
        fetchButton = new JButton("Fetch Questions");
        fetchButton.addActionListener(this::fetchQuestions);

        submitButton = new JButton("Submit Responses");
        submitButton.addActionListener(this::submitResponses);

        // Question Table
        String[] columnNames = {"Question ID", "Question", "Type", "Response"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        questionTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane tableScrollPane = new JScrollPane(questionTable);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(fetchButton);
        bottomPanel.add(submitButton);

        // Add to Frame
        add(selectionPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        scenarioDropdown.addActionListener(e -> loadEvaluations());
        visualizationDropdown.addActionListener(e -> loadEvaluations());

        loadDropdownData();
    }

    private JPanel createLabeledRow(String labelText, JComponent component) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(component, BorderLayout.CENTER);
        return panel;
    }

    private void loadDropdownData() {
        scenarioDropdown.removeAllItems();
        visualizationDropdown.removeAllItems();
        evaluationDropdown.removeAllItems();
        scenarioMap.clear();

        try (Connection conn = new MySQLconnectivity().getConnection()) {
            String scenarioQuery = "SELECT scenario_id, description FROM scenarios";
            PreparedStatement scenarioStmt = conn.prepareStatement(scenarioQuery);
            ResultSet scenarioRs = scenarioStmt.executeQuery();
            while (scenarioRs.next()) {
                int id = scenarioRs.getInt("scenario_id");
                String description = scenarioRs.getString("description");
                scenarioDropdown.addItem(description);
                scenarioMap.put(description, id);
            }

            String toolQuery = "SELECT DISTINCT tool_name FROM visualization_tools";
            PreparedStatement toolStmt = conn.prepareStatement(toolQuery);
            ResultSet toolRs = toolStmt.executeQuery();
            while (toolRs.next()) {
                visualizationDropdown.addItem(toolRs.getString("tool_name"));
            }

        } catch (Exception e) {
            System.out.println("Error loading dropdowns: " + e.getMessage());
        }
    }

    private void loadEvaluations() {
        evaluationDropdown.removeAllItems();

        String selectedScenario = (String) scenarioDropdown.getSelectedItem();
        String selectedTool = (String) visualizationDropdown.getSelectedItem();

        if (selectedScenario == null || selectedTool == null) return;

        try (Connection conn = new MySQLconnectivity().getConnection()) {
            String query = "SELECT e.evaluation_name FROM evaluations e " +
                    "JOIN scenarios s ON e.scenario_id = s.scenario_id " +
                    "JOIN visualization_tools vt ON e.tool_id = vt.tool_id " +
                    "WHERE s.description = ? AND vt.tool_name = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, selectedScenario);
            stmt.setString(2, selectedTool);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                evaluationDropdown.addItem(rs.getString("evaluation_name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fetchQuestions(ActionEvent evt) {
        String selectedScenario = (String) scenarioDropdown.getSelectedItem();
        String selectedTool = (String) visualizationDropdown.getSelectedItem();

        if (selectedScenario == null || selectedTool == null) {
            JOptionPane.showMessageDialog(this, "Please select a scenario and tool!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Call AI API
        String response = AIQuestionGenerator.getQuestionsFromAI(selectedScenario, selectedTool);

        if (response == null || response.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Failed to fetch questions from AI.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            JSONObject outer = new JSONObject(response);
            String content = outer
                    .getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content")
                    .trim();

            if (content.startsWith("```json")) content = content.replace("```json", "").trim();
            if (content.endsWith("```")) content = content.substring(0, content.length() - 3).trim();

            JSONArray questions = new JSONArray(content);
            tableModel.setRowCount(0);

            for (int i = 0; i < questions.length(); i++) {
                JSONObject qObj = questions.getJSONObject(i);
                String qText = qObj.optString("questionText", "Missing question");
                String qType = qObj.optString("questionType", "Unknown");
                tableModel.addRow(new Object[]{i + 1, qText, qType, ""});
            }

            JOptionPane.showMessageDialog(this, " AI-generated questions loaded!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, " Error parsing AI response.\nCheck console for details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void chooseImage(ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            imagePathLabel.setText("Selected: " + selectedFile.getName());

            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
            Image scaled = icon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        }
    }

    private void submitResponses(ActionEvent evt) {
        if (questionTable.isEditing()) {
            questionTable.getCellEditor().stopCellEditing();
        }

        int rowCount = tableModel.getRowCount();
        String evaluationName = (String) evaluationDropdown.getSelectedItem();
        int evaluationId = querying.getEvaluationIdFromDB(evaluationName);

        if (evaluationId == -1) {
            JOptionPane.showMessageDialog(this, "Invalid evaluation ID!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = 1;

        for (int i = 0; i < rowCount; i++) {
            int questionId = (int) tableModel.getValueAt(i, 0);
            String responseText = (String) tableModel.getValueAt(i, 3);

            if (responseText == null || responseText.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Response cannot be empty for question ID: " + questionId, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String scenarioName = (String) scenarioDropdown.getSelectedItem();
            querying.storeResponse(evaluationId, questionId, userId, scenarioName, responseText);
        }

        JOptionPane.showMessageDialog(this, "Responses Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new input().setVisible(true));
    }
}
