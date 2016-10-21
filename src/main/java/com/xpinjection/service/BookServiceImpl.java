// ============================================================================
//  File          : BookServiceImpl
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.service;

import com.xpinjection.dao.BookDao;
import com.xpinjection.domain.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;

    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> addBooks(Map<String, String> books) {
        return books.entrySet().stream()
                .map(entry -> new Book(entry.getKey(), entry.getValue()))
                .map(bookDao::save)
                .collect(toList());
    }

    @Override
    public List<Book> findBooksByAuthor(String author) {
        return bookDao.findByAuthor(author);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }
}
