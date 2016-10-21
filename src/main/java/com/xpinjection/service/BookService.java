// ============================================================================
//  File          : BookService
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.service;

import com.xpinjection.domain.Book;

import java.util.List;
import java.util.Map;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
public interface BookService {
    List<Book> addBooks(Map<String, String> books);

    List<Book> findBooksByAuthor(String author);

    List<Book> findAllBooks();
}
