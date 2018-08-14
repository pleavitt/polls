package pleavitt.polls.polls;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Choice {
    private String choice;
    private int votes;

    public Choice(String choice){
        this.choice = choice;
    }
}
