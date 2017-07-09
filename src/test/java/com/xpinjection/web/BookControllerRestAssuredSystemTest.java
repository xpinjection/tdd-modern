package com.xpinjection.web;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookControllerRestAssuredSystemTest {
    @Test
    public void allBooksFromDatabaseAreAvailableOnWeb() throws Exception {
        given()
            .accept("text/html;charset=UTF-8")
        .when()
            .get(URI.create("/library.htm"))
        .then()
            .statusCode(HttpStatus.SC_OK)
            .contentType("text/html;charset=UTF-8")
            .content(allOf(
                containsString("Spring in Action, <em>Who knows?</em>"),
                containsString("Hibernate in Action, <em>Who cares?</em>")));
    }
}
