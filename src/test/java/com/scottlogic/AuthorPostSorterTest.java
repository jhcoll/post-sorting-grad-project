package com.scottlogic;

import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorPostSorterTest {
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
    void sort_null_null() {
        List<UserPost> actual = new AuthorPostSorter().sort(null, SortOrder.ASC);

        assertNull(actual);
    }

    @Test
    void sort_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPost1);
        List<UserPost> expected = List.of(userPost1);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Items_sorted3Items() {
        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost2, userPost3, userPost1);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Lower_sorted3Lower() {
        UserPost userPost1 = new UserPost("jane smith",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("joe bloggs",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("joe bloggs",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost2, userPost3, userPost1);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3ItemsDESC_sorted3ItemsDESC() {
        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost1, userPost2, userPost3);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Num_sorted3Num() {
        UserPost userPost1 = new UserPost("2",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("3",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("1",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost3, userPost1, userPost2);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Surnames_sorted3Surnames() {
        UserPost userPost1 = new UserPost("Joe Blogg",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("Jack Collier",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("Arwell Andy",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost3, userPost1, userPost2);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3SurnamesFirstnames_sorted3SurnamesFirstnames() {
        UserPost userPost1 = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 2);

        UserPost userPost2 = new UserPost("Joseph",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost3 = new UserPost("Jack Collier",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 3);

        List<UserPost> inputList = Arrays.asList(userPost1, userPost2, userPost3);
        List<UserPost> expected = Arrays.asList(userPost1, userPost3, userPost2);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }
}