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
    - Be careful of making static variables and final vaiables, it will lead to race condition. Synchronisation needs to be applied which costs in performance      
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
    - bean is scoped to the lifecycle of Servlet Context.  only valid in the context os web-aware 
  * WebSocket
    - Scopes a Single Bean definition to the lifecycle of a WebSocket. Only Valid in the Context of web-aware Spring Application-Context 
  * Custom Scope 
    - Spring Scopes are Extensible, and we can define our own Scope by implementing spring's 'Scope' Interface
    
  * Note
    - We cannot override the built in singelton and prototype scopes    
    
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
      * Gives us a means to tap into the Spring COntext life cucle and interact with the beans as they are processed
      
      * postProcessBeforeInitialization 
        * called before bean initaliztion method
      
      * postProcessAfterInitializtion
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

  * DI via interface is highly preferred thank Concrete Classes(using the class it self directly)
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
        	
    * Setter Injection or as nor mal method
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
           
           If the annotation's value doesn't indicate a bean name, an appropriate name will be built based on the short name of the class (with the first letter lower-cased).
           
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
  * @Restcontroller(with response body for rest) or @Controller 
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
    * https://www.baeldung.com/spring-boot-add-filter
    * https://www.javadevjournal.com/spring-boot/spring-boot-add-filter/
    
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
      * compilr time weaving requires extra compilation step
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
      * does not have access to exception, if needed use use @AfterThrowing
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
  
# Packaging Spring Boot Project & Profule
  * https://stackoverflow.com/questions/38509511/spring-application-properties-profile-with-war-file
  * http://websystique.com/spring-boot/spring-boot-war-deployment-example/  
  * https://docs.spring.io/spring-boot/docs/current/maven-plugin/examples/run-profiles.html
  * https://www.baeldung.com/spring-profiles
  http://www.java2novice.com/spring-boot/profile-based-properties/
  
# JPA
  * Hibernate, OpenJPA are implementers of this 
  * Set of specification to be implemented
  * ORM Tool   
  * Criteria update, delete , Schema Generation, Validations, Queries against stored procedures  	 
  
  * Spring Data JPA:
    * This implements Repository Pattern
    
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
    * note here no Stereotype declarations for this like @ component, @service ...ect JPA implements it so it is avaliable in the
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
      
    # Return Association's as Json(Jackson Serialising and DeSerialising as Json / Marshalling and Unmarshalling)
      * Converting object to Json(Serialising), converting Json to Object(De-Serialising) 
      * https://www.javacodegeeks.com/2012/09/json-jackson-to-rescue.html
      * https://www.baeldung.com/jackson-bidirectional-relationships-and-infinite-recursion
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
          
  # Section 8, jhon
    * JDL-Studio for data modeling
    * Data Source Intialization(8, 146)
      * via import.sql by hibernate
      * via data.sql(recommended), data-${platform}.sql and schema.sql
      *  
    
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
    
# Annotaion used to declare a Spring Component inside Java configuration class
  * @Bean    
 
# Annotaion used to access Spring Bean Lifecycle
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
        
  # Loading intial data in spring boot
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
    
# Running compiler jar files from other Location
  * java -jar mobile-app-ws-0.0.1-SNAPSHOT.jar 
  
# Archiving Applications as Jar/War
  * when archived as Jar, it will even contain the apache tomcat in it, used then sharing the files to others machines 
  * when archived as War, it will not even contain the apache tomcat in it, used when already existing stand alone tomcat  
    server is running an need to use it   
    
# TomCat running from commad Line
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
  
  # Spring Cloud(Finchley version)
  
    * https://howtodoinjava.com/spring-cloud/spring-boot-ribbon-eureka/
     
    * https://stackoverflow.com/questions/42238335/when-to-configure-zuul-routes 
    
    * https://medium.com/omarelgabrys-blog/microservices-with-spring-boot-authentication-with-jwt-part-3-fafc9d7187e8
    * https://developer.okta.com/blog/2018/02/13/secure-spring-microservices-with-oauth - (https://www.youtube.com/watch?v=MY5m_s_U2H4&t=3s)
    * https://www.youtube.com/watch?v=ZIAi8sGHPII
    
    * Setup a Centralized server config using Spring Cloud Config Server, 
      and spring cloud will distibute to other microservices
      
    * spring cloud netflix (Dynamic Scale up and Scale Down)
        
      * Feign - RestFul Service Cleint, makes Easier to call REST Service using Rest Template using Proxy
        1) Used for writing easier restful clients
        
      * Ribbon - Client Side Load Balancing
        1) Make sure Load is balanced easily
        
      * Eureka - Naming Server - service discovery 
        1) all micro-services will register here, 
        2) service discovery - naming service will provide urls of current instance to asking microservice  
        
    * Visibilty And Monitoring
      1) Netflix API Gateway (Zuul) 
        * common functionalities like logging, security etc across microservices can be done using this
        * Rate Litmis(no of requests per hour or day), fault tolerant(if service goes down, should send message)
        * Service Aggregation, an external call may many calls for one functionality, and it may contain many calls, we
          need toaggregate it 
        * can be used for server side load balancing  
       
      2) Sleuth(assigns Id to requests, across all services) 
        * Spring Cloud Sleuth assigns Id to requests, and is used to trace across multiple components       
    
      3) Zipkin(Distibute Tracing) 
        * Consolidate all logs at one place
        * RABBIT_URI=amqp://localhost java -jar zipkin-server-2.11.12-exec.jar
                
    * Fault Tolerance
      1) Hystrix - if a service is down, hystrix helps to configure a default response. 
      
    * Zipkin Setup
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
               
    *  Inhertance(interfaces => default(like instance methods) and static methods)
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
        * Maninly used to associate a class with its outer class
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
    * can have abstract methods, wihtout implementations
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
             // U here mean's retuyn type
             public U returnTypeValue() { => public String/Integer/Object returnTypeValue() {}
             }
             
             // U here mean's it is 'U' type is going to be used inside the scope this method 
              public <U> String returnTypeValue(U u) { 
              }
      } 
      
    * Bounded Type Parameter using concept of inhertance
      * Upper Bound and Lower Bound
        * https://stackoverflow.com/questions/19795709/understanding-upper-and-lower-bounds-on-in-java-generics   
      
      - public class Box<T> {
            // we will passing a Generic Type here as a parameter, apart from the one we are passing to the class
            // U will be any Class that extends of inherits from Number classs. String won't work here
            public <U extends Number> String returnTypeValue(U u) { 
            }
        }
        
      - public class Box<T> {             
              // custom class will be lowest in the inheritance chain, so anyting from parent till CustomClass only allowed
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
    * mmutable class means that once an object is created, we cannot change its content. In Java, all the wrapper classes (like String, Boolean, Byte, Short) and String class is immutable. We can create our own immutable class as well.
      
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
              
    # SOLID
      *  https://springframework.guru/principles-of-object-oriented-design/
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
  
# Jenkins
  * https://www.youtube.com/watch?v=OwTv1aN5BUo  

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
  