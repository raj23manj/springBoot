package guru.springframework.didemo.services;

import org.springframework.stereotype.Component;

@Component
public class DemoGreetingRepositoryImpl implements GreetingRepository {
    @Override
    public String getEnglishGreeting() {
        return "Demo Greeting -  Hello - Primary Greeting service 1";
    }

    @Override
    public String getSpanishGreeting() {
        return "Demo Greeting - Servicio de Saludo Primario 2";
    }

    @Override
    public String getGermanGreeting() {

        return "Demo Greeting -  Primärer Grußdienst 3";

    }
}
