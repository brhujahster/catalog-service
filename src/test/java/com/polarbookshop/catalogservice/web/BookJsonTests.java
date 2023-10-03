package com.polarbookshop.catalogservice.web;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

     @Test
    void testeSerializer() throws Exception {
         var book = new Book(null,
                 "1234567890",
                 "Title",
                 "Author",
                 9.9,
                 "polarsophia",
                 LocalDateTime.now().toInstant(ZoneOffset.UTC),
                 LocalDateTime.now().toInstant(ZoneOffset.UTC),
                 0);
         var jsonContent = json.write(book);
         Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                 .isEqualTo(book.isbn());
         Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                 .isEqualTo(book.title());
         Assertions.assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                 .isEqualTo(book.author());
         Assertions.assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
                 .isEqualTo(book.price());
     }

     @Test
    void testDeserialize() throws Exception {
         var content = """
                 {
                     "isbn": "1234567890",
                     "title": "Title",
                     "author": "Author",
                     "price": 9.9,
                      "publisher": "polarsophia",
                     "createdDate": "2023-09-21T21:54:33Z",
                     "lastModifiedDate": "2023-09-21T21:54:33Z",
                     "version": 1
                 }
                 """;
         Instant instant = LocalDateTime.of(2023, 9, 21, 21, 54, 33).toInstant(ZoneOffset.UTC);
         Assertions.assertThat(json.parse(content))
                 .usingRecursiveComparison()
                 .isEqualTo(new Book(null,
                         "1234567890",
                         "Title",
                         "Author",
                         9.9,
                         "polarsophia",
                         instant,
                         instant,
                         1));
     }
}
