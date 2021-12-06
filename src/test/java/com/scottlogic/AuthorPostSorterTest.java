package com.scottlogic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorPostSorterTest {
    @Test
    void nullTest() {
        List<UserPost> actual = new AuthorPostSorter().sort(null);

        assertNull(actual);
    }

    @Test
    void emptyListTest() {
        List<UserPost> actual = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        actual = new AuthorPostSorter().sort(actual);

        assertEquals(expected, actual);
    }

    @Test
    void sortCapital() {
        UserPost userPost1 = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("Jane Smith",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> actual = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost3, userPost1, userPost2);

        actual = new AuthorPostSorter().sort(actual);

        assertEquals(expected, actual);
    }

    @Test
    void sortLower() {
        UserPost userPost1 = new UserPost("joe bloggs",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("joe bloggs",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("jane smith",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> actual = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost3, userPost1, userPost2);

        actual = new AuthorPostSorter().sort(actual);

        assertEquals(expected, actual);
    }
}