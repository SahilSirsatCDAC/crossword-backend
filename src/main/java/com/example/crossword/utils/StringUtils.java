package com.example.crossword.utils;

import com.example.crossword.domain.TokenizedWord;
import org.junit.Test;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;

import static com.example.crossword.domain.MarathiModifierChar.isModifierChar;

public class StringUtils {

    public static String normalize(String s) {
        if (s == null) return null;
        return Normalizer.normalize(s, Form.NFC);
    }

    public static TokenizedWord getTokenizedWord(String word) throws Exception {
        //What are the ruUUUles?
        //1. Break down into atomic unicode chars
        //2. put them into an array
        //3. create a new list<sting>
        //4. if the char does nto have a half-char before or after it, only then put into list immediately
        //5. If we encounter kana, matra, any half char, anything that is not a full char, append it to empty string
        //6. continue until such a char is encountered where there is no preceding half-char,
        //6. once such condition is met, put the string into list, empty string and start appending next letter/sequence
        char[] wordArray = word.toCharArray();
        List<String> tokensList = new ArrayList<>();

        int wordArraylLength = wordArray.length;
        String token = "";
        for (int i = 0; i < wordArraylLength; i++) {
            char currentChar = wordArray[i];
            if (i == wordArraylLength - 1) {
                token += currentChar;
                tokensList.add(token);
                token = "";
                continue;
            }
            char nextChar = wordArray[i + 1];
//            if (currentChar  == 'ि') {
//                token += currentChar ;
//                continue;
//            }
            if (isModifierChar(currentChar)) {
                if (!isModifierChar(nextChar)) {
                    if (currentChar == '्') {
                        token += currentChar;
                        continue;
                    }
                    token += currentChar;
                    tokensList.add(token);
                    token = "";

                } else {
//                    if (nextChar  == 'ि'){
//                        token += currentChar ;
//                        tokensList.add(token);
//                        token = "";
//                    } else
                    if (nextChar == '्') {
                        token += currentChar;
                    }

                }

            } else {
                if (!isModifierChar(nextChar)) {
                    token += currentChar;
                    tokensList.add(token);
                    token = "";
                } else {
                    token += currentChar;
                }

            }
        }
        return new TokenizedWord(tokensList);
    }

}
