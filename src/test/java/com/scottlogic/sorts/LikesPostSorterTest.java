package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import com.scottlogic.sorts.LikesPostSorter;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class
LikesPostSorterTest {
    UserPost userPost2Likes = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
            "Hello World!", 2);

    UserPost userPost1Likes = new UserPost("Joe Bloggs",
            OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
            "Another example post.", 1);

    UserPost userPost3Likes = new UserPost("Jane Smith",
            OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
            "An example of a post \nwith lines breaks.", 3);

    @Test
    void sort_null_null() {
        List<UserPost> actual = new LikesPostSorter().sort(null, SortOrder.ASC);

        assertNull(actual);
    }

    @Test
    void sort_EmptyList_EmptyList() {
        List<UserPost> inputList = Collections.emptyList();
        List<UserPost> expected = Collections.emptyList();

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_1ItemList_1ItemList() {
        List<UserPost> inputList = List.of(userPost1Likes);
        List<UserPost> expected = List.of(userPost1Likes);

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3Items_sorted3Items() {
        List<UserPost> inputList = Arrays.asList(userPost3Likes, userPost1Likes, userPost2Likes);
        List<UserPost> expected = Arrays.asList(userPost1Likes, userPost2Likes, userPost3Likes);

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3ItemsDESC_sorted3ItemsDESC() {
        List<UserPost> inputList = Arrays.asList(userPost1Likes, userPost2Likes, userPost3Likes);
        List<UserPost> expected = Arrays.asList(userPost3Likes, userPost2Likes, userPost1Likes);

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.DESC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3ItemsSame_sorted3ItemsSame() {
        UserPost userPost3Likes = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 3);

        UserPost userPost1Likes = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 1);

        UserPost userPost1Likes2 = new UserPost("Jane Smith",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 1);
        List<UserPost> inputList = Arrays.asList(userPost3Likes, userPost1Likes, userPost1Likes2);
        List<UserPost> expected = Arrays.asList(userPost1Likes, userPost1Likes2, userPost3Likes);

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }

    @Test
    void sort_unsorted3ItemsNoLikes_sorted3ItemsNoLikes() {
        UserPost userPost0Likes = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 7, 12, 3, 0, ZoneOffset.UTC),
                "Hello World!", 0);

        UserPost userPost0Likes2 = new UserPost("Joe Bloggs",
                OffsetDateTime.of(2020, 1, 3, 8, 53, 34, 0, ZoneOffset.UTC),
                "Another example post.", 0);

        UserPost userPost0Likes3 = new UserPost("Jane Smith",
                OffsetDateTime.of(2020, 3, 12, 13, 22, 12, 0, ZoneOffset.UTC),
                "An example of a post \nwith lines breaks.", 0);
        List<UserPost> inputList = Arrays.asList(userPost0Likes, userPost0Likes2, userPost0Likes3);
        List<UserPost> expected = Arrays.asList(userPost0Likes, userPost0Likes2, userPost0Likes3);

        List<UserPost> actual = new LikesPostSorter().sort(inputList, SortOrder.ASC);

        assertEquals(expected, actual);
    }
}