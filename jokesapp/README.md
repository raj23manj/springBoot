# Spring Boot Jokes App

# Spring Boot Documentation (must spen time and read)
  * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-documentation

 This sample web app
 
 # Good Links
   * https://stackoverflow.com/questions/19795709/understanding-upper-and-lower-bounds-on-in-java-generics
   * https://www.journaldev.com/721/java-annotations
   * https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java
   * http://www.techferry.com/articles/hibernate-jpa-annotations.html


# How to create a new project with spring initalizer
  * create new project > select spring initializer > selected dependencies
    then usual
    
# Ascii Art
  * http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20   

# How requests work in spring 
  * https://stackoverflow.com/questions/17235794/how-does-spring-mvc-handle-multiple-users
  * https://stackoverflow.com/questions/7457190/how-are-threads-allocated-to-handle-servlet-request
  * https://stackoverflow.com/questions/11139571/scope-of-a-spring-controller-and-its-instance-variables
    * Yes, controllers in Spring-MVC are singletons. Between multiple requests your class variable get shared and might 
      result into ambiguity. You can use @Scope("request") annotation above your controller to avoid such ambiguity.
  * Request & Principal
    * http://forum.spring.io/forum/spring-projects/security/95918-how-to-handle-multiple-logged-in-users 
    * For Async: 
      * https://www.baeldung.com/spring-security-async-principal-propagation
      * https://github.com/eugenp/tutorials/blob/master/spring-security-rest/src/main/java/org/baeldung/spring/SecurityJavaConfig.java
  * https://www.quora.com/How-do-servlets-handle-multiple-requests     
  
# Spring Container
  * it is known as Application Context
  * IOC
  * DI     
  
# Inversion Of Control(IOC) (Object Creation Factory)
  * Object creation, Object management
  * objects ready with all dependencies
  * DI via interface is highly preferred thank Concrete Classes(using the class it self directly)
    * Allows runtime to decide implementation to inject
    * follows interface Segregation principle of SOLID
    * Also makes code testable

# Dependency Injection(auto wiring)
  * objects with dependencies requirements will be injected using DI. If a class needs a service it will be injected 
  * Types: 
    * Constructor Injection
    * Setter Injection    
    * Property Injection

# Bean xml configuration => spring-demo-one
  * need to create .xml files and create beans there
  * no annotation for auto wiring, hence pass it via constructor-arg or property value for beans
     * for this type to work need do 
       * //load spring configuration
         ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
         Coach theCoach = context.getBean("myCoach", Coach.class);       
         		
     * <bean id="myCricketCoach" scope="singleton"
          		  class="com.luv2code.springdemo.CricketCoach">
          		  <!-- set up setter injection -->
          		  <property name="fortuneService" ref="myFortuneService" />
          		  <!-- set literal values -->
          		  <!-- <property name="emailAddress" value="thebestcoach@luv2code.com" />
          		  <property name="team" value="Royal Challengers Bangalore" /> -->
          		  <constructor-arg ref="myFortuneService" />
          		  <property name="emailAddress" value="${foo.email}" />
          		  <property name="team" value="${foo.team}" />    		  
           </bean> 
           
     * this is equal to for constructor-arg => CricketCoach myCricketCoach = new CricketCoach(myFortuneService)
     * property => myCricketCoach.setEmailAddress('thebestcoach@luv2code.com')
     * property is the class variables and constructor-arg, these are DI instead of using auto wiring 
       *  property => using setter method injection
     * Attaching string literals, ${foo.email}
       * in .xml file add =>  <context:property-placeholder location="classpath:sport.properties"/>  
       

# Bean Scope 
  - https://www.baeldung.com/spring-bean-scopes, 
  - https://www.journaldev.com/21039/spring-bean-scopes,
  - https://grokonez.com/spring-framework/spring-bean-scope-annotation-requestscope-sessionscope-applicationscope
  
  * Singleton Scope
    - Default is singleton 
    - only one instance of the bean is created and the same instance will be shared with all other's. It will be cached 
    - Be careful of making static variables and final variables, it will lead to race condition. Synchronisation needs to be applied which costs in performance      
  * Prototype Scope
    - new instance will be created every time
  * Request Scope
    - Scoped to an HTTP Web Request. Only Used for web apps
    - A single instance per http request, only valid in the context of web-aware Spring Application-Context
  * Session scope
    - Scoped to an HTTP Web Session. Only Used for web apps 
    - A Single instance per http session, only valid in the context of web-aware Spring Application-Context    
  * global-session
    - Scoped to an Global HTTP Web Session. Only Used for web apps 
    - A single Instance per Global Session. Typically used in a portlet Context. only valid in the context of web-aware Spring Application-Context     
    - Not Used Much, legacy code.
  * Application
    - bean is scoped to the lifecycle of Servlet Context. only valid in the context of web-aware 
  * WebSocket
    - Scopes a Single Bean definition to the lifecycle of a WebSocket. Only Valid in the Context of web-aware Spring Application-Context 
  * Custom Scope 
    - Spring Scopes are Extensible, and we can define our own Scope by implementing spring's 'Scope' Interface
    
  * Note
    - We cannot override the built in singleton and prototype scopes    
    
# Bean Life Cycles
  *  XML:
       * <bean id="myCoach" 
            		  class="com.luv2code.springdemo.TrackCoach"
            		  init-method="doMyStartupStuff"
            		  destroy-method="doMyCleanUpStuff">
            		  <!-- setup constructor injection -->
            		  <constructor-arg ref="myFortuneService" />
             </bean>     
  * For "prototype" scoped beans, Spring does not call the destroy method.  Gasp!  
  * In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean: the container 
    instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further 
    record of that prototype instance.  
  * Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case 
    of prototypes, configured destruction lifecycle callbacks are not called. The client code must clean up prototype-scoped 
    objects and release expensive resources that the prototype bean(s) are holding. 
    
  
  # John Videos (section 3 => 43)
    * process life-cycle
      * Start-up
          - Instantiate -> populate-properties -> call setBeanName of BeanNameAware -> call setBeanFactory of BeanFactoryAware ->
            call SetApplicationContext of ApplicationContextAware -> PreInitializtion(Bean PostProcessors) -> afterPropertiesSet of intializing Beans
            -> Custom Init Method -> Post Intialization(Bean Post Processors) -> Bean ready to use 
      
      * Shutdown  
        - Container ShutDown -> Disposable Bean's destroy() -> Call Custom Destroy Method  
    
    * CallBack Interfaces
      * spring has two interfaces you can implement for call back events
        - IntializingBean.afterPropertiesSet() => called after properties are set
        - DisposableBean.destroy() => called during bean destruction
        
    * Bean LifeCycle Annotations
      * @PostConstruct 
        * will be called after the bean has been constructed, but before its returned to the requesting object
        
      * @PreDestroy    
        * is called just before the bean is destroyed  
        
    * Bean PostProcessors
      * Gives us a means to tap into the Spring COntext life cycle and interact with the beans as they are processed
      
      * postProcessBeforeInitialization 
        * called before bean initialization method
      
      * postProcessAfterInitialization
        * called after bean initialization 
        
    * https://github.com/raj23manj/springBoot/blob/master/di-demo/src/main/java/guru/springframework/didemo/CustomBeanPostProcessor.java       
        
    * Aware Interfaces
      * used to access the spring Framework infrastructure
      * These are largely used inside the framework
      * Rarely used by developers 
        - ApplicationContextAware => commonly used   
        - ApplicationEventPublishAware  => can setup custom events and listen to it in the application
        
    * on git-hub
    - https://github.com/raj23manj/springBoot/blob/master/di-demo/src/main/java/guru/springframework/didemo/LifeCycleDemoBean.java       
    
# Beans => Java Annotations(IOC using annotation) (spring-demo-annotation)
  * Annotations are meta data of class(like labels on shoe box describing the content color, size, make)
  * Component Scan, will scan java application for annotation(@Bean, @service, etc) and register the beans in the Spring Container(Application Context)            
  * To enable component scanning in XML configuration
     * <context:component-scan base-package="com.luv2code.springdemo"></context:component-scan> 
     
