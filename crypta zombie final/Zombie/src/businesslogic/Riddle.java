package businesslogic;

public class Riddle {
    private String question;
    private String correctAnswer;
    private String[] options;

    public Riddle(String question, String correctAnswer, String[] options) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }
}
