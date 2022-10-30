package question;

import response.Response;

import java.util.List;

public class Question {
    private  int id;
    private  Topic topic;
    private  Difficulty difficulty;
    private  String question;
    private  List<Response> responses;

    public Question(int id, Topic topic, Difficulty difficulty, String question, List<Response> responses) {
        this.id = id;
        this.topic = topic;
        this.difficulty = difficulty;
        this.question = question;
        this.responses = responses;
    }

    public int getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question1 = (Question) o;

        if (id != question1.id) return false;
        if (topic != question1.topic) return false;
        if (difficulty != question1.difficulty) return false;
        if (!question.equals(question1.question)) return false;
        return responses.equals(question1.responses);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + topic.hashCode();
        result = 31 * result + difficulty.hashCode();
        result = 31 * result + question.hashCode();
        result = 31 * result + responses.hashCode();
        return result;
    }
}
