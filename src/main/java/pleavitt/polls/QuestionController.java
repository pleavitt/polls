package pleavitt.polls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;

@RestController
public class QuestionController {

    @Autowired
    private QuestionRepository repository;

    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/questions")
    public Collection<Question> getQuestions() {
        return repository.findAll();
    }

    @PostMapping("/questions")
    @ResponseStatus(HttpStatus.CREATED)
    public Question createQuestion(@Valid @RequestBody Question question, HttpServletResponse response) {

        Question result = repository.save(question);
        response.setHeader("Location:", "/questions/" + result.getId());
        return result;
    }
}