# Spring Configuration with Java Annotations & DI

  * DI via interface is highly preferred than Concrete Classes(using the class it self directly)
    * Allows runtime to decide implementation to inject
    * follows interface Segregation principle of SOLID
    * Also makes code testable
    
    
  * Autowiring/Injections 
    * Constructor Injection
     -  public class TennisCoach {
          private FortuneService fortuneService;
          //constructor injection
            @Autowired 
          	public void TennisCoach(FortuneService theFortuneService) {
          		fortuneService = theFortuneService;
          	}
        }
     - As of Spring Framework 4.3, an @Autowired annotation on such a constructor is no longer necessary if the target 
       bean only defines one constructor to begin with. However, if several constructors are available, at least one 
       must be annotated to teach the container which one to use.  
        	
    * Setter Injection or as normal method
     -  public class TennisCoach {
            private FortuneService fortuneService;
            // constructor
            public void TennisCoach() {}
           
              @Autowired   //as setter
              public void setFortuneService(FortuneService theFortuneService) {
            		fortuneService = theFortuneService;
              }
              
              //    @Autowired // as normal method
              //	public void initFortuneService(FortuneService theFortuneService) {
              //		System.out.println("autowiring the service");
              //		fortuneService = theFortuneService;
              //	} 
            
         }
       
    * Field Injection => Accomplished using Java Reflections
      -  public class TennisCoach {
              @Autowired
              private FortuneService fortuneService;
              // constructor
              public void TennisCoach() {}   
          }
          
    * Which One to use
      - Constructor Injection is most preffered  
      - Any one can be used, as stated in spring doc it is consistance in performance
    
    * Qualifiers for DI (multiple implementation of an interface and being injected)
      * By default it will not inject any implementation, will throw error 
        "Error creating Bean name 'name of bean Injection of autowired dependincies failed"
        " NoUniqueBeanDefinitionException" 
      * @Qualifier('servicename-name, beanid of class in samll')
      
      * Constructor Based
        -   @Autowired
            public TennisCoach(@Qualifier("randomFortuneService") FortuneService theFortuneService) {
        
                System.out.println(">> TennisCoach: inside constructor using @autowired and @qualifier");
                
                fortuneService = theFortuneService;
            }  
      
      * Filed Injection
        - @Qualifier("randomFortuneService") // when multiple implementations of interface is there to specify specific one
          	private FortuneService fortuneService;  
          	
      * Important Note
         * Annotations - Default Bean Names ... and the Special Case
           
           In general, when using Annotations, for the default bean name, Spring uses the following rule.
           
           If the annotation's value dosen't indicate a bean name, an appropriate name will be built based on the short name of the class (with the first letter lower-cased).
           
           For example:
           
           HappyFortuneService --> happyFortuneService
           
           ---
           
           However, for the special case of when BOTH the first and second characters of the class name are upper case, then the name is NOT converted.
           
           For the case of RESTFortuneService
           
           RESTFortuneService --> RESTFortuneService
           
           No conversion since the first two characters are upper case.
           
           Behind the scenes, Spring uses the Java Beans Introspector to generate the default bean name. Here's a screenshot of the documentation for the key method.    	     

         As always, you can give explicity names to your beans.
         
         @Component("foo")
         public class RESTFortuneService .... {
             
         }
       
        * When Spring creates the beans it follows this general process
          (https://www.udemy.com/spring-hibernate-tutorial/learn/v4/t/lecture/5385040?start=0)
          1. Construct an instance of class
          2. Inject dependencies
          3. Set properties etc (@Value) 
          
# Primary Annotation for Spring Beans(@Primary) => di-demo(Jhon)
  *  When there multiple beans of the same type which all implement the same interface, we can use this to choose which 
     one by default to be selected
     
  -  @Service
     @Primary // making it default if more beans of same impl, without others having an @primary mentioned
     //@Profile({"en", "default"})
     // making one profile as default, when more @Primary are there => wrong
     public class PrimaryGreetingService implements GreetingService {
     
         private GreetingRepository greetingRepository;
     
         public PrimaryGreetingService(GreetingRepository greetingRepository) {
             this.greetingRepository = greetingRepository;
        }
     }     
     
# Profiles
  * Profiles are used to set at runtime which configures spring how it wires up things
  * when run with profiles spring brings all beans marked with that profile
  - @Service
    //@Primary // making it default if more beans of same impl, without others having an @primary mentioned
    @Profile({"en", "default"}) // multiple profiles it can support
    // making one profile as default, when more @Primary are there
    public class PrimaryGreetingService implements GreetingService {
    
       private GreetingRepository greetingRepository;
    
       public PrimaryGreetingService(GreetingRepository greetingRepository) {
           this.greetingRepository = greetingRepository;
      }
    }              
     
  * Default profiles 
    * there is default profile available by default when no other profiles are active, and springboot at run time will use that.
    * we can set a profile as default as below
    - @Profile({"en", "default"}) 
      - if 'en' is te as active profile use it, or else this will be default profile
      - example set dev env as default profile.
        - @Profile({"dev", "default"})
           
# Bean Scopes With Anotations(@Scope)
    * @Scope("prototype") //"singleton" is default
      public class TennisCoach {
         @Autowired
         private FortuneService fortuneService;
         // constructor
         public void TennisCoach() {}   
      }
    * https://www.boraji.com/spring-singleton-scope-example-using-scope-annotation
    
# Bean Life Cycle with Annotations
  * // bean life cycle pre and post calls
    // init method
    @PostConstruct
    public void doMyStartUpStuff() {
        System.out.println("Init tennis init bean");
    }
    
    @PreDestroy
    public void doMyCleanUpStuff() {
        System.out.println("Destroy tennis destroy bean");
    }
  
  * Access modifier
    
    The method can have any access modifier (public, protected, private)
    
    Return type
    The method can have any return type. However, "void' is most commonly used. If you give a return type just note that
    you will not be able to capture the return value. As a result, "void" is commonly used.
    
    Method name
    The method can have any method name.
    
    Arguments
    The method can not accept any arguments. The method should be no-arg.  
    
# Configuring Spring Container using Java Code
  * in properties file
   * api.host=localhost
   * ane.authentication.url=https://${ane.host}/login/AuthenticationCheck?tok= 
  * create a java class and annotate as @Configuration
  * Add component Scanning support: @ComponentScan
  * Read Spring Java Configuration class
  * Retrive bean from Spring Container
  - //sportConfig.java
    @Configuration
    @ComponentScan("com.luv2code.springdemo") // it scans all @component in package and intializes
    @PropertySource("classpath:sport.properties") // this also gets loaded into spring context like bean and can be accessed using @Value("${foo.email}")
    public class SportConfig {
    
        // inject dependency, define bean 
        @Bean
        public FortuneService sadFortuneService() {
            return new SadFortuneService();
        }
        
        // define method to expose bean, method name is bean ID and inject
        @Bean
        public Coach swimCoach() {
            return new SwimCoach(sadFortuneService());
        }
    	
    }
    - AnnotationConfigApplicationContext context = 
      				new AnnotationConfigApplicationContext(SportConfig.class);
      Coach theCoach = context.getBean("tennisCoach", Coach.class);
      
    -   @Bean
        public FortuneService sadFortuneService() {
            return new SadFortuneService();
        }
        
        * This approach is taken if the class does not have stereotype annotation defined(@Component, @Service etc.), in
          this case beans won't be created hence we need to manually add them like we do in XML, and this is the approach.
          The classes with the annotation will be created and added into the spring container. 
          
    - Inject Values from properties file:
      * @PropertySource("classpath:sport.properties") 
      * in the class that uses these values
        - public class SwimCoach implements Coach {
          	
          	private FortuneService fortuneService;
          	
          	@Value("${foo.email}")
          	private String email;
          	    
          	@Value("${foo.team}")
          	private String team;
          	
          	public SwimCoach(FortuneService theFortuneService) {
          		fortuneService = theFortuneService;
          	}
        } 
    
    - External Properties (section 6, jhon)  
      * Using properties file
          - @Configuration
            // bring in the file
            @PropertySource("classpath:datasource.properties")
            //@PropertySource({"classpath:datasource.properties", "classpath:jms.properties"})
            //@PropertySources({
            //        @PropertySource("classpath:datasource.properties"),
            //        @PropertySource("classpath:jms.properties")
            //})
            public class PropertyConfig { 
                // this will get into the spring context and get it as externalised property
                @Value("${guru.username}")
                String user;
            
                @Value("${guru.password}")
                String password;
            
                @Value("${guru.dburl}")
                String url;
                
                //    @Bean
                //    // this will match/wire up the properties from the properties file to attributes
                //   // this going to be reading the file 
                     // this will go out and scan for property files and enable us to use @Value annotation
                //    public static PropertySourcesPlaceholderConfigurer properties() {
                //        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
                //        return propertySourcesPlaceholderConfigurer;
                //    }
              }   	
      
      * Using System(os level) Properties (Environment) => (Section 6, 95 Jhon) 
        * Using Intellij, we can set it in the system environment variable as we do in production          
        * Setting in the GURU_USERNAME(OS level) => guru.username(Application level)
        * OS level property has high precedence that property values
        * other way of getting ENV variable into the Application
          - public class PropertyConfig {
            
                    @Autowired
                    Environment env;
                    
                   system.out.println(env.getProperty("USERNAME"));
            }
      * Multiple Property Files (6, 96 jhon)
        *  @PropertySource({"classpath:datasource.properties", "classpath:jms.properties"})
           //@PropertySources({
           //        @PropertySource("classpath:datasource.properties"),
           //        @PropertySource("classpath:jms.properties")
           //})
      
      * YAML(di-demo)    
        *  spring boot will pick up application.yml
        
      * Property Hirearchy used by spring
        * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html
        * both application.properties and application.yml are at equal Hirearchy. Hence one will overrirde the other
          
      * Setting Properties with profiles
        * Section 6, 101  
        
      * @ConfigurationProperties
        * https://www.baeldung.com/configuration-properties-in-spring-boot  
        
# Spring MVC Configuration without Spring Boot => (99/100/102 chad)
  * Add Configurations to file WEB-INF/web.xml
    - Configure Spring MVC Dispatcher Servlet
    - Setup URL mappings to Spring MVC Dispatcher Servlet
  * Add Configurations to file WEB-INF/spring-mvc-demo-servlet.xml
    - Add support for Spring Component Scanning 
    - Add Support for conversion, formatting and validation
    - Configure Spring MVC View Resolver     
    
    
# Controller
  * @RestController(with response body for rest) or @Controller 
  * @RequestMapping for url mapping which is general, need to mention get, put, delete, post
  * @GetMapping, @PostMapping
    
  - Get Parameters for controller
    - using httpServlet request
    - public String letsShoutDude(HttpServletRequest request, Model model) {
      		String theName = request.getParameter("studentName");
      		
      		theName = theName.toUpperCase();
      		
      		String result = "Yo! " +  theName;
      		model.addAttribute("message", result);
      		
      		return "helloworld";
      		
      	}
    - Using @RequestParam => use to bind the incoming parameter
      * http://appsdeveloperblog.com/requestparam-spring-mvc/
      * methodName(@RequestParam("paramName") String theName)  => api/v1/index?page=1
      * public String processFormVersionThree(@RequestParam("studentName") String theName, Model model) {
        		
        		theName = theName.toUpperCase();
        		
        		String result = "Hey v3! " +  theName;
        		model.addAttribute("message", result);
        		
        		return "helloworld";
        		
        	}
     
    -  @RequestBody => maps the request body json to class defined in spring
      * https://www.baeldung.com/spring-request-response-body
      * public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {} 
      
    - @PathVariable => used to get the dynamic id from the url  
      * @GetMapping("recipe/{id}/update")
        public String updateRecipe(@PathVariable String id, Model model){
               
        }
      * Using model here is like instance varaibles in rails, so that the can be accessed in templates 
    
    * @InitBinder(chad)    
      - add in the controller
        -  @InitBinder
           	public void initBinder(WebDataBinder dataBinder) {
           		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
           		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
           	}
        
      - // add an initbinder ... to convert the trim input strings
        // remove leading and trailing white spaces
        // resolve issue for our validation   	
        
# Validations
  * https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-declaring-bean-constraints
  * Adding validation to DTO is ideal
  * Validation Rest Controller: 
    * http://www.springboottutorial.com/spring-boot-validation-for-rest-services
    * https://www.baeldung.com/spring-data-rest-validators  
    * http://appsdeveloperblog.com/validate-request-body-in-restful-web-service/
    * https://www.mkyong.com/spring-mvc/spring-rest-api-validation/
    * https://g00glen00b.be/validating-the-input-of-your-rest-api-with-spring/  (nice way to implement errors)
    * the BindingResult parameter must immediately after the model attribute.
      -  @RequestMapping("/processForm")
         public String processForm(
                 @Valid @ModelAttribute("customer") Customer theCustomer,
                 BindingResult theBindingResult) {
                         
         } 
    * @InitBinder
      Validating space, it gets skipped in validation to overcome use this
      - Add preprocessing code with @InitBinder  
      - It preprocesses each web request   
  
  * Regular Expressions Validations
    - @Pattern(regexp="^[a-zA-Z0-9]{5}", message="must have 5 chars")
      	private String postalCode; 
  
  * Custom Error messages chad(157)
  
  * Custom Validation => Custom Java Annotation => spring-Mvc-Demo
    * in entity class
      -   @CourseCode(value="TOPS", message="must start with TOPS")
        //@CourseCode
          private String courseCode;
    * Create an Interface 
      - @Constraint(validatedBy = CourseCodeConstraintValidator.class)
        @Target({ ElementType.METHOD, ElementType.FIELD })
        @Retention(RetentionPolicy.RUNTIME)
        public @interface CourseCode {
        	//define default course code
        	public String value() default "LUV";
        	
        	// define default error message
        	public String message() default "must start with LUV";
        	
        	//define default groups
        	public Class<?>[] groups() default {};
        	
        	//define default payloads
        	public Class<? extends Payload>[] payload() default {};		
        }
     
    * Need to implement ConstraintValidator  
      - public class CourseCodeConstraintValidator 
        			implements ConstraintValidator<CourseCode, String> {
        	
        	private String coursePrefix;
        	
        	@Override
        	public void initialize(CourseCode theCourseCode) {
        		//theCourseCode is default value given "LUV"
        		coursePrefix = theCourseCode.value();
        	}
        	
        	@Override
        	public boolean isValid(String theCode, ConstraintValidatorContext theConstraintValidatorContext) {
        		// theCode is value entered on html form
        		boolean result;
        				
        		if(theCode != null) {
        			result = theCode.startsWith(coursePrefix);
        		}
        		else {
        			result = true;
        		}
        		return result;
        	}
        
        }
      
# AOP (Logging & authorization, security and transactions, exception handling)
  * spring-demo-aop, spring-demo-aop-pointcut-declarations   
  * With Spring Boot
    * https://dzone.com/articles/implementing-aop-with-spring-boot-and-aspectj
    * https://www.baeldung.com/spring-aop-annotation
    * https://www.baeldung.com/spring-http-logging
    * https://www.javadevjournal.com/spring/log-incoming-requests-spring/
    * https://gist.github.com/int128/e47217bebdb4c402b2ffa7cc199307ba
    * http://slackspace.de/articles/log-request-body-with-spring-boot/
    
    * Using Filters
      * https://www.baeldung.com/spring-boot-add-filter
      * https://www.javadevjournal.com/spring-boot/spring-boot-add-filter/ (good)
      * https://stackoverflow.com/questions/19825946/how-to-add-a-filter-class-in-spring-boot (good)
    
    * http://www.makeinjava.com/logging-aspect-restful-web-service-using-spring-aop-log-requests-responses/
    
  * Frameworks
    * Spring AOP (implements Aop)
      * runtime weaving
      * Uses AOP in background for Security, Caching, transactions etc
      * Using Proxy pattern
      * only applicable to method-level
      * can migrate to Aspectj when using @Aspect annotation
      * Can only apply aspects to beans created by spring app context
      * minor performance cost for aspect execution(run time weaving)
      * Spring AOP is a light weight implementation of AOP
      * Solves common problems in enterprise applications
      
    * Aspectj  (see 316 chad for good reference books)
      * Provides complete support for AOP
      * Rich Support for join point => method-level, Constructor, field
      * compile-time weaving, post compile time weaving and run time
      * supports all join points
      * works on any pojo not just beans from app context
      * Faster performance
      * compiler time weaving requires extra compilation step
      * AspectJ pointCut syntax can become complex 
    
    - Declaring an AOP in Spring App, in Config file
      @Configuration // for pure java configuration 
      @EnableAspectJAutoProxy
      @ComponentScan("com.lu2code.aopdemo")
      public class DemoConfig {
          //	@Bean
          //	public AccountDAO accountDAO() {
          //		return new AccountDAO();
          //	}
      }

    - Creating an AOP
      @Aspect
      @Component
      public class MyDemoLoggingAspect {
      }  
      
  
  * Like spying on the requests  
  * Terminologies
    * Aspect
      * module of code for cross cutting concern(logic => logging, security)
      - public void beforeAddAccountAdvice() {
         System.out.println("\n=====>>> Executing @Before advice on addAccount()");    
      }	
      
    * Advice 
      * what action is taken and when it should be applied
        * Before, After returning, After Throwing, After Finally, Around
             
    * Join Point
      * when to apply code during program execution
      * Read Method arguments/parameters
      * Display method Signature
      * Gives info about method it is executing
      -  @Before("com.lu2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()") // any modifier ,return type, package name, any class, any method
         	public void beforeAddAccountAdvice(JoinPoint theJoinPoint) {	
         		myLogger.info("\n=====>>> Executing @Before advice on addAccount()");	
         		
         		// display the method signature
         		MethodSignature methodSig = (MethodSignature) theJoinPoint.getSignature();
         		myLogger.info("\nMethod SIgnature: " + methodSig);
         		
         		// display method arguments
         		
         		Object[] args = theJoinPoint.getArgs();
         		
         		for(Object tempArg : args) {
         			myLogger.info(tempArg.toString());
         			
         			if (tempArg instanceof Account)  {
         				// downcast and print Account specific stuff
         				Account theAccount = (Account) tempArg;
         				
         				myLogger.info("accountName :" + theAccount.getName());
         				myLogger.info("accountLevel :" + theAccount.getLevel());
         				
         			}
         		}
         	}  
      
    * PointCut Expressions
      * A predicate expression for where advice should be applied
      * can be directly mentioned in the advices or by can be done as re-useable and apply to multiple expression  
        * Pointcut Expression Language
          - execution(modifiers-pattern? return-type-pattern declaring-type-pattern(classname)? method-name-pattern(param-pattern) throws-pattern?)
          - "execution(public void com.luv2code.AccountDAO.addAccount())"
        * Re-useable PointCut Expressions
          - @Aspect
            public class LuvAopExpressions {
            	//#### point cut declarations ### for reusability
            	@Pointcut("execution(* com.lu2code.aopdemo.dao.*.*(..))")
            	public void forDaoPackage() {}

            	// match getter methods
            	@Pointcut("execution(* com.lu2code.aopdemo.dao.*.get*(..))")
            	public void getter() {}
            	
            	// match setter methods
            	@Pointcut("execution(* com.lu2code.aopdemo.dao.*.set*(..))")
            	public void setter() {}
            	
            	// combine pointcuts: include package and exclude getter/setter
            	@Pointcut("forDaoPackage() && !(getter() || setter())")
            	public void forDaoPackageNoGetterSetter () {}
            	
            	@Before("forDaoPackageNoGetterSetter")
            	public void beforeAddAccountAdvice() {
                    System.out.println("\n=====>>> Executing @Before advice on addAccount()");
                }	
            } 
  
    * AOP Ordering
        * Control the order of advices being applied
        * manage order when same conditions are met, i.e when multiple advices are targeted for same methods the order to
          follow
        * when order value for the aspects is same spring takes care of it randomly
          
        - @Aspect
          @Component
          @Order(1)
          public class MyCloudLogAsyncAspect {
            @Before("com.lu2code.aopdemo.aspect.LuvAopExpressions.forDaoPackageNoGetterSetter()") // any modifier ,return type, package name, any class, any method
            public void loggingToCloudAsync() {	
                System.out.println("\n=====>>> Logging to cloud");		
            }
      }
      
  
  * Weaving
    * connecting aspects to target objects to create an advised object 
    * Types
      * Compile time weaving
      * Load time weaving(run time)
        * this is the slowest
         
      
  * Advice Types
    * Before Advice => run before method
      * @Before Advice
          - @Aspect
            @Component
            public class MyDemoLoggingAspect {
                // this is where we add all of our related advices for logging
                
                // let's start with an @Before advice
            
                //@Before("execution(public void addAccount())") // pointcut expression matches any method
                //@Before("execution(public void com.lu2code.aopdemo.dao.AccountDAO.addAccount())") // match particular class method
                //@Before("execution(public void add*())") //match any with add*  (public * add*())
                //@Before("execution(void add*())") //match any with add* with void return type
                //@Before("execution(* add*())")  // any
                
                // ###Arguments Type####
                //@Before("execution(* add*(com.lu2code.aopdemo.Account))")  // specific param type
                //@Before("execution(* add*(com.lu2code.aopdemo.Account, boolean))")
                //@Before("execution(* add*(com.lu2code.aopdemo.Account, ..))")
                //@Before("execution(* add*(..))") // any params 0 or more
                
                //###Packages###
                @Before("execution(* com.lu2code.aopdemo.dao.*.*(..))") // any modifier ,return type, package name, any class, any method
                public void beforeAddAccountAdvice() {
                    
                    System.out.println("\n=====>>> Executing @Before advice on addAccount()");
                    
                }	
            }
            
    * After returning advice => run after method (Success Execution)
      * Post processing data
      * the result returned from the method executing with result need's to be sent back, hence "returning"
      -  // add a new advice for @AfterReturning on the findAccounts method
        @AfterReturning(
                pointcut="execution(* com.lu2code.aopdemo.dao.AccountDAO.findAccounts(..))",
                returning="result") // here returning should be same as param below
        public void afterReturningFindAccountsAdvice(JoinPoint theJoinPoint, List<Account> result) {
            
            String method = theJoinPoint.getSignature().toShortString();
            myLogger.info("\n ========> Executing @AfterReturning on method: " + method);
            
            myLogger.info("\n ========> result is: " + result);
            
            // post processing data
            // convert account names to upper case
            convertAccountNamesToUpperCase(result);
            
            myLogger.info("\n ========> result is: " + result);
            
        }
     
        private void convertAccountNamesToUpperCase(List<Account> result) {
            for(Account tempAccount : result) {
                String theUpperName = tempAccount.getName().toUpperCase();
                tempAccount.setName(theUpperName);
            }
        }
        
    * After Throwing Advice => run after method (if exception thrown)
      * Runs after exception is thrown, and post process it
      * logging exception
      * perform auditing
      * we will only be intercepting the exception, however the exception will be still propagated to the calling program(method)
        send emails, sms logic
      * But if we need to stop propagation and handle things then we must use @Around advice  
      - // AfterThrowing advice
        @AfterThrowing(
                    pointcut="execution(* com.lu2code.aopdemo.dao.AccountDAO.findAccounts(..))",
                    throwing="theExc"
                )
        public void afterTHrowingFindAccountAdvice(JoinPoint theJoinPoint, Throwable theExc) {
            String method = theJoinPoint.getSignature().toShortString();
            myLogger.info("\n ========> Executing @AfterThrowing on method: " + method);
            
            myLogger.info("\n ========> THe exception is: " + theExc);
    
        }            
      
    * After finally Advice => run after the method(finally)
      * does not have access to exception, if needed use @AfterThrowing
      * works just like finally block in java, runs if success or exception or error
      -  // After Finally, remember after runs first followed by after throwing. After runs for error or success 
        @After("execution(* com.lu2code.aopdemo.dao.AccountDAO.findAccounts(..))")
        public void afterFinallyFindAccountsAdvice(JoinPoint theJoinPoint) {
            String method = theJoinPoint.getSignature().toShortString();
            myLogger.info("\n ========> Executing @AfterFinally on method: " + method);
        }  
    
    * Around Advice => run before and after method  
      * Rethrow Exception or ordering for this also there
      * can be used to handle or stop exception 
      - // Around Advice, happens at start and end, notice here JoinPoint is different "ProceedingJoinPoint"
        @Around("execution(* com.lu2code.aopdemo.service.*.getFortune(..))")
         public Object aroundGetFortune(
                    ProceedingJoinPoint theProceedingJoinPoint 
                ) throws Throwable {
            
            // print out method we are advising on
            String method = theProceedingJoinPoint.getSignature().toShortString();
            myLogger.info("\n ========> Executing @Around on method: " + method);
            
            
            // get begin timestamp
            long begin = System.currentTimeMillis(); 
            
            // execute method
            // the ProceedingJoinPoint here is used to proceed the execution of the method that is called up, here getFortune
            Object result = null;
            
             try {
                result = theProceedingJoinPoint.proceed();
            } catch (Exception e) {
                //e.printStackTrace();
                
                // log exception
                myLogger.info(e.getMessage());
                
                // give user a custom message, to calling method 
                
                //result = "Major Accident! But no worries, your private AOP helicopter on the way";
                // rethrowing the exception
                throw e;
            }
            
             
            
            //get end timestamp
            long end = System.currentTimeMillis();
            
            //compute duration and display it
            long duration = end - begin;
            
            myLogger.info("\n======> Duration: " + duration/1000.0 + "seconds");
            
            // result consists of the calling program result returned by getFortune
            return result;
         }

# Custom Exception Handling in Spring Boot => spring-rest-demo

  *  define a exception class first implementing runtime exception
    -  public class StudentNotFoundException extends RuntimeException {
       
            public StudentNotFoundException(String message, Throwable cause) {
                super(message, cause);
                
            }
           
            public StudentNotFoundException(String message) {
                super(message);
                
            }
           
            public StudentNotFoundException(Throwable cause) {
                super(cause);
            
            }
       }

  * Now Add a exception handler
      *  @ControllerAdvice
         * for global exception handling use the @ControllerAdvice, it is similar to interceptor/filter
         * used when pre-process and post-process requests to controllers
         * RealTime use of AOP
         
          - @ControllerAdvice
            public class StudentRestExceptionHandler {
                // Add an exception handler using @ExceptionHandler
                @ExceptionHandler
                public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException exc) {
                    
                    StudentErrorResponse error = new StudentErrorResponse();
                    error.setStatus(HttpStatus.NOT_FOUND.value());
                    error.setMessage(exc.getMessage());
                    error.setTimeStamp(System.currentTimeMillis());
                    
                    
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                }
                
                // Add another exception handler .... to catch any exception (catch all)
                
                @ExceptionHandler
                public ResponseEntity<StudentErrorResponse> handleException(Exception exc) {
                    
                    StudentErrorResponse error = new StudentErrorResponse();
                    error.setStatus(HttpStatus.BAD_REQUEST.value());
                    error.setMessage(exc.getMessage());
                    error.setTimeStamp(System.currentTimeMillis());
                    
                    
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
            }

# Actuator
  * Monitor and Manage Application
  * Check health of the application
  * access application metrics
  
  * Endpoints => /actuautor/ ...
    - /health => health information about app
    - /info => info about project
    - /auditevents => audit events for the app
    - /beans => list of all registered beans in the spring application context
    - /mappings => for all path mappings
    * all end points
      - https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints
    
  * Security can be applied
    * https://www.devglan.com/spring-security/securing-spring-boot-actuator-endpoints-with-spring-security
    * https://dzone.com/articles/spring-boot-actuator-in-spring-boot-20  
  
# Spring HTTPClient-guide
  * https://www.baeldung.com/httpclient-guide 
  
# Spring Framework context
  * how to ge the spring context inside the framework, Section 3, 35. Jhon
  
# Maven Release Plugin(3 => 56 John)
  * generate releases of artifacts
  * Releases tags
  
# MultiModule Maven Builds
  * 3 => 55  
  
# Using Third party plugins in spring (section 4 => chuck norris)
  * Add to maven
  * create a bean
  * Autowire it  
    
# Component Scan (5 => 80 Jhon)
  * Used to scan the packages and create beans
  - package guru.springframework.didemo;
    @SpringBootApplication  => does a component scan from the package that is mentioned above and down
    // package scan
    @ComponentScan(basePackages = {"guru.springframework.didemo.services", "guru.springframework.didemo"})
    // @ComponentScan(basePackages = {"guru"}) // this also works
    public class DiDemoApplication {}
    
# Using XML configuration in Spring Boot (5 => 81 Jhon)
  * Jokes-app application
  * @ImportResource("classpath:chuck-config.xml")    
  
# Packaging Spring Boot Project & Profile
  * https://stackoverflow.com/questions/38509511/spring-application-properties-profile-with-war-file
  * http://websystique.com/spring-boot/spring-boot-war-deployment-example/  
  * https://docs.spring.io/spring-boot/docs/current/maven-plugin/examples/run-profiles.html
  * https://www.baeldung.com/spring-profiles
  * http://www.java2novice.com/spring-boot/profile-based-properties/
  
# Setting Spring Profiles
  *  https://stackoverflow.com/questions/22757318/different-property-variable-for-local-and-prod-environment-spring 
  
# JPA
  * Hibernate, OpenJPA are implementers of this 
  * Set of specification to be implemented
  * ORM Tool   
  * Criteria update, delete , Schema Generation, Validations, Queries against stored procedures  	 
  
  * Spring Data JPA:
    * This implements Repository Pattern
  
  * https://docs.spring.io/spring-data/jpa/docs/2.1.4.RELEASE/reference/html/#jpa.query-methods  
    
  # Different Repositories To Implement
    /* package org.springframework.data.repository; Depends on when to use what usecase*/
    * Repository (Base)
    * CrudRepository
    * PagingAndSortingRepository
    * JpaRepository(include's All three above)
             	
  # Hibernate  
    * By default in hibernate if did not specify cascade nothing will be mentioned   
    * https://o7planning.org/en/11223/generate-tables-from-entity-classes-in-hibernate 
    * http://www.techferry.com/articles/hibernate-jpa-annotations.html 
    
    * Serialization problem
      * https://stackoverflow.com/questions/30892298/infinite-loop-with-spring-boot-in-a-one-to-many-relation
  
  # JPA
    * addition to spring 5, it returns an optional instead of null
    * these dynamic queries like in rails, here they are called JPA Query methods
    * jpa takes care of all implementation  
    * note here no Stereotype declarations for this like @component, @service ...ect JPA implements it so it is avaliable in the
      Bean Container
    * JPA has @PrePersist and @PreUpdate for automatic update timestamp properties for audit purpose, 
    * same thing in hibernate has @CreationTimestamp and @UpdateTimestamp => (section 8, 136 10:44)
      - https://stackoverflow.com/questions/42366763/hibernate-creationtimestamp-updatetimestamp-for-calendar
    
    # Types Of Cascade 
      * Persist
      * Merge
      * Refresh
      * Remove   
      * Detach
      * All
      * https://www.baeldung.com/hibernate-save-persist-update-merge-saveorupdate
      * https://www.journaldev.com/3481/hibernate-session-merge-vs-update-save-saveorupdate-persist-example
    
    # Return Association's as Json(Jackson Serialising and DeSerialising as Json / Marshalling and Unmarshalling)
      * Converting object to Json(Serialising), converting Json to Object(De-Serialising) 
      * https://www.javacodegeeks.com/2012/09/json-jackson-to-rescue.html
      * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
      
    # Jackson Json  
      * https://www.baeldung.com/jackson  
      
    # Hibernates default persistence strategy for inheritance
      * Single Table Inheritance -> leads to lot of unused colomns, with type colomn as common  
      * Hibernates ddl-auto property control's if any ddl operatiions hibernate will perform on start up.
        * options for ddl-auto => 
          - none, 
          - validate(used for production to check if any attribute or table is missed), 
          - update, 
          - create, 
          - create-drop() using embeded DB
  
  # Spring Data
    * removes the old way of configuring and boiler plate code      
    * Intially we used to do and write all the logic in DaoImpl
      - EmployeeDao -> EmployeeDaoImpl -> EntityManager(mapping) -> DriverManagerSource(DBConnection)
    * Spring Data gives us  now pre defined repositories interface
      - EmployeeRepository(CRUD Repository) -> Employee(Entity)
      
  # Creating an Entity(bharat), Repository, Configure DataSource
    - Section4 -> productData    
  
  # Generators(Id Generations) 
    * section 5 => Bharat
    * Types
      * GenerationType.AUTO
        * Persistence Provider like hibernate has to check the underlying DB which kind of PK ID generation Strategy 
          does it support. It can be either one of IDENTITY or SEQUENCE or TABLE
           
      * GenerationType.IDENTITY
        * Persistence Provider will rely on the PK auto-increment and db should also be configured to it.
      
      * GenerationType.SEQUENCE (Sequence => create custom value to generate the PK like ORACLE)
        * Need to tell the Persistence Provider which sequence to use
      
      * GenerationType.TABLE
        * A special table is used, the Persistence Provider will generate a value and put it in this table as a column
          and use that column as PK in the actual table we use
        * whene creating a new record, it will generate a new value base on the special table and put in the actual table  
        * idgenerators project has an example of this
      
      -  @Id
         @GeneratedValue(strategy=GenerationType.IDENTITY)
         private int id;
  
  # Spring Data Finders  Section6 -> productData     
    * https://github.com/raj23manj/Hibernate_Basiscs/blob/master/productdata/src/main/java/com/rajesh/springdata/product/repos/ProductRepository.java
    
  # Paging And Sorting section7 -> productData
    * https://github.com/raj23manj/Hibernate_Basiscs/blob/master/productdata/src/test/java/com/rajesh/springdata/product/ProductdataApplicationTests.java 
    * implement sort and pageable classes provided by spring data and pass them to methods, hibernate will take care of the rest
  
  # JPQL(Java persistence Query Language) -> jpqlandnativesql
    * https://github.com/raj23manj/Hibernate_Basiscs/blob/master/jpqlandnativesql/src/main/java/com/rajesh/springdata/jpqlandnativesql/repos/StudentRepository.java
    * to write jpql, we use the domain class names and properties of the domain calss in th query.
    -  // @Query means it is only read operations
       @Query("from Student") // equal to select * from
       List<Student> findAllStudents(Pageable pageable); 
       
    - @Modifying // from spring data tells spring that it is modifying delete/update/insert
      @Query("delete from Student where firstName=:firstName")
      void deleteStudentsByFirstName(@Param("firstName") String firstName);   
  
  # Paging and Sorting with JPQL 
    - Section 9 -> jpqlandnativesql
  
  # Native SQL Query
    * Section 10 -> jpqlandnativesql
    -  @Query(value="select * from student  where fname=:fname", nativeQuery=true) 
       List<Student> findByFirstNameNQ(@Param("fname") String firstName); 
    
  # Inheritance Mapping  Section 11 -> hibernateinheritance
    * Types:
      * SINGLE_TABLE
      * TABLE_PER_CLASS 
      * JOINED
      
      * Example
        * Payment 
          * Card
          * Check
          
    * SINGLE_TABLE(STI)
      * Need a type column in the table to diffrentiate
        - @Inheritance, @DiscriminatorColumn => Parent Class
          - @DiscriminatorColumn(name="pmode", discriminatorType=DiscriminatorType.STRING) // type column in table
            public abstract class Payment {
            }
        - @DiscriminatorValue => Child class
          - @DiscriminatorValue("cc")
            public class CreditCard extends Payment{
            }
            
          - @DiscriminatorValue("ch")
              public class Check extends Payment{
              }  
    
    * TABLE_PER_CLASS 
      * Keeps Seperate table's for all
      * improves performance, Not recommended, as it violates nomalization. Keeps colomns repeated in each child tables
      - @Inheritance(strategy=InheritanceType.TABLE_PER_CLASS) => parent     
      
    * JOINED (most used inheritance type)
      * Keeps Seperate table's for all with PK and FK 
      - @Inheritance(strategy=InheritanceType.JOINED) // most used inheritance type
      - @PrimaryKeyJoinColumn(name="id") => child 
     
    * Implicit and explicit polymorphism 
      - http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#entity-inheritance-polymorphism
      
  # Component Mapping(Embbedable) -> Section12 -> componentmapping
    - public class Employee {  // parent
      	@Id
      	private int id;
      	private String name;
      	
      	@Embedded
       private Address address;
      }
      
    - @Embeddable // child class
      public class Address {}  
      
  # RelationsShips in Hibernate -> Section13 -> associations
    * OneToMany => this is always lazy by default
      - @OneToMany(mappedBy="customer", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
        private List<PhoneNumber> numbers; 
        
      - @ManyToOne
        @JoinColumn(name="customer_id")
        private Customer customer;  
             
    * Cascading => 108
      * PERSIST => Save/Insert Child also when parent is is Saved
      * MERGE => Update/Insert Child also when parent is Saved/Updated
      * REMOVE => Delete Child also when parent is Deleted  
      * REFRESH => Refresh the parent object will automatically refresh child aobject
      * DETACH => Detach the parent object will automatically detach child object
      * ALL => DOES ALL of the above
      
    * LAZY & EAGER Loading (see from 111 to 115)
      * Earger => there is a join query and mapped to parent and child objects. Json will be created automatically
      * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion (jackson mapping)
    
    * MantToMany -> Section 14 -> associations 
      - @ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
        @JoinTable(name="programmers_projects", 
        			   joinColumns=@JoinColumn(name="programmer_id", referencedColumnName="id"), 
        			   inverseJoinColumns=@JoinColumn(name="project_id", referencedColumnName="id"))
        private Set<Project> projects;
        
      - //@ManyToMany(cascade=CascadeType.ALL)
        //	@JoinTable(name="programmers_projects", 
        //			   joinColumns=@JoinColumn(name="project_id", referencedColumnName="id"), 
        //			   inverseJoinColumns=@JoinColumn(name="programmer_id", referencedColumnName="id"))
        @ManyToMany(mappedBy="projects") // it already defined so no need of above one, even that can done
        private Set<Programmer> programmers;  
        
    * OneToOne -> Section 15 -> associations 
      - @OneToOne(cascade=CascadeType.ALL)
        @JoinColumn(name="person_id")
        private Person person;
        
      - @OneToOne(mappedBy="person")
        private License license; 
        
    * Hibernate Caching Mechanism(EH caching) -> Section 16 ->  productdata
      * There two levels of cache  - 134  
        * Session - Level1 
          * Each client has a hibernate session, and uses it to Query DB
          * Each client has it's own Cache
          * come's in by default
          * to make Level 1 caching to work we need to mark the method as @Transactional on the method
          * example 135
          * show's how to use entityManager in spring boot => 136
          * Evict -> remove an object from cache  
            * To remove from level1 cache => 136
            - @Autowired EntityManager entityManager => (javax.persistence.EntityManager)
              
              @Transactional
              // below code in a method
              Session session = entityManager.unwrap(Session.class);
              Product p = repository.findOne(1);
              repository.findOne(1);
              session.evict(p);
              repository.findOne(1);
               
        * SessionFactory - Level2
          * Session Factory is used to create multiple Sessions
          * has a common cache, and all sessions uses this cache
          * A Shared cache
          * EhCache/Redsi Cache
          
        * Properties For Cahcing => 139
          - spring.jpa.properties.hibernate.cache.use_second_level_cache=true => Enabling second level cache
            spring.jpa.properties.javax.persistence.sharedCache.mode=ALL => mentioning that all should use shared cache
            
        * Cache Concurrency Strategy => 141
          * impacts the way caching is done in our application
          * READ_ONLY
            * used when the entites never change, only used in a read only applications
            - @Cache(usage=CacheConcurrencyStrategy.READ_ONLY) 
            * https://github.com/raj23manj/Hibernate_Basiscs/blob/master/productdata/src/main/java/com/rajesh/springdata/product/entities/Product.java
            
          * NONSTRICT_READ_WRITE
            * updates the cache only when a transaction completely commints the data to the database
            * Non-consistent data exist's, when two users access the same data, when one is updating and other is viewing.
            
          * READ_WRITE
            * if consistenty needed, like having locks in DB use this
            * in the same case mentioned above, when one user is updating a lock is added. When the other user makes a request see's the soft lock in cache
              goes to the DB directly and fetches
            
          * TRANSACTIONAL    
            * Rarely used
            * Used for distributed caching
          
        * EhCache(Inmemory/disk)
          * 137 - 143
                   
        * Redis Cache  
          - pom.xml
            -  <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-redis</artifactId>
                  <exclusions>
                    <exclusion>
                     <groupId>io.lettuce</groupId>
                     <artifactId>lettuce-core</artifactId>
                    </exclusion>
                  </exclusions>		    
                </dependency>		
                <dependency>
                  <groupId>redis.clients</groupId>
                  <artifactId>jedis</artifactId>
                </dependency> 
                
            - Redis Config
              -  import java.time.Duration;
                 
                 import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
                 import org.springframework.context.annotation.Bean;
                 import org.springframework.context.annotation.Configuration;
                 import org.springframework.context.annotation.Primary;
                 import org.springframework.data.redis.cache.RedisCacheConfiguration;
                 import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
                 import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
                 import org.springframework.data.redis.core.RedisTemplate;
                 import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
                 import org.springframework.data.redis.serializer.GenericToStringSerializer;
                 
                 @Configuration
                 @EnableRedisRepositories
                 public class RedisConfig {
                 
                     @Bean
                     public JedisConnectionFactory jedisConnectionFactory() {
                         RedisProperties properties = redisProperties();
                         RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
                         configuration.setHostName(properties.getHost());
                         configuration.setPort(properties.getPort());
                 
                         return new JedisConnectionFactory(configuration);
                     }
                 
                     @Bean
                     public RedisTemplate<String, Object> redisTemplate() {
                         final RedisTemplate<String, Object> template = new RedisTemplate<>();
                         template.setConnectionFactory(jedisConnectionFactory());
                         template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
                         return template;
                     }
                 
                     @Bean
                     @Primary
                     public RedisProperties redisProperties() {
                         return new RedisProperties();
                     }
                 
                     @Bean
                     public RedisCacheConfiguration cacheConfiguration() {
                 	 	RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                 	 	  .entryTtl(Duration.ofSeconds(600))
                 	 	  .disableCachingNullValues();	
                 	 	return cacheConfig;
                     }
                 }   
                 
              * Controller
                -  @Cacheable(value= "processRunCache", key= "#root.methodName")
                   @RequestMapping(method=RequestMethod.GET, value="/api/listAllRuns")
              
              * Entities
                * enable serialisation
                - public class Process implements Serializable {}
              
              * Enable Caching
                - @EnableCaching
    
    * Default Fetch Types
      OneToMany: LAZY
      ManyToOne: EAGER
      ManyToMany: LAZY
      OneToOne: EAGER    
    
    * Transaction Management -> Section17 -> transactionmanagement 
    
      * ACID Properties => 144
        * Atomicity
          * Either all the queries execute or nothing will execute(Rollback)
          
        * Consistency  
          * At the end of transactions DBshould be in a consistent state, meaning if credit and debit is happening transaction
            money sould be added to the one account and removed from another account
            
        * Isolation
          * If multiple queries are running in the transaction, each should happen in isolation without interferring other queries
        
        * Durability    
          * once transaction are completed, the data should stay in the DB 
          
      * Transaction Management Component 
        * Transactional App (Commit/Rollback) -> Transactional Manager/Transaction Co-oridinator -> Resource Manager(JBDC/Hibernate/orms) -> DB 
        
      * Code
        - @Transactional // this dose transaction management
          	public void transfer(int amount) {
          		BankAccount obama = repository.findById(1).get();	
          		obama.setBal(obama.getBal() - amount);
          		repository.save(obama);
          		
          		if(true) {
          			throw new RuntimeException();
          		}
          		
          		
          		BankAccount trump = repository.findById(2).get();		
          		trump.setBal(trump.getBal() + amount);
          		repository.save(trump);
          }  
          
    * Save and Retrive Files in DB -> Section 18
      * saving a pdf/image etc using BLOB  
      
  # Flushing => http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#pc-filter-join-table
    * the process of synchronizing the state of the persistence context with the underlying datbase
    * the flush operations take every entity state change and transalte it to an INSER, UPDATE or DELETE
    * Flush Modes
      * ALWAYS
        * The ALWAYS is only available with the native Session API.
        * The ALWAYS flush mode triggers a persistence context flush even when executing a native SQL query against the Session API.
      
      * AUTO 
        * Default Hibernate uses this mode
        
      * COMMIT
        * When executing a JPQL query, the persistence context is only flushed when the current running transaction is committed.
        
      * MANUAL   
        * Both the EntityManager and the Hibernate Session define a flush() method that, when called, triggers a manual flush.
          Hibernate also provides a MANUAL flush mode so the persistence context can only be flushed manually. 
  
  # Interceptors And Events 
    * http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#events     
    * https://www.boraji.com/hibernate-5-event-listener-example => good one    
    
    * Interceptors
      * The org.hibernate.Interceptor interface provides callbacks from the session to the application, allowing the application to inspect 
        and/or manipulate properties of a persistent object before it is saved, updated, deleted or loaded. 
        
      -  public static class LoggingInterceptor extends EmptyInterceptor {
         	@Override
         	public boolean onFlushDirty( // => Note this
         		Object entity,
         		Serializable id,
         		Object[] currentState,
         		Object[] previousState,
         		String[] propertyNames,
         		Type[] types) {
         			LOGGER.debugv( "Entity {0}#{1} changed from {2} to {3}",
         				entity.getClass().getSimpleName(),
         				id,
         				Arrays.toString( previousState ),
         				Arrays.toString( currentState )
         			);
         			return super.onFlushDirty( entity, id, currentState,
         				previousState, propertyNames, types
         		);
         	}
         }  
        
    * Native Event System
      *  If you have to react to particular events in the persistence layer, you can also use the Hibernate event architecture. 
         The event system can be used in place of or in addition to interceptors.
         
    * JPA Callbacks => http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#events-declarative-security
      * @PrePersist
        * Executed before the entity manager persist operation is actually executed or cascaded. This call is synchronous with the persist operation.
        
      * @PreRemove 
        * Executed before the entity manager remove operation is actually executed or cascaded. This call is synchronous with the remove operation.
        
      * @PostPersist 
        * Executed after the entity manager persist operation is actually executed or cascaded. This call is invoked after the database INSERT is executed.
        
      * @PostRemove
        * Executed after the entity manager remove operation is actually executed or cascaded. This call is synchronous with the remove operation.
        
      * @PreUpdate
        * Executed before the database UPDATE operation.
        
      * @PostUpdate
        * Executed after the database UPDATE operation.
        
      * @PostLoad 
        * Executed after an entity has been loaded into the current persistence context or an entity has been refreshed.   
        
      * There are two available approaches defined for specifying callback handling:
        * The first approach is to annotate methods on the entity itself to receive notifications of a particular entity lifecycle event(s).
        * The second is to use a separate entity listener class. An entity listener is a stateless class with a no-arg constructor. 
          The callback annotations are placed on a method of this class instead of the entity class. The entity listener class is then 
          associated with the entity using the javax.persistence.EntityListeners annotation    
        
        -  @Entity
           @EntityListeners( LastUpdateListener.class )
           public static class Person {
           
           	@Id
           	private Long id;
           
           	private String name;
           
           	private Date dateOfBirth;
           
           	@Transient  // @Transient annotation is used to indicate that a field is not to be persisted in the database, https://stackoverflow.com/questions/2154622/why-does-jpa-have-a-transient-annotation
           	private long age;
           
           	private Date lastUpdate;
           
           	public void setLastUpdate(Date lastUpdate) {
           		this.lastUpdate = lastUpdate;
           	}
           
           	/**
           	 * Set the transient property at load time based on a calculation.
           	 * Note that a native Hibernate formula mapping is better for this purpose.
           	 */
           	@PostLoad
           	public void calculateAge() {
           		age = ChronoUnit.YEARS.between( LocalDateTime.ofInstant(
           				Instant.ofEpochMilli( dateOfBirth.getTime()), ZoneOffset.UTC),
           			LocalDateTime.now()
           		);
           	}
           }
           
           public static class LastUpdateListener {
           
           	@PreUpdate
           	@PrePersist
           	public void setLastUpdate( Person p ) {
           		p.setLastUpdate( new Date() );
           	}
           }
           
    * Default entity listeners  => see the example has good one 
      * http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#events-declarative-security     
      * The JPA specification allows you to define a default entity listener which is going to be applied for every entity in 
        that particular system. Default entity listeners can only be defined in XML mapping files.    
        
      - public class DefaultEntityListener {
        
            public void onPersist(Object entity) {
                if ( entity instanceof BaseEntity ) {
                    BaseEntity baseEntity = (BaseEntity) entity;
                    baseEntity.setCreatedOn( now() );
                }
            }
        
            public void onUpdate(Object entity) {
                if ( entity instanceof BaseEntity ) {
                    BaseEntity baseEntity = (BaseEntity) entity;
                    baseEntity.setUpdatedOn( now() );
                }
            }
        
            private Timestamp now() {
                return Timestamp.from(
                    LocalDateTime.now().toInstant( ZoneOffset.UTC )
                );
            }
        }  
        
      * to exclude default listeners
        * @ExcludeDefaultListeners
          @ExcludeSuperclassListeners 
          
      * Register and Handle Event's using java config
        * https://www.boraji.com/hibernate-5-event-listener-example      
        
  # Section 8, jhon
    * JDL-Studio for data modeling
    * Data Source Intialization(8, 146)
      * via import.sql by hibernate
      * via data.sql(recommended), data-${platform}.sql and schema.sql 
    
  # Complex Json Return nested
    * https://www.youtube.com/watch?v=TWOzV8H5Kfc
    * https://vladmihalcea.com/the-best-way-to-map-a-many-to-many-association-with-extra-columns-when-using-jpa-and-hibernate/     
          
# @SpringBootApplication 

  * this is the main annotation
  * this includes automatically
    * @Configuration - declares class as spring configuration
    * @EnableAutoConfiguration - Enables auto configuration
    * @ComponentScan - scans for components in current package and child packages
    
# command-line (section 5 - 84 john)     

   * mvn spring-boot:help -Ddetail=true
   * mvn spring-boot:run -Drun.arguments=--debug
     * intellij next to hammer/run btn > click onit > Edit Configurations > enable debug is same as above
     
   * mvn spring-boot:run
   * mvn spring-boot:run -Drun.arguments="--spring.profiles.active=dev"  
   * mnv clean
   * mvn install
   * mvn clean package -P prod => target/ .war or .jar
   

# Bean Scope
  * Commonly used in Singleton and Prototype 
  * You Cannot override the built in Singleton and Prototype Scopes
     * Singleton (default) - only one instance of the bean is the created in the Ioc container
     * Prototype - A new instance is created each time the bean is requested
     * Only valid in the context of a web-aware Spring ApplicationContext
         * Request - A single instance per http request. 
         * Session - A Single instance per http session. 
         * Global-Session - A Single instance per global session. Typically only used in Portlet context(Legacy code).
         * Websocket - Scopes a single bean defenition to the lifecycle of a WebSocket
     * Custom Scope - Spring Scopes are extensible, and you can define your own scope by implementing Spring's 'Scope' interface.
     
  * Declaring Bean Scope:
    * No declaration needed for singleton scope.
    * In Java Configuration use @Scope annotation
    * In XML configuration scope is an XML attribute of the 'bean' tag   
    
# Configurations Options For Spring Framework
  * XML
  * Annotation
  * Java Based Classes
  * Groovy
  * DSL    
  
# Types of Annotation @Componentscan looks for
  * Stereotype annotations (Inherits from @Component)
    * @Controller
    * @Service
    * @Component
    * @Repository  
    
# Annotation used to declare a Spring Component inside Java configuration class
  * @Bean    
 
# Annotation used to access Spring Bean Lifecycle
  * @PostConstruct
  * @PreDestroy  
  
# Subtypes Of @Component
  * @Controller
    * @RestController(extends @ResponseBody, it's a convinence annotaion combined with @Controller)  
  * @Repository
  * @Service  
  
# what is @Repository used for
  * Spring will detect platform specific persistance exceptions and re-throw them as spring exceptions 
  * this can be used to set the class as a repository, if there is no implementation og 'Crud or JPA' repository
  
# Why Should we allow spring boot maven projects to inherit from the spring boot parent pom ?
  * This allows the project to inherit curated dependencies which are known to be compatible  
  
# other dependency management for spring projects
  * Ant build, ideally need to configure Ivy for dependency resolution
  
# Spring Boot Starter
  * It is a POM which declares a common set of dependencies. It is available for most java projects 
  
# @SpringBootApplication, include 3 annotaions
  * @Configuration
  * @ComponentScan
  * @EnableAutoConfiguration  
  
# How To display Auto-configuaration Report
   * Start spring boot with the command line parameter --debug
   * mvn spring-boot:run -Drun.arguments=--debug
   * intellij next to hammer/run btn > click onit > Edit Configurations > enable debug is same as above
   
# Disable Specific Spring Boot auto-configuration classes
   * Need to pass the class name to 'exclude' parameter of @EnableAutoConfiguration 

# @Order
   * https://www.baeldung.com/spring-order
   
# Connect h2
  * http://localhost:8080/h2-console
  * To enable, in application-properties set 'spring.h2.console.enable=true'
  * JDBC URL: jdbc:h2:mem:testdb
  * Username: sa
  * Password: 
  
# Jhipster
  * https://start.jhipster.tech/jdl-studio/
          
  # injecting other dependencies in the config class (section 5 => 82)
    - @Configuration
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
     }        
        
  # Loading initial data in spring boot
    * https://www.baeldung.com/spring-boot-data-sql-and-schema-sql  
    
  # Load up Data on start up
    * (section 5, 87 jhon)
    * CommandLineRunner (pet clinic project)
      * when implementing this interface, on loading the spring context, spring will run the menthod
      - @Component
        public class DataLoader implements CommandLineRunner {
        
            private final OwnerService ownerService;
            private final VetService vetService;
            private final PetTypeService petTypeService;
            private final SpecialtyService specialtyService;
            private final VisitService visitService;
        
            @Autowired // need not required to mention
            public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                              SpecialtyService specialtyService, VisitService visitService) {
                this.ownerService = ownerService;
                this.vetService = vetService;
                this.petTypeService = petTypeService;
                this.specialtyService = specialtyService;
                this.visitService = visitService;
            }
        
            //    public DataLoader() {
        //        ownerService = new OwnerServiceMap();
        //        vetService = new VetServiceMap();
        //    }
        
            // when spring context is up, it will run this method
            @Override
            public void run(String... args) throws Exception {
                /* if we add JPA rerunning this will populate data again and again in DB to avoid it */
                int count = petTypeService.findAll().size();
        
                if (count == 0 ){
                    loadData();
                }
            }
        }
    * applicationContext     
    
  # Generate Schema file
    * Section 18, 318 jhon
    * https://shekhargulati.com/2018/01/09/programmatically-generating-database-schema-with-hibernate-5/   
  
  # Files used by spring Boot to initialize the DB
    * schema.sql
    * data.sql(by default spring searches for this file)
  
  # CORS Global Configuration
    * https://stackoverflow.com/questions/32319396/cors-with-spring-boot-and-angularjs-not-working
    * https://www.baeldung.com/spring-cors  
    * sergey section 29
        
  # Lombok
    @Getter and @Setter
    @FieldNameConstants
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
    @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger
    @Data(@ToString, @EqualsAndHashCode, @Getter, @Setter and required args constructor)
    @Builder
    @Singular
    @Delegate
    @Value
    @Accessors
    @Wither
    @SneakyThrows 
    
  # Testing 
    * Code under Test (testing code)
    * Test fixture(dummy objects for tests)
    * Unit Test
      * testing specific code, like method in a class
      * should not have external dependencies(no DB, spring context)
    * Integration Test
      * test behaviour between objects and parts of overall system
      * can include DB, Spring COntext, message Brokers
    * Functional Test
      * Testing a running application, which is deployed
      * using web driver, calling web services, send/receiving messages 
    * TDD
      * write tests first and the code
    * BDD
      * cucumber, spock, Jbehave
    * Mock
      * fake implementaion of a class , test double
    * Spy
      * override selct methods of a class   
    * Dependencies
      * Junit - for unit testing
      * Spring Test and Spring Boot Test - Utilities and Integration test support for Spring Boot Applications
      * AssertJ - assertion lib
      * Hamcrest - matcher objects lib
      * Mockito - mocking framework
      * JSONassert - assertion lib for JSON
      * JSONPath - Xpath for JSON 
      
      * @SpringBootTest 
        *  bring up the complete spring context with everything loaded(loads all beans services, controller's, no need to manually set up)
      * @DataJpaTest
        * brings up a light weight spring context(data jpa, data layer, need to setup service manually) 
    * Creating seperate profile foe running testcases
      *  https://stackoverflow.com/questions/45200126/spring-boot-junit-run-all-unit-tests-for-multiple-profiles    
         
  # Command Objects
    * not expose domain/models classes/objects
    * bind web form data    
  
  # To ensure thread safety on multi threaded environment machines use lombok's @Synchronized
  
  # @init Binder
    * something like use to process the incoming request paramas, validating then, ex. white listing params like 
      * permit().require() in rails, and other things
      
  # @ModelAttribute
    * similar to before_filters in rails         
  
  # Jenv
    * https://stackoverflow.com/questions/26252591/mac-os-x-and-multiple-java-versions
  
  # Seeding like rails, in spring boot
    * Application context refresh(events) - not spring boot specific
    * command line runner - spring boot specific    
    
  # run java jar
    * java -cp target/sumofnumbers-1.0-SNAPSHOT.jar com.rajesh.App 1 2
    * java -jar *.jar
  
  # Maven
    * mvn install(compiles and builds project)
    * mvn install -DskipTests (to skip tests)   
    * mvn archetype:generate -DgroupId=com.rajesh -DartifactId=hellomaven -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
    
  # Scopes in plugins
    * compile (default)   
    * test
    * provide
    * system
    * import
   
  # Automatic Date Update
    * https://stackoverflow.com/questions/46504448/how-to-insert-data-with-automatic-value-to-createddate-and-updateddate-using-spr/46504581  
    * http://techie-mixture.blogspot.com/2018/01/spring-data-jpa-auditing-auto-save.html
    
  # EH Cache (Level 2 Cache)
    * Cache provider, caches objects at session factory level  
    * Fast And LightWeight
    * Can be used as in memory or disk based caching(serializing and deserializing)
    * Allows time out for an object in cache. (XML based)
    * https://www.concretepage.com/spring-4/spring-4-ehcache-configuration-example-with-cacheable-annotation
    * https://dzone.com/articles/spring-boot-with-ehcache-3-and-jsr-107
    * https://www.youtube.com/watch?v=cWqNeANzEz0
    
  # Redis Cache
    * https://www.concretepage.com/spring-boot/spring-boot-redis-cache  
    * https://medium.com/@MatthewFTech/spring-boot-cache-with-redis-56026f7da83a
    * https://www.baeldung.com/spring-data-redis-tutorial
    * https://dzone.com/articles/implementation-of-redis-in-micro-servicespring-boo
    * https://stackoverflow.com/questions/34201135/spring-redis-read-configuration-from-application-properties-file
    * https://stackoverflow.com/questions/51818137/spring-boot-2-converting-duration-java-8-application-properties
    * https://github.com/cristianprofile/spring-data-redis-lettuce
    * https://howtodoinjava.com/spring-boot2/spring-boot-cache-example/
    * https://stackoverflow.com/questions/34893279/spring-data-redis-expire-key
    * https://aggarwalarpit.wordpress.com/2017/01/25/setting-ttl-for-cacheable-spring/
    * https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
    * https://github.com/cristianprofile/spring-data-redis-lettuce/blob/master/src/main/java/com/cromero/redis/configuration/RedisConfiguration.java -> lettuce
    * https://chartio.com/resources/tutorials/how-to-get-all-keys-in-redis/ => get keys and values in redis
    * https://www.tutorialspoint.com/redis/keys_ttl.htm
    * https://docs.spring.io/spring-data/redis/docs/2.2.0.M1/reference/html/#reference
    * https://grokonez.com/spring-framework/spring-data/spring-data-redis-example-spring-boot-redis-example
    * https://redis.io/commands/del
  
  # Connecting to multiple DB's
    * https://www.devglan.com/spring-boot/spring-boot-multiple-database-configuration
    
  # in 28 minutes
    * http://www.springboottutorial.com/spring-boot-projects-with-code-examples    
    
  # github tree like in browser
    * https://opencollective.com/octotree
    
  # Good Blog
    * http://www.appsdeveloperblog.com
    
# Deploying Apps
  * mvn clean (removes the already generated target folder, with compiled code)
  * mvn install (to build and compile )  
  * mvn spring-boot:run (this command will the app in apache tomcat servlet container like rails s)   
    
# Running compiler jar/war files from other Location
  * java -jar mobile-app-ws-0.0.1-SNAPSHOT.jar 
  
# Archiving Applications as Jar/War
  * when archived as Jar, it will even contain the apache tomcat in it, used then sharing the files to others machines 
  * when archived as War, it will not even contain the apache tomcat in it, used when already existing stand alone tomcat  
    server is running an need to use it   
    
# TomCat running from command Line
  * move to where tomcat is installed /Users/rmanjunath/Desktop/tomcat/apache-tomcat-9.0.12/bin 
  * ./startup.sh
  * ./shutdown.sh
  * or  ./catalina.sh start  ,  stop
  * if say's permission denied, then chmod a+x *.sh
  
# Java Path on Mac Jenv
  * /Library/Java/JavaVirtualMachines/  
  
# Cas Server    
  * https://apereo.github.io/2017/03/28/cas5-gettingstarted-overlay/
  
# Java Uninstall
  * https://stackoverflow.com/questions/19039752/removing-java-8-jdk-from-mac  

# Microservices
  * Bounded Context (Boundaries - figure out how to break build individual microservices)
  * Configuration Management (there will be multiple instances with different env's of each app, need to configure them) 
  * Dynamic Scale Up and Dynamic Scale Down (how many instance of each microservice should be running)
  * Visibility (centralized logging and monitoring with )
  * Fault tolerance
  
  * Advantages
    * Adaptation of new technologies and process(like node.js, kotlin etc)
    * Dynamic Scaling
    * Faster Release cycles
    
  * Zuul Gateway
    - https://howtodoinjava.com/spring-cloud/spring-cloud-api-gateway-zuul/
    - https://www.xoriant.com/blog/product-engineering/microservices-security-using-jwt-authentication-gateway.html  
  
  # Spring Cloud(Finchley version)
  
    * https://howtodoinjava.com/spring-cloud/spring-boot-ribbon-eureka/
     
    * https://stackoverflow.com/questions/42238335/when-to-configure-zuul-routes 
    
    * https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8 => microservice security
    * https://developer.okta.com/blog/2018/02/13/secure-spring-microservices-with-oauth - (https://www.youtube.com/watch?v=MY5m_s_U2H4&t=3s)
    * https://www.youtube.com/watch?v=ZIAi8sGHPII
    
    * Setup a Centralized server config using Spring Cloud Config Server, 
      and spring cloud will distibute to other microservices
      
    * spring cloud netflix (Dynamic Scale up and Scale Down)
    
      * Spring cloud config server (package => config server)
          * Used for keeping a centralized different env properties for the applications
          * spring-microservices => 57      
          * spring-cloud-config-server folder
          * https://github.com/raj23manj/spring-microservices/blob/master/git_localconfig_repo_files/limits-service.properties
          * to make the config server to work we need to add 
            - @EnableConfigServer
          * we can access it from url to test  
        
        * Important on configurations on client service => 65
          * need to add spring cloud config client(for all client services), it will connect to the config server
          * based on the application name set below it will pick up the properties, and the profile
          * when using the spring cloud config server, the client which uses it should rename the application.properties to
            "bootstrap.properties". This file should have the name of its current application and the spring cloud config server
            uri. The below should be present on the client service(limits service), this will call automatically 
              - spring.cloud.config.uri=http://localhost:8888 
                spring.application.name=limits-service
                spring.profiles.active=qa
          
          * if new properties are updated and pushed to git, need to restart the services. we can automate this 
          * there is a refresh link to do without restarting service, in the service which uses config server like limits service, need actuator => 100
            - disable the security on the actuators end-points
            - localhost:8080/actuator/refresh 
          * if multiple instances of the service are running, then we need to indivudually hit the url one by one, to avoid this and make one call
            so that all instances get refreshed, we need to use spring-cloud-bus  
        
      * Feign - RestFul Service Cleint, makes Easier to call REST Service using Rest Template using Proxy => 77
        1) Used for writing easier restful clients
        * it makes a proxy call 
        * to enable @EnableFeignClients
        * to interact with client
          - @FeignClient(name="currency-exchange-service", url="localhost:8000")
          - see currency-exchange-serivice proxy
        
      * Ribbon - Client Side Load Balancing => 78 & 79
        1) Make sure Load is balanced easily, between the multiple instance of a particular service
        * in pom set spring-cloud-starter-netflix-ribbon in the service client which (currency-conversion-service)
        * use this on the calling service 
          - @RibbonClient(name="currency-exchange-service")
          * Use to register all the instances of the service to be called that is running on, from the calling service
            * eureka handles this for us, a naming server, ribbon asks eureka what all instances of currency exchange are running and it gets back, hten ribbon invokes the appropriate service 
            - currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001  
        
      * Eureka - Naming Server - service discovery & service registration => 82
        1) all micro-services will register here, 
        2) service discovery - naming service will provide urls of current instance to asking microservice 
        * After setting up everything need to restart all services and eureka server and wait for 60 secs
        * Things to do in a service client that want's to register to eureka
            * need to add in pom 
              - spring-cloud-starter-netflix-eureka-client/eureka discovery
            * enable the discovery client 
              - @EnableDiscoveryClient  
            * using this in the service that want's to register with the naming server
              - eureka.client.service-url.default-zone=http://localhost:8761/eureka 
              
            * Distributing calls using eureka & ribbon => 84
              * eureka handles this for us, a naming server, ribbon asks eureka what all instances of currency exchange are running and it gets back, hten ribbon invokes the appropriate service 
              - #currency-exchange-service.ribbon.listOfServers=http://localhost:8000,http://localhost:8001   
              - for registration, it uses the application name we specify during registration with eureka "spring.application.name=currency-exchange-service"
                and use this same "spring.application.name" in the feign and ribbon to discover it. 
                
                - @FeignClient(name="currency-exchange-service") 
                - @RibbonClient(name="currency-exchange-service")
        
        * Eureka application setup
          - eureka server
          - config client => to have configuration in config server
          - @EnableEurekaServer
          * in application-properties
            - spring.application.name=netflix-eureka-naming-server
            - eureka.client.register-with-eureka=false => to avoid the this application to register itself
            - eureka.client.fetch-registry=false => to avoid fetching a registry
        
    * Visibilty, Monitoring, Gateway
      1) Netflix API Gateway (Zuul) 
        * common functionalities like logging, security etc across microservices can be done using this
        * Rate Litmis(no of requests per hour or day), fault tolerant(if service goes down, should send message)
        * Service Aggregation, an external call may many calls for one functionality, and it may contain many calls, we
          need toaggregate it 
        * can be used for server side load balancing  
        
        * Setup Zuul Server => 87, 88
          - zuul, eureka discovery
          - @EnableZuulProxy
          
        * Url Routing wihtout zuul routes setup using application name => 89
          - http://localhost:8765/{application-name}/{uri}  
          
        * For Feign interaction with other service, instead of doing it directly we need to do via zuul
          - remove this @FeignClient(name="currency-exchange-service")  
          - @FeignClient(name="netflix-zuul-api-gateway-server") // feign will interact with the zuul server and get the api from naming server  
            @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
        
        * Logging via zuul
          * log requests going through zuul  
          - https://github.com/raj23manj/spring-microservices/blob/master/netflix-zuul-api-gateway-server/src/main/java/com/in28minutes/microservices/netflixzuulapigatewayserver/ZuulLoggingFilter.java
          - public class ZuulLoggingFilter extends ZuulFilter{ 
            }
          - pre/post/error filters
          - interception logic  
      
      2) Zipkin(Distibute Tracing system) & sleuth(Distibute Tracing) 
        * add it to zuul application
        * Consolidate all logs from all the services and put it in a rabbit mq and send it to the zipkin server 
        * https://dzone.com/articles/spring-cloud-amp-spring-bootimplementing-zipkin-se
       
        * Sleuth(assigns Id to requests, across all services) 
          * Spring Cloud Sleuth assigns Id to requests, and is used to trace across multiple components   
          * need to add it to services and zuul app
            - spring-cloud-starter-sleuth  
          * to intercept all requests     
            * create a sampler, in zuul application and the services that implements sleuth
            - @Bean
             	public Sampler defaultSampler() {
             		return Sampler.ALWAYS_SAMPLE;
             	}
          * for one request(which might contain call to other services from one service like nested), there will be same id assigned by
            sleuth, and it automatically takes care of it   
          * we can check this id individually on the server logs, it is difficult to maintain hence use zipkin  	
             	
        * Zipkin => 93
          * need to install rabbit mq
          * download zipkin server, and make zipkin listen to rabbit mq
          * localhost:9411
          * RABBIT_URI=amqp://localhost java -jar zipkin-server-2.11.12-exec.jar    => make zipkin listen to rabbit mq
          * used to centralize all the request with the id's assigned by sleuth to one place, a centralized dashboard  
          
          * now make the services to put the messages to rabbit mq => 98
            * we need to add dependencies to the services
            - spring-cloud-starter-sleuth-zipkin => used for giving sleuth id's in the format zipkin accepts	     
            - spring-cloud-bus-amqp(for old version of spring boot) => spring-rabbit(new version of spring boot)
              * https://www.udemy.com/microservices-with-spring-boot-and-spring-cloud/learn/v4/t/lecture/12451184?start=0
              
    * Spring-Cloud-Bus => 102
      * we can use rabbit mq, kafka multiple options available
      * if multiple instances of the service are running, then we need to individually hit the url one by one, to avoid this and make one call
        so that all instances get refreshed, we need to use spring-cloud-bus     
      * issue with finchely implementing with spring boot 2.0.2+ => 101
      * use "spring-rabbit" to connect the services to rabbit mq
      * diable the security for actuators
      * hit the url "/actuator/bus-refresh" => in this case on limits service, this will refresh all instances of limits service
      
      * how it works
        * on the start-up of the services all the instace's register with the rabbit-mq bus
        * if there is a chnage in the config server, and hit the refresh url on any one of the instances will create a event to refresh other instances on the cloud
          bus, and the cloud bus will propogate this to all the instances
        * once we add all the right dependencies, spring will auto configure all for us, seeing an rabbit-mq dependency in the class path, it will automatically configure    
                    
    * Fault Tolerance => 103
      - https://www.baeldung.com/spring-cloud-netflix-hystrix
      - https://howtodoinjava.com/spring-cloud/spring-hystrix-circuit-breaker-tutorial/
      1) Hystrix - if a service is down, hystrix helps to configure a default response. 
      * if the service is down it need's respond back to the other service that it is down so that the other service which is dependent on it
        can handle it
      * need to add this to service
        - spring-cloud-starter-netflix-hystrix
        - @EnableHystrix
        * and on all the controller method need to add
          - @HystrixCommand(fallbackMethod="fallbackRetrieveConfiguration")
        
      
    * Microservice Setup
      1) Start them in the order - Naming Server, Distributed Tracing Server, API Gateway, Calculation Service, Exchange Service      
      2) Wait for a couple of minutes      
      3) Run the Service that you would want to execute! (hit hte url)     
      4) See Zipkin 
    
    * Security   
      
      * Architecture
        * https://stackoverflow.com/questions/37180375/using-zuul-as-an-authentication-gateway 
      
      * JWT 
        * https://www.xoriant.com/blog/product-engineering/microservices-security-using-jwt-authentication-gateway.html    
        * https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8
        * https://www.oodlestechnologies.com/blogs/Securing-Micro-Services-Using-Zuul-Gateway-Filters 
        
        - OAUTH
          * https://www.youtube.com/watch?v=cKjgkNt-tFg 
      
      * Session
        * https://stackoverflow.com/questions/33921375/zuul-api-gateway-authentication   
        
