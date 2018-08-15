package pleavitt.polls;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
public class PollsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PollsApplication.class, args);
    }

    @Bean
    ApplicationRunner init(QuestionRepository repository) {
        return args -> {

            Question firstQuestion = new Question("What is your favourite meat?", Arrays.asList(new Choice("none"), new Choice("pork"), new Choice("Steak"), new Choice("Chicken")));
            Question secondQuestion = new Question("How many vegemite sandwiches can you eat for lunch?", Arrays.asList(new Choice("0"), new Choice("1"), new Choice("2"), new Choice("3"), new Choice("4"), new Choice("5"), new Choice("6")));
            Question thirdQuestion = new Question("What was your first pet?", Arrays.asList(new Choice("Cat"), new Choice("Dog"), new Choice("Guinea Pig"), new Choice("Bird")));
            Question fourthQuestion = new Question("What was your first job?", Arrays.asList(new Choice("English"), new Choice("Mathematics"), new Choice("Science")));
            Question fifthQuestion = new Question("What is your favourite colour?", Arrays.asList(new Choice("Red"), new Choice("Green"), new Choice("Blue")));

            repository.save(firstQuestion);
            repository.save(secondQuestion);
            repository.save(thirdQuestion);
            repository.save(fourthQuestion);
            repository.save(fifthQuestion);

            repository.findAll().forEach(System.out::println);

        };
    }
}
