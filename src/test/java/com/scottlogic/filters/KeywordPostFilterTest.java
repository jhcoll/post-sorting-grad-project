package com.scottlogic.filters;

import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeywordPostFilterTest {
    UserPost userPost_Hello = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost userPost_Example_Post = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 10, 20, 22, 12, 0, ZoneOffset.UTC),
            "Example Post", 3);

    UserPost userPost_example_post = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost userPost_example_post2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    @Test
    void filter_null_null() {
        List<UserPost> actual = new KeywordPostFilter("").filter(null);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new KeywordPostFilter("").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPost_Hello);
        List<UserPost> expected = List.of(userPost_Hello);

        List<UserPost> actual = new KeywordPostFilter("Hello").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1Item0Match_EmptyList() {
        List<UserPost> inputList = List.of(userPost_Hello);
        List<UserPost> expected = List.of();

        List<UserPost> actual = new KeywordPostFilter("Test").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3ItemsEmptyFilter_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPost_Hello, userPost_Example_Post, userPost_example_post);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new KeywordPostFilter("").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items0Match_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPost_Hello, userPost_Example_Post, userPost_example_post);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new KeywordPostFilter("Test").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items1Match_1Item() {
        List<UserPost> inputList = Arrays.asList(userPost_Hello, userPost_Example_Post, userPost_example_post);
        List<UserPost> expected = List.of(userPost_Hello);

        List<UserPost> actual = new KeywordPostFilter("Hello").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items2Match_2Items() {
        List<UserPost> inputList = Arrays.asList(userPost_Hello, userPost_Example_Post, userPost_example_post);
        List<UserPost> expected = Arrays.asList(userPost_Example_Post, userPost_example_post);

        List<UserPost> actual = new KeywordPostFilter("post").filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items3Match_3Items() {
        List<UserPost> inputList = Arrays.asList(userPost_example_post2, userPost_Example_Post, userPost_example_post);
        List<UserPost> expected = Arrays.asList(userPost_example_post2, userPost_Example_Post , userPost_example_post);

        List<UserPost> actual = new KeywordPostFilter("post").filter(inputList);

        assertEquals(expected, actual);
    }
}