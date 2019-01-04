# Spring Boot Jokes App

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
  * Session scope
    - Scoped to an HTTP Web Session. Only Used for web apps   
  * global-session
    - Scoped to an Global HTTP Web Session. Only Used for web apps  
    
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
    
# Beans => Java Annotations(IOC using annotation) (spring-demo-annotation)
  * Annotations are meta data of class(like labels on shoe box describing the content color, size, make)
  * Component Scan, will scan java application for annotation(@Bean, @service, etc) and register the beans in the Spring Container(Application Context)            
  * To enable component scanning in XML configuration
     * <context:component-scan base-package="com.luv2code.springdemo"></context:component-scan> 
     
# Spring Configuration with Java Annotations & DI
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
           
# Bean Scopes With Anotations(@Scope)
    * -   @Scope("prototype") //"singleton" is default
          public class TennisCoach {
                     @Autowired
                     private FortuneService fortuneService;
                     // constructor
                     public void TennisCoach() {}   
                 }

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

# Spring MVC Configuration without Spring Boot => (99/100/102 chad)
  * Add Configurations to file WEB-INF/web.xml
    - Configure Spring MVC Dispatcher Servlet
    - Setup URL mappings to Spring MVC Dispatcher Servlet
  * Add COnfigurations to file WEB-INF/spring-mvc-demo-servlet.xml
    - Add support for Spring Component Scanning 
    - Add Support for conversion, formatting and validation
    - Configure Spring MVC View Resolver     

# @SpringBootApplication 

  * this is the main annotation
  * this includes automatically
    * @Configuration
    * @EnableAutoConfiguration
    * @ComponentScan
    
# command-line      

   * mvn spring-boot:help -Ddetail=true
   * mvn spring-boot:run -Drun.arguments=--debug
     * intellij next to hammer/run btn > click onit > Edit Configurations > enable debug is same as above

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
    * @Repositiory  
    
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
  *  Spring will detect platform specific persistance exceptions and re-throw them as spring exceptions 
  
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
  
# Jhipster
  * https://start.jhipster.tech/jdl-studio/
    
# Hibernate  
  * By default in hibernate if did not specify cascade nothing will be mentioned   
  * https://o7planning.org/en/11223/generate-tables-from-entity-classes-in-hibernate 
  * http://www.techferry.com/articles/hibernate-jpa-annotations.html 

# JPA
  * addition to spring 5, it returns an optional instead of null
  * these dynamic queries like in rails, here they are called JPA Query methods
  * jpa takes care of all implementation  
  * note here no Stereotype declarations for this like @ component, @service ...ect JPA inmplements it so it is avaliable in the
    Bean Container
  * JPA has @PrePersist and @PreUpdate for automatic update timestamp properties for audit purpose, same thing in hibernate has @CreationTimestamp and @UpdateTimestamp
  
  # Types Of Cascade
    * Persist
    * Merge
    * Refresh
    * Remove   
    * Detach
    * All
    
  # Hibernates default persistence strategy for inheritance
    * Single Table Inheritance -> leads to lot of unused coloms, with type colom as common  
    * Hibernates ddl-auto property control's if any ddl operatiions hibernate will perform on start up.
      * options for ddl-auto => none, validate(used for production to check if any attribute or table is missed), update, create, create-drop() using embeded DB
  
  # Files used by spring Boot to initialize the DB
    * schema.sql
    * data.sql(by default spring searches for this file)
    
  # Different Repositories To Implement
    /* package org.springframework.data.repository; Depends on when to use what usecase*/
    * Repository (Base)
    * CrudRepository
    * PagingAndSortingRepository
    * JpaRepository(include's All three above)
    
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
  
  # Spring Cloud(Finchley)
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
     
       
   
# JAVA
  # Good Links
    * https://howtodoinjava.com/
    
  # Steams and Lambda
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
        
  # Arrays & Lists => ArrayLists
    * Array
        * int[] myVAr = new String[10]#{1,2,3};     
        * int[] myVar = #new int[1,2,3]  
    * List
      * Ordered Collection
      * ArrayList<Integer> myVar = new ArrayList(); // new ArrayList<Integer>();     
        * add, size, set(position, item) => update, remove(position), contains, indexOf(item), get(index/position)
        
  # Auto Boxing and Unboxing
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
      * Executor service 
         ExecutorService executorService = Executors.newFixedThreadPool(3);
         (new Thread(r)).start(); => ex.execute(r);
    * ArrayBlockingQueue => Thread safe,  this is bounded and manages locks internally
      * methods to this put, take and pee all take lock for executing    
      
      
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
      