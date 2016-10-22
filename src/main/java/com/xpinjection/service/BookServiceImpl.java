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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.splitByCharacterTypeCamelCase;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final ConcurrentMap<String, List<Book>> cache = new ConcurrentHashMap<>();

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
        Validate.notBlank(author, "Author is empty!");
        String correctAuthor = splitFirstAndLastName(author);
        return cache.computeIfAbsent(correctAuthor, bookDao::findByAuthor);
    }

    @Override
    public List<Book> findAllBooks() {
        return bookDao.findAll();
    }

    private String splitFirstAndLastName(String author) {
        return Arrays.stream(splitByCharacterTypeCamelCase(author))
                .filter(StringUtils::isNotBlank)
                .collect(joining(" "));
    }
}