# Micro Services 2.1.1

  * Config Client
      * Setting up all services first => section 3 
      
      * Configure git to hold the properties  
      * server side config
      - <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>   
        
      * Client Side Config
      - <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>    
        
        # properties need to connect to the config server in bootstrap.properties
        spring.profiles.active=dev
        #spring.application.name=currency-conversion-service => to be same as folder in git/or like ranga rao explains
        spring.application.name=currency-conversion 
        spring.cloud.config.uri=http://localhost:500
        
      * for both client and server
      - <properties>
            <java.version>1.8</java.version>
            <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
        </properties>
        <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring-cloud.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
            </dependencies>
         </dependencyManagement> 
         
         <repositories>
            <repository>
                <id>spring-milestones</id>
                <name>Spring Milestones</name>
                <url>https://repo.spring.io/milestone</url>
            </repository>
        </repositories> 
        
      # dynamically refresh the properties files
        * using actuator/refresh
        * there was issue
        
      # Secure the Properties values
        * spring supports symmetric and assymmetric encryption
          * symmetric => shared secret
              * the default jcs policy files bundled with jre need to replace inbuilt jre security policy, has restriction for country by country, so with oracles unlimited crypto graphic policy open source one
              * download from oracle corporation   
          * assymmetric => public and private key
          
        * Since the properties are coming as json, we need to encrypt or decrypt the properties    
        * passworddb: {cipher} akdjhskdhash
        * things to do for encryption
          - use the secret key on client and config server for encryption and decryption
          - spring.cloud.config.server.encrypt.enabled=false => to show the decrypted password on config server
    
        * Override application properties if not want to set in properties file
          * https://stackoverflow.com/questions/52373541/spring-cloud-config-server-user-id-and-password-to-connect-to-github
          * java -jar config-server.jar --spring.cloud.config.server.git.username=xxx --spring.cloud.config.server.git.password=xxx
          
  * Service discovery(eureka)
    * Eureka Client & Server
    * OpenFeign
    * Ribbon Client Load Balancer  
    
    * uses
      * it abstracts the physical IP's of the instance's on the client and maintains on the eureka server,
        the client can access it using the service discovery by its service name
      * if one of the instances is down it will take care of rerouting the requests to other instances  
      * All the services registered will send regular heart beats to the eureka client to make sure it is up and running
      * There will be one instance on eureka server running, if it fails entire architecture fails, to avoid this we can have 
        another one step as back up (cluster of load balancer). It increases the cost and complexcity. In this case each instance
        interact with each other to check if it is down or up and share the service lookup state with each other, if a node in the cluster fails
        peer nodes make sure they are active and running 
    
    * Eureka Server - server side setup     
      * select Eureka-server from components
      * whenever we run a eureka server, it assumes that it is a node and is a part of a cluster, so it needs to register itself to other
        node. If there is only one instance running then we need to make sure it does not register's with itself
        - eureka.client.register-with-eureka=false/true
      * when the client is running it tries to fetch a local registry from the eureka server to speed up the process if multiple nodes of eureka
        are present in the cluster, as we have one we can disable it
        - eureka.client.fetch-registry=false
      * Once the eureka server is up and clients register, it takes adefault of 30 secs to wait before it appears on the dashboard. The reason for 
        this is the client after registering tries to give a heart beat at an interval of 10s for three time, and the eureka sever verifies it 
        once it is successful it shows up on the dashboard, and then singnals that this service can be used. In development phase we can reduce this
        time to 5 secs
        - eureka.server.wait-time-in-ms-when-sync-empty=5
      * when the eureka service starts up, it tries to fetch the registry information from the other nodes(peer nodes) it cluster aswell, it tries for 5 times  
        - eureka.server.registry-sync-retries=3
      * when eureka starts up, it starts thread to replicate the states with the peer discovery nodes. So that all the nodes in the cluster can be at the
        same place to share the information the consumers
        - eureka.server.max-threads-for-peer-replication=0  
      * @EnableEurekaServer tells application that it is service discovery application
      
    * Eureka Client Configuration
      * @EnableEurekaClient
      * dependency
        -<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
      * Properties Setup
        * Location of the eureka server, the required service contacts for resolution of the  service physical location
          - eureka.client.service-url.defaultZone=http://localhost:8001/eureka
        * tells the current service which behaves like a client for eureka, need to register with eureka server 
          - eureka.client.register-with-eureka=true
        * to cache the registry of the eureka service locally, so that for every request call it can look up this cache and not hit the eureka server.
          This cache refresh's periodically. Optimises the performance
          - eureka.client.fetch-registry=true
        * this property is used not display the entry of the client on eureka server Dashboard it goes down after defined seconds
          - eureka.server.eviction-interval-timer-in-ms=15000
        * when the service comes online and sends a heart beat without registration, it recieves back response as "not found" from the server, the client
          then tries itself to register which takes 30 sec's by default and can be controlled, this is the reason why there is delay in the dashboard of the 
          client entry registry. best not change it from 30 or reduce it, self-preservation presumes that heartbeats are at interval of 30 secs, it change it to
          3 sec's, then self-preservation gets affected
          - eureka.instance.leaseRenewalIntervalInSeconds=30
        * if a service shuts down or gets killed, it immediately does not remove the entry instead eureka server sends 3 consecutive missing heart beats, 
          and after 90 sec's it marks as dead and deletes the entry from the registry
          - eureka.instance.leaseExpirationDurationInSeconds=90
        * how the instance will be displayed on the dashboard, format
          - eureka.instance.instance-id=${spring.application.name}:${server.port}
        * tell the service to register with eureka servers with IP address instead of host names. In container environment this is best as hostname is randomly
          assigned and dns name is not available for container based deployment
          - eureka.instance.prefer-ip-address=true
  
        * Self-Preservation
          * a feature to minimize the registry inconsistency between the servers. If eureka server does not recieve consecutive heart beats then it will mark,
            as dead or expire it from registry when they don't recieve beyond a certain threshold
    
      * Setting up of clusters of Eureka Services
        * each eureka node instance in a cluster is a client for it's peer and need to register itself, for this we need to add a vaild peer eureka
          instance server URL
        * all the services will register will all the nodes of eureka servers in the cluster automatically 
        * when multiple instances are running, the client request are not sent to both eureka instances, when requesting for a registry eureka sends
          only one registry of the node, this is sharing attribute of the cluster to register both the instances  
            
      * Consuming the api exposed by the provider after registering with eureka
        * eureka client uses ribbon to the registry from eureka server with all the instances of registere client's locally and with that
          eureka client gives us a method to find an application by name
          -  import com.netflix.discovery.shared.Application;
             Application app = eurekaClient.getApplication("currency-conversion");  
      
      * Using Ribbon Load Balancer
        * Eureka client uses ribbon as load balancer, we can manually do this by usi a annotation @LoadBalanced on a RestTemplate as bean, which behind uses ribbon load 
          balancer by intercepting the requests 
        * By default when requesting with the service-name, ribbon resolves it automatically  and gives chances to all other services 
        * Ribbon does a round robbin among all the instances to keep it efficient  
        
        - in the bootstrap class(@SpringBootApplication) add, making the rest template aware of load balancer client
          @LoadBalanced
          	@Bean
          	public RestTemplate getRestTemplate() {
          		return new RestTemplate();
          	}
            
            
          in controller, using service name ribbon resolves by load balancing
          ResponseEntity<CurrencyConversionVO> responseEntity = restTemplate.getForEntity(
          					"http://currency-conversion/api/v1/from/{from}/to/{to}", CurrencyConversionVO.class,
          					urlPathVariables);
          					
      * Using Feign          
        * To abstract rest based calls to other services
        * We need define an interface and annotate it with spiring cloud annotaions, to map what eureka based service ribbon will invoke. It is
          the responsibilty of the spring cloud framework now to generate a proxy calls on the fly, that will be used to invoke a targeted  service.
        * on the client service which uses feign to make calls, we need to declare them all under same packages so that it contains all thw feign client 
          proxies
        * the interface should have same method signature as the actual call, to proxy it with same path as well          
        * with degin proxy we need to do "@PathVariable("from")", it does not bind automatically  
        - @FeignClient(name = "currency-conversion")
          @RequestMapping(value = "/api/v1")
          public interface CurrencyConversionServiceProxy {
          	@GetMapping(value = "/from/{from}/to/{to}")
          	public CurrencyConversionVO convertCurrency(@PathVariable("from") String from, @PathVariable("to") String to);
          }   
        
        * when using fegin client it throws only feginexception for any error situation, but if we need application specific exception  
          it can be done by our own implementation of error decoder that is available using feign.builder.errordecoder
        * https://github.com/OpenFeign/feign/wiki/Custom-error-handling  
        * https://github.com/OpenFeign/feign-annotation-error-decoder
        * https://stackoverflow.com/questions/38786207/netflix-feign-propagate-status-and-exception-through-microservices
        * https://cloud.spring.io/spring-cloud-netflix/multi/multi_spring-cloud-feign.html
        
    * Zuul Gateway(logging, authentication, routing)  
      * Zuul Service Gateway
      * Routing
      - <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
        </dependency>
      
      * @EnableZuulProxy, in application bootsrap class  
        * this adds zuul level proxy filters and integration capabilities for eureka service discovery
      
      * If we do not want to use zuul provided routing filters and eureka discovery and implement our own then we need to add
        * @EnableZuulServer
        
      * Zuul gateway is a reverse proxy, it accepts the request from clients and forwards it to the services and does manupliations inbetween as well  
      
      * the actuator on the zuul, on hitting the get routes url, it hits the eureka server and gets a json payload of the routes, this has the details of the
        routes managed by Zuul  
        
      * if any of the client service is down, eureka will not retunr any routes for that service  
      
      * Zuul has 3 mechanisms to route to the services
        * 1) if any routes are not configured  explicitly, then it uses the service discovery to resovle the incoming requests
        * 2) Another way is to configure routes explictly which at the end are shared with the consumers and use service discovery engine to resolve the physical
             location
        * 3) last way is to define routes and map it to the static URL's, instead of using discovery engine 
        
      
      * 1) Automatic discovery of routes using eureka 
        * Zuul server has eureka client that talks to the Eureka server and gets the registry 
        * when we hit /currency-conversion/api/v1/from/USD/to/INR, zuul takes "currency-conversion" from the url, checks it with the regitry it has
          if it finds an mapping in Json, it will take that name and call eureka to resolve it to the actual location, and constructs the url 
          and sends it
      
      2) This method is used when we want to shorten the service-name, or use a different name for the routes and map it to the existing service 
         in eureka. But when the currency-conversion service is down, we won't get key-value pair for it, but "/cc/**" will be there, coz it is on zuul
        - zuul.routes.currency-conversion=/cc/**
          - the name after routes is taken from after hitting actuator/routes, the key
            {
              /flight-schedule/**: "flight-schedule",
              /currency-conversion/**: "currency-conversion"
            }
            
      3) Manual Custom routes  
         * need to set properties 
         - zuul.routes.greeting-service.serviceId=greet
           zuul.routes.greeting-service.path=/greet/**
           zuul.routes.greeting-service.url=http://localhost:9999    
         
      * properties
        * add custom routes mapping to service-name
          - zuul.routes.currency-conversion=/cc/**
        *  after doing the previous step, to remove entry of default one use this
          - zuul.ignored-services=currency-conversion
        * use to set prefix for the route, flights/cc/**
          - zuul.prefix=/flights
        * Setting a id for the service 
          - zuul.routes.greeting-service.serviceId=greet
        * setting custom manual route
          - zuul.routes.greeting-service.path=/greet/**
        * setting manual location
          - zuul.routes.greeting-service.url=http://localhost:9999
          
      * Zuul Filters
        * 3 types of filters are present in zuul
          * Pre Filters (Authentication Filters)
            * Can implement auhtentication and authorization
          * Route Filters
            * invoked after pre filters, but just before the request is sent to the destination service
            * best place for making decision based dynamic routing 
            * example, if we have two versions of a service, if we want to route 30-70 ratio, it can be done here 
          * Post Filters (Response Audit Filters) 
            * this filter is invoked when the response from service is done and ready to respond with response to the client  
            * we can override the response here
            * perform auditing here
            
        * Implementation of filters => section 6, 40
          * to implement ZuulFilter - Base Abstract Class this we need override the methods
            * filterType() => to classify type of filter - pre, post, route
            * filterOrder() => to define the execution order of the filter
            * shouldFilter() => is the filter active or not
            * run() => write the filter logic
          * Predecoration Filter - ServiceId, in ZuulFilter
            * // this service-id will be only set after pre-decoration filter
              Object serviceId = ctx.get("serviceId");  
              
        * Zuul Routing filters  section 6, 42    
          * Zuul comes up with its own routing logic, and it achieves this using
            * SimpleHostRoutingFilter
            * RibbonRoutingFilter
            * when using this filter and trying to send the current request to a new request, then
              we need to discard the current route request. To do that,
                //disable SimpleHostRoutingFilter
                ctx.setRouteHost(null);
                
                //disable RibbonRouting Filter
                ctx.remove(serviceId); 
            
    # Microservice security
      * https://github.com/spring-cloud/spring-cloud-netflix/issues/162 
      * pass jwt between microservices
        * https://keyholesoftware.com/2016/06/20/json-web-tokens-with-spring-cloud-microservices/
        * https://stackoverflow.com/questions/47362618/spring-cloud-zuul-pass-jwt-downstream  
      
      Our target architecture is several small microservices, Eureka as service registry and Zuul as edge servers.
      We using JWT-based auth mechanism.
      The question is - how to organize auth? Use custom-written Zuul Filters on Edge or auth and pass plain requests to downstream servers or use Spring Security on the front-door and use Zuul only for routing?
      Or pass JWT token to downstream servers and write some custom stuff for each service?
      
      Client sends POST /api/login request to Zuul
      Zuul sees that this request does not contain JWT token in header
      Zuul redirects request to Auth server
      Downstream perform auth and in successful case responses with Token
      Zuul returns token to client
      Client uses this token in following requests
      When client sends request with JWT token Zuul figures out this and parsed it. It is able to parse since the secret key is also stored on Zuul.
      Zuul parses token and break it into several fields - user id and role.
      Zuul sets custom headers
      All downstream services use custom filter who takes user id and role from headers and transforms it into Spring Security auth object or whatever.           

  * Resiliency (Hystrix, Circuit breaker)   
    * https://github.com/Netflix/Hystrix/wiki/Configuration#circuitBreaker.sleepWindowInMilliseconds 
    * https://dzone.com/articles/microservices-part-4-spring-cloud-circuit-breaker 
    * multiple apps on hystrix
      * https://github.com/Netflix/Hystrix/issues/117
      * https://exampledriven.wordpress.com/2016/07/05/spring-cloud-hystrix-example/ => this worked
    * Circuit Breaker
      * This makes sure that client will not call to service that is failing  repeatedly or responding poorly, to have rich user experience
      * Hystrix Closely monitor's each service call and if calls get failed frequently, it fails fast to close the circuit, 
        then it opens the circuit to source for alternate source of data, hystrix checks if the calls to service is working and if
        it does it closes the circuit to normal path for service execution
        
      * Fallback Approach
        * implements another source for data  
        
      * Bulk Head Approach  
        * This approach seggregates the service calls into multiple thread pools, to ensure that degredation of the system will affect the execution
           part 
        * Each service  will be assigned to a thread   
        * Hystrix provides an option of processing of the services in multiple thread pools
        
      * implementation on the service
        - <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
          </dependency>  
        * in bootstrap class
          - @EnableCircuitBreaker => tell to enable hystrx circuit breakers for the service 
      
      * Annotation Based    
          * @HystrixCommand
            * by default it wait's for 1 sec then throws exception, if this annotation is added to the methods
            * we use this annotation to wrap the method which has potentially a risky functionality in it, for example a call for the DB or
              a call over the network 
            * During the deployment time, when spring finds this annotation, it generates a proxy and wraps this method to take control of its execution
              by one of the thread managed by one of the threads managed by the thread pool hystrix        
            * whenever a call is made, hystrix expects a responce within a second range, if not it throws the Hystrix runtime exception expalning the
              time out
            * It tell to wait for 7s and the hystrix property for it is  
            - @HystrixCommand(commandProperties = {
                        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "7000") })  
            * List of properties
              * https://github.com/Netflix/Hystrix/wiki/Configuration#introduction
              
      * Property Based
        * set the command properties as key value pair or use method name in the propery file to set the values          			
        - hystrix.command.getFlightsKey.execution.isolation.thread.timeoutInMilliseconds=3000
          #hystrix.command.getFLights.execution.isolation.thread.timeoutInMilliseconds=3000 => not good, methods name might be same in other class
    
      * Fallback Strategy
        * This is the right way in doing hystrix, we should not increase the time, but if it takes more time we need to implement
          a fallback method to respond back to the client with alternate response 
        * the reason for interruption might be due to some exception, error in service or due to interruption by hystrix command to service
          calling thread due to delay in the response by the service 
        * The fall back method defined should have the same signature of the actual calling method.
          - public List<Flight> getFLights(String from, String to){};
          - public List<Flight> buildFallbackFlights(String from, String to) {}   
          * reduce the time aswell 
            - hystrix.command.getFlightsKey.execution.isolation.thread.timeoutInMilliseconds=3000
            
      * Bulk Head Strategy
        * By default when we annotate our bootstrap class with @EnableCircuitBreaker, Hystrix on the start up of the service creates a thread
          pool and all the calls to the method which are annotated with @HystrixCommand are processed by a thread in the thread pool, so when we have 
          multiple functionalities defined in our service which are again dependent on other down stream services and we have annotated the functionalities
          with @HystrixCommand and calls to all the functionalites will be severved by the threads in the same thread pool. 
        * Let's assume one of the dependent service is not performing well, so can block the threads of the thread pool which can affect other functionalities 
          due to unavailability of the threads. Bulk head Strategy comes to rescue, we can place remote service calls to its own thread pool with a configuration
          of number of thread, usage etc so that bad performing services should not affect other service calls to avoid sysem degradation and crash at last 
        - @HystrixCommand(commandKey="getFlightsKey", fallbackMethod="buildFallbackFlights", threadPoolKey="getFlightsThreadPool",
          			threadPoolProperties= {
          					@HystrixProperty(name="coreSize", value="1"), @HystrixProperty(name="maxQueueSize", value="2")
          			})
          			
            * coresize => used to set the number of core threads needs to be available for the processing at the max
            * maxQueueSize => quesize to hold the requests if thread is not available 	
            
      * How Hystrix works behind the scene
        * if a request is failing contineously to serve, Hystrix monitors it if fails beyond a threshold limit, if it stops making requests
          to service for a certain point of time, in order to give service the time to recover or to resume if down and forward the request to fallback path
          to serve the request if implemented this process is called short circuit. This is the characteristics os a Circuit breaker       
        * The Monitoring is controlled by three properties 
          * Time Window   	
            * it is used by hystrix to observer how often the service call is having the problem 
          * Number of service call  
            * There is threshold for service call number, to identify how many calls are getting failed	  
          * Failure Rate
            * There is threshold of failure rate that defines whether circuit should be open or not, default is 10 sec. 
          * When first call gets failed, hystrix starts monitoring
            for next 10 secs, if for the next 10 sec the number of request coming to the system remains lower then the configured number of service call, it 
            does not take action regarding circuit trip. But if it crosses the limit next it checks how many calls are getting failed, if the failure ratio
            crosses the configured failure rate, then hystrix open the circuit the and fail fast the circuit call. 
          * when the circuit is open it does mean it open all the time, after opening it checks if the failed service is resumed or working fine again, this
            checking is done in a periodic interval      
      
      * Customization properties
        * For Time Window
          - hystrix.command.getFlightsKey.metrics.rollingStats.timeInMilliseconds=15000
        * It denotes the number of calls that must occur in the observation window, in above mentioned 15 sec time window 
          - hystrix.command.getFlightsKey.circuitBreaker.requestVolumeThreshold=10
        * The % of calls that must fail during the observation window due to exception or timeout
          - hystrix.command.getFlightsKey.circuitBreaker.errorThresholdPercentage=50
        * Time window aftre which hystrix will check if service is working, to close the circuit
          - hystrix.command.getFlightsKey.circuitBreaker.sleepWindowInMilliseconds=6
  
  * Distributed Tracing(Sleuth, Zipkin, Hystrix dashboard, Turbine Stream)
    * Sleuth
      * implements distributed tracing solution
      * responsible for generating tracing ID, and handles the complexcities in tracing and the propagation as well
      * it adds the tracing information to slf4j mapped diagonistic context(mvc), so that the information can be further sent to a aggregator 
        for further analysis at a central place   
      * it has good implementation with zipkin tracing system and publish the tracing information to zipkin  
    
    * Zipkin server & UI
      * it is a distributed tracing system, helps record timing, meta data of operations executed in the microservice architecture and give a visual
        representation to the users for the actions across the system 
        
    * Hysrix Dashboard & Turbine
      * used to monitor the hystrix metrics, when the @hystrixCommand is introduced into the service it publishes metrics to the operational events using
        turbine streams which hystrix dashboard consume and display it on the dashboard. 
        
    * Implementation       
       * Sleuth
           - <!-- Sleuth Client -->
             <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-sleuth</artifactId>
             </dependency>
           * Sleuth will inspect all the incoming services for the tracing information, and will inject it if it is not present for logging purpose.
           * it also maintains propogation of this ID when forwards the requests to dowstream service calls.
       
          * Output
              * Format: servicename - TraceID - Trace span ID - Boolean Variable
                * TraceID will be same for all the services
                * Trace Span ID will be generated for every service call
                * boolean variable represents whether the trace information has been sent to zipkin server or not
                
                - example:
                  zuul-service-gateway,d17b49e4256dff07,d17b49e4256dff07,false
                  flight-fare,d17b49e4256dff07,48f210561e30f115,false   
                  currency-conversion,d17b49e4256dff07,61c62da6ecc8e7be,false
                  
       * Zipkin Server
          * we can't do like eureka server a seperate server
          * helps us view the transactional flow across the services visually  
          * it shows the time taken for a transaction, and breaking down individually how long each service took 
          * There is issue with setting up of zipkin server, we need to do it by set it up using this
            https://zipkin.io/pages/quickstart.html (Running from source) or the java one
          * it runs on port http://localhost:9411/zipkin/          
          
          * on the client service
            - <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-starter-zipkin</artifactId>
            </dependency> 
            
            * zipkin uses amqp to process the traces, recieve the traces from services. SO we need it to be running
            * properties to set on client
              *
                - ribbon.readTimeout=60000
              * by default no span id is sent to zipkin server can be seen only in log. This property allows us to define
                the probability of sending traces to Zipkin server. Value is from 1-100, 100 means send every sample to zipkin 
                  
                - spring.sleuth.sampler.probability=100
              * used to set the URL of zipkin running server
                - #Not mandatory; this is the default value
                - spring.zipkin.baseUrl=http://localhost:9411  
            
            * need to download and install rabbit mq(see below for rabbit mq), and set zipkin to listen to it and restart zipkin    
              - RABBIT_URI=amqp://localhost java -jar zipkin-server-2.11.12-exec.jar
              - RABBIT_URI=amqp://localhost java -jar zipkin.jar
              
       * Hystrix Dashboard and Turbine cluster 
         * https://dzone.com/articles/spring-cloud-with-turbine 
         * Hystrix Dashboard
           * used to monitor the metrics 
           
         * Turbine Stream                  
           * Used to monitor real time metrics using 
           * this is the one which aggregates the data from the servers and to a real stream
         
         * Hystrix dashboard would figure out the instances of the cluster using Eureka, source the Hystrix stream from each instance and 
           aggregate it into the Turbine stream
           
         * Setup up hystrix server dashboard
           * dependencies
             - <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
                </dependency>
             * in bootstrap class add,
                - @EnableHystrixDashboard => for showing dashboard
                  @EnableTurbine =>  aggregating the data into the turbine stream, so that data from different services can be collected 
                                     to a single place, this is mandatory because dashboard can show data only when we some data in the central
                                     place      
             * Properties
               * used to define the service, for which we want to collect the metrics
                 - turbine.app-config=flight-schedule
               *  takes the expression to create a turbine cluster
                 - turbine.cluster-name-expression=new String("default")               
         
  # What is Microservices
    * 
    
               
# To Access Environment(application-properties)
    @Autowired
    private Environment environment;  
    environment.getProperty("local.server.port")    
          
# Aws
  * ssh -i *.pem.txt/.pem ec2-user@dns ...  
  
  # other way
    * chmod 400 *.pem
    * ssh-add
    * ssh-add *.pem
  
  # Issues for Tom cat
    * https://stackoverflow.com/questions/38551166/403-access-denied-on-tomcat-8-manager-app-without-prompting-for-user-password 
    
    
# Rabbit MQ(message broker)
   * rabbitmq-server 
   * /usr/local/etc/rabbitmq/    
   * /usr/local/sbin
   * rabbitmqctl stop
   * https://stackoverflow.com/questions/8737754/node-with-name-rabbit-already-running-but-also-unable-to-connect-to-node
   * https://stackoverflow.com/questions/49071667/cant-enable-plugin-in-rabbitmq-3-7-3
   
   
# Debugging
  * https://dzone.com/articles/spring-tips-reactive-sql-data-access-with-spring-d  
     
# Spring Security 
   * https://stackoverflow.com/questions/41770156/spring-add-custom-user-details-to-spring-security-user
   * http://forum.spring.io/forum/spring-projects/security/122998-how-to-use-custom-userdetails-in-spring-security
   * https://stackoverflow.com/questions/29479840/spring-session-and-spring-security(gives idea good one)
   * https://www.baeldung.com/spring-security-authentication-provider (custom authentication provider)
   * https://www.youtube.com/watch?v=WHLmJSRrtuQ
   * https://blog.ngopal.com.np/2017/10/10/spring-boot-with-jwt-authentication-using-redis/ (redis + security + spring + JWT)
      * https://github.com/privatejava/spring-boot-redis-jwt
   * https://www.baeldung.com/registration-with-spring-mvc-and-spring-security (registering new user)  
   * https://www.youtube.com/playlist?list=PLTgRMOcmRb3P7yTaWigTyEmnTlCy6w6bE(Youtube Packt) 
   * https://www.packtpub.com/application-development/spring-security-essentials
   
   * Spring Security Course Baeldung
     * https://www.baeldung.com/learn-spring-security-course
   
   * Architecture:
     * https://springbootdev.com/2017/08/23/spring-security-authentication-architecture/ (Authentication Architecture)
     * https://blog.imaginea.com/spring-security-architecture-part-1/
     * https://www.dineshonjava.com/spring-security-java-based-configuration-with-example/
   
   * JWT Microservices
     * https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8  
   
   # Get Current Logged in User(custom-user 2nd one)
     * https://dzone.com/articles/how-to-get-current-logged-in-username-in-spring-se
     * https://stackoverflow.com/questions/22678891/how-to-get-user-id-from-customuser-on-spring-security
     * https://www.baeldung.com/get-user-in-spring-security
     * https://github.com/jhipster/generator-jhipster/issues/3988
     * https://aykutakin.wordpress.com/2013/07/08/spring-security-spring-custom-authentication-provider/
     * http://forum.spring.io/forum/spring-projects/security/99252-how-to-get-logged-user-id
        * this.findUsersByUsername(SecurityContextHolder.get Context().getAuthentication().getName()).getSingle Result().getId() b
     
   # Spring Sessions
     * https://www.baeldung.com/manually-set-user-authentication-spring-security
     * https://dzone.com/articles/spring-jdbc-session
     * http://djeison.me/2017/10/30/spring-security-session-redis/
     * https://docs.spring.io/spring-security/site/docs/5.0.5.RELEASE/reference/htmlsingle/#what-is-authentication-in-spring-security
     * https://www.baeldung.com/spring-security-session
     
     # Filters
       * https://itexpertsconsultant.wordpress.com/2015/11/26/spring-security-custom-filterchainproxy-using-java-annotation-configuration/
       * https://stackoverflow.com/questions/41480102/how-spring-security-filter-chain-works
       * https://www.programcreek.com/java-api-examples/?api=org.springframework.security.web.FilterChainProxy
       * Custom Filters:
         * https://www.baeldung.com/spring-security-custom-filter
         * https://www.baeldung.com/spring-delegating-filter-proxy
       * How Filters are managed in spring boot
         * https://dzone.com/articles/what-does-spring-delegatingfilterproxy-do  
     
   # Notification Security
     * https://www.baeldung.com/spring-security-websockets    
        
   # Theory
     * Just adding 'spring-boot-starter-security' to pom enables a basic form based security to browser with sessions, and a basic security
     for only requests with out sessions. The default usename is 'user', password is printed on the console
     *      
   
   # Logging
     * https://www.baeldung.com/java-logging-intro
     * https://www.baeldung.com/slf4j-with-log4j2-logback  
     * https://stackoverflow.com/questions/33744875/spring-boot-how-to-log-all-requests-and-responses-with-exceptions-in-single-pl
     * https://dzone.com/articles/logging-spring-rest-apis  
     * https://stackoverflow.com/questions/41293847/how-to-create-log-files-in-spring-boot
     * https://www.callicoder.com/spring-boot-log4j-2-example/
     * https://springframework.guru/using-log4j-2-spring-boot/
     * https://logging.apache.org/log4j/log4j-2.7/manual/configuration.html
     * https://dzone.com/articles/log4j-2-configuration-using-properties-file
     * https://stackoverflow.com/questions/48941104/use-spring-boot-application-properties-in-log4j2-xml
     * https://howtodoinjava.com/log4j2/log4j2-properties-example/
     * https://docs.spring.io/spring-boot/docs/current/reference/html/howto-logging.html
     * https://www.mkyong.com/logging/logback-set-log-file-name-programmatically/
     * https://examples.javacodegeeks.com/enterprise-java/log4j/log4j-writing-different-log-files-example/
     * https://dzone.com/articles/logging-with-log4j-in-java
     
     * Default Spring logging
       * https://howtodoinjava.com/spring-boot/logging-application-properties/
       * https://howtodoinjava.com/spring-boot2/spring-boot2-log4j2-properties/
   
# JAVA
  # Good Links
    * https://howtodoinjava.com/
    
  # Streams and Lambda
    * https://www.youtube.com/watch?v=suSdjhS03qk&index=24&list=PLqq-6Pq4lTTa9YGfyhyW2CqdtW9RtY-I3  
  
  # Serialisation and deserialisation
    * https://www.youtube.com/watch?v=n8if6X47KvE  
    
  # Exceptions
     # https://crunchify.com/better-understanding-on-checked-vs-unchecked-exceptions-how-to-handle-exception-better-way-in-java/
     # Unchecked Exceptions(Runtime):  
         * But the few I have used are - IllegalAccessException , ArithmeticException, NumberFormatException and SecurityException.
         * IllegalArgumentException, IllegalStateException, NullPointerException, IndexOutOfBoundsException,ConcurrentModificationException,
           UnsupportedOperationException, ArrayIndexOutOfBoundsException
           
     # Checked Exceptions(Compile Time - this can be avoided by using throws on method, class or try catch)
       * IOException, SQLException, DataAccessException, ClassNotFoundException, InvocationTargetException, MalformedURLException 
       * https://beginnersbook.com/2013/04/java-throws/      
       -  public void myMethod() throws ArithmeticException, NullPointerException
          {
            // Statements that might throw an exception 
          }
          
          public static void main(String args[]) { 
            try {
              myMethod();
            }
            catch (ArithmeticException e) {
              // Exception handling statements
            }
            catch (NullPointerException e) {
              // Exception handling statements
            }
          }
          
  # DATA-TYPES
    * primitive: byte, short, 
                 int, long (choose Int by default)
                 float, double (choose double by default)
                 char,
                 boolean,
    *              
                 String
                 
  # Oops
    *  Classes
        * State => variables
        * Behaviours => methods 
               
    *  Inheritance(interfaces => default(like instance methods) and static methods)
      * https://medium.com/@martinomburajr/java-8-default-static-interface-methods-similar-but-different-a8dcc3460280
      * Overloading
        - Compile time polymorphism
      * Overriding
        - Runtime polymorphism
        - @override to be used if overriding, compiler will check for signature with parent
        - only instance methods to be can be overriden not static methods 
        - while overriding the method or variable can't have a lower access modifier
          - ex if parent method is protected then using private in child will throw errr, but making it public is allowed 
        - methods that are final cannot be overriden  
      * Static Methods, varaibles
        - declared using static
        - these methods can't access instance methods and instance variables direclty
        - these are used for operations that don't require any data from an instance of class, ex calculate discount 
        - no access to this 
        - static varaible shared among all objects of class
        
    * Composition
      * "has a " relation. Car has a Engine. Computer has a motherboard
      * modeling parts as a whole
      * Delegation, 69 tim's course
    
    * Encapsulation
      * To restrict Access to certain components, variables which is the inner workings of a class
      * Access Modifiers - https://www.geeksforgeeks.org/access-modifiers-java/
        * private
        * protected
        * public
        * default              
    
    * Polymorphism
      * Overloading
          - Compile time polymorphism
      * Overriding
        - Runtime polymorphism
        - @override to be used if overriding, compiler will check for signature with parent
        - only instance methods to be can be overriden not static methods 
        - while overriding the method or variable can't have a lower access modifier
          - ex if parent method is protected then using private in child will throw errr, but making it public is allowed 
        - methods that are final cannot be overriden 
        
  # Boxing and Unboxing
      * For array List and other collections Lists if we need to hold an array of integer values then we can't do this
        - ArrayList<int> arr = new ArrayList<int>(); // compilation error Type argument cannot be primitive
        - example of it will work, let's create a custom Int class
          - class IntClass { // wrapper class for myValue
              private int myValue;
              
              public IntClass(int myValue) {
                  this.myValue = myValue;
              }
              
              public void setMyValue(int myValue){
                  this.myValue = myValue;
              }
              
              public int getMyValue(){
                  return myValue
              }
          }
          
           - ArrayList<IntClass> arr = new ArrayList<IntClass>();
           - arr.add(new IntClass(23));
      * Hence Collections can't hod primitive type directly, but can hold objects hence we need to box and unbox the primitive types.
      * There are pre built classes for Primitive Types provided by Java 
        - Integer 
          - Longer Way
              - ArrayList<Integer> arr = new ArrayList<Integer>(); 
                - arr.add(Integer.valueOf(23)); => Auto Boxing
                - arr.get(23).intValue(); => Unboxing
              - adding a new value like setter, Integer.valueOf(23) => boxing
              - like getter to get the value,  arr.get(index).intValue();
          - Shorter Way => Java takes care of it at compile time and it converts to above boxing format
            - Integer myVal = 23; => Auto Boxing => Integer.valueOf(23)
            - int myInt = myVal; => Unboxing =>  myVal.intValue()    
        - Double
        -                 
        
  # Arrays & Lists => ArrayLists
    * Arrays are contiguous memory
    * fixed time access based on index, very fast
    * Array
        * int[] myVAr = new String[10]#{1,2,3};     
        * int[] myVar = #new int[1,2,3]  
    * List
      * Ordered Collection
      * ArrayList<Integer> myVar = new ArrayList(); // new ArrayList<Integer>();     
        * add, size, set(position, item) => update, remove(position), contains, indexOf(item), get(index/position)
  
  # LinkedList
    * In array list if only adding new elements and deleting elements will work better like stack without adding any new 
      elements or deleting inbetween the list at any random position, but if a element needs to be added at specific position then
      shuffeling the elements takes time, hence Linked list can save time   
      
    * LinkedList<String> arr = new LinkedList<String>();  
    
  # Interfaces => https://www.baeldung.com/java-static-default-methods
    * Class that needs to implement will be defined here used by different classes
    * methods with definition can be defined like modules
    * static methods with definition will be class methods
    * interfaces are contracts
    * methods by default are public and abstract 
    * from java 9 onwards interfaces can contain private methods 
    * default methods acts like instance methods   => https://dzone.com/articles/interface-default-methods-java
    - public interface oldInterface {
          public void existingMethod();
          
          default public void newDefaultMethod() {
            System.out.println("New default method" " is added in interface");
          }
          
          static boolean isNull() { 
            return true;
          }
      }
  
  # Inner Classes / Anonomyous Class => like compositions
    * Nesting classes
    * 4 types 
      * Static Nested classes 
        * Mainly used to associate a class with its outer class
        * Packaged inside the outer class
        * this class cannot access non static members of it's outer class without creating an instance of it
        
      * Non Static Nested Class => (Inner class) => used much
        * importtant, the data variable used for both classes needs to be different, or else child class will shadow 
          parent class variables
        - public class GearBox {
            private int maxGears;
            private int currentGear = 0;
            
            GearBox(int gear) {
              this.maxGear = gear
            }
            
            // non static inner class
            public class Gear {
                private int gearNumber;
                private double ratio;
                
                Gear(int gearNum, double ratio) {
                  ...
                }
            }
        }
        
        - in psvm class
          - GearBox gb = new GearBox(6);
          - GearBox.Gear first = gb.new Gear(1, 12.3) => using the instance of GearBox
          - // GearBox.Gear second = new GearBox.Gear(1, 12.3) => won't work for non static inner classes
          
        * As a good programming practice,
          * it will be good if we make the inner class private, and create a method to instantiate a new object of 
            Gear box and expose it, and use it's method just like delegation in composition  
      
      * Local Class => not much used
        * inner class defined inside a scope block which is a method
        * Scope restricted to that block
          -  see 105 pretty complex(Tim)
        * https://www.geeksforgeeks.org/local-inner-class-java/
        * Local Inner Classes are the inner classes that are defined inside a block. Generally, this block is a method 
          body. Sometimes this block can be a for loop, or an if clause.Local Inner classes are not a member of any 
          enclosing classes. They belong to the block they are defined within, due of which local inner classes cannot 
          have any access modifiers associated with them. However, they can be marked as final or abstract. These class 
          have access to the fields of the class enclosing it. Local inner class must be instantiated in the block they 
          are defined in
        
        -  public class Outer 
           { 
               public int data = 10; 
               public int getData() 
               { 
                   return data; 
               } 
               public static void main(String[] args) 
               { 
                   Outer outer = new Outer(); 
                     
                   if(outer.getData() < 20) 
                   { 
                       // Local inner class inside if clause 
                       class Inner 
                       { 
                           public int getValue() 
                           { 
                               System.out.println("Inside Inner class"); 
                               return outer.data; 
                           } 
                       } 
              
                       Inner inner = new Inner(); 
                       System.out.println(inner.getValue()); 
                   } 
                   else
                   { 
                       System.out.println("Inside Outer class"); 
                   } 
               } 
           }   
        
      * Anonomyous class is also a local class => used a lot in android
        * https://www.geeksforgeeks.org/anonymous-inner-class-java/
        * Inner class without a class name
        * used only when one instance of the local class is required
        - using it as interface.
          interface Age 
          { 
              int x = 21; 
              void getAge(); 
          } 
          
          /* not needed */
          class MyClass implements Age{ 
              @Override
              public void getAge()  
              { 
                  // printing the age 
                  System.out.print("Age is "+x); 
              } 
          }
          /* not needed */
          
          class AnonymousDemo 
          { 
              public static void main(String[] args) { 
            
                  // Myclass is hidden inner class of Age interface 
                  // whose name is not written but an object to it  
                  // is created. 
                  Age oj1 = new Age() { 
                                          @Override
                                          public void getAge() { 
                                               // printing  age 
                                              System.out.print("Age is "+x); 
                                          } 
                                      }; 
                  oj1.getAge(); 
              } 
          }
          
          * Difference between Normal/Regular class and Anonymous Inner class:
            
            * A normal class can implement any number of interfaces but anonymous inner class can implement only one 
            interface at a time.
            * A regular class can extend a class and implement any number of interface simultaneously. But anonymous 
              Inner class can extend a class or can implement an interface but not both at a time.
            * For regular/normal class, we can write any number of constructors but we cant write any constructor for 
              anonymous Inner class because anonymous class does not have any name and while defining constructor class 
              name and constructor name must be same.
              
  # Abstract Class
    * when classes share same functionality and needs to be shared.
    * this class cannot be instantiated
    * can have abstract methods, without implementations
    * the subclass usually provides implementations for all of the abstract methods in its parent class. However, 
      if it does not, then the subclass must also be declared abstract 
    * we can declare fields that are not static and final
    * we can define public, protected and private methods   
    * it can extend from one parent only
    -  
      public abstract class Animal {
        private String name;
        
        public Animal(String name) {
          this.name = name
        }
        
        public abstract void eat();
        public abstract void breathe();
        
        public String getName() {
          return name;
        }
      }   
      
  # Abstract Vs Interface
    * Use abstract when there exists "is a" relationship like inheritance  dog is a animal 
    * Use Interface  when there exists "can" relationship
      - Animal -> bird and mamals
      - bird can fly
      - mamal -> bat can fly
      - use interface here      
         
  # Generics
    * https://www.youtube.com/watch?v=9tHLV0u87G4
      - public class Box<Type> {}
      
    * multiple type parameters   
      - public interface pair<K, V> {
              
      }
      
      - public class OrderedPair<K,V> implements pair<K, V> {}
      
    * Diffrernce between U and <U> using in a method
      -  public class Box<T> {
             // U here mean's return type
             public U returnTypeValue() { => public String/Integer/Object returnTypeValue() {}
             }
             
             // U here mean's it is 'U' type is going to be used inside the scope this method 
              public <U> String returnTypeValue(U u) { 
              }
      } 
      
    * Bounded Type Parameter using concept of inheritance
      * Upper Bound and Lower Bound
        * https://stackoverflow.com/questions/19795709/understanding-upper-and-lower-bounds-on-in-java-generics   
      
      - public class Box<T> {
            // we will passing a Generic Type here as a parameter, apart from the one we are passing to the class
            // U will be any Class that extends of inherits from Number class. String won't work here
            public <U extends Number> String returnTypeValue(U u) { 
            }
        }
        
      - public class Box<T> {             
              // custom class will be lowest in the inheritance chain, so anything from parent till CustomClass only allowed
              public <U super CustomClass> String returnTypeValue(U u) { 
              }
          } 
          
      * Super(lower Bound, lowest in the chain) Vs Extends (upper Bound Highest in the chain)
          * Extends(used more)
              * ? extends T means any class/interface which extends/implements T. Thus, we are referring to the children of T. 
              Hence, T is the upper bound. The upper-most class in the inheritance hierarchy
              
              * Use this when we need to use getters inside the logic of the block
              
              - public void sumOfNumbers(list<? extends Number> numbers) {
                double d = 0.0;
                for(Number n : numbers) {
                  d += n.doubleValue();
                }
                
                System.out.println(d);
              }
              
          * Super (used less)
              * ? super T means any class / interface which is super of T. Thus we are referring to all the parents of T. 
                T is thus the lower bound. The lower-most class in the inheritance hierarchy  
              
              * use this as generic as possible. see 43:30  
              * Use this when we need to use setters inside the logic of the block 
              
               - public void sumOfNumbers(list<? extends Number> numbers) {
                  numbers.add(1);
                }
        
    * Wild Cards (making things more flexible)
      -  public class Box<? extends Number> { 
        // any Type(class) which extends or implements Number class/interface
        // no inheritance hirerchical chain involved 
      }
      
  # Static & Final
    * Static
        * used for creating class methods and variables
        
    * Static Intializers  => Static equivalent of constructors  
        * Static blocks, can also be created these are called before the constructors
        * will be executed once when the class is loaded 
        - public class Test {
          static {
            // code here
          }
          
          Test() {}
        }
        
    * Final 
        * Used for constant's
        * Not to be modified
        * when the class constructor is called it can be changed
        * can be used to stop overriding methods in subclass
        
  # Java Collections
    * sets, maps,  queues, array list, linked list
    
    * Collection
      * set(Hash-Set, Linked HashSet)
        * SortedSet (treeSet)
      * List
      * Queue
      * Deque
      * Functions
          * binarysearch
          * mergeSort
          * reverse
          * min
          * max
          * swap
          * copy => deep copy
      
      
    * Comparable(compareTO) & Comparator  
      * https://www.youtube.com/watch?v=oT973Y-IiMA
      
      * Comparable 
        * natural ordering
        * Collection.Sort(List<T> list)
        * implement comparable, and override compareTo() for lists for comparing. Like Collection.binarysearch(), or any 
          comparing operation, and will be classes default compare to method  
      
      * Comparator
        * Collection.Sort(List<T> list, Comparator<? super T> c) 
        * if we need to override the default logic done by comparable in the class, then use this
        * Collections have sort alogrithms to use the and sort based on logic, this logic will be mentioned in the 
          comparator method  
        * Use Comparator for custom sort logic, using anonmouys class
        - static final Comparator<Seat> Price_Order = new Comparator<Seat>() {
          @Override
          public int compare(Seat seat1, Seat seat2) {
          // logic -1 is less, 0 is equal, 1 is greater
            return -1 or 0 or 1
          }
        };
        
        - Collection.sort(seats, Theater.Price_Order)  
        
      * Minor Known Issue if value comparing are equal
        * ordering being inconsistent between equals
        * need to pass 0 if equal  
        * in this case need to add additional condition for equals
    
     
    * Map(Dictonaries) => {a => 'b'}, {a => [1,2,3]}
        * HashMap and Linked HashMap => unique keys
        * need to override HashCode
    
      * SortedMap  
      
    * All implement collection interface   
       
             
  # EqualsTo and HashCode
    * https://www.youtube.com/watch?v=ghswNpRv2t0
    * https://www.youtube.com/watch?v=V-vDUbK6wm0
    
    * https://www.youtube.com/watch?v=Nr56SlbMed4 => good
    
    * In HashMap and HashSet there will be no duplicates(key;s in HashMapn and value's in HashSet). Two different objects
      can have same hashing value and be put in the same bucket, when comparing it uses equalTo() method, when object same
      values are present with same hashcode it first checks with equals then rejects
    
    * equalsTo()
      * check if two objects are equal
      * by default it will only check for two objects are instances of the same class 
      * need oveeriding so we can customise
      
      
    *  hashcode() 
      * returns an integer which is the memory location of the item 
      * helps to identify on object in an heap of objects in the memory
      * helps in performance (searching elements)
      * need to be unique
    
    * when to override them
      * You must override hashCode() in every class that overrides equals().
      * Collections such as HashMap and HashSet use a hashcode value of an object to determine how it should be stored 
        inside a collection, and the hashcode is used again in order to locate the object in its collection.
      * https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java  
      
    * https://howtodoinjava.com/java/basics/java-hashcode-equals-methods/         
    * https://stackoverflow.com/questions/3563847/what-is-the-use-of-hashcode-in-java
    
  # immutable classes
    * https://www.geeksforgeeks.org/create-immutable-class-java/
    * immutable class means that once an object is created, we cannot change its content. In Java, all the wrapper classes (like String, Boolean, Byte, Short) and String class is immutable. We can create our own immutable class as well.
      
    * Following are the requirements:
      • Class must be declared as final (So that child classes can’t be created)
      • Data members in the class must be declared as final (So that we can’t change the value of it after object creation)
      • A parameterized constructor
      • Getter method for all the variables in it
      • No setters(To not have option to change the value of the instance variable)  
     
    -  public final class Student 
       { 
           final String name; 
           final int regNo; 
         
           public Student(String name, int regNo) 
           { 
               this.name = name; 
               this.regNo = regNo; 
           } 
           public String getName() 
           { 
               return name; 
           } 
           public int getRegNo() 
           { 
               return regNo; 
           } 
       }   
            
  # Set and HashSet
    * No duplicates
    * Ordered sets  
    - Set<> demo = new HashSet<>();
    
  # Streams and HashMap
    * http://www.java67.com/2017/07/how-to-sort-map-by-values-in-java-8.html  
                   
  # Threads/Concurrency 
    * Application or Process refers same and has one thread, each thread can have multiple threads
    * Each thread has it's own heap of memory space  
    * Threads Creadted by a process shares the same resources anf files of the parent thread or process
    * Each Thread has it's own thread stack      
    * Every Java Application runs as a single process or thread, each process can have multiple threads. 
      each process has a heap and each process has a  thread stack.
    * Concurrency means, dowload some data draw on screen, then repeat.  
    * Not allowed to start the instance of thread more than onceif needed create a sub class
      of Thread Class and run multiple instances
    * Two ways of creating a Thread.
      1) Extend Thread Class and override "run()"
      2) Make the class implement Thread Class and implement "run()" method
    * A Thread will terminate once it returns from its run method after completing or else if it encounters a return statement  
    * When calling start() method after overriding run method it will execute in another thread, if run() itself is called, it will
      execute in the same thread
    * Interrupting a thread will terminate the thread, to interrup an thread we instance of that thread from the main thread.
    * We can make a thread to join with other thread and wake it up if it is sleeping   
       // when a thread is put to sleep here, anotherThread instead making it to sleep
       // for 3 sec's, we can make it join to this(myRunnableTHread) thread, once this
       // thread execution is completed it will start anotherThread, then it will execute the code following
       //  System.out.println(ANSI_RED + "another Thread terminated, so im running again"); 
    * When multiple threads(bank account example) are run with same instance(countdownclass - some work), the instances(countdownclass) 
      instance variable will be shared, i,e it will allocated on the heap and shared. if the variables are local
      variables on the instances(countdownclass) it will be added to the thread's Stack      
    * Race-Condition(Thread Interference): when multiple thread share the same resource and write them, leads to a race condition.
    * Synchronisation: To avoid race condition, we need to use synchronised methods or statements
      - Constructors cannot ne synchronised
      - not use local variables if using synchronisation  
      - we need to synchronise the code that only needs to be synchronised, like the for loop(int i), this will affect performance of application
      - Methods that can be only called inside synchronised code: (Pub/Sub)
        - wait() : 
           - it release the locks it is holding(current thread) for the other thread to execute
           - always needs to be called inside the loop, so that if it misses the conditions, it will go back to begining of the loop
        - notify() :
          - notify a particular thread
        - notifyAll() :
          - notify all the threads
    * Locks: Also used to avoid race conditions, it is used on statements. When one thread has a lock other threads are suspended and
      wait until lock is free  
    * Critical sections means, all the shared resources(variables) are made synchronised
    * Thread Safe, means the developer whos has developed the code has handled the synchronise section. We as a developer need not worry about thread interference  
    * Not Thread Safe, means we as a developer need to make sure synchronisation is handled for multiple threads
    * There are some operation for which threads cannot be suspended, these are called Atomic operations that happens all at once and a thread connot be suspended while doing them, they are
      - Reading and writting refference variables
        ex: myobj1 = myobj2
      - Reading and writting of primitive variables except long, double 
        ex: int y =10; => cannot be suspended
            Long y =10; => can be suspended 
      - Declaring all variables as "volatile"      
    * Collections: (lecture 249, section 15)
      - Some collections are not thread safe
        ex: ArrayList 
    * java.util.concurrent package was introduced to run multiple threads, help dev's properly synchronise code
    * Reentrant Lock - when a thread is holding a lock and enters again it can execute
    * Thread Pool - manages the threads for us
      * Executor service https://www.baeldung.com/httpclient-guide
         ExecutorService executorService = Executors.newFixedThreadPool(3);
         (new Thread(r)).start(); => ex.execute(r);
    * ArrayBlockingQueue => Thread safe,  this is bounded and manages locks internally
      * methods to this put, take and pee all take lock for executing    
      
      
