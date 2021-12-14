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

class LikePostFilterTest {

    UserPost userPostLikes0 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 0);

    UserPost userPostLikes0_2 = new UserPost("Janet Small",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 0);

    UserPost userPostLikes0_3 = new UserPost("Jared Smooth",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 0);

    UserPost userPostLikes1 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 10, 20, 22, 12, 0, ZoneOffset.UTC),
            "Example Post", 1);

    UserPost userPostLikes2 = new UserPost("Jack Blaggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 2);

    UserPost userPostLikes3 = new UserPost("John Biggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    @Test
    void filter_null_null() {
        List<UserPost> actual = new LikePostFilter().filter(null);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPostLikes1);
        List<UserPost> expected = List.of(userPostLikes1);

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1Item0Match_EmptyList() {
        List<UserPost> inputList = List.of(userPostLikes0);
        List<UserPost> expected = List.of();

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items0Match_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPostLikes0, userPostLikes0_2, userPostLikes0_3);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items1Match_1Item() {
        List<UserPost> inputList = Arrays.asList(userPostLikes0, userPostLikes0_2, userPostLikes2);
        List<UserPost> expected = List.of(userPostLikes2);

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items2Match_2Items() {
        List<UserPost> inputList = Arrays.asList(userPostLikes0, userPostLikes1, userPostLikes2);
        List<UserPost> expected = Arrays.asList(userPostLikes1, userPostLikes2);

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items3Match_3Items() {
        List<UserPost> inputList = Arrays.asList(userPostLikes1, userPostLikes2, userPostLikes3);
        List<UserPost> expected = Arrays.asList(userPostLikes1, userPostLikes2, userPostLikes3);

        List<UserPost> actual = new LikePostFilter().filter(inputList);

        assertEquals(expected, actual);
    }
}