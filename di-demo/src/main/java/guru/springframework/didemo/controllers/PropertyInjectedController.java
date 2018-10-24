package guru.springframework.didemo.controllers;

import guru.springframework.didemo.services.GreetingService;
import guru.springframework.didemo.services.GreetingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class PropertyInjectedController {
    // best way is to use explicit @Qualifier
    @Autowired
    // 1st
    // this will pass because of the hard type
    //public GreetingServiceImpl greetingService;

    // 2nd
    // @Qualifier("greetingServiceImpl")
    //public GreetingService greetingService;

    //3rd
    // from 2nd, if we give the property name same as the Bean name name @Qualifier is not required
    public GreetingService greetingServiceImpl;
    public String sayHello(){
//        return greetingService.sayGreeting();
        return greetingServiceImpl.sayGreeting();
    }
}
