package com.scottlogic.filters;

import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorPostFilterTest {
    UserPost userPostJane = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost userPostJoe1 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost userPostJoe2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    UserPost userPostJoe3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 10, 13, 21, 12, 0, ZoneOffset.UTC),
            "Third Example", 2);

    @Test
    void filter_null_null() {
        List<UserPost> actual = new AuthorPostFilter("").filter(null);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new AuthorPostFilter("").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPostJane);
        List<UserPost> expected = List.of(userPostJane);

        List<UserPost> actual = new AuthorPostFilter("Jane").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1Item0Match_EmptyList() {
        List<UserPost> inputList = List.of(userPostJane);
        List<UserPost> expected = List.of();

        List<UserPost> actual = new AuthorPostFilter("David").filter(inputList);

        assertEquals(expected, actual);
    }


    @Test
    void filter_3ItemsEmptyFilter_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new AuthorPostFilter("").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items0Match_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new AuthorPostFilter("David").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items1Match_1Item() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = List.of(userPostJane);

        List<UserPost> actual = new AuthorPostFilter("Jane").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items2Match_2Items() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = Arrays.asList(userPostJoe1, userPostJoe2);

        List<UserPost> actual = new AuthorPostFilter("Joe").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items3Match_3Items() {
        List<UserPost> inputList = Arrays.asList(userPostJoe1, userPostJoe2, userPostJoe3);
        List<UserPost> expected = Arrays.asList(userPostJoe1, userPostJoe2, userPostJoe3);

        List<UserPost> actual = new AuthorPostFilter("Joe").filter(inputList);

        assertEquals(expected, actual);
    }
}