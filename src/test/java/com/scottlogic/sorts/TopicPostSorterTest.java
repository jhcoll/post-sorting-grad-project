package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    UserPost topicFrogs3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Frogs suck, I hate frogs, ugh frogs", 1);

    UserPost topicFrogs4 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "Anyone who says frogs suck is a loser, \n frogs rule, frogs are the best things since sliced frogs", 3);

    UserPost topicDog2 = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "What's better than two dogs, three dogs", 2);

    UserPost topicDog3 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Dogs are incredible, I love dogs more than life, dogs", 1);

    UserPost topicDog4 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "dogs dogs dogs dogs", 3);

    @Mock
    KeywordExtractor localKeywordExtractor = mock(KeywordExtractor.class);

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
    void sort_when1Item_returnSameItemList() {
        List<UserPost> inputList = List.of(topicWorld2);
        List<UserPost> expected = List.of(topicWorld2);

        when(localKeywordExtractor.extract(topicWorld2.getContents())).thenReturn(Map.entry("world", 2));

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_when2SameKeyword1Other_return2SameGroupedAllInList() {
        List<UserPost> inputList = Arrays.asList(topicFrogs3, topicDog2, topicFrogs4);
        List<UserPost> expected = Arrays.asList(topicDog2, topicFrogs3, topicFrogs4);

        when(localKeywordExtractor.extract(topicDog2.getContents())).thenReturn(Map.entry("dog", 2));
        when(localKeywordExtractor.extract(topicFrogs3.getContents())).thenReturn(Map.entry("frogs", 3));
        when(localKeywordExtractor.extract(topicFrogs4.getContents())).thenReturn(Map.entry("frogs", 2));


        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_when2SameKeyword1OtherDesc_return2SameGroupedAllInListDesc() {
        List<UserPost> inputList = Arrays.asList(topicFrogs3, topicDog2, topicFrogs4);
        List<UserPost> expected = Arrays.asList(topicFrogs4, topicFrogs3, topicDog2);

        when(localKeywordExtractor.extract(topicDog2.getContents())).thenReturn(Map.entry("dog", 2));
        when(localKeywordExtractor.extract(topicFrogs3.getContents())).thenReturn(Map.entry("frogs", 3));
        when(localKeywordExtractor.extract(topicFrogs4.getContents())).thenReturn(Map.entry("frogs", 2));

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_whenItemOnlyHas1Keyword_returnGroupedByKeyword() {
        List<UserPost> inputList = Arrays.asList(topicWorld2, topicFrogs3, topicWorld1);
        List<UserPost> expected = Arrays.asList(topicWorld2, topicWorld1,  topicFrogs3);

        when(localKeywordExtractor.extract(topicWorld2.getContents())).thenReturn(Map.entry("world", 2));
        when(localKeywordExtractor.extract(topicFrogs3.getContents())).thenReturn(Map.entry("frogs", 3));
        when(localKeywordExtractor.extract(topicWorld1.getContents())).thenReturn(Map.entry("world", 1));

        List<UserPost> actual = new TopicPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_when1ItemNoContent_return1Item() {
        List<UserPost> inputList = List.of(topicNoContent);
        List<UserPost> expected = List.of(topicNoContent);

        when(localKeywordExtractor.extract(topicNoContent.getContents())).thenReturn(Map.entry("", 0));

        List<UserPost> actual = new AuthorPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);

    }
}