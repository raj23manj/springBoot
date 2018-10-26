package guru.springframework.didemo.config;

import guru.springframework.didemo.services.GreetingRepository;
import guru.springframework.didemo.services.GreetingService;
import guru.springframework.didemo.services.GreetingServiceFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
public class GreetingServiceConfig {
//    Mentioning only like this takes the only implementation of that service and inject's if multiple ones
//    are there then use qualifier
//    @Bean
//    GreetingServiceFactory greetingServiceFactory(GreetingRepository repository) {
//        return  new GreetingServiceFactory(repository);
//    }

    @Bean
    GreetingServiceFactory greetingServiceFactoryCreate(@Qualifier("greetingRepositoryImpl")GreetingRepository repository) {
        return  new GreetingServiceFactory(repository);
    }
/* greetingServiceFactory is the class name intialzied in bean container from line 23, and is not the
 * method name on line 22 */
    @Bean
    @Primary
    @Profile({"default", "en"})
    GreetingService primaryGreetingService(GreetingServiceFactory greetingServiceFactory){
        return greetingServiceFactory.createGreetingService("en");
    }

    @Bean
    @Primary
    @Profile("es")
    GreetingService primarySpanishGreetingService(GreetingServiceFactory greetingServiceFactory){
        return greetingServiceFactory.createGreetingService("es");
    }

    @Bean
    @Primary
    @Profile("de")
    GreetingService primaryGermanGreetingService(GreetingServiceFactory greetingServiceFactory){
        return greetingServiceFactory.createGreetingService("de");
    }
}
