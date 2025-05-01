package DataObject;

/**
 * This class encapsulates all of the information needed for an evaluation question.
 * Created by the DB Manager class after a DB query.
 * <p>
 * Only getter methods, since fields should not be modified after creation.
 */
public class Question {
    private int questionId;
    private int evaluationId;
    private String questionText;
    private String questionType;
    private String correctAnswer;

    /**
     * Constructor for the Question class.
     */
    public Question(int questionId, int evaluationId, String questionText, String questionType, String correctAnswer) {
        this.questionId = questionId;
        this.evaluationId = evaluationId;
        this.questionText = questionText;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
    }

    //  Ensure these methods exist
    public int getQuestionId() {
        return questionId;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public String getQuestionText() {  // Fix: Rename if needed
        return questionText;
    }

    public String getQuestionType() {  // Fix: Rename if needed
        return questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
