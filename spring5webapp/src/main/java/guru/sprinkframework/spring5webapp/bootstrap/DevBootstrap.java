package guru.sprinkframework.spring5webapp.bootstrap;

import guru.sprinkframework.spring5webapp.model.Author;
import guru.sprinkframework.spring5webapp.model.Book;
import guru.sprinkframework.spring5webapp.model.Publisher;
import guru.sprinkframework.spring5webapp.repositories.AuthorRepository;
import guru.sprinkframework.spring5webapp.repositories.BookRepository;
import guru.sprinkframework.spring5webapp.repositories.PublisherRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }





    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        initData();
    }

    // class for seeding data

    private void initData() {

        // set publisher

        Publisher publisher = new Publisher();
        publisher.setName("foo");

        publisherRepository.save(publisher);

        //Eric
        Author eric = new Author("Eric", "Evans");
//        Book ddd = new Book("Domain Driven Design", "1234", "Harper Collins");
        Book ddd = new Book("Domain Driven Design", "1234", publisher);
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        authorRepository.save(eric);
        bookRepository.save(ddd);

        //Rod
        Author rod = new Author("Rod", "Johnson");
//        Book noEJB = new Book("j2ee", "23444", "Worx");
        Book noEJB = new Book("j2ee", "23444", publisher);

        rod.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        authorRepository.save(rod);
        bookRepository.save(noEJB);
    }
}
