// ============================================================================
//  File          : DemoRunner
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection;

import com.xpinjection.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
@Component
public class DemoRunner implements CommandLineRunner {
    private final BookService bookService;

    public DemoRunner(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... strings) throws Exception {
        Map<String, String> books = new HashMap<>();
        books.put("Spring in Action", "Who knows?");
        books.put("Hibernate in Action", "Who cares?");
        bookService.addBooks(books).forEach(System.out::println);
    }
}
