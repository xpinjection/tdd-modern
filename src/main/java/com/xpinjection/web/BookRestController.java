package com.xpinjection.web;

import com.xpinjection.domain.Book;
import com.xpinjection.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Alimenkou Mikalai
 */
@RestController
public class BookRestController {
    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/books")
    public List<Book> findBooksByAuthor(@RequestParam String author) {
        return bookService.findBooksByAuthor(author);
    }
}