# Lambdas
  * https://www.youtube.com/watch?v=gpIUfj3KaOc&list=PLqq-6Pq4lTTa9YGfyhyW2CqdtW9RtY-I3&index=1
  
  * Enable us to write functional programming
  * enables support for parallel processing
  * Easier to use API's and Libraries
  
  * lambdas are functions that does not belong to a class, and exist in Isolation and these functions can be 
    treated as values.  
  
  * Scenario
    * imagine if i class has only one menthod and i want to call it, i need to instantiate the class. Instead if
      I could oly call or pass the method, that is where lambda's come in.
  
  * In Java 7
    * https://www.youtube.com/watch?v=nL7H4F_ly_k&list=PLqq-6Pq4lTTa9YGfyhyW2CqdtW9RtY-I3&index=5
    * we can do this by using interfaces and passing the instance of the class and calling the method
    
    
  * In Java 8 using Lambdas
    - aBlockOfCode = () -> {
                            System.out.print("Hello world");  
                            return null;  
                         };
                         
    - doubleNumberFunction = (int a) -> a * 2; => ((int a) -> return a * 2)
    
    - addFunction = (int a, int b) -> a + b; 
    
  * Types for lambdas
    * we can use an interface to declare lambda's
    * But the interface should have only method as per the lambda expression
    * we can use existing interfaces for types also
    
    - interface MyLambda {
        void foo();  
      }  
      
    - interface MyAdd {
         int add(int x, int y);  
       }                  
  
    - MyLambda aBlockOfCode = () -> { system.out.print("Hello") };
    - MyAdd addFunction = (int a, int b) -> a + b;
    - // an anonnomouys class
      MyAdd innerClassMyAdd = new MyAdd() {  // => see video 9 Java Brains
        public int add(int x, int y) {
          System.out.print(x, y);
        }
      }
    
    - aBlockOfCode.foo();
    - addFunction.add(1, 2); => since it implements the interface, the method name defined can be used to call 
    - innerClassMyAdd.add(1,2); 
  
  * Type Inference
    * used to figure out the type of lambdas belong to.
    * Looking at the interface method definition, java will also understand the type of method passed
    -  interface StringLambda {
        int getLength(String s);
      }
      
      StringLambda myLambda = (s) -> s.length // => (String s) => return s.length();
      myLambda.getLength('Hello');
  
  * Runnable Using Lambdas => Threads
    * Doing in Inner class way
      - Thread myThread = new Thread(new Runnable() { // Runnable is a interface
            @Override
            public void run() {
                    System.out.println("Printed Inside Runnable");
            }
       }) 
       
       myThread.run();        
    
    * Doing in Lambda Way
    - Thread myThread = new Thread(() ->  System.out.println("Printed Inside Runnable"); )
      myThread.run();
  
  * Functional Interface
    * For a Lambda Type we need one interface with only one abstract method with it's signature
    * We can create one in the normal way, but if in future a developer comes and changes it will cause problems. To 
      avoid this we mark it as "@FunctionalInterface", and it will enforce. This is optional
    - @FunctionalInterface
      public interface Greeting {
        public void perform();
      }
      
    * Java gives built functional interfaces for specific purposes
      * predicate
        * A predicate is a statement that may be true or false depending on the values of its variables. 
          It can be thought of as a function that returns a value that is either true or false
          - Predicate<String> isALongWord = t -> t.length() > 10;   
      
      * Consumer   
        * This functional interface represents an operation that accepts a single input argument and returns no result. 
        The real outcome is the side-effects it produces. Since it's a functional interface, you can pass a lambda 
        expression wherever a Consumer is expected.
          - class Product {
              private double price = 0.0;
            
              public void setPrice(double price) {
                this.price = price;
              }
            
              public void printPrice() {
                System.out.println(price);
              }
            }
            
            public class Test {
              public static void main(String[] args) {
                Consumer<Product> updatePrice = p -> p.setPrice(5.9);
                Product p = new Product();
                updatePrice.accept(p);
                p.printPrice();
              }
            }
            
      * Function
        * This functional interface represents a function that accepts one argument and produces a result. 
          One use, for example, it's to convert or transform from one object to another. Since it's a functional interface, 
          you can pass a lambda expression wherever a Function is expected.
          -  public class Test {
               public static void main(String[] args) {
                 int n = 5;
                 modifyTheValue(n, val-> val + 10);
                 modifyTheValue(n, val-> val * 100);
               }
             
               static void modifyValue(int v, Function<Integer, Integer> function){
                 int result = function.apply(v);
                 System.out.println(newValue);
               }
             
             }
             
      * Supplier
        *  This functional interface does the opposite of the Consumer, it takes no arguments but it returns some value. 
           It may return different values when it is being called more than once. Since it's a functional interface, 
           you can pass a lambda expression wherever a Supplier is expected.   
           -  public class Program {
                  public static void main(String[] args) {
                      int n = 3;
                      display(() -> n + 10);
                      display(() -> n + 100);
                  }
              
                  static void display(Supplier<Integer> arg) {
                      System.out.println(arg.get());
                  }
              }  
            
  * More Lambda Features(15, 16)
    * java.utils.functions  => has pre-defined functions for common use  
    * https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html  
  
  * Exception Handling in Lambdas
    *  17 , 18
    *  using try/catch in the lambda itself
  
  * Closures in Lambdas
    * 19 
    * the way variables can be accessed in the scope of a function and the lambda
    
  * this reference in lambdas
    * 20  
   
  * Method Reference and Collections => little complex
    * 21 
    * Alternate way of writing lambdas
    * if arguments passed via the lambda and the params accepted by the function being called we can use this
    - public class Test{   
        Thread t = new Thread(() -> printMessage());//  => new Thread(Test::printMessage)
        public static void printMessage() {
          System.out.println("Hello");
        }  
      }
  
  * Java Iterations
    * 22
    * External Iterators(sequentional and ordered)
      - for(int i=0; i< array.size; i++) { System.out.println(array.get(i)) }
      - for(Person p : array) { System.out.println(p) }
      
    * internal Iterators in java 8, this runs in multiple threads, not ordered
      - array.forEach((p) -> System.out.println(p))
      
