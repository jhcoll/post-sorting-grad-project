package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import com.scottlogic.filters.KeywordPostFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KeywordPostSorterTest {
    UserPost keywords0 = new UserPost("jane smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost keywords1 = new UserPost("joe bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post", 1);

    UserPost keywords2 = new UserPost("joe bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks and post", 3);

    UserPost keywords3 = new UserPost("joe bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "A post example of a post \nwith lines breaks and post", 3);

    UserPost keywords1only = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Post", 2);

    UserPost keywords1_2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost keywords0_2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    @Mock
    KeywordPostFilter localKeywordPostFilter = mock(KeywordPostFilter.class);

    @Test
    void filter_null_null() {
        when(localKeywordPostFilter.filter(null)).thenReturn(null);

        List<UserPost> actual = new KeywordPostSorter(localKeywordPostFilter).sort(null, SortOrder.ASC);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        when(localKeywordPostFilter.filter(inputList)).thenReturn(List.of());

        List<UserPost> actual = new KeywordPostSorter(localKeywordPostFilter).sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemKeyword_1ItemList() {
        List<UserPost> inputList = List.of(keywords1);
        List<UserPost> expected = List.of(keywords1);
        when(localKeywordPostFilter.filter(inputList)).thenReturn(List.of(keywords1));
        when(localKeywordPostFilter.getKeyword()).thenReturn("post");

        List<UserPost> actual = new KeywordPostSorter(localKeywordPostFilter).sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }


    @Test
    void combine_3Items3Match_3ItemsList() {
        List<UserPost> inputList = Arrays.asList(keywords1, keywords2, keywords3);
        List<UserPost> expected = Arrays.asList(keywords3, keywords2, keywords1);

        when(localKeywordPostFilter.filter(inputList)).thenReturn(Arrays.asList(keywords1, keywords2, keywords3));
        when(localKeywordPostFilter.getKeyword()).thenReturn("post");

        List<UserPost> actual = new KeywordPostSorter(localKeywordPostFilter).sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }


    @Test
    void sort_3ItemDifferentCount_Sorted3Items() {

        List<UserPost> inputList = Arrays.asList(keywords1, keywords2, keywords0);
        List<UserPost> expected = Arrays.asList(keywords2, keywords1);

        when(localKeywordPostFilter.filter(inputList)).thenReturn(Arrays.asList(keywords1, keywords2));
        when(localKeywordPostFilter.getKeyword()).thenReturn("post");

        List<UserPost> actual = new KeywordPostSorter(localKeywordPostFilter).sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }
}