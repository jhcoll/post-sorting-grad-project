package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TopicPostSorterTest {
    UserPost topicWorld2 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World! this World is great", 2);

    UserPost topicPost2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Post is my favourite thing, i love post", 1);

    UserPost topicWorld1 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "world", 3);

    UserPost topicNoContent = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "", 2);

    UserPost topicFrog3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Frogs suck, I hate frogs, ugh frogs", 1);

    UserPost topicFrog4 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "Anyone who says frogs suck is a loser, \n frogs rule, frogs are the best things since sliced frog", 3);

    UserPost topicDog2 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "What's better than two dogs, three dogs", 2);

    UserPost topicDog3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Dogs are incredible, I love dogs more than life, dogs", 1);

    UserPost topicDog4 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "dogs dogs dogs dogs", 3);

    @Test
    void sort_null_null() {
        List<UserPost> actual = new TopicPostSorter().sort(null, SortOrder.ASC);

        assertNull(actual);
    }

    @Test
    void sort_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(topicWorld2);
        List<UserPost> expected = List.of(topicWorld2);

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Items_sorted3Items() {
        List<UserPost> inputList = Arrays.asList(topicFrog3, topicDog2, topicFrog4);
        List<UserPost> expected = Arrays.asList(topicDog2, topicFrog3, topicFrog4);

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3ItemsDesc_sorted3ItemsDesc() {
        List<UserPost> inputList = Arrays.asList(topicFrog3, topicDog2, topicFrog4);
        List<UserPost> expected = Arrays.asList(topicFrog4, topicFrog3, topicDog2);

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Items1wordItem_sorted3Items() {
        List<UserPost> inputList = Arrays.asList(topicWorld2, topicFrog3, topicWorld1);
        List<UserPost> expected = Arrays.asList(topicWorld2, topicWorld1,  topicFrog3);

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_1ItemNoContent_1Item() {


        List<UserPost> inputList = List.of(topicNoContent);
        List<UserPost> expected = List.of(topicNoContent);

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);

    }
}