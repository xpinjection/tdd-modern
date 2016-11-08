package com.xpinjection.service;

import com.xpinjection.domain.Book;

import java.util.List;
import java.util.Map;

/**
 * @author Alimenkou Mikalai
 */
public interface BookService {
    List<Book> addBooks(Map<String, String> books);

    List<Book> findBooksByAuthor(String author);

    List<Book> findAllBooks();
}
