package com.scottlogic;

import com.scottlogic.filters.AuthorPostFilter;
import com.scottlogic.filters.LikePostFilter;
import com.scottlogic.filters.combine.OrCombineFilters;
import com.scottlogic.sorts.DatePostSorter;
import com.scottlogic.sorts.TopicPostSorter;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserPostServiceTest {
    UserPost Biggs2020_01_01_0230_of2_3Likes = new UserPost("Rob Biggs",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.of("-2")),
            "An example of a post \nwith lines breaks.", 3);

    UserPost Smooth2020_02_01_2200_3Likes = new UserPost("Anne Smooth",
            OffsetDateTime.of(2020, 2, 1, 22, 0, 0, 0, ZoneOffset.UTC),
            "Hello World!", 3);

    UserPost Smooth2020_02_01_2200_0Likes = new UserPost("Anne Smooth",
            OffsetDateTime.of(2020, 2, 1, 22, 0, 0, 0, ZoneOffset.UTC),
            "Hello World!", 0);

    UserPost Small2021_01_01_1230_0Likes = new UserPost("Joe Small",
            OffsetDateTime.of(2021, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC),
            "Example Post", 0);

    UserPost BiggsKeywordPost= new UserPost("Rob Biggs",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.of("-2")),
            "An example of a post \nwith lines breaks. post", 3);

    UserPost SmoothKeywordPost= new UserPost("Rob Smooth",
            OffsetDateTime.of(2020, 1, 1, 2, 30, 0, 0, ZoneOffset.of("-2")),
            "An example of a post \nwith lines breaks. post", 3);

    UserPost SmoothKeywordWorld= new UserPost("Anne Smooth",
            OffsetDateTime.of(2020, 2, 1, 22, 0, 0, 0, ZoneOffset.UTC),
            "Hello World! I love this world", 3);

    UserPost SmoothKeywordPost2 = new UserPost("Joe Smooth",
            OffsetDateTime.of(2021, 1, 1, 12, 30, 0, 0, ZoneOffset.UTC),
            "This Post is an Example Post", 0);


    @Mock
    AuthorPostFilter authorPostFilter = mock(AuthorPostFilter.class);
    @Mock
    LikePostFilter likePostFilter = mock(LikePostFilter.class);
    @Mock
    DatePostSorter datePostSorter = mock(DatePostSorter.class);
    @Mock
    TopicPostSorter topicPostSorter = mock(TopicPostSorter.class);
    @Mock
    OrCombineFilters orCombineFilters = mock(OrCombineFilters.class);

    @Test
    void postSort_whenUnsortedItemsDateSorter_returnItemsSortedByDate() {
        List<UserPost> inputList =
                Arrays.asList(Small2021_01_01_1230_0Likes, Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes);
        List<UserPost> expected =
                Arrays.asList(Biggs2020_01_01_0230_of2_3Likes, Smooth2020_02_01_2200_3Likes, Small2021_01_01_1230_0Likes);

        when(datePostSorter.sort(inputList, SortOrder.ASC)).thenReturn(Arrays.asList(Biggs2020_01_01_0230_of2_3Likes,
                Smooth2020_02_01_2200_3Likes, Small2021_01_01_1230_0Likes));


        List<UserPost> actual = new UserPostService(inputList).postSort(datePostSorter, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void postFilter_whenSomeItemsHaveNoLikes_returnFilteredListWithOnlyLikes() {
        List<UserPost> inputList =
                Arrays.asList(Small2021_01_01_1230_0Likes, Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes);
        List<UserPost> expected = Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes);

        when(likePostFilter.filter(inputList))
                .thenReturn(Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes));

        List<UserPost> actual = new UserPostService(inputList).postFilter(likePostFilter);

        assertEquals(expected, actual);
    }

    @Test
    void filterSorter_whenFilterNameTopic_returnChosenNameSortedByTopic() {
        List<UserPost> inputList = Arrays.asList(SmoothKeywordPost, SmoothKeywordWorld, BiggsKeywordPost, SmoothKeywordPost2);
        List<UserPost> expected = Arrays.asList(SmoothKeywordPost,SmoothKeywordPost2, SmoothKeywordWorld);

        when(authorPostFilter.filter(inputList))
                .thenReturn(Arrays.asList(SmoothKeywordPost, SmoothKeywordWorld, SmoothKeywordPost2));
        when(topicPostSorter.sort(Arrays.asList(SmoothKeywordPost, SmoothKeywordWorld, SmoothKeywordPost2), SortOrder.ASC))
                .thenReturn(Arrays.asList(SmoothKeywordPost,SmoothKeywordPost2, SmoothKeywordWorld));

        List<UserPost> actual = new UserPostService(inputList).filterSorter(authorPostFilter, topicPostSorter, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void andFilter_whenListWithUnwantedItems_returnListWithoutUnwantedItems() {
        List<UserPost> inputList =
                Arrays.asList(Small2021_01_01_1230_0Likes, Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes);
        List<UserPost> expected = List.of(Biggs2020_01_01_0230_of2_3Likes);

        when(likePostFilter.filter(inputList))
                .thenReturn(Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes));
        when(authorPostFilter.filter(Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes)))
                .thenReturn(List.of(Biggs2020_01_01_0230_of2_3Likes));

        List<UserPost> actual = new UserPostService(inputList).andFilter(likePostFilter, authorPostFilter);

        assertEquals(expected, actual);
    }

    @Test
    void orFilter_when() {
        List<UserPost> inputList =
                Arrays.asList(Small2021_01_01_1230_0Likes, Smooth2020_02_01_2200_0Likes, Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes);
        List<UserPost> expected =
                Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes, Smooth2020_02_01_2200_0Likes, Smooth2020_02_01_2200_3Likes);

        when(likePostFilter.filter(inputList))
                .thenReturn(Arrays.asList(Smooth2020_02_01_2200_3Likes, Biggs2020_01_01_0230_of2_3Likes));
        when(authorPostFilter.filter(inputList))
                .thenReturn(Arrays.asList(Smooth2020_02_01_2200_0Likes, Smooth2020_02_01_2200_3Likes));

        List<UserPost> actual = new UserPostService(inputList).orFilter(likePostFilter, authorPostFilter);

        assertEquals(expected, actual);
    }
}