package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NameDatePostSorterTest {

    UserPost Smooth2020_01_01_0230 = new UserPost("Jane Smooth",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.UTC),
            "Hello World!", 1);

    UserPost Biggs2020_01_01_0230_of2 = new UserPost("Rob Biggs",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.of("-2")),
            "An example of a post \nwith lines breaks.", 3);

    UserPost Smooth2020_01_01_0630 = new UserPost("Clarence Smooth",
            OffsetDateTime.of(2020, 1, 1, 6, 30, 0, 0, ZoneOffset.UTC),
            "Another example post.", 2);

    UserPost Small2020_01_02_0504 = new UserPost("Boris Small",
            OffsetDateTime.of(2020, 1, 2, 5, 4, 0, 0, ZoneOffset.UTC),
            "Hello World!", 4);

    UserPost Smooth2020_02_01_2200 = new UserPost("Anne Smooth",
            OffsetDateTime.of(2020, 2, 1, 22, 0, 0, 0, ZoneOffset.UTC),
            "Hello World!", 3);

    UserPost Small2021_01_01_1230 = new UserPost("Joe Small",
            OffsetDateTime.of(2021, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC),
            "Example Post", 0);

    @Mock
    AuthorPostSorter localAuthorPostSorter = mock(AuthorPostSorter.class);
    @Mock
    DatePostSorter localDatePostSorter = mock(DatePostSorter.class);

    @Test
    void sort_null_null() {
        when(localAuthorPostSorter.sort(null, SortOrder.ASC)).thenReturn(null);
        when(localDatePostSorter.sort(null, SortOrder.ASC)).thenReturn(null);

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(null, null);

        assertNull(actual);
    }

    @Test
    void sort_EmptyList_EmptyList() {
        List<UserPost> inputList = List.of();
        List<UserPost> expected = List.of();

        when(localAuthorPostSorter.sort(inputList, SortOrder.ASC)).thenReturn(inputList);
        when(localDatePostSorter.sort(inputList, SortOrder.ASC)).thenReturn(inputList);

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_1ItemList_1ItemList() {
        List<UserPost> inputList = Arrays.asList(Smooth2020_01_01_0230);
        List<UserPost> expected = List.of(Smooth2020_01_01_0230);

        when(localAuthorPostSorter.sort(inputList, SortOrder.ASC)).thenReturn(inputList);
        when(localDatePostSorter.sort(inputList, SortOrder.ASC)).thenReturn(inputList);

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_2ItemsWrongOrder_2ItemsRightOrder() {
        List<UserPost> inputList = List.of(Smooth2020_01_01_0230, Biggs2020_01_01_0230_of2);
        List<UserPost> expected = List.of(Biggs2020_01_01_0230_of2, Smooth2020_01_01_0230);

        when(localDatePostSorter.sort(inputList, SortOrder.ASC))
                .thenReturn(List.of(Smooth2020_01_01_0230, Biggs2020_01_01_0230_of2));
        when(localAuthorPostSorter.sort(List.of(Smooth2020_01_01_0230, Biggs2020_01_01_0230_of2), SortOrder.ASC))
                .thenReturn(List.of(Biggs2020_01_01_0230_of2, Smooth2020_01_01_0230));

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_3ItemsUnSorted_3ItemsSorted() {
        List<UserPost> inputList = List.of(Smooth2020_02_01_2200, Smooth2020_01_01_0230, Small2021_01_01_1230);
        List<UserPost> expected = List.of(Small2021_01_01_1230, Smooth2020_01_01_0630, Smooth2020_02_01_2200);

        when(localDatePostSorter.sort(inputList, SortOrder.ASC))
                .thenReturn(List.of(Smooth2020_01_01_0630, Smooth2020_02_01_2200, Small2021_01_01_1230));
        when(localAuthorPostSorter.sort(List.of(Smooth2020_01_01_0630, Smooth2020_02_01_2200, Small2021_01_01_1230), SortOrder.ASC))
                .thenReturn(List.of(Small2021_01_01_1230, Smooth2020_01_01_0630, Smooth2020_02_01_2200));

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_3ItemsUnSortedDesc_3ItemsSortedDesc() {
        List<UserPost> inputList = List.of(Smooth2020_02_01_2200, Smooth2020_01_01_0230, Small2021_01_01_1230);
        List<UserPost> expected = List.of(Smooth2020_02_01_2200, Smooth2020_01_01_0630, Small2021_01_01_1230);

        when(localDatePostSorter.sort(inputList, SortOrder.DESC))
                .thenReturn(List.of(Small2021_01_01_1230, Smooth2020_02_01_2200, Smooth2020_01_01_0630));
        when(localAuthorPostSorter.sort(List.of(Small2021_01_01_1230, Smooth2020_02_01_2200, Smooth2020_01_01_0630), SortOrder.DESC))
                .thenReturn(List.of( Smooth2020_02_01_2200, Smooth2020_01_01_0630, Small2021_01_01_1230));

        List<UserPost> actual = new NameDatePostSorter(localDatePostSorter, localAuthorPostSorter)
                .sort(inputList, SortOrder.DESC);

        assertEquals(expected, actual);
    }
}