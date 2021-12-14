package com.scottlogic.filters.combine;

import com.scottlogic.UserPost;
import com.scottlogic.filters.AuthorPostFilter;
import com.scottlogic.filters.DatePostFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OrCombineFiltersTest {
    UserPost userPostJane = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost userPostJoe1 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost userPostJoe2 = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    @Mock
    AuthorPostFilter localAuthorPostFilter = mock(AuthorPostFilter.class);
    @Mock
    DatePostFilter localDatePostFilter = mock(DatePostFilter.class);

    @Test
    void filter_null_null() {
        when(localAuthorPostFilter.filter(null)).thenReturn(null);
        when(localDatePostFilter.filter(null)).thenReturn(null);

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(null);

        assertNull(actual);
    }

    @Test
    void filter_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        when(localAuthorPostFilter.filter(inputList)).thenReturn(List.of());

        when(localDatePostFilter.filter(inputList)).thenReturn(List.of());

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1ItemListMatchBoth_1ItemList() {
        List<UserPost> inputList = List.of(userPostJane);
        List<UserPost> expected = List.of(userPostJane);

        when(localAuthorPostFilter.filter(inputList)).thenReturn(List.of(userPostJane));
        when(localDatePostFilter.filter(inputList)).thenReturn(List.of(userPostJane));

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void filter_1Item1Match_EmptyList() {
        List<UserPost> inputList = List.of(userPostJane);
        List<UserPost> expected = List.of(userPostJane);

        when(localAuthorPostFilter.filter(inputList)).thenReturn(List.of(userPostJane));
        when(localDatePostFilter.filter(inputList)).thenReturn(List.of());

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void combine_3Items2Match_2ItemList() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = List.of(userPostJane, userPostJoe1);

        when(localAuthorPostFilter.filter(inputList)).thenReturn(Arrays.asList(userPostJane, userPostJoe1));
        when(localDatePostFilter.filter(inputList)).thenReturn(List.of(userPostJane));

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void combine_3Items3Match_3ItemList() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = List.of(userPostJane,userPostJoe1, userPostJoe2);

        when(localAuthorPostFilter.filter(inputList))
                .thenReturn(Arrays.asList(userPostJane, userPostJoe1, userPostJoe2));
        when(localDatePostFilter.filter(inputList))
                .thenReturn(List.of(userPostJane, userPostJoe2));

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }

    @Test
    void combine_3Items3MatchSame_3ItemsList() {
        List<UserPost> inputList = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);
        List<UserPost> expected = Arrays.asList(userPostJane, userPostJoe1, userPostJoe2);

        when(localAuthorPostFilter.filter(inputList))
                .thenReturn(Arrays.asList(userPostJane, userPostJoe1, userPostJoe2));
        when(localDatePostFilter.filter(inputList))
                .thenReturn(Arrays.asList(userPostJane, userPostJoe1, userPostJoe2));

        List<UserPost> actual = new OrCombineFilters(localAuthorPostFilter, localDatePostFilter).filter(inputList);

        assertEquals(expected, actual);
    }
}