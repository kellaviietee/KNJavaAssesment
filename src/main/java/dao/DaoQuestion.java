package dao;

import question.Difficulty;
import question.Question;
import question.Topic;
import response.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DaoQuestion {
    private static final String url = "jdbc:mysql://localhost:3306/quiz";
    private static final String username = "root";
    private static final String password = "Root1Root1";

    public static List<Response> getResponsesByQuestionId(int questionId) {
        List<Response> responses = new ArrayList<>();
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = "select * from responses where question_id = " + questionId;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Response response = new Response(rs.getInt("id"),
                            rs.getInt("question_id"),
                            rs.getString("response_text"),
                            rs.getBoolean("is_correct"));
                    responses.add(response);
                }
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return responses;
    }

    public static Optional<Question> getQuestionById(int id) {
        Optional<Question> question = Optional.empty();
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = "select * from questions where id = " + id;
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Question actualQuestion = new Question(
                            rs.getInt("id"),
                            Topic.valueOf(rs.getString("topic")),
                            Difficulty.valueOf(rs.getString("difficulty")),
                            rs.getString("question"),
                            getResponsesByQuestionId(id)
                    );
                    question = Optional.of(actualQuestion);
                }
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return question;
    }

    public static boolean saveResponseToDatabase(Response response) {
        boolean result = false;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = String.format("insert into responses (id, question_id, response_text, is_correct) \n " +
                            "values(%d, %d, '%s', %b)", response.getId(), response.getQuestion_id(),
                    response.getResponseText(), response.isCorrect());
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return result;
    }

    public static boolean deleteQuestionById(int id) {
        if (!deleteResponsesByQuestionId(id)) {
            return false;
        }
        boolean result = false;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = "delete from questions where id = " + id;
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return result;
    }

    public static boolean deleteResponsesByQuestionId(int questionId) {
        boolean result = false;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = "delete from responses where question_id = " + questionId;
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return result;
    }

    public static boolean saveQuestionToDatabase(Question question) {
        boolean result = false;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = String.format("insert into questions (id, topic, difficulty, question) \n " +
                    "values(%d, '%s', '%s', '%s')", question.getId(), question.getTopic(), question.getDifficulty(), question.getQuestion());
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        for (Response response : question.getResponses()) {
            saveResponseToDatabase(response);
        }
        return result;
    }

    public static boolean updateQuestion(Question question) {
        boolean result = false;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = String.format("update questions set question = '%s', topic = '%s'," +
                            " difficulty = '%s' where id = %d",
                    question.getQuestion(), question.getTopic().toString(),
                    question.getDifficulty().toString(), question.getId());
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        for (Response response : question.getResponses()) {
            updateResponse(response);
        }
        return result;
    }

    public static boolean updateResponse(Response response) {
        boolean result = false;
        int responseCorrectValue = response.isCorrect() ? 1 : 0;
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = String.format("update responses set question_id = '%d', response_text = '%s'," +
                            " is_correct = '%d' where id = %d",
                    response.getQuestion_id(), response.getResponseText(),
                    responseCorrectValue, response.getId());
            try (Statement stmt = connection.createStatement()) {
                int resultInt = stmt.executeUpdate(query);
                result = resultInt > 0;
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return result;
    }

    public static List<Question> getQuestionsByTopic(Topic topic) {
        List<Question> questions = new ArrayList<>();
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
            String query = "select * from questions where topic = '" + topic.toString() + "'";
            try (Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    Question actualQuestion = new Question(
                            rs.getInt("id"),
                            Topic.valueOf(rs.getString("topic")),
                            Difficulty.valueOf(rs.getString("difficulty")),
                            rs.getString("question"),
                            getResponsesByQuestionId(rs.getInt("id"))
                    );
                    questions.add(actualQuestion);
                }
            } catch (SQLException e) {
                System.out.println("statement executing problem");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
        }
        return questions;
    }
}
