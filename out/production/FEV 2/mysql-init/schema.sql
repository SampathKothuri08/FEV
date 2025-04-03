CREATE DATABASE fev_system;
USE fev_system;

-- USERS
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(100),
    role ENUM('admin', 'evaluator', 'participant')
);


-- SCENARIOS
CREATE TABLE scenarios (
    scenario_id INT AUTO_INCREMENT PRIMARY KEY,
    scenario_name VARCHAR(255) UNIQUE,
    description TEXT
);

-- VISUALIZATION TOOLS
CREATE TABLE visualization_tools (
    tool_id INT AUTO_INCREMENT PRIMARY KEY,
    tool_name VARCHAR(100) UNIQUE
);

-- EVALUATIONS
CREATE TABLE evaluations (
    evaluation_id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_name VARCHAR(255) UNIQUE,
    description TEXT,
    created_by INT,
    scenario_id INT,
    tool_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    FOREIGN KEY (scenario_id) REFERENCES scenarios(scenario_id),
    FOREIGN KEY (tool_id) REFERENCES visualization_tools(tool_id)
);

-- QUESTIONS
CREATE TABLE questions (
    question_id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT,
    question_text TEXT,
    question_type ENUM('multiple-choice', 'short-answer', 'rating-scale', 'yes-no'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evaluation_id) REFERENCES evaluations(evaluation_id)
);

-- RESPONSES
CREATE TABLE responses (
    response_id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT,
    question_id INT,
    user_id INT,
    response_text TEXT,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evaluation_id) REFERENCES evaluations(evaluation_id),
    FOREIGN KEY (question_id) REFERENCES questions(question_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- DATA ANALYSIS
CREATE TABLE data_analysis (
    analysis_id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT,
    analysis_result TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evaluation_id) REFERENCES evaluations(evaluation_id)
);




INSERT INTO users (first_name, last_name, email, role) VALUES
('Alice', 'Smith', 'alice.smith@example.com', 'admin'),
('Bob', 'Johnson', 'bob.johnson@example.com', 'evaluator'),
('Charlie', 'Lee', 'charlie.lee@example.com', 'participant'),
('Diana', 'Clark', 'diana.clark@example.com', 'participant'),
('Ethan', 'Walker', 'ethan.walker@example.com', 'participant'),
('Fiona', 'Hall', 'fiona.hall@example.com', 'evaluator'),
('George', 'Allen', 'george.allen@example.com', 'participant'),
('Hannah', 'Young', 'hannah.young@example.com', 'admin'),
('Ian', 'King', 'ian.king@example.com', 'participant'),
('Jenna', 'Scott', 'jenna.scott@example.com', 'participant'),
('Kevin', 'Green', 'kevin.green@example.com', 'evaluator'),
('Luna', 'Adams', 'luna.adams@example.com', 'participant'),
('Mike', 'Nelson', 'mike.nelson@example.com', 'participant'),
('Nina', 'Baker', 'nina.baker@example.com', 'admin'),
('Oscar', 'Turner', 'oscar.turner@example.com', 'participant'),
('Penny', 'Campbell', 'penny.campbell@example.com', 'participant'),
('Quinn', 'Mitchell', 'quinn.mitchell@example.com', 'participant'),
('Rita', 'Perez', 'rita.perez@example.com', 'evaluator'),
('Sam', 'Roberts', 'sam.roberts@example.com', 'participant'),
('Tina', 'Edwards', 'tina.edwards@example.com', 'participant');




INSERT INTO scenarios (scenario_name, description) VALUES
('Scenario 1', 'Evaluating data trend clarity.'),
('Scenario 2', 'Assessing UI effectiveness.'),
('Scenario 3', 'Detecting chart outliers.'),
('Scenario 4', 'Understanding hierarchical data.'),
('Scenario 5', 'Checking network graph connectivity.'),
('Scenario 6', 'Clarity of heatmaps.'),
('Scenario 7', 'Pie chart readability.'),
('Scenario 8', 'Scatter plot effectiveness.'),
('Scenario 9', 'Bar chart interpretation.'),
('Scenario 10', 'Geospatial data visualization.'),
('Scenario 11', 'Time series analysis.'),
('Scenario 12', 'Color contrast evaluation.'),
('Scenario 13', 'Dashboard usability test.'),
('Scenario 14', 'Heatmap clustering.'),
('Scenario 15', 'Network diagram utility.'),
('Scenario 16', 'Interactive tool assessment.'),
('Scenario 17', 'Real-time visualization.'),
('Scenario 18', 'User preference analysis.'),
('Scenario 19', 'AI-assisted chart interpretation.'),
('Scenario 20', 'Trend identification accuracy.');


