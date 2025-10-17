package com.example.crossword.utils;

import com.example.crossword.domain.TokenizedWord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    @Test
    void getTokenizedWord() throws Exception{
        String word = "स्वतःश्रीकक्षविज्ञान";
        TokenizedWord tokenized = StringUtils.getTokenizedWord(word);

        assertNotNull(tokenized);
        System.out.println(tokenized);
        System.out.println(tokenized);
    }
}