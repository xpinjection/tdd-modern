// ============================================================================
//  File          : BookDao
//  Created       : 18.10.2016   
//  Description   :
//  Modifications :
//
// ============================================================================
//  Copyright(c) 2016 XP Injection, Ukraine
// ============================================================================
package com.xpinjection.dao;

import com.xpinjection.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
public interface BookDao extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);
}