INSERT INTO visualization_tools (tool_name) VALUES
('Line Chart'),
('Bar Chart'),
('Pie Chart'),
('Scatter Plot'),
('Tree Map'),
('Heat Map'),
('GMap'),
('Node-Link Diagram'),
('Bubble Chart'),
('Histogram'),
('Choropleth Map'),
('Radar Chart'),
('Box Plot');


INSERT INTO evaluations (evaluation_name, description, created_by, scenario_id, tool_id) VALUES
('Evaluation 1', 'Data trend clarity test', 1, 1, 1),
('Evaluation 2', 'UI effectiveness test', 2, 2, 2),
('Evaluation 3', 'Outlier detection in charts', 3, 3, 3),
('Evaluation 4', 'Hierarchy understanding', 4, 4, 4),
('Evaluation 5', 'Network graph structure', 5, 5, 5),
('Evaluation 6', 'Heatmap clarity', 6, 6, 6),
('Evaluation 7', 'Pie chart readability', 7, 7, 3),
('Evaluation 8', 'Scatter plot insights', 8, 8, 4),
('Evaluation 9', 'Bar chart analysis', 9, 9, 2),
('Evaluation 10', 'Map-based visualization', 10, 10, 7),
('Evaluation 11', 'Time series readability', 11, 11, 1),
('Evaluation 12', 'Color contrast clarity', 12, 12, 6),
('Evaluation 13', 'Dashboard usability', 13, 13, 5),
('Evaluation 14', 'Cluster detection in heatmaps', 14, 14, 6),
('Evaluation 15', 'Network diagram analysis', 15, 15, 8),
('Evaluation 16', 'Interactive tool usage', 16, 16, 4),
('Evaluation 17', 'Live data handling', 17, 17, 1),
('Evaluation 18', 'Visualization preference', 18, 18, 2),
('Evaluation 19', 'AI interpretation aid', 19, 19, 9),
('Evaluation 20', 'Trend detection accuracy', 20, 20, 10);


INSERT INTO questions (evaluation_id, question_text, question_type) VALUES
(1, 'How clear was the data trend?', 'rating-scale'),
(1, 'Was the trend understandable?', 'yes-no'),
(1, 'What would improve this trend chart?', 'short-answer'),
(1, 'Would you use this visualization again?', 'yes-no'),
(1, 'Did the trend support your insights?', 'yes-no'),
(1, 'On a scale of 1-5, how useful was it?', 'rating-scale'),
(1, 'Was it visually pleasing?', 'yes-no'),
(1, 'Describe any confusion points.', 'short-answer'),
(1, 'Rate your overall satisfaction.', 'rating-scale'),
(1, 'Was the title clear?', 'yes-no'),

(2, 'How intuitive was the UI?', 'rating-scale'),
(2, 'Did the buttons behave as expected?', 'yes-no'),
(2, 'How would you describe the interface?', 'short-answer'),
(2, 'Was it clutter-free?', 'yes-no'),
(2, 'Did you enjoy using it?', 'yes-no'),
(2, 'Any improvement suggestions?', 'short-answer'),
(2, 'Rate the ease of navigation.', 'rating-scale'),
(2, 'Was it responsive?', 'yes-no'),
(2, 'What confused you?', 'short-answer'),
(2, 'Would you use this UI again?', 'yes-no'),

(3, 'Were outliers easy to spot?', 'yes-no'),
(3, 'How many outliers were visible?', 'short-answer'),
(3, 'Rate the clarity of outlier data.', 'rating-scale'),
(3, 'Did they distort the chart?', 'yes-no'),
(3, 'Should they be highlighted?', 'yes-no'),
(3, 'What improvement do you suggest?', 'short-answer'),
(3, 'Was the axis clear?', 'yes-no'),
(3, 'Did the chart guide your eyes?', 'yes-no'),
(3, 'Rate overall detection experience.', 'rating-scale'),
(3, 'Would you use this for anomalies?', 'yes-no'),

