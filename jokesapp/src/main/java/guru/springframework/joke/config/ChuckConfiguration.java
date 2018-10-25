package guru.springframework.joke.config;

import guru.springframework.norris.chuck.ChuckNorrisQuotes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// this type of configuration is used a lot when using a third part package like chucknorris here
@Configuration
public class ChuckConfiguration {
    @Bean
    public ChuckNorrisQuotes chuckNorrisQuotes() {
        return new ChuckNorrisQuotes();
    }
}
