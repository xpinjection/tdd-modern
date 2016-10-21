// ============================================================================
//  File          : BookServiceImplTest
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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.when;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class BookServiceImplTest {
    @Mock
    private BookDao dao;

    private BookService bookService;

    @Before
    public void init() {
        bookService = new BookServiceImpl(dao);
    }

    @Test
    public void ifNoBooksPassedEmptyListIsReturned() {
        assertThat(bookService.addBooks(Collections.emptyMap()), is(empty()));
    }

    @Test
    public void forEveryPairOfTitleAndAuthorBookIsCreatedAndStored() {
        Book first = new Book("The first", "author");
        Book second = new Book("The second", "another author");
        when(dao.save(notNull(Book.class))).thenReturn(first).thenReturn(second);

        Map<String, String> books = new HashMap<>();
        books.put("The first", "author");
        books.put("The second", "another author");
        assertThat(bookService.addBooks(books), hasItems(first, second));
    }
}