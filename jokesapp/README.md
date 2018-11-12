# Spring Boot Jokes App

 This sample web app
 
 # Good Links
   * https://stackoverflow.com/questions/19795709/understanding-upper-and-lower-bounds-on-in-java-generics
   * https://www.journaldev.com/721/java-annotations
   * https://stackoverflow.com/questions/2265503/why-do-i-need-to-override-the-equals-and-hashcode-methods-in-java
   * http://www.techferry.com/articles/hibernate-jpa-annotations.html


#How to create a new project with spring initalizer
  * create new project > select spring initializer > selected dependencies
    then usual
    
# Ascii Art
  * http://patorjk.com/software/taag/#p=display&f=Graffiti&t=Type%20Something%20   
  
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
    * In Java Configuration use @Scope annotaion
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
  
# what is @ Repository used for
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
    * mvn install
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
  
  # Connecting to multiple DB's
    * https://www.devglan.com/spring-boot/spring-boot-multiple-database-configuration
    
  # in 28 minutes
    * http://www.springboottutorial.com/spring-boot-projects-with-code-examples    