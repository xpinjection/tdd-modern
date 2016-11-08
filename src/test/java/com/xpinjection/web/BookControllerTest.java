package com.xpinjection.web;

import com.xpinjection.domain.Book;
import com.xpinjection.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Alimenkou Mikalai
 */
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    @Mock
    private BookService bookService;

    private BookController controller;

    private MockMvc mockMvc;

    private List<Book> books = asList(new Book("First", "author"),
            new Book("Second", "another author"));

    @Before
    public void init() {
        controller = new BookController(bookService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        when(bookService.findAllBooks()).thenReturn(books);
    }

    @Test
    public void allBooksAreAddedToModelForLibraryView() {
        Model model = new ExtendedModelMap();
        assertThat(controller.booksPage(model), equalTo("library"));
        assertThat(model.asMap(), hasEntry("books", books));
    }

    @Test
    public void requestForLibraryIsSuccessfullyProcessedWithAvailableBooksList() throws Exception {
        this.mockMvc.perform(get("/library.htm"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", equalTo(books)))
                .andExpect(forwardedUrl("library"));
    }
}