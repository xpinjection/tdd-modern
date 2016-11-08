package com.xpinjection.web;

import com.xpinjection.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Alimenkou Mikalai
 */
@Controller
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/library.htm")
    public String booksPage(Model model) {
        model.addAttribute("books", bookService.findAllBooks());
        return "library";
    }
}
