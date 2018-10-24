package guru.springframework.didemo;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by jt on 6/5/17.
 */
@Component
public class LifeCycleDemoBean implements InitializingBean, DisposableBean, BeanNameAware,
        BeanFactoryAware, ApplicationContextAware{


    public LifeCycleDemoBean() {
        System.out.println("## I'm in the LifeCycleBean Constructor");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("## The Lifecycle bean has been terminated");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("## The LifeCycleBean has its properties set!");

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("## Bean Factory has been set");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("## My Bean Name is: " + name);

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("## Application context has been set");
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("## The Post Construct annotated method has been called");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("## The Predestroy annotated method has been called");
    }

    // the below methods will be called by bean post processor, here customBeanPostProcessor

    public void beforeInit(){
        System.out.println("## - Before Init - Called by Bean Post Processor");
    }

    public void afterInit(){
        System.out.println("## - After init called by Bean Post Processor");
    }
}

/*
* Bean LifeCycle/hooks:

  Call back interfaces:

     IntializingBean.afterPropertiesSet()

    DisposableBean.destroy()

LifeCycle Hooks:

   @PostConstruct - called after bean is constructed, before returning to requesting object
   @PreConstruct - called before bean is destroyed from container

Bean Post Processors: (FYI)

   Implement interface Bean Processor

       postProcessBeforeInitialization - call before bean initialisation
       postProcessAfterInitialization  -


‘Aware Interfaces’ : 14
     used inside spring framework, rarely used by developers
* */


/*
## I'm in the LifeCycleBean Constructor
## My Bean Name is: lifeCycleDemoBean
## Bean Factory has been set
## Application context has been set
## - Before Init - Called by Bean Post Processor
## The Post Construct annotated method has been called
## The LifeCycleBean has its properties set!
## - After init called by Bean Post Processor

$$$$
$$$$  other's

## The Predestroy annotated method has been called
## The Lifecycle bean has been terminated


*
* */