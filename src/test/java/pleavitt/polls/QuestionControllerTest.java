package pleavitt.polls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PollsApplication.class)
public class QuestionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private QuestionRepository repository;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void ping() throws Exception {

        this.mockMvc.perform(get("/")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));
    }

    @Test
    public void retrieveQuestionsTest() throws Exception {
        mockMvc.perform(get("/questions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("[0].question").value("What is your favourite meat?"))
                .andExpect(jsonPath("[4].question").value("What is your favourite colour?"));
    }

    @Test
    public void createQuestionTest() throws Exception {

        Question newQuestion = new Question("What was your first car?", Arrays.asList(new Choice("Hyundai Excel"), new Choice("Holden Commodore"), new Choice("Volkswagen Beetle")));

        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromPojo = gsonBuilder.toJson(newQuestion);


        this.mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromPojo))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question", is("What was your first car?")));
    }

    @Test
    public void createEmptyQuestionTest() throws Exception {

        Question newQuestion = new Question("", Arrays.asList(new Choice("Pie"), new Choice("Chips"), new Choice("Hot Dog")));

        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromPojo = gsonBuilder.toJson(newQuestion);

        this.mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromPojo))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createEmptyChoiceTest() throws Exception {

        Question newQuestion = new Question("Favourite Drink?", Arrays.asList());

        Gson gsonBuilder = new GsonBuilder().create();
        String jsonFromPojo = gsonBuilder.toJson(newQuestion);

        this.mockMvc.perform(post("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonFromPojo))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


}
