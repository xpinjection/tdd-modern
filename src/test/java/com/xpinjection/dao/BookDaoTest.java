// ============================================================================
//  File          : BookDaoTest
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.dao;

import com.xpinjection.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class BookDaoTest {
    private static long ID = 1;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookDao dao;

    @Test
    public void ifThereIsNoBookWithSuchAuthorEmptyListIsReturned() {
        assertThat(dao.findByAuthor("unknown"), is(empty()));
    }

    @Test
    public void ifBooksByAuthorAreFoundTheyAreReturned() {
        long id = addBookToDatabase("Title", "author");
        addBookToDatabase("Another title", "another author");

        Book book = new Book("Title", "author");
        book.setId(id);
        assertThat(dao.findByAuthor("author"), hasItem(samePropertyValuesAs(book)));
    }

    @Test
    @Sql("/books-for-the-same-author.sql")
    public void severalBooksForTheSameAuthorAreReturned() {
        Book first = new Book("First book", "author");
        first.setId(1L);
        Book second = new Book("Second book", "author");
        second.setId(2L);
        assertThat(dao.findByAuthor("author"),
                hasItems(samePropertyValuesAs(first), samePropertyValuesAs(second)));
    }

    private long addBookToDatabase(String title, String author) {
        long id = ID++;
        jdbcTemplate.update("INSERT INTO book (id, name, author) VALUES (?, ?, ?)", id, title, author);
        return id;
    }
}