(4, 'Was hierarchy properly visualized?', 'yes-no'),
(4, 'Did nesting make sense?', 'yes-no'),
(4, 'How many levels were used?', 'short-answer'),
(4, 'Any visual overload?', 'yes-no'),
(4, 'Rate data clarity.', 'rating-scale'),
(4, 'What could be simplified?', 'short-answer'),
(4, 'Was it suitable for the dataset?', 'yes-no'),
(4, 'Was it easy to navigate?', 'yes-no'),
(4, 'Rate your confidence in understanding.', 'rating-scale'),
(4, 'Add suggestions to improve.', 'short-answer'),

(5, 'Was the connectivity clear?', 'yes-no'),
(5, 'How smooth were the transitions?', 'rating-scale'),
(5, 'Did the chart capture relations?', 'yes-no'),
(5, 'Were nodes well-distinguished?', 'yes-no'),
(5, 'Any layout confusion?', 'short-answer'),
(5, 'Would you suggest another layout?', 'yes-no'),
(5, 'Rate overall comprehension.', 'rating-scale'),
(5, 'Any insights gained?', 'short-answer'),
(5, 'What stood out the most?', 'short-answer'),
(5, 'Was the interaction intuitive?', 'yes-no'),

(6, 'How fast did you grasp the concept shown in the heatmap?', 'rating-scale'),
(6, 'Was the visual over-complicated?', 'yes-no'),
(6, 'What information stood out most to you?', 'short-answer'),
(6, 'Did the tool match your learning style?', 'yes-no'),
(6, 'How easy was it to navigate this evaluation?', 'rating-scale'),
(6, 'Were all values accurately represented?', 'yes-no'),
(6, 'What would you add to improve understanding?', 'short-answer'),
(6, 'How does this compare to other tools you''ve used?', 'short-answer'),
(6, 'Would this be useful in your field of work?', 'yes-no'),
(6, 'Rate your overall experience in Evaluation 6.', 'rating-scale'),

(7, 'Was the use of space optimized in Evaluation 7?', 'yes-no'),
(7, 'How confident are you with your interpretation?', 'rating-scale'),
(7, 'What felt missing in this visualization?', 'short-answer'),
(7, 'Did you feel engaged while exploring the data?', 'yes-no'),
(7, 'Was the interface intuitive?', 'yes-no'),
(7, 'Did the tool guide you well?', 'yes-no'),
(7, 'How easy was it to spot trends or anomalies?', 'rating-scale'),
(7, 'What data point drew your attention the most?', 'short-answer'),
(7, 'Would you share this evaluation with others?', 'yes-no'),
(7, 'Rate your interest level in this evaluation.', 'rating-scale'),

(8, 'Did Evaluation 8 meet your expectations?', 'yes-no'),
(8, 'What aspect of the visualization needs work?', 'short-answer'),
(8, 'Was the content overload or well-balanced?', 'yes-no'),
(8, 'How confident are you in using this data?', 'rating-scale'),
(8, 'Was the color scheme visually pleasing?', 'yes-no'),
(8, 'Were you able to extract meaningful conclusions?', 'yes-no'),
(8, 'Would you prefer another chart for this scenario?', 'yes-no'),
(8, 'What improvements would you suggest?', 'short-answer'),
(8, 'How useful were the titles and labels?', 'rating-scale'),
(8, 'Rate the clarity of the visual representation.', 'rating-scale'),

(9, 'Was the visualization too minimalistic?', 'yes-no'),
(9, 'How easy was it to interact with the chart?', 'rating-scale'),
(9, 'What confused you the most?', 'short-answer'),
(9, 'Did the visualization help solve a problem?', 'yes-no'),
(9, 'Was the overall tone/data representation neutral?', 'yes-no'),
(9, 'What detail made a difference in understanding?', 'short-answer'),
(9, 'Did you notice any redundancy in the visual?', 'yes-no'),
(9, 'How informative was the context given?', 'rating-scale'),
(9, 'Would you explore similar evaluations?', 'yes-no'),
(9, 'Rate how satisfied you were with this experience.', 'rating-scale'),

