package pleavitt.polls.polls;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Choice {
    private String choice;
    private int votes;

    public Choice(String choice){
        this.choice = choice;
    }
}
