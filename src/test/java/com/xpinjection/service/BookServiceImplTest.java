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

import static java.util.Collections.emptyList;
import static org.codehaus.groovy.runtime.InvokerHelper.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.*;

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

    @Test
    public void ifNoBooksFoundForAuthorReturnEmptyList() {
        when(dao.findByAuthor("a")).thenReturn(emptyList());

        assertNoBooksFound("a");
        verify(dao, only()).findByAuthor("a");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifAuthorIsEmptyThrowException() {
        bookService.findBooksByAuthor(" \t \n ");
    }

    @Test(expected = NullPointerException.class)
    public void ifAuthorIsNullThrowException() {
        bookService.findBooksByAuthor(null);
    }

    @Test
    public void booksByAuthorShouldBeCached() {
        Book book = new Book("The book", "author");
        when(dao.findByAuthor("a")).thenReturn(asList(book));
        when(dao.findByAuthor("a a")).thenReturn(emptyList());

        assertBooksByAuthor("a", book);
        assertBooksByAuthor("a", book);
        assertNoBooksFound("a a");
        verify(dao, times(1)).findByAuthor("a");
    }

    @Test
    public void ifCamelCaseDetectedThenSplitInvalidAuthorNameOnFirstAndLastName() {
        Book book = new Book("The book", "Mikalai Alimenkou");
        when(dao.findByAuthor("Mikalai Alimenkou")).thenReturn(asList(book));

        assertBooksByAuthor("MikalaiAlimenkou", book);
    }

    @Test
    public void authorNameShouldBeTrimmedBeforeUsage() {
        Book book = new Book("The book", "Mikalai Alimenkou");
        when(dao.findByAuthor("Mikalai Alimenkou")).thenReturn(asList(book));

        assertBooksByAuthor(" \t Mikalai \n Alimenkou \t ", book);
    }

    private void assertBooksByAuthor(String author, Book book) {
        assertThat(bookService.findBooksByAuthor(author), hasItem(book));
    }

    private void assertNoBooksFound(String author) {
        assertThat(bookService.findBooksByAuthor(author), is(empty()));
    }
}