(10, 'Was the message of the data obvious in Evaluation 10?', 'yes-no'),
(10, 'What feedback would you give to the designer?', 'short-answer'),
(10, 'Was the story behind the data compelling?', 'yes-no'),
(10, 'How engaging was this visualization?', 'rating-scale'),
(10, 'Was your attention drawn to the right details?', 'yes-no'),
(10, 'What would you change about this tool?', 'short-answer'),
(10, 'Did the data feel complete and well-rounded?', 'yes-no'),
(10, 'How effective was the legend/annotation?', 'rating-scale'),
(10, 'Was the visualization too static or interactive enough?', 'yes-no'),
(10, 'Rate the usefulness of Evaluation 10.', 'rating-scale'),

(11, 'Was the time series chart easy to interpret?', 'yes-no'),
(11, 'Did you find any anomalies over time?', 'short-answer'),
(11, 'How smooth was the visual representation?', 'rating-scale'),
(11, 'Was the temporal granularity appropriate?', 'yes-no'),
(11, 'How would you improve the chart?', 'short-answer'),
(11, 'Did the visualization help identify trends?', 'yes-no'),
(11, 'Were the axis labels informative?', 'yes-no'),
(11, 'What confused you the most?', 'short-answer'),
(11, 'How helpful was the time-based layout?', 'rating-scale'),
(11, 'Would you use this type of visualization again?', 'yes-no'),

(12, 'Was the color contrast helpful in identifying key data points?', 'yes-no'),
(12, 'Did any colors seem too similar or confusing?', 'short-answer'),
(12, 'Rate the overall color scheme.', 'rating-scale'),
(12, 'Would you suggest using different hues?', 'yes-no'),
(12, 'How did the colors affect your interpretation?', 'short-answer'),
(12, 'Did the background color distract you?', 'yes-no'),
(12, 'Were tooltips or highlights effective?', 'yes-no'),
(12, 'What colors would improve readability?', 'short-answer'),
(12, 'Rate your satisfaction with visual contrast.', 'rating-scale'),
(12, 'Would you recommend this visualization to others?', 'yes-no'),

(13, 'Was the dashboard layout user-friendly?', 'yes-no'),
(13, 'Which component was most useful?', 'short-answer'),
(13, 'Rate the ease of navigation.', 'rating-scale'),
(13, 'Were all elements clearly labeled?', 'yes-no'),
(13, 'Suggest any improvements for usability.', 'short-answer'),
(13, 'Did you feel overwhelmed by the layout?', 'yes-no'),
(13, 'Was the information well organized?', 'yes-no'),
(13, 'What was missing in the dashboard?', 'short-answer'),
(13, 'Rate your overall experience.', 'rating-scale'),
(13, 'Would you reuse this dashboard?', 'yes-no'),

(14, 'Could you distinguish the clusters clearly?', 'yes-no'),
(14, 'How helpful was the color coding?', 'rating-scale'),
(14, 'Was any cluster misleading or confusing?', 'yes-no'),
(14, 'Write a short summary of what you observed.', 'short-answer'),
(14, 'Would you recommend this clustering visualization?', 'yes-no'),
(14, 'Were the boundaries between clusters clear?', 'yes-no'),
(14, 'Did the heatmap help spot patterns?', 'yes-no'),
(14, 'What changes would you suggest?', 'short-answer'),
(14, 'Rate the effectiveness of the clustering.', 'rating-scale'),
(14, 'Would this tool work in real-world scenarios?', 'yes-no'),

(15, 'Did the network connections make sense?', 'yes-no'),
(15, 'What insights did you gain from node-link relationships?', 'short-answer'),
(15, 'Rate the effectiveness of the layout.', 'rating-scale'),
(15, 'Were any nodes or links unclear?', 'yes-no'),
(15, 'How would you improve the diagram?', 'short-answer'),
(15, 'Did the diagram feel too cluttered?', 'yes-no'),
(15, 'Was the interaction intuitive?', 'yes-no'),
(15, 'Describe a key observation you made.', 'short-answer'),
(15, 'How helpful were the node sizes/colors?', 'rating-scale'),
(15, 'Would you use this type of diagram again?', 'yes-no'),

