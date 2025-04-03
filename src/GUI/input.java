package GUI;

import DBConnection.MySQLconnectivity;
import DBConnection.Querying;
import DataObject.EvalObject;
import DataObject.Question;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class input extends javax.swing.JFrame {
    private JComboBox<String> scenarioDropdown, visualizationDropdown;
    private JComboBox<String> evaluationDropdown;
    private JTable questionTable;
    private DefaultTableModel tableModel;
    private JButton fetchButton, submitButton;
    private Querying querying;

    private HashMap<String, Integer> scenarioMap = new HashMap<>();

    public input() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Evaluation System");
        setSize(850, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        querying = new Querying(new MySQLconnectivity());

        JPanel selectionPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Select Evaluation"));

        scenarioDropdown = new JComboBox<>();
        visualizationDropdown = new JComboBox<>();
        evaluationDropdown = new JComboBox<>();

        selectionPanel.add(new JLabel("Select Scenario:"));
        selectionPanel.add(scenarioDropdown);
        selectionPanel.add(new JLabel("Select Visualization Tool:"));
        selectionPanel.add(visualizationDropdown);
        selectionPanel.add(new JLabel("Evaluation:"));
        selectionPanel.add(evaluationDropdown);

        fetchButton = new JButton("Fetch Questions");
        fetchButton.addActionListener(this::fetchQuestions);

        String[] columnNames = {"Question ID", "Question", "Type", "Response"};
        tableModel = new DefaultTableModel(columnNames, 0);
        questionTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(questionTable);

        submitButton = new JButton("Submit Responses");
        submitButton.addActionListener(this::submitResponses);

        add(selectionPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(fetchButton, BorderLayout.WEST);
        bottomPanel.add(submitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        scenarioDropdown.addActionListener(e -> loadEvaluations());
        visualizationDropdown.addActionListener(e -> loadEvaluations());

        loadDropdownData();
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
            System.out.println(" Error loading dropdowns: " + e.getMessage());
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
        String evaluationName = (String) evaluationDropdown.getSelectedItem();

        if (evaluationName == null || evaluationName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select an evaluation!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int evaluationId = querying.getEvaluationIdFromDB(evaluationName);
        if (evaluationId == -1) {
            JOptionPane.showMessageDialog(this, "No evaluation found with this name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EvalObject eval = new EvalObject("Evaluation", selectedScenario, new ArrayList<>(), new HashMap<>());
        eval = querying.executeQuery(evaluationId, eval);

        tableModel.setRowCount(0);
        for (Question q : eval.getQuestions()) {
            tableModel.addRow(new Object[]{q.getQuestionId(), q.getQuestionText(), q.getQuestionType(), ""});
        }

        JOptionPane.showMessageDialog(this, "Questions loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
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

            querying.storeResponse(evaluationId, questionId, userId, responseText);
        }

        JOptionPane.showMessageDialog(this, "Responses Submitted Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new input().setVisible(true));
    }
}
