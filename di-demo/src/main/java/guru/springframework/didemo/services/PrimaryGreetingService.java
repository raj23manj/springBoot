package guru.springframework.didemo.services;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

//@Service
//@Primary // making it default if more beans of same impl, without others having an @primary mentioned
//@Profile({"en", "default"})
// making one profile as default, when more @Primary are there
public class PrimaryGreetingService implements GreetingService {

    private GreetingRepository greetingRepository;

    public PrimaryGreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public String sayGreeting() {
        return greetingRepository.getEnglishGreeting();
    }

//    @Override
//    public String sayGreeting() {
//        return "Hello - Primary Greeting Service";
//    }
}
