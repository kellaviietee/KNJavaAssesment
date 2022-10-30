package quiz;

import question.Question;

public class Quiz {

    private Question[] questions;

    public Quiz(Question[] questions) {
        this.questions = questions;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }
}
