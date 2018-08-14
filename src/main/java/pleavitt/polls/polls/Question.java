package pleavitt.polls.polls;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Question {

    String question;

    List<Choice> choices;

    public Question(String question, List<Choice> choices){
        this.question = question;
        this.choices = choices;
    }
}