# Java Streams
  * http://www.java67.com/2014/04/java-8-stream-examples-and-tutorial.html
  * http://www.java67.com/2017/07/how-to-sort-map-by-values-in-java-8.html
  * https://www.youtube.com/watch?v=6TKDjfLGVNc&list=PLIGmoZSj3zVl_GljdHUNmRNeaQWHwLf4c
  * https://www.baeldung.com/java-inifinite-streams
  
  * 23
  * stream is like a conveyer belt, converts the array to one, and each expression attached is like one mechanic/department 
    does a job . 
  * need to add a terminla operation, like count, collect, forEach etc      
    - people.stream()
            .filter(p -> System.println(p))
            .forEach(p -> System.println(p))   
            .count(); 
  * Streams can be used with multi threads, each expression can handle section of the data.
    - people.parallelStream()
            .filter(p -> System.println(p))
            .forEach(p -> System.println(p))   
            .count();
            
  * https://www.youtube.com/watch?v=6TKDjfLGVNc&list=PLIGmoZSj3zVl_GljdHUNmRNeaQWHwLf4c
    
    * Ways' of Obtaining Streams
    
      - List<String> list = {'listval1', 'listval2', 'listval3'}; // assume data is this
        Map<String, String> map = {'mapkey1' => "mapvalue1", 'mapkey2' => "mapvalue2", 'mapkey3' => "mapvalue3" }
      
      * From List => Array
        - list.stream().forEach(p -> System.println(p)); 
        
      * obtain stream from map using entrySet() => HashMap
        - map.entrySet().stream().forEach(p -> System.println(p));
      
      * obtain stream from map using keySet() => HashMap keys
         - map.keySet().stream().forEach(p -> System.println(p));  
      
      * obtain stream from map using values() => HashMap values
         - map.values().stream().forEach(p -> System.println(p));  
      
      * obtain a stream from a string using chars()
        - "1234556asds".chars().forEach(p -> System.println(p));
        
      * obtain a stream using string using split()
        - java.util.stream.Stream;
          Stream.of("A,B,C,D".split(",")).forEach(p -> System.println(p));
      
      * obtain stream from array
        - Integer[] array = {0,1,2,3,4,5}
          Stream.of(array).forEach(p -> System.println(p));
      
      * obtain stream of values  
        - Stream.of("one", "two").forEach(p -> System.println(p));
      
      * obtain a stream from function generate
        - Stream.generate(() -> {return Math.random();}).limit(20).forEach(p -> System.println(p));
      
      * obtain a stream from function iterate
        - Stream.iterate(0,i -> i + 1).limit(20).forEach(p -> System.println(p));
      
      * obtain stream from builder
        - Stream.builder().add("1").add("2").build().forEach(p -> System.println(p));
      
      * obtain stream from another stream                
        -  list.stream().distinct().limit(2).sorted().forEach(p -> System.println(p))
        
    * map, forEach, filter, toArray 
      * toArray types
        - Person[] personArray = persons.stream().toArray(Person[]::new);
          Arrays.aslist(personArray).forEach(person -> System.out.println(person.getId()));
          
        - Object[] objectArray = persons.stream().toArray();
          Arrays.aslist(objectArray).forEach(object -> System.out.println((Person)object.getId())) 
    
    * groupingBy, partitioningBy, counting       
      * for grouping and partitioning
      
    * toList, toCollection collectors  => https://www.youtube.com/watch?v=Z_uFZfYxICw&index=4&list=PLIGmoZSj3zVl_GljdHUNmRNeaQWHwLf4c
      * Collectors.toList() 
        - List<Person> list = persons.stream().filter(p -> p.getCountry().equals("US")).collect(Collectors.toList());
         
      * Collectors.toSet()  
        - Set<Person> list = persons.stream().filter(p -> p.getCountry().equals("US")).collect(Collectors.toSet());
       
      * Collectors.toCollection(ArrayList::new)
        - ArrayList<Person> list = persons.stream().filter(p -> p.getCountry().equals("US")).collect(Collectors.toCollection(() -> new ArrayList<Person>());  // Collectors.toCollection(ArrayList::new) 
      
      * Collectors.toCollection(Vector::new), Collectors.toCollection(HashSet::new), Collectors.toCollection(Stack::new) 
         Vector<Person> list ,     HashSet<Person> list , Stack<Person> list   
         
    * allMatch Stream => https://www.youtube.com/watch?v=1a4CUPVR8m4&list=PLIGmoZSj3zVl_GljdHUNmRNeaQWHwLf4c&index=5
      * returns whether all elements of this stream match the provided predicate, even if one fails it breaks.
        - persons.stream().allMatch(p -> {return p.getCountry().equals("US")});
        
    * noneMatch, anyMatch
      * noneMatch
        - persons.stream().noneMatch(p -> p.getCountry().equals("US"));   
        
      * anyMatch
        - persons.stream().anyMatch(p -> p.getCountry().equals("US"));     
         
# Maven
  # Archetypes
    - Maven project templating tool
         ex mojo, java ee, maven project
  # Snapshot
    - when maven builds a project it will be present in jar or war file
    -        
  # Command Line
    - https://maven.apache.org/guides/mini/guide-creating-archetypes.html
    - mvn archetype:generate                                  \
        -DarchetypeGroupId=<archetype-groupId>                \
        -DarchetypeArtifactId=<archetype-artifactId>          \
        -DarchetypeVersion=<archetype-version>                \
        -DgroupId=<my.groupid>                                \
        -DartifactId=<my-artifactId>
    
    - mvn archetype:generate
        -DgroupId=[your project's group id]
        -DartifactId=[your project's artifact id]
        -DarchetypeArtifactId=maven-archetype-archetype    
        
    - Goal/Task
        - generate
    - Maven Co-ordinate
        - GroupId + ArtifactId + Version   
            - GroupId
                - com.somedomain    
            - ArtifactId(project name)
                - sample  
            - Version
                - 1.0-snapshot 
  # POM
    1) General Project Info (Name,url, dev name, contributors, License details)
    2) Build Settings (Customise MVN lifecycles, add new plugins and goals)
    3) Build Environment (profiles are used to configure different deployment environments, ie dev, staging, prod)
    4) POM Relationships (Modules and sub modules)
    
    - Parent Pom/ Super Pom
        - <modelVersion></modelVersion>
    - Effective Pom
        - Super Pom + Pom    
        
    
    - Packaing Types
        - POM => Multi Module Packaging
        - JAR
        - WAR (without TomCat Server)
        - <packaging>war</packaging>
        
    - LifeCycle - can be customised
        - Clean
        - Build(default)
        - Site (cration of project site documentation)
        
  # Plugins    
    - configurations
      - has optional parameters
    - javadoc
      - For adding documentation
    - Surefire Plugin
      - For running unit test cases        
   
  # Phases & Life Cycles
     * clean
       - clean:clean
         
     * Default
       - process-resources
       - compile
       - process-test-resources
       - test
       - package
       - install
       - deploy
       
       * install's all the plugings under .m2 directory 
          
     * site
       - site:site
       - site:deploy        
  
  # Customising LifeCycle
    -  Need to dig in more      

