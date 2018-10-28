# Spring Boot Jokes App

 This sample web app

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