package pleavitt.polls;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pleavitt.polls.choice.Choice;
import pleavitt.polls.question.Question;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollsApplication.class)
@WebAppConfiguration
public class PollsIT {

    private static RequestSpecification setup;

    @BeforeClass
    public static void setup() {

        setup = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:8080/")
                .addFilter(new ResponseLoggingFilter())
                .addFilter(new RequestLoggingFilter())
                .build();
    }

    @Test
    public void ping() {

        given()
                .spec(setup)
                .when()
                .get("")
                .then()
                .statusCode(200);
    }

    public void retrieveQuestionsTest() {

        given()
                .spec(setup)
                .when()
                .get("questions")
                .then()
                .body("question", hasItems("What is your favourite meat?", "What is your favourite colour?"))
                .body("question", not(hasItems("What is your favorite meat?", "What is your favourite hat?")))
                .body("question", hasSize(greaterThanOrEqualTo(5)))
                .statusCode(200);
    }

    public void createQuestionCreation() {

        Question newQuestion = new Question("What was your first car?", Arrays.asList(new Choice("Hyundai Excel"), new Choice("Holden Commodore"), new Choice("Volkswagen Beetle")));
        Question notQuestion = new Question("What is your favourite Subject?", Arrays.asList(new Choice("English"), new Choice("Mathematics"), new Choice("Science")));

        Question responseQuestion = given()
                .spec(setup)
                .body(newQuestion)
                .when()
                .post("questions")
                .then()
                .statusCode(201)
                .extract().as(Question.class);

        assertThat(responseQuestion).isEqualToIgnoringGivenFields(newQuestion, "id", "published_at");

        assertThat(responseQuestion.getQuestion()).isNotEqualTo(notQuestion.getQuestion());
    }

    public void createEmptyQuestion() {

        Question emptyQuestion = new Question("", Arrays.asList(new Choice("Economics"), new Choice("Art"), new Choice("Social Studies")));

        given()
                .spec(setup)
                .body(emptyQuestion)
                .when()
                .post("questions")
                .then()
                .statusCode(400);
    }

    public void createQuestionEmptyChoices() {

        Question emptyQuestion = new Question("How long is a piece of string?", Arrays.asList());

        given()
                .spec(setup)
                .body(emptyQuestion)
                .when()
                .post("questions")
                .then()
                .statusCode(400);
    }

}
