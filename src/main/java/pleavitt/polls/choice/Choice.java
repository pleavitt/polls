package pleavitt.polls.choice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pleavitt.polls.question.Question;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    private String choice;

    private int votes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;

    public Choice(String choice) {
        this.choice = choice;
    }

    @Override
    public String toString() {
        return "Choice(id=" + id + ", choice=" + this.choice + ", votes=" + this.votes + ")";
    }
}
