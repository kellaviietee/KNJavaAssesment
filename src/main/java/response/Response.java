package response;

public class Response {

    private final int id;
    private final int question_id;
    private final String responseText;
    private final  boolean isCorrect;

    public Response(int id, int question_id, String responseText, boolean isCorrect) {
        this.id = id;
        this.question_id = question_id;
        this.responseText = responseText;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getResponseText() {
        return responseText;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    @Override
    public String toString() {
        return "Response{" +
                "question_id=" + question_id +
                ", responseText='" + responseText + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Response response = (Response) o;

        if (id != response.id) return false;
        if (question_id != response.question_id) return false;
        if (isCorrect != response.isCorrect) return false;
        return responseText.equals(response.responseText);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + question_id;
        result = 31 * result + responseText.hashCode();
        result = 31 * result + (isCorrect ? 1 : 0);
        return result;
    }
}
