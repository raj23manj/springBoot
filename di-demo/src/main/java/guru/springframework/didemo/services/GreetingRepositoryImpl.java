package guru.springframework.didemo.services;

import org.springframework.stereotype.Component;

@Component
public class GreetingRepositoryImpl implements GreetingRepository {

    @Override
    public String getEnglishGreeting() {
        return "Hello - Primary Greeting service 1";
    }

    @Override
    public String getSpanishGreeting() {
        return "Servicio de Saludo Primario 2";
    }

    @Override
    public String getGermanGreeting() {

        return "Primärer Grußdienst 3";

    }
}