package com.scottlogic.sorts;

import com.scottlogic.SortOrder;
import com.scottlogic.UserPost;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class KeywordExtractorTest {
    String emptyString = "";
    String noKeyword = "This content has no keywords";
    String chicken2 = "The chicken keyword in this content is chicken";
    String dogs3other2 = "Dogs are the keyword, dogs are also the greatest dogs";

    @Test
    void extract_null_null(){
        Map.Entry<String, Integer> actual = new KeywordExtractor().extract(null);

        assertNull(actual);
    }

    @Test
    void extract_emptyString_emptyMap(){
        Map.Entry<String, Integer> expected = Map.entry("", 0);

        Map.Entry<String, Integer> actual = new KeywordExtractor().extract(emptyString);

        assertEquals(expected, actual);
    }

    @Test
    void extract_whenNoKeyword_returnFirstWord(){
        Map.Entry<String, Integer> expected = Map.entry("content", 1);

        Map.Entry<String, Integer> actual = new KeywordExtractor().extract(noKeyword);

        assertEquals(expected, actual);
    }

    @Test
    void extract_whenKeyword2Entries_returnKeyword2Entries(){
        Map.Entry<String, Integer> expected = Map.entry("chicken", 2);

        Map.Entry<String, Integer> actual = new KeywordExtractor().extract(chicken2);

        assertEquals(expected, actual);
    }

    @Test
    void extract_whenKeyword3EntriesOther2Entries_returnKeyword3Entries(){
        Map.Entry<String, Integer> expected = Map.entry("dogs", 3);

        Map.Entry<String, Integer> actual = new KeywordExtractor().extract(dogs3other2);

        assertEquals(expected, actual);
    }
}