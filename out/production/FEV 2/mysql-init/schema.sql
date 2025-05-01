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
    tool_id INT,
    question_text TEXT,
    question_type ENUM('multiple-choice', 'short-answer', 'rating-scale', 'yes-no', 'paragraph'),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (evaluation_id) REFERENCES evaluations(evaluation_id),
    FOREIGN KEY (tool_id) REFERENCES visualization_tools(tool_id)
);

-- RESPONSES
CREATE TABLE responses (
    response_id INT AUTO_INCREMENT PRIMARY KEY,
    evaluation_id INT,
    question_id INT,
    user_id INT,
    scenario_name VARCHAR(255),
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
('Scatter Plot'),
('GMap'),
('Bar Chart'),
('Pie Chart'),
('Heat Map'),
('Tree Map'),
('Node-Link Diagram'),
('Box Plot'),
('Histogram'),
('Radar Chart'),
('Choropleth Map'),
('Bubble Chart');



INSERT INTO evaluations (evaluation_name, description, created_by, scenario_id, tool_id) VALUES
('Evaluation 1', 'Data trend clarity test', 1, 1, 1),
('Evaluation 2', 'UI effectiveness test', 2, 2, 4),
('Evaluation 3', 'Outlier detection in charts', 3, 3, 5),
('Evaluation 4', 'Hierarchy understanding', 4, 4, 2),
('Evaluation 5', 'Network graph structure', 5, 5, 7),
('Evaluation 6', 'Heatmap clarity', 6, 6, 6),
('Evaluation 7', 'Pie chart readability', 7, 7, 5),
('Evaluation 8', 'Scatter plot insights', 8, 8, 2),
('Evaluation 9', 'Bar chart analysis', 9, 9, 4),
('Evaluation 10', 'Map-based visualization', 10, 10, 3),
('Evaluation 11', 'Time series readability', 11, 11, 1),
('Evaluation 12', 'Color contrast clarity', 12, 12, 6),
('Evaluation 13', 'Dashboard usability', 13, 13, 7),
('Evaluation 14', 'Cluster detection in heatmaps', 14, 14, 6),
('Evaluation 15', 'Network diagram analysis', 15, 15, 8),
('Evaluation 16', 'Interactive tool usage', 16, 16, 2),
('Evaluation 17', 'Live data handling', 17, 17, 1),
('Evaluation 18', 'Visualization preference', 18, 18, 4),
('Evaluation 19', 'AI interpretation aid', 19, 19, 13),
('Evaluation 20', 'Trend detection accuracy', 20, 20, 10);


-- Generalized INSERT queries for 130 questions (10 per visualization tool)
-- Visualization tool_id values assumed from your DB schema
-- Each INSERT: (evaluation_id, tool_id, question_text, question_type)

INSERT INTO questions (evaluation_id, tool_id, question_text, question_type) VALUES

-- Line Chart (tool_id = 1)
(1, 1, 'Can you determine the maximum value depicted in this line chart?', 'short-answer'),
(1, 1, 'What is the minimum value visible in this chart?', 'short-answer'),
(1, 1, 'Describe a section where the data rises quickly. When does this occur?', 'paragraph'),
(1, 1, 'Describe any noticeable downward trends and their implications.', 'paragraph'),
(1, 1, 'How stable are the trends throughout the chart?', 'paragraph'),
(1, 1, 'Are there any values that deviate significantly from the overall pattern?', 'yes-no'),
(1, 1, 'Does the data suggest a repeating or predictable behavior?', 'yes-no'),
(1, 1, 'Rate how easily you can follow the timeline in this visualization.', 'rating-scale'),
(1, 1, 'Would this visualization help in comparing trends over time?', 'yes-no'),
(1, 1, 'How suitable is this chart for long-term pattern analysis?', 'rating-scale'),

-- Scatter Plot (tool_id = 2)
(2, 2, 'What clusters or groupings of data points can you observe?', 'paragraph'),
(2, 2, 'Can you identify data points that fall outside the general distribution?', 'short-answer'),
(2, 2, 'Is there a visible correlation between the variables plotted?', 'yes-no'),
(2, 2, 'Are both axes scaled in a balanced and meaningful way?', 'yes-no'),
(2, 2, 'How would you describe the data density in different chart areas?', 'paragraph'),
(2, 2, 'Which quadrant contains the highest concentration of points?', 'short-answer'),
(2, 2, 'What do the axes represent in this visualization?', 'short-answer'),
(2, 2, 'Would adding a trend line help make the relationships clearer?', 'yes-no'),
(2, 2, 'Does this visualization help identify patterns in the data?', 'rating-scale'),
(2, 2, 'Is this visualization useful for distinguishing between different data groups?', 'yes-no'),

-- GMap (tool_id = 7)
(3, 7, 'Which areas are most densely populated with data points?', 'short-answer'),
(3, 7, 'Do any regions appear overrepresented in this visualization?', 'yes-no'),
(3, 7, 'What broad insights can you draw from the geographic distribution?', 'paragraph'),
(3, 7, 'Are there noticeable clusters of data activity?', 'yes-no'),
(3, 7, 'Which areas have minimal or no data representation?', 'short-answer'),
(3, 7, 'How effectively does this map convey spatial trends?', 'rating-scale'),
(3, 7, 'Rate the ease of interpreting intensity or density from this map.', 'rating-scale'),
(3, 7, 'Is there any apparent geographic bias in the data?', 'yes-no'),
(3, 7, 'What stands out most in this geographic visualization?', 'paragraph'),
(3, 7, 'Does this map help convey the scope or spread of the dataset?', 'rating-scale'),

-- Bar Chart (tool_id = 2)
(4, 2, 'Which bar represents the highest value?', 'short-answer'),
(4, 2, 'Identify the category with the lowest count.', 'short-answer'),
(4, 2, 'Are the bars organized in a logical order?', 'yes-no'),
(4, 2, 'Is it easy to visually compare bar lengths?', 'rating-scale'),
(4, 2, 'Are any values significantly larger than others?', 'yes-no'),
(4, 2, 'Do the bars correctly reflect proportions among categories?', 'yes-no'),
(4, 2, 'Can you identify visual patterns in how the categories are distributed?', 'paragraph'),
(4, 2, 'Are the bar labels clearly displayed?', 'yes-no'),
(4, 2, 'Would the chart be clearer if oriented horizontally?', 'yes-no'),
(4, 2, 'How well does this bar chart support quick comparisons?', 'rating-scale'),

-- Pie Chart (tool_id = 3)
(5, 3, 'Which slice occupies the largest portion?', 'short-answer'),
(5, 3, 'Estimate the percentage represented by the smallest slice.', 'short-answer'),
(5, 3, 'Are all slices labeled correctly and clearly?', 'yes-no'),
(5, 3, 'Is it easy to recognize the most dominant category?', 'rating-scale'),
(5, 3, 'Rate the distinguishability of colors used for each slice.', 'rating-scale'),
(5, 3, 'Would another chart type (e.g., bar) be more effective here?', 'yes-no'),
(5, 3, 'Can you compare similar-sized slices accurately?', 'yes-no'),
(5, 3, 'Does the layout allow for quick understanding of proportions?', 'rating-scale'),
(5, 3, 'Would additional interaction like tooltips enhance understanding?', 'yes-no'),
(5, 3, 'Is the pie chart an effective medium for this dataset?', 'rating-scale'),

-- Heat Map (tool_id = 6)
(6, 6, 'Which region shows the highest intensity or concentration?', 'short-answer'),
(6, 6, 'What visual trends can be identified from this heatmap?', 'paragraph'),
(6, 6, 'Are hot and cold spots clearly distinguishable?', 'rating-scale'),
(6, 6, 'Rate how intuitive the color scheme is.', 'rating-scale'),
(6, 6, 'Do labels enhance the interpretability of the heatmap?', 'yes-no'),
(6, 6, 'Are there symmetric or repetitive patterns?', 'paragraph'),
(6, 6, 'Which region draws your attention for deeper analysis?', 'paragraph'),
(6, 6, 'Is the legend or color scale clear and helpful?', 'rating-scale'),
(6, 6, 'Does the layout of the heatmap support easy navigation?', 'rating-scale'),
(6, 6, 'Do you notice any inconsistencies or visual mismatches?', 'yes-no'),

-- Tree Map (tool_id = 5)
(7, 5, 'Which block occupies the largest space?', 'short-answer'),
(7, 5, 'What does the size of each tile indicate?', 'short-answer'),
(7, 5, 'Are both size and color used to encode values?', 'yes-no'),
(7, 5, 'Can you identify primary categories and subcategories?', 'paragraph'),
(7, 5, 'How many levels of hierarchy are visible?', 'short-answer'),
(7, 5, 'Rate how easy it is to distinguish between blocks.', 'rating-scale'),
(7, 5, 'Would a traditional tree structure provide better clarity?', 'yes-no'),
(7, 5, 'Are some tiles visually cluttered or hard to read?', 'paragraph'),
(7, 5, 'Do you understand the color encoding used?', 'yes-no'),
(7, 5, 'Does the treemap layout facilitate exploration?', 'rating-scale'),

-- Node-Link Diagram (tool_id = 8)
(8, 8, 'How many direct connections are there from the main node?', 'short-answer'),
(8, 8, 'Are there isolated nodes in this graph?', 'yes-no'),
(8, 8, 'What does the thickness of each connection represent?', 'short-answer'),
(8, 8, 'Are you able to spot communities or groupings?', 'paragraph'),
(8, 8, 'Is the node placement visually meaningful?', 'rating-scale'),
(8, 8, 'Do overlapping links create confusion?', 'yes-no'),
(8, 8, 'Which node appears to have the most influence?', 'short-answer'),
(8, 8, 'Do link labels add or reduce clarity?', 'yes-no'),
(8, 8, 'Does node size aid interpretation?', 'rating-scale'),
(8, 8, 'Can you trace the path between specific nodes?', 'paragraph'),

-- Box Plot (tool_id = 13)
(9, 13, 'What value represents the median?', 'short-answer'),
(9, 13, 'What are the lower and upper quartile values?', 'short-answer'),
(9, 13, 'Are there any visible outliers in the distribution?', 'yes-no'),
(9, 13, 'Rate the symmetry of the boxplot.', 'rating-scale'),
(9, 13, 'Do the whiskers span a large range?', 'yes-no'),
(9, 13, 'Which category has the widest distribution?', 'short-answer'),
(9, 13, 'Are all groups clearly labeled?', 'yes-no'),
(9, 13, 'Does this box plot help detect skewness?', 'rating-scale'),
(9, 13, 'Is this type of visualization easy to understand?', 'rating-scale'),
(9, 13, 'What insights can you gather by comparing boxes?', 'paragraph'),

-- Histogram (tool_id = 10)
(10, 10, 'Which bin contains the highest frequency?', 'short-answer'),
(10, 10, 'How many bins does this histogram contain?', 'short-answer'),
(10, 10, 'Which bin has the lowest count?', 'short-answer'),
(10, 10, 'Does the data follow a normal distribution?', 'yes-no'),
(10, 10, 'Are all bins uniformly spaced?', 'yes-no'),
(10, 10, 'Are the groupings logically organized?', 'yes-no'),
(10, 10, 'Would changing the bin width help clarify the pattern?', 'yes-no'),
(10, 10, 'What is represented along the x-axis?', 'short-answer'),
(10, 10, 'Are there any unusually tall bars?', 'yes-no'),
(10, 10, 'Does the histogram effectively show data trends?', 'rating-scale'),

-- Radar Chart (tool_id = 12)
(11, 12, 'Which metric shows the highest value on the radar chart?', 'short-answer'),
(11, 12, 'Are there dimensions with zero or missing values?', 'yes-no'),
(11, 12, 'Rate how easy it is to compare values across dimensions.', 'rating-scale'),
(11, 12, 'Does the overall shape appear balanced?', 'yes-no'),
(11, 12, 'What key takeaways can you derive from this radar plot?', 'paragraph'),
(11, 12, 'Is the chart overly cluttered with overlapping lines?', 'yes-no'),
(11, 12, 'Would a tabular view offer better clarity?', 'yes-no'),
(11, 12, 'Are labels aligned well for readability?', 'yes-no'),
(11, 12, 'Which metric is most visually prominent?', 'short-answer'),
(11, 12, 'Does the shape aid your understanding of the dataset?', 'rating-scale'),

-- Choropleth Map (tool_id = 11)
(12, 11, 'Which region appears most prominent based on shading?', 'short-answer'),
(12, 11, 'Rate the accuracy with which density is represented visually.', 'rating-scale'),
(12, 11, 'Is the color gradient meaningful and easy to interpret?', 'rating-scale'),
(12, 11, 'Do lighter shades potentially mislead interpretation?', 'yes-no'),
(12, 11, 'Can you identify regions where bias might be present?', 'yes-no'),
(12, 11, 'Are all region boundaries clearly marked?', 'yes-no'),
(12, 11, 'Would interactive elements enhance clarity?', 'yes-no'),
(12, 11, 'Does the map legend help clarify the color coding?', 'rating-scale'),
(12, 11, 'Are smaller regions adequately visible?', 'yes-no'),
(12, 11, 'How confident are you in your interpretation of this visualization?', 'rating-scale'),

-- Bubble Chart (tool_id = 9)
(13, 9, 'What does the size of each bubble represent?', 'short-answer'),
(13, 9, 'Which bubble is most visually prominent?', 'short-answer'),
(13, 9, 'Are both axes clearly labeled?', 'yes-no'),
(13, 9, 'Does overlapping of bubbles affect readability?', 'yes-no'),
(13, 9, 'Do colors indicate distinct categories or values?', 'yes-no'),
(13, 9, 'How many unique groups are visible?', 'short-answer'),
(13, 9, 'Would reducing bubble size improve the layout?', 'yes-no'),
(13, 9, 'Rank the bubbles based on size or importance.', 'paragraph'),
(13, 9, 'What patterns or relationships can you deduce?', 'paragraph'),
(13, 9, 'Is the overall visualization too complex?', 'rating-scale');


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

