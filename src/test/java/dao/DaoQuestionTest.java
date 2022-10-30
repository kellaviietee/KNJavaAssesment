package dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import question.Difficulty;
import question.Question;
import question.Topic;
import response.Response;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DaoQuestionTest {
    private Question question;
    private List<Response> responses;

    @BeforeEach
    void beforeEach() {
        Response testResponse = new Response(2, 1, "Poland", false);
        Response testResponse2 = new Response(3, 1, "Czech Republic", false);
        Response testResponse3 = new Response(4, 1, "Ukraine", true);
        Response testResponse4 = new Response(5, 1, "Slovakia", true);
        List<Response> responseList = List.of(testResponse, testResponse2, testResponse3, testResponse4);
        responses = responseList;
        question = new Question(1, Topic.GEOGRAPHY, Difficulty.MEDIUM,
                "What two countries border directly north of Hungary?", responseList);
    }

    @Test
    void testGettingQuestionFromDatabase() {
        //when
        Optional<Question> databaseQuestion = DaoQuestion.getQuestionById(1);
        //then
        databaseQuestion.ifPresent(value -> assertEquals(question, value));
    }

    @Test
    void testSaveNewQuestionToDataBase() {
        //given
        Response testResponse = new Response(101, 100, "Poland", false);
        Response testResponse2 = new Response(102, 100, "Czech Republic", false);
        Response testResponse3 = new Response(103, 100, "Ukraine", true);
        Response testResponse4 = new Response(104, 100, "Slovakia", true);
        List<Response> responseList = List.of(testResponse, testResponse2, testResponse3, testResponse4);
        responses = responseList;
        Question testQuestion = new Question(100, Topic.GEOGRAPHY, Difficulty.MEDIUM,
                "What two countries border directly north of Hungary?", responseList);
        //when
        Boolean isQuestionSaved = DaoQuestion.saveQuestionToDatabase(testQuestion);
        //then
        assertTrue(isQuestionSaved);
    }

    @Test
    void testDeletingQuestionFromDatabaseById() {
        //given
        int notExistingId = 101;
        int existingId = 100;
        //when
        boolean wasNotDeleted = DaoQuestion.deleteQuestionById(notExistingId);
        boolean wasDeleted = DaoQuestion.deleteQuestionById(existingId);
        //then
        assertFalse(wasNotDeleted);
        assertTrue(wasDeleted);
    }

    @Test
    void testUpdatingQuestion() {
        //given
        Question currentQuestion = DaoQuestion.getQuestionById(3).get();
        //when
        Question updatedQuestion = new Question(currentQuestion.getId(),Topic.VARIOUS,Difficulty.EASY,
                currentQuestion.getQuestion(), currentQuestion.getResponses());
        DaoQuestion.updateQuestion(updatedQuestion);
        //then
        assertEquals(updatedQuestion, DaoQuestion.getQuestionById(3).get());
        DaoQuestion.updateQuestion(currentQuestion);

    }

    @Test
    void testGettingQuestionsByTopic() {
        //when
        List<Question> questionList = DaoQuestion.getQuestionsByTopic(Topic.SPORTS);
        for (Question question : questionList) {
            assertEquals(Topic.SPORTS, question.getTopic());
        }
    }

}