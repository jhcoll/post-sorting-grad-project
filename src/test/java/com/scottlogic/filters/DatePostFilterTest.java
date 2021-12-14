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

class DatePostFilterTest {

    UserPost userPost2020_01_01_0230 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.UTC),
            "Hello World!", 1);

    UserPost userPost2020_01_01_0230_of2 = new UserPost("John Biggs",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.of("-2")),
            "An example of a post \nwith lines breaks.", 3);

    UserPost userPost2020_01_01_0630 = new UserPost("Jack Blaggs",
            OffsetDateTime.of(2020, 1, 1, 6, 30, 0, 0, ZoneOffset.UTC),
            "Another example post.", 2);

    UserPost userPost2020_01_02_0504 = new UserPost("Janet Small",
            OffsetDateTime.of(2020, 1, 2, 5, 4, 0, 0, ZoneOffset.UTC),
            "Hello World!", 4);

    UserPost userPost2020_02_01_2200 = new UserPost("Jared Smooth",
            OffsetDateTime.of(2020, 2, 1, 22, 0, 0, 0, ZoneOffset.UTC),
            "Hello World!", 3);

    UserPost userPost2021_01_01_1230 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2021, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC),
            "Example Post", 0);

    OffsetDateTime date_2020_01_01_0000 = OffsetDateTime.parse("2020-01-01T00:00:00+00:00");
    OffsetDateTime date_2020_01_03_0000 = OffsetDateTime.parse("2020-01-03T00:00:00+00:00");
    OffsetDateTime date_2020_03_01_0000 = OffsetDateTime.parse("2020-03-01T00:00:00+00:00");
    OffsetDateTime date_2020_01_01_0300 = OffsetDateTime.parse("2020-01-01T03:00:00+00:00");

    @Test
    void filter_null_null() {
        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_03_0000).filter(null);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_03_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPost2020_01_01_0230);
        List<UserPost> expected = List.of(userPost2020_01_01_0230);

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_03_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1Item0Match_EmptyList() {
        List<UserPost> inputList = List.of(userPost2020_02_01_2200);
        List<UserPost> expected = List.of();

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_03_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items0Match_EmptyList() {
        List<UserPost> inputList = Arrays.asList(userPost2020_01_02_0504, userPost2020_02_01_2200, userPost2021_01_01_1230);
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_01_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items1Match_1Item() {
        List<UserPost> inputList = Arrays.asList(userPost2020_01_02_0504, userPost2020_02_01_2200, userPost2021_01_01_1230);
        List<UserPost> expected = List.of(userPost2020_01_02_0504);

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_03_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items2Match_2Items() {
        List<UserPost> inputList = Arrays.asList(userPost2020_01_02_0504, userPost2020_02_01_2200, userPost2021_01_01_1230);
        List<UserPost> expected = Arrays.asList(userPost2020_01_02_0504, userPost2020_02_01_2200);

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_03_01_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_3Items3Match_3Items() {
        List<UserPost> inputList = Arrays.asList(userPost2020_01_01_0230, userPost2020_01_02_0504, userPost2020_02_01_2200);
        List<UserPost> expected = Arrays.asList(userPost2020_01_01_0230, userPost2020_01_02_0504, userPost2020_02_01_2200);

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_03_01_0000).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_2Items1offSet_1Items() {
        List<UserPost> inputList = Arrays.asList(userPost2020_01_01_0230, userPost2020_01_01_0230_of2);
        List<UserPost> expected = List.of(userPost2020_01_01_0230);

        List<UserPost> actual = new DatePostFilter(date_2020_01_01_0000, date_2020_01_01_0300).filter(inputList);

        assertEquals(expected, actual);
    }

}