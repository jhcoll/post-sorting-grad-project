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
    UserPost userPost1 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost userPost2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost userPost3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

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
        List<UserPost> inputList = List.of(userPost1);
        List<UserPost> expected = List.of(userPost1);

        List<UserPost> actual = new AuthorPostFilter("Jane").filter(inputList);

        assertEquals(expected, actual);
    }


    @Test
    void filter_3Items2Match_2Items() {
        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost2, userPost3);

        List<UserPost> actual = new AuthorPostFilter("Joe").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items1Match_1Item() {
        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = List.of(userPost1);

        List<UserPost> actual = new AuthorPostFilter("Jane").filter(inputList);

        assertEquals(expected, actual);
    }


    @Test
    void filter_3Items0Match_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new AuthorPostFilter("").filter(inputList);

        assertEquals(expected, actual);
    }
}