################### Testing

 * Helps maintain bug free code
 * Helps to modularise code

 # Concepts 
  * Unit Tests
    * Designed for specific sections of code
    * should be very fast
    * should not have external dependencies(no DB, no web service calls, no spring context)
  
  * Integration Tests
    * If need test behaviour between objects to see interaction
    * can include spring context, message broker etc ...
    * slower in running than unit tests
    
  * Functional Tests
    * Means testing a running application
    * Functional touch points are tested (using web driver, calling web services, sending/receiving messages)   
  
  * Importance 
    * write major of test cases as unit
    * then integration tests
    * functional tests 
    
  * Agile Testing Methods
    * TDD
      * write tests first, code to fix tests, refractor coe to cleanup, improve
    * BDD(cucumber)   
      * similar to TDD
      * describe the excepted behaviour of software
      * often expressed as: when/then; given/when/then
      
  * Testing components
    * Mocks
      * a fake implementation of a class used for testing
      * a test double for dependent objects - like datasource
      * can provide expected response
      * can verify expected interactions
      
    * Spy    
      * like a mock, but a real object is used
      * mocks completely replace expected object
      * spys are wrappers, but with real objects inside
  
  * Junit Tests
    * Use Assertions
      * https://junit.org/junit5/docs/5.0.1/api/org/junit/jupiter/api/Assertions.html
    * using junit 5
    * a test method will be annotated as @Test
      - @Test
        void getBar(){}    
    * when the junit test runner will know this is test method and will pick this class and run it  
    
  * Common Testing Frameworks (all used together)
    * Junit 4/5 by Jupiter
    * mockito
    * Spring MVC Test
      * used for testing controllers
      * provide mock servlet environment
    * Rest-assured
      * used for testing restful web services
      * can be used with spring mock mvc  
    * Selenium
      * Functional test, browser level
      
  * CI vs CD(continuous deployment) vs CD(continuous delivery)    
    * CI => running test immediately after commit
    * CD => deployment after test succeeds, build artifacts after CI tests run and deploy
    * CD(delivery) => process of automatically delivering changes directly to production 
  
  * Commands to run test
    * https://www.codejava.net/testing/junit-test-suite-example-how-to-create-and-run-test-suite-in-command-line-and-eclipse
    * https://www.eclipse.org/community/eclipse_newsletter/2017/october/article5.php
    * https://stackoverflow.com/questions/2235276/how-to-run-junit-test-cases-from-the-command-line
    * Commands
      - mvn clean test
      * Or you can run a particular test as below
     
      - mvn clean test -Dtest=your.package.TestClassName
      - mvn clean test -Dtest=your.package.TestClassName#particularMethod
      * If you would like to see the stack trace (if any) in the console instead of report files in the 
        target\surefire-reports folder, set the user property surefire.useFile to false. For example:
     
      - mvn clean test -Dtest=your.package.TestClassName -Dsurefire.useFile=false
  
  * Example's  
    * https://github.com/springframeworkguru/tdd-by-example 
    
    * Project setup for testing section 3, 15
      * Degenerate Objects
        * create new instance of class when testing
          - Dollar product = five.times(2);
            assertEquals(10, product.amount);
            product = five.times(3);
            assertEquals(15, product.amount);
    
      * Equality for All
        * checking if two objects are equal, by overriding Equals method
        - assertEquals(), assertNotEquals
        
  * Important tips
    * to display the object attributes, override toString method in the class. 
    
  * Section 4, Junit 5 setup
    * Major annotations in Junit
      * @Test => marks a method as a test method
      * @ParameterizedTest => marks the method as parameterized test
      * @RepeatedTest => Repeat test N times
      * @TestFactory => Test Factory method for dynamic tests
      * @TestInstance => used to configure test instance lifecycle  
      * @TestTemplate => creates a template to be used by multiple test cases
      * @DisplayName => Human friendly name for test
      * @BeforeEach => Method to run before each test
      * @AfterEach => Method to run after each testcase
      * @BeforeAll => Static method to run before all test cases in current class 
      * @AfterAll => Static method to run after all test cases in current class 
      * @Nested => Creates a nested test class
      * @Tag => declares tags for filtering tests
      * @Disabled => disable test or test class
      * @ExtendWith => used to register with extensions
      
    * Test Life Cycle
      * @BeforeAll => @BeforeEach => @Test => @AfterEach => @AfterAll  
      
  * Section 5
    * assertions
      * JUnit Assertions
        * branch
          * assertion - section 5, 47 
          * groupedassertion - 48
            * assertAll() => grouped assertions 
            - assertAll("Person Properties",
                                        () -> assertEquals("Joe", owner.getFirstName(), "First Name Did not Match"),
                                        () -> assertEquals("Buck", owner.getLastName()))  
          * Dependent assertions - 49
            * assertAll("Properties Test",
            -  () -> assertAll("Person Properties",
                          () -> assertEquals("Joe", owner.getFirstName(), "First Name Did not Match"),
                          () -> assertEquals("Buck", owner.getLastName())),
                  () -> assertAll("Owner Properties",
                          () -> assertEquals("Key West", owner.getCity(), "City Did Not Match"),
                          () -> assertEquals("1231231234", owner.getTelephone())
                  ));
                  
          * Skipping Tests - 50
            * @Disabled
              @Test
              void findByLastName() {}
          
          * Display Name - 51
            * @DisplayName("Test Proper View name is returned for index page")
              @Test
              void index() {}                
          
          * Expected exceptions - 52
            *  assertThrows(ValueNotFoundException.class, () -> { controller.oopsHandler(); });  
            
          * TimeOut - 53
            * assertTimeout(Duration.ofMillis(100), () -> {
                          Thread.sleep(5000);
              
                          System.out.println("I got here");
                      }); => runs in on the same thread and waits until it finishes
            *  assertTimeoutPreemptively()   => junit runs it on different thread, and fails it          
          
          * Junit Assumptions - 54
            * assumeTrue("GURU".equalsIgnoreCase("GURU"));
          
          * Conditional Junit Execution - 55
            *  @EnabledOnOs(OS.MAC), @EnabledOnJre(JRE.JAVA_8), @EnabledIfEnvironmentVariable(named = "USER", matches = "jt") 
            
          * AssertJ alternate library for assertion - 56
            *  
          
          * Tagging and filtering - 62  
            * @Tag("model"), @Tag("controllers")
          
          * Nested Tests - 63
            * @Nested  
            
          * JUnit test interfaces - 64
            * @Tag("controllers")
              public interface ControllerTests {
              }
              
              class IndexControllerTest implements ControllerTests{}
              
          * JUnit Default methods - 65
            * @TestInstance(TestInstance.Lifecycle.PER_CLASS)
              @Tag("controllers")
              public interface ControllerTests {
              
                  @BeforeAll
                  default void beforeAll(){
                      System.out.println("Lets do something here");
                  }
              }
          
          * Repeated Test - 66 
            * @RepeatedTest(value = 10, name = "{displayName} : {currentRepetition} - {totalRepetitions}")
              @DisplayName("My Repeated Test")
              @Test
              void myRepeatedTest() {
                //todo - impl
              } 
          
          * JUnit Test Dependency Injection - 67
            * @RepeatedTest(5)
              void myRepeatedTestWithDI(TestInfo testInfo, RepetitionInfo repetitionInfo) {  // TestReporter
                  System.out.println(testInfo.getDisplayName() + ": " + repetitionInfo.getCurrentRepetition());
          
              }      
          
          * JUnit parameterized Tests - 70
            * <dependency>
                  <groupId>org.junit.jupiter</groupId>
                  <artifactId>junit-jupiter-params</artifactId>
                  <version>${junit-platform.version}</version>
              </dependency>      
              
              @ParameterizedTest
              @ValueSource(strings = {"Spring", "Framework", "Guru"})
              void testValueSource(String val) {
                  System.out.println(val);
              }
              
            * Display Name - 71
               * @DisplayName("Value Source Test")
                @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                @ValueSource(strings = {"Spring", "Framework", "Guru"})
                void testValueSource(String val) {}
                
            * Enum - 72 
              * public enum OwnerType {
                
                    INDIVIDUAL, COMPANY
                }
              
                DisplayName("Enum Source Test")
                @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                @EnumSource(OwnerType.class)
                void enumTest(OwnerType ownerType) {
                    System.out.println(ownerType);
                }  
                
            * CSV Source(List) - 73 
              *     @DisplayName("CSV Input Test")
                    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                    @CsvSource({
                            "FL, 1, 1",
                            "OH, 2, 2",
                            "MI, 3, 1"
                    })
                    void csvInputTest(String stateName, int val1, int val2) {
                        System.out.println(stateName + " = " + val1 + ":" + val2);
                    }
                    
            * CSV Input File - 74
              *  @DisplayName("CSV From File Test")
                 @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                 @CsvFileSource(resources = "/input.csv", numLinesToSkip = 1)
                 void csvFromFileTest(String stateName, int val1, int val2) {
                     System.out.println(stateName + " = " + val1 + ":" + val2);
                 } 
                 
            * Parameterized Tests - Methods - 75
              *     @DisplayName("Method Provider Test")
                    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                    @MethodSource("getargs")
                    void fromMethodTest(String stateName, int val1, int val2) {
                        System.out.println(stateName + " = " + val1 + ":" + val2);
                    }
                
                    static Stream<Arguments> getargs() {
                        return Stream.of(
                                Arguments.of("FL", 5, 1),
                                Arguments.of("OH", 2, 8),
                                Arguments.of("MI", 3, 5));
                    }                   
            
            * Custom Args provider - 76
              *     @DisplayName("Custom Provider Test")
                    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
                    @ArgumentsSource(CustomArgsProvider.class)
                    void fromCustomProviderTest(String stateName, int val1, int val2) {
                        System.out.println(stateName + " = " + val1 + ":" + val2);
                    }
                    
                    public class CustomArgsProvider implements ArgumentsProvider {
                        @Override
                        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
                            return Stream.of(
                                    Arguments.of("FL", 7, 10),
                                    Arguments.of("OH", 11, 42),
                                    Arguments.of("MI", 44, 77));
                        }
                    }
                    
            * Junit Extensions -  78
              * @ExtendWith(TimingExtension.class)
                class PetTypeSDJpaServiceIT {}       
            
        * Test Execution - Section 7  (circle CI Badge/Maven Surefire/ Maven Fail safe)  
          * maven surefire => <!-- used for running unit test for maven-->
          * maven failsafe 83(important) =>  <!-- used for running integration test and has logics-->
            * <plugin>
                  <groupId>org.apache.maven.plugins</groupId>
                  <artifactId>maven-failsafe-plugin</artifactId>
                  <version>2.22.0</version>
                  <configuration>
                      <argLine>
                          --illegal-access=permit
                      </argLine>
                  </configuration>
                  <executions>
                      <execution>
                      <!-- important -->
                          <goals>
                              <goal>integration-test</goal>
                              <goal>verify</goal>
                          </goals>
                      </execution>
                  </executions>
              </plugin>
              
          * Reporting surefireTest - 84
           
        * Mockito - Section 9  
          * Test doubles for classes for fast and light weight test
          * works well with Dependency Injection
          * Types of mocks
            * Dummy - object used just to get code to compile
            * Fake - An Object that has an implementation, but not production ready
            * Stub - An Object with pre-defined answers to method calls
            * Mock - An Object with pre-defined answers to method calls, and has expectations of executions. Can throw an
              exception if an unexpected invocation is detected
            * spy - in mockito spies are Mock like wrappers around the actual object  
          
          * Important Terminology
            * Verify - used to verify number of times a mocked method has been called
            * Argument Matcher - Matches a  rguments passed to Mocked Method & will allow or disallow
            * Argument Captor - captures arguments passed to a Mocked method
            
          * Annotations
            * @Mock => intialise a mock
            * @Spy
            * @injectMocks => tells mockito to create an instance of this and inject it
            * @Captor  
          
          * Maven dependencies and setup - 102
          
          * Inline Mocks - 103
            * import static org.mockito.Mockito.mock;
              @Test
              void testInlineMock() {
                  Map mapMock = mock(Map.class);
          
                  assertEquals(mapMock.size(), 0);
              }
          
          * Mocks with Annotations - 104
            * @Mock
              Map<String, Object> mapMock;
          
              @BeforeEach
              void setUp() {
                  MockitoAnnotations.initMocks(this);
              }
          
              @Test
              void testMock() {
                  mapMock.put("keyvalue", "foo");
              }
              
          * Junit Mockito Extension - 105
            *  @ExtendWith(MockitoExtension.class) => instead of using setup method above short cut
               public class JUnitExtensionTest {
               
                   @Mock
                   Map<String, Object> mapMock;
               
                   @Test
                   void testMock() {
                       mapMock.put("keyvalue", "foo");
                   }
               } 
               
          * Injecting Mocks with Mockito - 106
            * @ExtendWith(MockitoExtension.class)
              class SpecialitySDJpaServiceTest {
              
                  @Mock => intialise a mock
                  SpecialtyRepository specialtyRepository;
              
                  @InjectMocks => tells mockito to create an instance of this and inject it
                  SpecialitySDJpaService service;
              
                  @Test
                  void deleteById() {
                      service.deleteById(1l);
                  }
              
                  @Test
                  void testDelete() {
                      service.delete(new Speciality());
                  }
              }   
              
          * Verify Mock Interactions(like spy) - 107
            *  specialtyRepository => mocked object, 
               verify(specialtyRepository, times(2)).deleteById(1l);  => verify if it calling twice 
               verify(specialtyRepository, atLeastOnce()).deleteById(1l);  => atleast once it has to be called
               verify(specialtyRepository, atMost(5)).deleteById(1l); => max of 5 times it can be called           
               verify(specialtyRepository, never()).deleteById(5L); => never called with that ID
               
          * Returning Values for mockito - 110
            *  @Test
               void findByIdTest() {
                   Speciality speciality = new Speciality();
           
                   when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));
           
                   Speciality foundSpecialty = service.findById(1L);
           
                   assertThat(foundSpecialty).isNotNull();
           
                   verify(specialtyRepository).findById(1L);
           
               }
               
          * Argument Matcher - 111
            * @Test
              void testDeleteByObject() {
                  Speciality speciality = new Speciality();
          
                  service.delete(speciality);
          
                  verify(specialtyRepository).delete(any(Speciality.class)); => in delete is the argument matcher
                  verify(specialtyRepository).findById(anyLong());
              } 
              
      * BDD Mockito - Section 10
        * Given - Setup of the text
        * When - Action of the test - ie when the method is called
        * Then - Verification of expected results             
        
        * BDD Mockito - 117
          *  @Test
            void findByIdBddTest() {
                Speciality speciality = new Speciality();
        
                given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
        
                Speciality foundSpecialty = service.findById(1L);
        
                assertThat(foundSpecialty).isNotNull();
        
                verify(specialtyRepository).findById(anyLong());
            }    
                   
        * BDD verification in mockito - 118
          * @Test
            void findByIdBddTest() {
                //given
                Speciality speciality = new Speciality();
                given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));
        
                //when
                Speciality foundSpecialty = service.findById(1L);
        
                //then
                assertThat(foundSpecialty).isNotNull();
                then(specialtyRepository).should().findById(anyLong());
                then(specialtyRepository).shouldHaveNoMoreInteractions();
            } 
            
        * Advanced Mockito - section 11
          
          * Throwing Exceptions - 124
            * @Test
              void testDoThrow() {
                  doThrow(new RuntimeException("boom")).when(specialtyRepository).delete(any());
          
                  assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
          
                  verify(specialtyRepository).delete(any());
              }
          
              @Test
              void testFindByIDThrows() {
                  given(specialtyRepository.findById(1L)).willThrow(new RuntimeException("boom"));
          
                  assertThrows(RuntimeException.class, () -> service.findById(1L));
          
                  then(specialtyRepository).should().findById(1L);
              }
          
              @Test
              void testDeleteBDD() {
                  willThrow(new RuntimeException("boom")).given(specialtyRepository).delete(any());
          
                  assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
          
                  then(specialtyRepository).should().delete(any());
              }
              
        * Java 8 lambda Argument Matchers - 125
          * https://github.com/springframeworkguru/tb2g-bdd-mockito/commit/2222a0212b089061555a04ef54fa177d502cc9e6      
          * @Mock(lenient = true) => when using argThat to set it to leninent
            SpecialtyRepository specialtyRepository;
            @Test
            void testSaveLambda() {
                //given
                final String MATCH_ME = "MATCH_ME";
                Speciality speciality = new Speciality();
                speciality.setDescription(MATCH_ME);
        
                Speciality savedSpecialty = new Speciality();
                savedSpecialty.setId(1L);
        
                //need mock to only return on match MATCH_ME string
                given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);
        
                //when
                Speciality returnedSpecialty = service.save(speciality);
        
                //then
                assertThat(returnedSpecialty.getId()).isEqualTo(1L);
            }
        
            @Test
            void testSaveLambdaNoMatch() {
                //given
                final String MATCH_ME = "MATCH_ME";
                Speciality speciality = new Speciality();
                speciality.setDescription("Not a match");
        
                Speciality savedSpecialty = new Speciality();
                savedSpecialty.setId(1L);
        
                //need mock to only return on match MATCH_ME string
                given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(MATCH_ME)))).willReturn(savedSpecialty);
        
                //when
                Speciality returnedSpecialty = service.save(speciality);
        
                //then
                assertNull(returnedSpecialty);
            }        
        * Mockito Argument Captors - 128
          * @Test
            void processFindFormWildcardString() {
                //given
                Owner owner = new Owner(1l, "Joe", "Buck");
                List<Owner> ownerList = new ArrayList<>();
                final ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
                given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);
        
                //when
                String viewName = controller.processFindForm(owner, bindingResult, null);
        
                //then
                assertThat("%Buck%").isEqualToIgnoringCase(captor.getValue());
            }
        
            @Test
            void processFindFormWildcardStringAnnotation() {
                //given
                Owner owner = new Owner(1l, "Joe", "Buck");
                List<Owner> ownerList = new ArrayList<>();
                given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);
        
                //when
                String viewName = controller.processFindForm(owner, bindingResult, null);
        
                //then
                assertThat("%Buck%").isEqualToIgnoringCase(stringArgumentCaptor.getValue());
            } 
            
        * Mockito Answer - 129
          * https://github.com/springframeworkguru/tb2g-bdd-mockito/commit/051ce2c7f0e16ed291e4519e8c4d714063d41482      
          * @BeforeEach
            void setUp() {
                given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                        .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();
        
                    String name = invocation.getArgument(0);
        
                    if (name.equals("%Buck%")) {
                        owners.add(new Owner(1l, "Joe", "Buck"));
                        return owners;
                    } else if (name.equals("%DontFindMe%")) {
                        return owners;
                    } else if (name.equals("%FindMe%")) {
                        owners.add(new Owner(1l, "Joe", "Buck"));
                        owners.add(new Owner(2l, "Joe2", "Buck2"));
                        return owners;
                    }
        
                    throw new RuntimeException("Invalid Argument");
                });
            }
        * Verify order of interactions - 130
          * to test the sequence of the calls
          * InOrder inOrder = inOrder(ownerService, model);
            inOrder.verify(ownerService).findAllByLastNameLike(anyString());
            inOrder.verify(model).addAttribute(anyString(), anyList());    
        
        * Verify interactions within specified time - 131
          * to check timeouts
          * then(specialtyRepository).should(timeout(100).times(2)).deleteById(1L);
                }
        
        * Verify zero or no more interactions - 132
          *  inOrder.verify(model, times(1)).addAttribute(anyString(), anyList());
             verifyNoMoreInteractions(ownerService);
             verifyZeroInteractions(model); 
             
        * Using Mockito Spies - 133
          * @Spy //@Mock
            PetMapService petService;  
            given(petService.findById(anyLong())).willCallRealMethod(); //.willReturn(pet);
             
          * https://github.com/springframeworkguru/tb2g-bdd-mockito/commit/558a9e26e3fdc267320a8142ea0d309af52500ee    

      * Testing With Spring Framework - section 12
        * Things inside of spring
          * Environment - mock environment and properties Source
          * JNDI - Mock of JNDI lookup
          * Servlet API - For Testing of web environment
          * Spring Web Reactive - Testing of reactive web environment
          * Spring MVC Test - ofr testing controller interactions
            * MockHttpServletRequest - Mock implementation of request/response
            * MockHttpSession - Mock of Http Session
            * ModelAndViewAssert - assertion utilities
            
          * Spring Framework Testing Annotations
            * @BootstrapWith - Class-level annotation to configure how the test context is bootstrapped
            * @ContextConfiguration - Class-level annotation to configure the application context
            * @WebAppConfigurtation - Class-level annotation to configure a web application context
            * @ContextHiearchy - Class-level annotation to set multiple @ContextConfigurations
            * @ActiveProfiles - Class-level annotation to set active profiles for test
            * @TestPropertySource - Class-level annotation to set property sources for test
            * @DirtiesContext - Class or method level annotation which tells Spring to re-load context after
              test - (slows down your tests)
            * @TestExecutionListeners - Used to configure test execution listeners
            * @Commit - Class or method level annotation to commit action of test to database.
            * @Rollback - Class or method level annotation to rollback action of test from database.
            * @BeforeTransaction - run a method which returns void before a transaction is started
            * @AfterTransaction - run a method which returns void after a transaction has completed   
            * @Sql - Used to configure SQL scripts to run before a test
            * @SqlConfig - Configuration for the parsing of SQL scripts
            * @SqlGroup - Configure a grouping of SQL scripts 
          * Junit 5 Testing Annotations
            * @SpringJUnitConfig - Combines @ContextConfiguration with
              @ExtendWith(SpringExtension.class) to configure the Spring Context for the test
            * @SpringJUnitWebConfig - Combines @ContextConfiguration and @WebAppConfiguration with
              @ExtendWith(SpringExtension.class) to configure the Spring Context for the test
            * @EnabledIf - Conditional execution of test
            * @DisabledIf - Conditional execution of test
        
        * Example assignment to test code - 139
          * https://github.com/springframeworkguru/tb2g-testing-spring/commit/c933eb2c08a657b315165894b193a0feccb696f4
          
    * Spring Framework Testing Context - section 13
      * Junit 4
          * @RunWith(SpringRunner.class)
            // telling the configuration classes
            @ContextConfiguration(classes = {BaseConfig.class, LaurelConfig.class})
            public class HearingInterpreterTest {
            
                @Autowired
                HearingInterpreter hearingInterpreter;
            
                @Test
                public void whatIheard() {
                    String word = hearingInterpreter.whatIheard();
            
            
                    assertEquals("Laurel", word);
                }
            }      
      
      * Junit 5
        * @SpringJUnitConfig(classes = {BaseConfig.class, LaurelConfig.class})
          class HearingInterpreterLaurelTest {
          
              @Autowired
              HearingInterpreter hearingInterpreter;
          
              @Test
              void whatIheard() {
                  String word = hearingInterpreter.whatIheard();
          
                  assertEquals("Laurel", word);
              }
          } 
          
    * Using Inner Class Configuration - 150
      * @SpringJUnitConfig(classes = HearingInterpreterInnerClassTest.TestConfig.class)
        class HearingInterpreterInnerClassTest {
        
            @Configuration
            static class TestConfig {
        
                @Bean
                HearingInterpreter hearingInterpreter(){
                    return new HearingInterpreter(new LaurelWordProducer());
                }
            }
        
            @Autowired
            HearingInterpreter hearingInterpreter;
        
            @Test
            void whatIheard() {
                String word = hearingInterpreter.whatIheard();
        
                assertEquals("Laurel", word);
            }
        }   
        
    * Using Component Scans - 151
      * @SpringJUnitConfig(classes = HearingInterpreterComponentScanTest.TestConfig.class)
        class HearingInterpreterComponentScanTest {
        
            @Configuration
            @ComponentScan("org.springframework.samples.petclinic.sfg")
            static class TestConfig {
        
            }
        
            @Autowired
            HearingInterpreter hearingInterpreter;
        
            @Test
            void whatIheard() {
                String word = hearingInterpreter.whatIheard();
        
                assertEquals("Laurel", word);
            }
        }   
        
    * Setting Active Profiles for Tests - 152
      * @ActiveProfiles("base-test")
      * https://github.com/springframeworkguru/tb2g-testing-spring/commit/f64c820d4473d296f0eeb0ab07bdd26c0e376238 
      
    * Spring Test Properties - 153
      * @TestPropertySource("classpath:yanny.properties")
        @ActiveProfiles("externalized")
        @SpringJUnitConfig(classes = PropertiesTest.TestConfig.class)
        public class PropertiesTest {}
         
      * https://github.com/springframeworkguru/tb2g-testing-spring/commit/855b7be5ed90d414de44fba85800d1eabfbe756b  
      
  * Spring MVC Test - section 14
    * mock servlet 
    *  Provides Servlet API Mock objects to mock the web environment
      * MockHttpServletRequest - Mock of Java’s HttpServletRequest
      * MockHttpServletResponse - Mock of Java’s HttpServletResponse
      * DispatcherServlet - Requests are routed through Spring MVC’s DispatcherServlet  
    * Standalone Setup
      * Very light weight - Ideal for unit tests
      * Tests one controller at a time
      * Allows for testing of controller requests and responses
    * WebAppContext Setup
      * Loads larger context of Spring Configuration
      * Tests many controllers - per configuration
      * Allows for testing of application config    
    * Spring MVC Test uses a “fluent” API via several static imports
      * MockMvcRequestBuilders.* - Builds request
      * MockMvcResultMatchers.* - Create assertions against response
      * MockMvcBuilders.* - Configure and build an instance of MockMvc. 
      
    * Spring Mock mvc stand alone - 159
      * MockMvc mockMvc;
        @BeforeEach
            void setUp() {
                vetsList.add(new Vet());
        
                given(clinicService.findVets()).willReturn(vetsList);
        
                mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
            }
        @Test
        void testControllerShowVetList() throws Exception {
            mockMvc.perform(get("/vets.html"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vets"))
                .andExpect(view().name("vets/vetList"));
        }  
      
      * Form Params - 163
        *   mockMvc.perform(get("/owners")
           .param("lastName", "Dont find ME!"))
           .andExpect(status().isOk())
           .andExpect(view().name("owners/findOwners"));
      
      * Post Form - 166
        *  mockMvc.perform(post("/owners/new")
          .param("firstName", "Jimmy")
          .param("lastName", "Buffett")
          .param("Address", "123 Duval St ")
          .param("city", "Key West")
          .param("telephone", "3151231234"))
          .andExpect(status().is3xxRedirection());
          
      * Validation Errors - 167
        * mockMvc.perform(post("/owners/new")
          .param("firstName", "Jimmy")
          .param("lastName", "Buffett")
          .param("city", "Key West"))
          .andExpect(status().isOk())
          .andExpect(model().attributeHasErrors("owner"))
          .andExpect(model().attributeHasFieldErrors("owner", "address"))
          .andExpect(model().attributeHasFieldErrors("owner", "telephone"))
          .andExpect(view().name("owners/createOrUpdateOwnerForm"));    
 
    * Testing with SPring Boot - section 15
      * @SpringBootTest - will enable spring context 
      * see 172
 
# API Testing
  * https://examples.javacodegeeks.com/core-java/junit/junit-example-rest-web-services/
  * https://thepracticaldeveloper.com/2017/07/31/guide-spring-boot-controller-tests/
        
# set up test profile with h2 
  * https://www.baeldung.com/spring-testing-separate-data-source   
  

# Multi Threading
  * Processes & Threads - section 2
    * Creating a process is costly, but a thread is cheap
    * A thread is light weight process. 
    * A process can have any number of threads, these threads the share the same resources (memory etc) as that of the process that created it.
    * Thing a java microservices application, each application is a process that can create threads.
    * A thread is used to execute a block of code, threads can communicate with each other(IPC - inter process communication)
  
    * Benefits
    * do several things at the same time
    * improve performance
    * better utilization  
    
    * Disadvantages
    * threads manipulate data located on the same memory area -> need to synchronize 
    * hard to detect errors
    * expensive operations 
      * pass data between threads using callable and future
      * switching between threads cpu save local data, pointers of the current thread and loads the data of other thread 
    
    * Threads Life Cycle
    * stages 
      * new - when we instantiate a new thread it is in this state, until start() is called
      * runnable
      * running - threads stars executing it's tasks
      * waiting - when a thread is waiting for another thread to finish its task. When other thread signals, it starts executing again (wait() & sleep())
      * dead - after the thread finishes it's tasks    
      
  * Basic Multithreading - section 3
    * Sequential Processing - 6
    * 7 & 8 how to create threads
    * Join - 9
      * waits for this thread to die.
      * t1.join() -> if a t1 thread is running then calling this the main thread will wait until t1 is dead to continue execution
      * 	Runner1 t1 = new Runner1();
        		Runner2 t2 = new Runner2();
        		
        		t1.start();
        		t2.start();
        		
        		try {
        			t1.join();
        			//t2.join();
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}
        		
        		System.out.println("Finished the tasks..."); -> if we want this to be displayed at the end then use join(), if not it will print first only
        
    * Volatile - 10
      * There is a main memory "RAM", each thread1(cache + CPU1), thread2(cache + CPU2) are processors which have their own cache that keep
        local variables stored for better performance. Now if there is a variable that need's to be shared along with notifying its value changed
        between the threads we can use a Volatile variable, which will be stored on the "RAM". This variable will be shared between threads, when
        thread changes it's value other threads get notified of it's changes so that the value remains consistent between the threads.                 
      
      * IMPORTANT
        * not only sharing between thread's, if a variable is present in a runnable thread, it stores in the cache associated with thread only.
        * class Worker implements Runnable {
          
          	private boolean isTerminated = false; // => making this volatile, private volatile boolean isTerminated; stores it in the Main memory
          	
          	public void run() {
          		
          		while(!isTerminated) {
          			
          			System.out.println("Hello from worker class...");
          			
          			try {
          				Thread.sleep(300);
          			} catch (InterruptedException e) {
          				e.printStackTrace();
          			}
          		}
          	}
          
          	public boolean isTerminated() {
          		return isTerminated;
          	}
          
          	public void setTerminated(boolean isTerminated) {
          		this.isTerminated = isTerminated;
          	}
          }

      * it's slower.
    
    * Deadlocks & Livelocks - 11     
      * Deadlocks is a situation in which two or more competing actions are each waiting for the other to finish, and thus neither does.
        * examples: 
          * Databases: deadlocks happens when two processes each within it's own transaction updates two rows of information but in the opposite order.
            For example, process A updates row 1 then row 2, in the same time frame Process B updates row 2 and then row 1
      
      * LiveLocks
        * A thread often acts in response to the action of another thread.
        * if the other thread's action is also a response to the action of the another thread, this is live lock.
        * LiveLocked threads are unable to make further progress. However, the threads are not blocked, they are simply too busy responding to each other
          to resume work.
        * Example:
          * like two people attempting to pass each other in a narrow corridor. A moves to his left to let B pass, but in response B moves to his right to let
            A pass. Hence blocking
            
    * Synchronized - 12
      * we should make sure the threads are going to wait for each other to finish the given task on the variables.
      * assume counter = 0, two threads accessing same counter, when thread one access it, it is 0 and increments it to 1, in the mean time when thread
        two access the counter it also still at 0 where as it should be 1 incremented, but in the time thread one set's the value to the counter, thread 2
        reads and goes and the cycle repeats, we get wrong outputs  
      * public static synchronized void increment() {
        		++counter;
        	}  
             
    * Synchronized Blocks(avoid class intrensic locks) - 13
      * when using "synchronized" on the methods as show below, java add's an class intrensic lock to App class, which means when two threads are started at the
        same time, when thread 1 is accessing count1 then thread2 cannot access count2 variable because the "synchronized" is present on both hte methods and
        making thread 1 to complete and adding an "Intrensic lock", this makes performance slower, to avoid this, see below example   
      * public class App { // wait & notify refer here
        
        	private static int count1 = 0;
        	private static int count2 = 0;
        	
        	public synchronized static void add() {
        		count1++;
        	}
        	
        	public synchronized static void addAgain() {
        		count2++;
        	}
       }
       
      * Better approach
        public class App {
                
            private static int count1 = 0;
            private static int count2 = 0;
            
            private static Object lock1 = new Object();
            private static Object lock2 = new Object();
            
            public  static void add() {
               synchronized(lock1) {  //  synchronized(this) || synchronized(App.class) => this again makes java to apply internsic lock, hence a better solution create new Object and apply on them
                count1++;
               } 
            }
            
            public synchronized static void addAgain() {
            synchronized(lock2) {  // synchronized(App.class) 
                count2++;
               }
            }
       } 
       
    * Wait & Notify & NotifyAll- 14
      * wait() => wait for infinite time, so say wait(10000)
      * wait() can be called only in a synchronized block 
      * when the thread comes across wait(10000ms), it goes to wait until another thread comes, when it does come it hand's over the intrensic lock on the class
        to that thread so that it can take control and execute on the same object(Processor), once it is done it can notify other threads that it is done and other threads can take
        control again ang continue execution, that is waiting for the lock on the same object(Processor)
      * this scenario comes because if the all the methods are synchronized, then it is case of one thread has to wait while other thread completes fully,
        see above example. To avoid this we use the "better approach" or use wait() and notify(). When thread1 starts execution it acquires lock, then waits after
        thread2 starts see's synchronized methods and wait's but instead because of the wait() in thread1 it gets the lock by transferring and continues.
      
      * Notify & NotifyAll
        *  notifies all the threads that is waiting for this lock on the same object that is "Processor" 
          
        * class Processor {
          	
          	public void produce() throws InterruptedException {
          		
          		synchronized (this) {
          			System.out.println("We are in the producer method...");
          			wait(10000);
          			System.out.println("Again producer method...");
          		}
          	}
          	
          	public void consume() throws InterruptedException {
          		
          		Thread.sleep(1000);
          		
          		synchronized (this) {
          			System.out.println("Consumer method...");
          			notify(); //
          			//notifyAll();  // notifies all the threads that is waiting for this lock on the same object that is "Processor"
          			Thread.sleep(3000);  // keeping this it won't immediatedly notify, but wait until 3000 then only notify
          		}
          	}
          }
          
          public class App {
          	
          	public static void main(String[] args) {
          
          		Processor processor = new Processor();
          		
          		Thread t1 = new Thread(new Runnable() {
          			public void run() {
          				try {
          					processor.produce();
          				} catch (InterruptedException e) {
          					e.printStackTrace();
          				}
          			}
          		});
          		
          		Thread t2 = new Thread(new Runnable() {
          			public void run() {
          				try {
          					processor.consume();
          				} catch (InterruptedException e) {
          					e.printStackTrace();
          				}
          			}
          		});
          		
          		t1.start();
          		t2.start();
          		
          		try {
          			t1.join();
          			t2.join();
          		} catch (InterruptedException e) {
          			e.printStackTrace();
          		}
          	}
          } 
        
        O/P: we are in the producer...
             consumer method.....
             Again producer method
    
    * Producer and Consumer with wait and notify - 15
      * Important: 
        * if any code present after notify(), in this case there is a while loop, so only when it hits wait(), it will transfer
          control to the other thread
      * class Processor {
        
        	private List<Integer> list = new ArrayList<>();
        	private final int LIMIT = 5;
        	private final int BOTTOM = 0;
        	private final Object lock = new Object();
        	private int value = 0;
        	
        	public void producer() throws InterruptedException {
        
        		synchronized (lock) {
        			
        			while(true) {
        				
        				if( list.size() == LIMIT ) {
        					System.out.println("Waiting for removing items from the list...");
        					lock.wait();
        				} else {
        					System.out.println("Adding: "+value);
        					list.add(value);
        					value++;
        					lock.notify();
        				}
        				
        				Thread.sleep(500);
        			}
        		}
        	}
        
        	public void consumer() throws InterruptedException {
        
        		synchronized (lock) {
        			
        			while(true) {
        				
        				if( list.size() == BOTTOM ) {
        					System.out.println("Waiting for adding items to the list...");
        					lock.wait();
        				} else {
        					System.out.println("Removed: "+list.remove(--value));
        					lock.notify();
        				}
        				
        				Thread.sleep(500);
        			}
        			
        		}
        		
        	}
        }
        
        public class App {
        
        	static Processor processor = new Processor();
        
        	public static void main(String[] args) {
        
        		Thread t1 = new Thread(new Runnable() {
        			public void run() {
        				try {
        					processor.producer();
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		});
        
        		Thread t2 = new Thread(new Runnable() {
        			public void run() {
        				try {
        					processor.consumer();
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		});
        
        		t1.start();
        		t2.start();
        	}
        }
      o/p: Adding: 0
           Adding: 1
           Adding: 2
           Adding: 3
           Adding: 4
           waiting for items to be removed
           Removing: 4
           Removing: 3
           Removing: 2
           Removing: 1
           Removing: 0
           Waiting for adding items to the list...
           .   
           ......(infinife loop)    
           
    * Locks(Re-entrant Lock) - 16
      * it has the same behaviour as the "Synchronized approach", with some additional features
      * ReentrantLock is an implementation of Lock interface
      * new ReentrantLock(boolean fairnessParameters)
        - if fairnessParameters is set to true, the longest waiting thread will get the lock   
        - if fairnessParameters is set to false, there is no access order, leads to thread starvation    
      * we have to use try-catch block when doing critical section that may throw exceptions
      * we call unlock() in the finally block()      
      * in this type of lock's we can unlock the lock from anyother methods, that is an additional feature of this locks
      * we can lock in it many times, but we need to unlock in the same number of times.
      *  public class App {
         
         	private static int counter = 0;
         	private static Lock lock = new ReentrantLock();
         	
         	public static void increment(){
         		lock.lock();
         		try{
         		    for(int i=0;i<1000;i++){
                      counter++;
                    }
         		} finally {
         		    lock.unlock();
         		}		
         	}
         	
         	// in this type of lock's we can unlock the lock from anyother methods, that is an additional feature of this locks
         	//public static void add() {
         	//  lock.unlock();
         	//}
         	
         	public static void main(String[] args) {
         		
         		Thread t1 = new Thread(new Runnable() {
         			public void run() {
         				increment();
         			}
         		});
         		
         		Thread t2 = new Thread(new Runnable() {
         			public void run() {
         				increment();
         			}
         		});
         		
         		t1.start();
         		t2.start();
         		
         		try {
         			t1.join();
         			t2.join();
         		} catch (InterruptedException e) {
         			e.printStackTrace();
         		}
         		
         		System.out.println(counter); 		
         	}
         }
         O/P: 2000
         
    * Producer Consumer with Locks - 17
      * here when using re-entrant locks, instead of using wait and notity, re-entrant locks gives, await() and signal() methods from 
        Condition condition = lock.newCondition();
      * class Worker {
        	
        	private Lock lock = new ReentrantLock();
        	private Condition condition = lock.newCondition();
        	//private List<Integer> list = new ArrayList<>();
        	
        	public void produce() throws InterruptedException {
        		lock.lock();
        		System.out.println("Producer method...");
        		condition.await();
        		System.out.println("Producer method again...");
        	}
        	
        	public void consume() throws InterruptedException {
        		lock.lock();
        		Thread.sleep(2000);
        		System.out.println("Consumer method...");
        		Thread.sleep(3000);
        		condition.signal();
        		lock.unlock();
        	}
        }
        
        public class App {
        
        	public static void main(String[] args) {
        		
        		final Worker worker = new Worker();
        		
        		Thread t1 = new Thread(new Runnable() {
        			public void run() {
        				try {
        					worker.produce();
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		});
        		
        		Thread t2 = new Thread(new Runnable() {
        			public void run() {
        				try {
        					worker.consume();
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		});
        		
        		t1.start();
        		t2.start();
        		
        	}
        }   
        O/P: Producer method .....
             Consumer Method .....
             Producer method again...
    
    * Locks & Synchronization - 18
      * A re-entrant lock has the same basic behaviour as we have seen for synchronized blocks, but with additional features
      * using re-entrant lock, we can make a lock fair, prevent thread starvation, Synchronized blocks are unfair by default
      * we can check whether the given lock is held or not using re-entrant lock     
      * we can get the list of threads waiting for the given lock with re-entrant locks
      * Synchronized blocks are nicer, we do not need the try-catch-finally block
      
    * Semaphores - 19 & 20
      * semaphores are thread safe be default
      * Semaphores are variables or ADT(abstract data types) that are used for controlling access to a common resource 
      * important in OS
      * It has a record of how many units of a particular resource are available. It wait's until a unit of the resource becomes
        available
      * Types:
        * Counting Semaphores: allows an arbitrary resource count
        * Binary Semaphores: semaphore which are restricted to the values 0 and 1   
      
      * Semaphore are used in situations for example an library with study rooms, which has front desk. If there aer 10 rooms, and students want
        to use it one. To prevent disputes students must request a room from the front desk. If no rooms are available then they have to wait until
        any of the rooms get free. When a student using room has finished, he must return to the front desk and indicate that a room is free. 
        
      * Semaphore tracks only how many resources are free, it does not keep track of which of the resources are free
      * the semaphores count may serve as a useful trigger for a number of different actions
      * producer-consumer problem can be implemented with the help of semaphores efficiently  
      
      * Mutex
        * a mutex is essentially the same thisng as a binary semaphore
        * while a binary semaphore may be used as a mutex, a mutex is a more specific use-case 
        * Mutexes have a concept of a owner. only the process that locked the mutex is suppose to unlock it.
        * mutexes may provide priority inversion safety. if the mutex knows its current owner, it is possible to promote the priority of the owner
          whenever a higher priority tasks starts waiting on the mutex
        * mutex can provide deletion safety   
        
        * semaphore maintains a set of permits
           	- acquire() -> if a permit is available then takes it
           	- release() -> adds a permit		
            * Semaphore just keeps a count of the number available
            * new Semaphore(int permits, boolean fair) !!!  fair => prevent thread starvation
            
        * enum Downloader { // singleton
          	
          	INSTANCE;  // thread safe
          	
          	private Semaphore semaphore = new Semaphore(5, true);
          	
          	public void downloadData() {
          		
          		try {
          			semaphore.acquire();
          			download();
          		} catch (InterruptedException e) {
          			e.printStackTrace();
          		} finally {
          			semaphore.release();
          		}
          		
          	}
          
          	private void download() {
          		System.out.println("Downloading data from the web...");
          		try {
          			Thread.sleep(2000);
          		} catch (InterruptedException e) {
          			e.printStackTrace();
          		}
          	}
          }
          
          public class App {
          
          	public static void main(String[] args) {
          
          		ExecutorService executorService = Executors.newCachedThreadPool();
          		
          		for(int i=0;i<12;i++) { // 12 threads,
          			executorService.execute(new Runnable() {
          				public void run() {
          					Downloader.INSTANCE.downloadData();
          				}
          			});
          		}
          		
          	}
          }
          
          o/p:   Downloading data from the web... (x 12 times but 5 first the 5 then 2)  
          
    * Executors - 21
      *  Used to create N number of threads, dynamically 
      *  1.) ExecutorService es = Executors.newCachedThreadPool();
         	 *  	- going to return an executorService that can dynamically
         	 *  		reuse threads
         	 *		- before starting a job -> it going to check whether there are any threads that
         	 *			finished the job...reuse them
         	 *		- if there are no waiting threads -> it is going to create another one 
         	 *		- good for the processor ... effective solution !!!
         	 *
         2.) ExecutorService es = Executors.newFixedThreadPool(N);
         	 *		- maximize the number of threads
         	 *		- if we want to start a job -> if all the threads are busy, we have to wait for one
         	 *			to terminate
         	 *
         3.) ExecutorService es = Executors.newSingleThreadExecutor();
         	 *		It uses a single thread for the job
         	 *
         	 *		execute() -> runnable + callable
         	 *		submit() -> runnable       
         
      * class Worker implements Runnable{
        
        		@Override
        		public void run() {
        			for(int i=0;i<10;i++){
        				System.out.println(i);
        				try {
        					Thread.sleep(300);
        				} catch (InterruptedException e) {
        					e.printStackTrace();
        				}
        			}
        		}
        }
        
        public class App {
        
        	public static void main(String[] args) {
        		
        		ExecutorService executorService = Executors.newFixedThreadPool(5);
        		
        		for(int i=0;i<5;i++){
        			executorService.execute(new Worker());  // execute
        		}
        		
        	}
        }    
        
    * Callable & Future - 22
      * used for returning a value from a given thread
      * Callable throws an exception, where as runnable does'nt
      * for callable we use Submit() instead of execute()
      * to get value future.get()
      * then need to shutdown the tasks
      * class Processor implements Callable<String>{
        	
        	private int id;
        	
        	public Processor(int id){
        		this.id = id;
        	}
        
        	@Override
        	public String call() throws Exception {
        		Thread.sleep(1000);
        		return "Id: "+this.id;
        	}
        }
        
        public class App {
        	public static void main(String[] args) {
        		
        		ExecutorService executorService = Executors.newFixedThreadPool(2);
        		List<Future<String>> list = new ArrayList<>();
        		
        		for(int i=0;i<5;i++){
        			Future<String> future = executorService.submit(new Processor(i+1));  // submit here
        			list.add(future);
        		}
        		
        		for(Future<String> future : list){
        			try{
        				System.out.println(future.get()); // important
        			}catch(Exception e){
        				e.printStackTrace();
        			}
        		}
        		
        		executorService.shutdown(); // important to shutdown
        		
        	}
        }

            
###### Links

    # @Async  
      * https://springbootdev.com/2017/10/02/spring-framework-asynchronous-task-execution-with-async/ 
      * https://grokonez.com/java-integration/start-spring-async-spring-boot 
      * https://www.devglan.com/spring-boot/spring-boot-async-task-executor
    
    #  Spring Boot Multiple Database Configuration
      * https://www.devglan.com/spring-boot/spring-boot-multiple-database-configuration
      * https://www.baeldung.com/spring-data-jpa-multiple-databases
      
    # Creating Plugin Project(MOJO)  
      * https://dzone.com/articles/a-simple-maven-3-plugin
      
    # Spring Cloud Data Flow
      * https://www.javainuse.com/spring/cloud-data-flow
      
    # java releated
      * https://www.baeldung.com/java-optional
      * https://www.youtube.com/watch?v=n8if6X47KvE
      * https://www.youtube.com/watch?v=suSdjhS03qk&index=24&list=PLqq-6Pq4lTTa9YGfyhyW2CqdtW9RtY-I3
      * https://www.youtube.com/watch?v=6TKDjfLGVNc&list=PLIGmoZSj3zVl_GljdHUNmRNeaQWHwLf4c
      
    # Spring Application Events (Advanced Spring Guru)
      * https://www.baeldung.com/spring-events
      * https://www.baeldung.com/spring-context-events  
      * Publish/Subscribe pattern
              
    # SOLID
      * https://springframework.guru/principles-of-object-oriented-design/
      * Section 3, 48-53
      
    # SAML
        #Okta General
          * https://github.com/ankidaemon/SpringSecurity-LDAP-SAML-integration/blob/master/Section-3/Video-3.4/SpringSecurity-SAML-WSO2/readMe.MD
          * https://docs.spring.io/spring-security-saml/docs/1.0.x-SNAPSHOT/reference/pdf/spring-security-saml-reference.pdf
          * https://www.youtube.com/watch?v=YN2DOJttEaA(Okta) - https://github.com/talk2amareswaran/Spring-Boot-SAML-and-OKTA
          * https://www.youtube.com/watch?v=SgLQfD7c3EY&t=537s
          * https://developer.okta.com/code/java/spring_security_saml - https://www.youtube.com/watch?v=TaZqDrwBWwA (Groovy based)
         
        # Angular + Spring
          * https://developer.okta.com/blog/2017/12/04/basic-crud-angular-and-spring-boot?_ga=2.228341853.1119814729.1544412223-402971053.1542366627  
          * https://devforum.okta.com/t/angular-with-saml/990/3  
          * https://www.youtube.com/watch?v=0rJypX70E4U => (angular)
          * https://developer.okta.com/blog/2017/12/04/basic-crud-angular-and-spring-boot
          * https://www.sylvainlemoine.com/2018/03/29/spring-security-saml2.0-websso-with-angular-client/
          
        # Okta + spring boot + Angular + Microservices
          * https://dzone.com/articles/add-spring-security-and-okta-for-a-secure-spring-m 
        
        # Okta Sessions
          * https://developer.okta.com/docs/api/resources/sessions     
          
        # Saml Imagenia
          * https://blog.imaginea.com/implementing-java-single-signon-with-saml/  
      
    # Spring Boot Interview
      * http://candidjava.com/tutorial/spring-boot-interview-questions/
      
    # Rails
      * https://www.wisdomjobs.com/e-university/ruby-on-rails-interview-questions.html     
      
    # S3  
       * https://www.youtube.com/watch?v=UQ7xnELIuq4&t=27s 
       * https://grokonez.com/aws/amazon-s3/amazon-s3-springboot-restapis-upload-download-file-image-to-s3
       * https://grokonez.com/spring-framework/spring-boot/amazon-s3-springboot-restapis-list-all-files-in-s3-bucket 
       * https://grokonez.com/java-integration/angular-6-upload-get-multipartfile-spring-boot-example 
       * https://grokonez.com/frontend/angular/angular-6/angular-6-springboot-amazon-s3-upload-download-files-images-example
       * https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/s3/AmazonS3Client.html#getUrl-java.lang.String-java.lang.String-
       * https://docs.aws.amazon.com/AmazonS3/latest/dev/ShareObjectPreSignedURLJavaSDK.html
       * https://aws.amazon.com/premiumsupport/knowledge-center/s3-troubleshoot-403/
       * https://forums.aws.amazon.com/thread.jspa?threadID=170579
    
    # Notifications - STOMP
      * https://medium.com/oril/spring-boot-websockets-angular-5-f2f4b1c14cee (Angular 5)
      * https://grokonez.com/spring-framework/spring-websocket/spring-boot-websocket-angular-5-client-sockjs-stomp
      * http://newjavafaqs.blogspot.com/2018/06/web-socket-using-spring-and-stomp.html
      * http://newjavafaqs.blogspot.com/2018/06/web-socket-using-spring-and-stomp.html
      * https://stomp-js.github.io/guide/ng2-stompjs/2018/11/04/ng2-stomp-with-angular7.html (angular 7 & 6)
      * https://grokonez.com/spring-framework/spring-websocket/spring-boot-websocket-angular-5-client-sockjs-stomp (with angular)
      * https://www.youtube.com/watch?v=OK2Fn6k7pwo&t=974s
      
    # Controller
      * https://studiofreya.com/2012/02/06/spring-mvc-how-to-build-a-thread-safe-controller/  
      * https://stackoverflow.com/questions/30171027/is-spring-boot-mvc-controller-multithreaded
      
# Jshell like irb shell
  * https://javabrains.io/courses/java_jshellbasics/      
      
########################################  
  
# Ember Link    
  * https://gist.github.com/kristianmandrup/ae3174217f68a6a51ed5
  * https://www.youtube.com/watch?v=bhJ6YzBIoWo


# ToDo
  * notification server(websockets)
  * S3
  * Caching  
  * Download - https://www.youtube.com/watch?v=UQ7xnELIuq4
  * Background Jobs
  * Okta(saml, oauth)
  * Microservices Zipkin authentication
 
  
# Angular Deployment s3
  * https://theinfogrid.com/tech/developers/angular/automate-deployment-of-angular-apps-using-aws-codebuild/
  * https://medium.com/@ibliskavka/aws-angular-stack-automation-b45767bda2ec
  
# Data Structures
  * when to use what
    * http://careerdrill.com/blog/coding-interview/choosing-the-right-data-structure-to-solve-problems/  
    * https://stackoverflow.com/questions/48442/rule-of-thumb-for-choosing-an-implementation-of-a-java-collection
 
# Interview Questions(spring)
  * https://d1fto35gcfffzn.cloudfront.net/academy/Spring-Professional-Certification-Study-Guide.pdf
  * https://stackoverflow.com/questions/29074270/why-in-spring-i-am-not-allowed-to-annotate-a-final-class-with-configuration      
  
# Certification
  * https://www.certification-questions.com/buy-dumps-exams/core-professional-v5.0-dumps##portfolio  
  
# Rails Engine, Gem
  * https://corlewsolutions.com/articles/article-126-how-to-setup-rspec-in-a-rails-engine  
  
# Installing Java
  * https://stackoverflow.com/questions/24342886/how-to-install-java-8-on-mac  

# Java Types
  * https://www.ibm.com/support/knowledgecenter/en/SSEPGG_11.1.0/com.ibm.db2.luw.apdv.java.doc/src/tpc/imjcc_rjvjdata.html   
  
# Redis UI
  * https://app.redsmin.com/
  * my gmail id  
  
# For Table specific schema
  * @Table(schema = "dbo")  
  * Entity listeners and Callback methods
  * @EntityListeners(class=Audit.class)
  
# Hibernate
  * http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#pc  
  
# Jenkins
  * https://stackoverflow.com/questions/39794811/jenkins-does-not-start-on-macos-10-12-sierra
  * https://www.youtube.com/watch?v=Bi5Gl2O5d0s
  * https://stackoverflow.com/questions/6959327/how-to-stop-jenkins-installed-on-mac-snow-leopard
  * Commands
    http://localhost:8080/exit
    http://localhost:8080/restart
    http://localhost:8080/reload  
    start: brew services start jenkins
    restart: brew services restart jenkins
    
  * How To Setup
    - https://medium.com/@MaciejNajbar/setup-jenkins-for-private-repository-9060f54eeac9
    - https://wiki.jenkins.io/display/JENKINS/Publish+Over+SSH+Plugin
     
  * Pipeline
    * https://www.youtube.com/watch?v=s73nhwYBtzE  
    * https://medium.com/@weblab_tech/how-to-publish-artifacts-in-jenkins-f021b17fde71
  
  * Deploy to Tomcat
    * https://www.youtube.com/watch?v=OwTv1aN5BUo 
    * https://www.youtube.com/watch?v=j5D8SLxn6YA
    * https://www.youtube.com/watch?v=jfM1GiTOHSc
    
  * Install jenkins as java
    * https://www.blazemeter.com/blog/how-to-install-jenkins-on-the-apache-tomcat-server  
     
  # Deployment
  * https://www.baeldung.com/tomcat-deploy-war  
  * https://stackoverflow.com/questions/46341886/deploy-jar-file-using-jenkins
  * https://www.youtube.com/watch?v=uCleiJw40Kw
  * https://www.youtube.com/channel/UCdpxocfI1dJ7J7eOr3dxQlA
  * https://www.youtube.com/watch?v=a_AOTn7FQUM => ssh/jenkins
  * https://www.youtube.com/watch?v=l9lSW5sQbvc  => telugu
  * https://wiki.jenkins.io/display/JENKINS/Publish+Over+SSH+Plugin
  
  
    
# Github as Maven Repo
  * https://dzone.com/articles/using-github-as-maven-repository     
  

# Setup Tomcat on EC2
  * https://www.youtube.com/watch?v=m21nFreFw8A  
  
# AES
  - https://examples.javacodegeeks.com/core-java/util/base64/java-8-base64-encoding-example/
  - https://stackoverflow.com/questions/10759392/java-aes-encryption-and-decryption
  
 import java.io.UnsupportedEncodingException;
 import java.net.URLDecoder;
 import java.net.URLEncoder;
 import java.security.InvalidKeyException;
 import java.security.NoSuchAlgorithmException;
 import java.util.Base64;
 
 import javax.crypto.BadPaddingException;
 import javax.crypto.Cipher;
 import javax.crypto.IllegalBlockSizeException;
 import javax.crypto.NoSuchPaddingException;
 import javax.crypto.spec.SecretKeySpec;
 
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 

 -  public class AESEncryptionDecryption {
    	private static final Logger LOGGER = LoggerFactory.getLogger(AESEncryptionDecryption.class);
    	private AESEncryptionDecryption() {
    
    	}
    	
    	public static String encryptStringToBase64(String data) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException  {
    		String encrypted = "";
    		Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
    		if(cipher != null) {
    			byte[] encVal = cipher.doFinal(data.getBytes());
    			encrypted = Base64.getEncoder().encodeToString(encVal);
    			encrypted = URLEncoder.encode( encrypted, StringConstants.UTF_FORMAT );
    		}
    		return encrypted;
    
    	}
    	
    	public static String decrypt(String encryptedData) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException{
    		//encryptedData = URLDecoder.decode(encryptedData, StringConstants.UTF_FORMAT);
            byte[] e64bytes = StringUtils.getBytesUtf8(encryptedData); 
            byte[] decValue = StringConstants.EMPTY_STRING.getBytes();
            byte[] decordedValue = Base64.getDecoder().decode(e64bytes);
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
    		if(cipher != null) {
    			decValue = cipher.doFinal(decordedValue);
    		}
    		return new String(decValue);
    	}
    
    	private static Cipher getCipher(int mode) {
    		Cipher cipher = null;
    		try {
    			byte[] keybyte = StringConstants.ANE_ENCRYPTION_KEY.getBytes(StringConstants.UTF_FORMAT);
    			SecretKeySpec secretKey = new SecretKeySpec(keybyte, StringConstants.AES);
    			cipher = Cipher.getInstance(StringConstants.ALGO);
    			cipher.init(mode, secretKey);
    
    		} 
    		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException exception) {
    			LOGGER.error(exception.getMessage());
    		} 
    		return cipher;
    	}
    }  
    
# Transactions
  * https://examples.javacodegeeks.com/enterprise-java/hibernate/hibernate-transaction-example/  
  
# Google => Guava
  * https://github.com/google/guava/wiki/OrderingExplained#creation    
  
# WebConfig
  -  @Override
     	protected void configure(HttpSecurity http) throws Exception {
     		http.csrf().disable()
     				// make sure we use stateless session; session won't be used to store user's
     				// state.
     				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
     				// handle an authorized attempts
     				.exceptionHandling()
     				.authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
     				.addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
     				// authorization requests config
     				//
     				.authorizeRequests()
     //		.antMatchers(HttpMethod.OPTIONS).permitAll()
     				.antMatchers("/security/login", "/security/consume", "/security/logOut").permitAll()
     //		.antMatchers("/admin/**").hasAuthority("ADMIN")
     //		.antMatchers("/api/**").hasAuthority("ANALYST")
     				// Any other request must be authenticated
     				.anyRequest().authenticated();
     		http.headers().frameOptions().disable();
     		http.cors();
     
     	}
     
     	@Bean
     	public CorsConfigurationSource corsConfigurationSource() {
     		final CorsConfiguration configuration = new CorsConfiguration();
     		configuration.setAllowedOrigins(ImmutableList.of("*"));
     		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
     		// setAllowCredentials(true) is important, otherwise:
     		// The value of the 'Access-Control-Allow-Origin' header in the response must
     		// not be the wildcard '*' when the request's credentials mode is 'include'.
     		configuration.setAllowCredentials(true);
     		// setAllowedHeaders is important! Without it, OPTIONS preflight request
     		// will fail with 403 Invalid CORS request
     		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
     		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     		source.registerCorsConfiguration("/**", configuration);
     		return source;
     	}  
     	
# Authorization  
  -  public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {
     	private static final Logger LOGGER = (Logger) LogManager.getLogger(JwtTokenAuthenticationFilter.class);
     
     	private final JwtConfig jwtConfig;
     
     	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig) {
     		this.jwtConfig = jwtConfig;
     	}
     
     	@Override
     	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
     			throws ServletException, IOException {
     
     		// 1. get the authentication header. Tokens are supposed to be passed in the
     		// authentication header
     		String header = request.getHeader(jwtConfig.header);
     		// 2. validate the header and check the prefix
     		if (header == null || !header.startsWith(jwtConfig.prefix)) {
     			chain.doFilter(request, response); // If not valid, go to the next filter.
     			return;
     		}
     		// 3. remove the prefix and get token
     		String token = header.replace(jwtConfig.prefix + StringConstants.SPACE, StringConstants.EMPTY_STRING);
     
     		try { // exceptions might be thrown in creating the claims if for example the token is
     				// expired
     
     			// 4. Validate the token
     			Claims claims = Jwts.parser().setSigningKey(jwtConfig.secret.getBytes()).parseClaimsJws(token).getBody();
     			String username = claims.get(StringConstants.EMAIL_STRING).toString();
     			if (username != null) {
     				@SuppressWarnings("unchecked")
     				// List<String> authorities = (List<String>)
     				// claims.get(StringConstants.ROLES_STRING);
     				// LOGGER.debug("Authorities from Claims {}", claims);
     				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
     				SecurityContextHolder.getContext().setAuthentication(auth);
     			}
     
     		} catch (ExpiredJwtException exception) {
     			// In case of failure. Make sure it's clear; so guarantee user won't be
     			// authenticated
     			LOGGER.debug("Token expired for user ");
     			LOGGER.debug("Security exception trace: {}", exception);
     			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
     			SecurityContextHolder.clearContext();
     		}
     
     		// go to the next filter in the filter chain
     		chain.doFilter(request, response);
     	}
     
     }
     
# LogOut
  * https://www.mkyong.com/java/how-to-get-http-request-header-in-java/
  - @GetMapping(value = "/logOut")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        LOGGER.debug("inside logout");
        String queryString = request.getQueryString();
        String jwtToken = userService.getAnETokenFromDB(queryString);
        
        String[] queryString = (request.getHeader("Authorization").split(" "));
        String jwtToken = userService.getAnETokenFromDB(queryString[1]);
        
        String callBackURL = externalURLs.aneCallBackUrl;
        if(jwtToken.isEmpty()) {
            LOGGER.debug("user not found in DB");
        }
        userService.deleteByToken(jwtToken);
        LOGGER.debug(callBackURL);
        String token = generateToken.createToken(callBackURL);
        String redirectUrl = externalURLs.aneLogoutUrl + token;
        LOGGER.debug("logout redirect url {}", redirectUrl);
        httpServletResponse.setHeader(StringConstants.LOCATION_STRING, redirectUrl);
        httpServletResponse.setStatus(302);
    }     
     
# Logger
  * LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);      
  
# Query JPQL
  - @Query(value = NamedQueries."Inner joined Queries, nativeQuery = true)
    public List<Map<String,Object>> getDosingDetailsForForecast(@Param("forecastId") Long forecastId);  

# Redirect From Controller
  - public static final String LOCATION_STRING = "Location";
  - httpServletResponse.setHeader(StringConstants.LOCATION_STRING, redirectUrl);
    httpServletResponse.setStatus(302); 
    
# Cors
  * @Bean
    	public CorsConfigurationSource corsConfigurationSource() {
    		final CorsConfiguration configuration = new CorsConfiguration();
    		configuration.setAllowedOrigins(ImmutableList.of("*"));
    		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    		// setAllowCredentials(true) is important, otherwise:
    		// The value of the 'Access-Control-Allow-Origin' header in the response must
    		// not be the wildcard '*' when the request's credentials mode is 'include'.
    		configuration.setAllowCredentials(true);
    		// setAllowedHeaders is important! Without it, OPTIONS preflight request
    		// will fail with 403 Invalid CORS request
    		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
    		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    		source.registerCorsConfiguration("/**", configuration);
    		return source;
    	}
    	
# when you are redirecting from spring boot in FE -> Angular
  * need to replace the window location itself, because it redirects from there
  *  window.location.href = `${this.baseURL}security/login`; // localhost:4200/security/login 
  

# Spring Env Configuration
  - https://dzone.com/articles/spring-core-diving-into-the-propertyplaceholdercon
  - @Configuration
  public class SpringConfiguration{
  	@Profile("dev")
  	static class SpringConfigurationDev {
  
  		@Bean
  		public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
  			String propertiesFilename = "app-dev.properties";
  			PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
  			configurer.setLocation(new ClassPathResource(propertiesFilename));
  			return configurer;
  		}
  	}
  
  	@Profile("local")
  	static class SpringConfigurationLocal {
  
  		@Bean
  		public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
  			String propertiesFilename = "app-local.properties";
  			PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
  			configurer.setLocation(new ClassPathResource(propertiesFilename));
  			return configurer;
  		}
  	}
}           	


# Spring Security Role Based Method Based
  * https://www.devglan.com/spring-security/jwt-role-based-authorization
  * https://www.baeldung.com/role-and-privilege-for-spring-security-registration
  * https://www.youtube.com/watch?v=GgVlI4zbUUI
  * http://www.svlada.com/jwt-token-authentication-with-spring-boot/   	
  * https://stackoverflow.com/questions/43253707/spring-boot-how-make-a-user-role-managing-with-jwt
  
# setting authorities for role base access .
  * need to set the authorithies after validation of token in authorization file
  - 	// 4. Validate the token
    			Claims claims = Jwts.parser()
    					.setSigningKey(StringConstants.JWT_SECRET.getBytes())
    					.parseClaimsJws(token)
    					.getBody();
    			String username = claims.get(StringConstants.EMAIL_STRING).toString();
    			if(username != null) {
    				@SuppressWarnings("unchecked")
    				List<String> authorities = (List<String>) claims.get(StringConstants.ROLES_STRING);
    				LOGGER.debug("Authorities from Claims {}", claims);
    				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
    						username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
    				SecurityContextHolder.getContext().setAuthentication(auth);
    			}


# Java Linting (Code quality like robocop for rails)
 * sonar

# Coverage report
  * jacoco
  * https://thepracticaldeveloper.com/2016/02/06/test-coverage-analysis-for-your-spring-boot-app/ 
  * EclEmma (Powered by jacoco)
    * https://dzone.com/articles/java-code-coverage-in-eclipse
    * https://www.eclemma.org/userdoc/index.html
 
# Sample controller
  - @GetMapping(value = "/logOut")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        LOGGER.debug("inside logout");
        String queryString = request.getQueryString();
        String jwtToken = userService.getAnETokenFromDB(queryString);
        String callBackURL = externalURLs.aneCallBackUrl;
        if(jwtToken.isEmpty()) {
            LOGGER.debug("user not found in DB");
        }
        userService.deleteByToken(jwtToken);
        LOGGER.debug(callBackURL);
        String token = generateToken.createToken(callBackURL);
        String redirectUrl = externalURLs.aneLogoutUrl + token;
        LOGGER.debug("logout redirect url {}", redirectUrl);
        httpServletResponse.setHeader(StringConstants.LOCATION_STRING, redirectUrl);
        httpServletResponse.setStatus(302);
    } 
    
    
# Custom Serializer 
 * https://www.concretepage.com/jackson-api/jackson-custom-serializer    
 
# Microservices
  * https://dzone.com/users/230813/sivaprasad.html 
  * https://developer.okta.com/blog/2017/06/15/build-microservices-architecture-spring-boot
  
# Jackson
  * https://www.youtube.com/watch?v=hq8Im5FRMgM&index=8&list=PLjXUjSTUHs0TQl-W-B5u-d6acs23Y-1te  
  * Json View to filter attributes:
    - https://medium.com/@_neha_kumari/jackson-jsonview-for-serialising-and-deserialising-object-a54005c875c0 
    - https://stackoverflow.com/questions/38279782/what-is-the-json-view-class-in-jackson-and-how-does-it-work
    - http://techtraits.com/programming/2011/08/13/implementing-jackson-views.html
    - https://grokonez.com/json/use-jsonview-serializede-serialize-customize-json-format-java-object => (what i want)
    - https://stackoverflow.com/questions/23260464/how-to-serialize-using-jsonview-with-nested-objects
    - http://www.studytrails.com/java/json/java-jackson-serialization-list/
    - http://www.makeinjava.com/convert-list-objects-tofrom-json-java-jackson-objectmapper-example/
    
    * list of objects readValue
      - https://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
  
  * https://www.baeldung.com/jackson-field-serializable-deserializable-or-not => making fields private and using setters and getters    
  
  * Issue with bidirectional
    - https://stackoverflow.com/questions/22615317/serialize-bi-directional-jpa-entities-to-json-with-jackson
    - https://stackoverflow.com/questions/53863697/jsongenerationexception-when-serializing-nested-object-using-custom-serializer-i
    - https://grokonez.com/json/resolve-json-infinite-recursion-problems-working-jackson => good one
      
# Ubuntu Server Setup 
  * https://www.youtube.com/watch?v=26ipmonPmRw      
  
# Tomcat Paths
  * /usr/share/tomcat8 
  * /var/lib/tomcat8 => logs, cache  
  
# Jenkins Code Setup
  * normal project, need to try with maven
  
  * Created a sub-directory
    * under source code management
      * git 
        * add additional behaviours
          *  checkout a subdirectort
          
    name of directory => applicationname-stage/applicationName
    
  * pre-build 
    cd applicationname-stage
    zip -r applicationName.zip applicationName
    mv applicationName.zip ../
    ls -lrth
    
  * post build
    * send build artifact over ssh
    * source file name: *.zip
    
    * this file will be sent over
    * then your commands
      pwd
      cd /opt/builds/git
      mv applicationName.zip applicationname-stage/
      cd applicationname-stage
      pwd 
      ls -lrt
      
      sudo rm -rf applicationName
      
      unzip applicationName.zip
      
      sudo rm applicationName.zip
      ls -rlt
      
      cd applicationName/
      mvn clean package -P QA
      
      sudo systemctl stop tomcat8
      
      sudo cp applicationNameAPI-0.0.1-SNAPSHOT.war /var/lib/tomcat8/webapps/applicationNameAPI.war
      
      sudo systemctl start tomcat8
      sudo systemctl status tomcat8
      
      cd /var/lib/tomcat8/webapps
      
      ls -lrth    
      
      
# Spring Boot Integrations Examples
  * https://grokonez.com/java-integration-tutorial      
  
# Set Env for deployment
  * https://stackoverflow.com/questions/15814497/setting-spring-profile-variable  
  
# Refractoring
  * https://refactoring.com/catalog/

# Principles
  * http://wiki.c2.com/?YouArentGonnaNeedIt
  
# Rest Response Codes
  * https://restfulapi.net/http-status-codes/
  
# Demo Queries
public class RunFormQueries {
    public static final String UPDATE_RUN_CALCULATIONS  = "UPDATE process_run SET run_name = :run_name, notes = :notes, process_state_id = :process_state_id WHERE id = :run_id";        
  } 
  
# New Project Setup
  * new packages:
    * models
    * dao
    * controllers
    * services   
      * service-interface
      * service impl of the interface
  
# Basic's Of Spring 
  * https://www.baeldung.com/inversion-control-and-dependency-injection-in-spring   
  * https://dzone.com/articles/the-springbootapplication-annotation-example-in-ja

# Class Path in Spring Boot
  * maven-dependencies folder
  * adding dependencies to pom will add all the jars to class path 
  * https://www.journaldev.com/2389/java-8-features-with-examples
  * http://eherrera.net/ocpj8-notes/04-lambda-built-in-functional-interfaces
  
# OOPS(EIPA)
  * Encapsulation
  * Inheritance
  * Polymorphism
  * Abstraction   
  
# Get user ID
   https://stackoverflow.com/questions/50803727/spring-with-jwt-auth-get-current-user  

# Get Headers from request
  * https://stackoverflow.com/questions/19556039/how-to-get-access-to-http-header-information-in-spring-mvc-rest-controller/19556081   
   
# Design patterns
  * https://springframework.guru/gang-of-four-design-patterns/  
  
# Audit Aware Interface
  * https://www.baeldung.com/database-auditing-jpa
  * https://springbootdev.com/2018/03/13/spring-data-jpa-auditing-with-createdby-createddate-lastmodifiedby-and-lastmodifieddate/  
  * https://thoughts-on-java.org/persist-creation-update-timestamps-hibernate/
  * https://www.programmingmitra.com/2017/02/automatic-spring-data-jpa-auditing-saving-CreatedBy-createddate-lastmodifiedby-lastmodifieddate-automatically.html

# Good tutorial about Hibernate
  * https://thoughts-on-java.org/tutorials/  