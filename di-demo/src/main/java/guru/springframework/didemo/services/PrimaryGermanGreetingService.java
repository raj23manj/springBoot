package guru.springframework.didemo.services;

import org.springframework.stereotype.Service;

//@Service
//@Primary
//@Profile("de")
// making one profile as default
public class PrimaryGermanGreetingService implements GreetingService {

    private GreetingRepository greetingRepository;

    public PrimaryGermanGreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }

    @Override
    public String sayGreeting() {
        return greetingRepository.getGermanGreeting();
    }

//    @Override
//    public String sayGreeting() {
//        return "Primärer Grußdienst";
//    }
}
