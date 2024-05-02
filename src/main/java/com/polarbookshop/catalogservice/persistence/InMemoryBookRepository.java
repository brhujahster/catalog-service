package com.polarbookshop.catalogservice.persistence;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryBookRepository implements BookRepository {

    public static final Map<String, Book> books = new HashMap<>();

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return existingBook(isbn) ? Optional.of(books.get(isbn)) : Optional.empty();
    }

    @Override
    public boolean existingBook(String isbn) {
        return books.get(isbn) != null;
    }

    @Override
    public Book save(Book book) {
        books.put(book.isbn(), book);
        return  book;
    }

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }
}