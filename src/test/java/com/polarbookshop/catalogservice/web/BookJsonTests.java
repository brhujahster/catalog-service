package com.polarbookshop.catalogservice.web;


import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> jsonTest;

    @Test
    void serialize() throws Exception{
        var book =  Book.of("1234567890", "Title", "Author", 9.90, "polarsophia");

        var jsonContent = jsonTest.write(book);
        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo(book.isbn());
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo(book.title());
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo(book.author());
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                .isEqualTo(book.price());

    }

    @Test
    void desserialize() throws Exception{
        var content = """
                        {
                            "isbn": "1234567890",
                            "title": "Title",
                            "author": "Author",
                            "publisher" : "polarsophia",
                            "price": 9.90
                        }
                       """;
        assertThat(jsonTest.parse(content))
                .usingRecursiveComparison()
                .isEqualTo( Book.of("1234567890", "Title", "Author", 9.90, "polarsophia"));
    }
}
