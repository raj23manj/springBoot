package guru.services;

import org.springframework.stereotype.Service;

@Service
//@Primary
//@Profile("de")
// making one profile as default
public class PrimaryGermanGreetingService implements GreetingService {
    @Override
    public String sayGreeting() {
        return "Primärer Grußdienst";
    }
}
