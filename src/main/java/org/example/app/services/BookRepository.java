package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book " + book);
        repo.add(book);
    }

    @Override
    public void removeItemById(Integer bookIdToRemove) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                repo.remove(book);
                logger.info("remove book completed " + book);
                return;
            }
        }
        logger.info("Cannot delete the book with id " + bookIdToRemove + " Reason: Id is not found");
    }

    @Override
    public void removeItemByRegexp(String regexpQuery) {
        Pattern pattern = Pattern.compile(regexpQuery);
        for (Book book : retrieveAll()) {
            if (pattern.matcher(book.getAuthor()).matches()
                    || pattern.matcher(book.getTitle()).matches()
                    || pattern.matcher(String.valueOf(book.getSize())).matches()) {
                repo.remove(book);
                logger.info("remove book completed " + book);
            }
        }
    }
}