(16, 'Was the interactivity smooth and responsive?', 'yes-no'),
(16, 'Rate the performance of the interactive elements.', 'rating-scale'),
(16, 'What did you find most engaging?', 'short-answer'),
(16, 'Did you experience any lag or delay?', 'yes-no'),
(16, 'Would you trust this tool in a live setting?', 'yes-no'),
(16, 'How would you enhance the interactivity?', 'short-answer'),
(16, 'Were the controls intuitive?', 'yes-no'),
(16, 'Describe a feature you liked.', 'short-answer'),
(16, 'Was the tool accessible on all devices?', 'yes-no'),
(16, 'Rate your satisfaction with the tool.', 'rating-scale'),

(17, 'Was the data updated in real-time as expected?', 'yes-no'),
(17, 'How reliable did the live data feel?', 'rating-scale'),
(17, 'What information caught your attention first?', 'short-answer'),
(17, 'Did real-time updates cause distraction?', 'yes-no'),
(17, 'Would you rely on this for real-world monitoring?', 'yes-no'),
(17, 'What improvements could enhance clarity?', 'short-answer'),
(17, 'Was it difficult to follow the changing data?', 'yes-no'),
(17, 'Rate the performance under load.', 'rating-scale'),
(17, 'Describe your overall experience.', 'short-answer'),
(17, 'Would you prefer static over real-time for this use case?', 'yes-no'),

(18, 'Which chart type did you prefer the most?', 'short-answer'),
(18, 'Did you find the chosen visualization intuitive?', 'yes-no'),
(18, 'Rate how well the visualization matched your preference.', 'rating-scale'),
(18, 'Would you like more customization options?', 'yes-no'),
(18, 'Describe why you preferred this over others.', 'short-answer'),
(18, 'Were your expectations met?', 'yes-no'),
(18, 'How satisfied are you with the visual style?', 'rating-scale'),
(18, 'What would improve your experience?', 'short-answer'),
(18, 'Was the layout aesthetically pleasing?', 'yes-no'),
(18, 'Would you use the same chart again?', 'yes-no'),

(19, 'Was the AI-generated visualization relevant?', 'yes-no'),
(19, 'How confident are you in the AI suggestions?', 'rating-scale'),
(19, 'Describe a key takeaway from the AI output.', 'short-answer'),
(19, 'Did the AI misinterpret the data?', 'yes-no'),
(19, 'Would you rely on AI for data visualizations?', 'yes-no'),
(19, 'What improvements would you suggest for AI tuning?', 'short-answer'),
(19, 'Was the explanation from AI helpful?', 'yes-no'),
(19, 'Rate the accuracy of the visualization.', 'rating-scale'),
(19, 'Did AI identify patterns correctly?', 'yes-no'),
(19, 'Would you like a hybrid (AI + manual) system?', 'yes-no'),

(20, 'Was the trend clearly visible?', 'yes-no'),
(20, 'Rate your understanding of the shown trend.', 'rating-scale'),
(20, 'What stood out the most in the trend?', 'short-answer'),
(20, 'Did the visualization make the trend obvious?', 'yes-no'),
(20, 'Would you recommend this to a colleague?', 'yes-no'),
(20, 'Describe any confusion you had.', 'short-answer'),
(20, 'How well did the visual highlight changes?', 'rating-scale'),
(20, 'Was additional context needed to understand it?', 'yes-no'),
(20, 'What would you add to make it better?', 'short-answer'),
(20, 'Did you find the data trustworthy?', 'yes-no');



INSERT INTO data_analysis (evaluation_id, analysis_result) VALUES
(1, 'Users found the data trend fairly clear.'),
(2, 'UI was considered mostly intuitive.'),
(3, 'Outliers were spotted by majority.'),
(4, 'Hierarchical layout needed simplification.'),
(5, 'Network graph had medium clarity.'),
(6, 'Heatmap colors were helpful.'),
(7, 'Pie chart readability was average.'),
(8, 'Scatter plot showed visible patterns.'),
(9, 'Bar charts were well understood.'),
(10, 'Geospatial visuals were detailed.'),
(11, 'Time series needed clearer legends.'),
(12, 'High contrast was effective.'),
(13, 'Users liked dashboard interactivity.'),
(14, 'Clustering was mostly understood.'),
(15, 'Network diagrams needed decluttering.'),
(16, 'Interactivity boosted comprehension.'),
(17, 'Live updates were smooth.'),
(18, 'Tool preference varied by role.'),
(19, 'AI suggestions aided analysis.'),
(20, 'Trends were mostly identified correctly.');

