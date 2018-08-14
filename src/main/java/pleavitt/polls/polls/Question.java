package pleavitt.polls.polls;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {

    String question;

    List<Choice> choices;

    public Question(String question, List<Choice> choices){
        this.question = question;
        this.choices = choices;
    }
}
