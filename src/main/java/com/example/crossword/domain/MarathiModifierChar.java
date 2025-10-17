package com.example.crossword.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public enum MarathiModifierChar {
    // Vowel signs / matras
    AA('\u093E'),      // ा
    I('\u093F'),       // ि
    II('\u0940'),      // ी
    U('\u0941'),       // ु
    UU('\u0942'),      // ू
    VOCALIC_R('\u0943'),   // ृ
    VOCALIC_RR('\u0944'),  // ॄ
    VOCALIC_L('\u0962'),   // ॢ
    VOCALIC_LL('\u0963'),  // ॣ
    E('\u0947'),       // े
    AI('\u0948'),      // ै
    O('\u094B'),       // ो
    AU('\u094C'),      // ौ

    // Special signs
    VIRAMA('\u094D'),      // ्
    ANUSVARA('\u0902'),    // ं
    VISARGA('\u0903'),     // ः
    CANDRABINDU('\u0901'); // ँ

    private final char codePoint;

    MarathiModifierChar(char codePoint) {
        this.codePoint = codePoint;
    }

    public char getCodePoint() {
        return codePoint;
    }

    // -----------------------------
    // Lookup set for fast O(1) checks
    // -----------------------------
    private static final Set<Character> lookup = new HashSet<>(
            Arrays.stream(values())
                    .map(MarathiModifierChar::getCodePoint)
                    .collect(java.util.stream.Collectors.toSet())
    );

    /**
     * Check if a given character is a Marathi modifier character.
     *
     * @param c the character to check
     * @return true if it is a modifier (matra or special sign), false otherwise
     */
    public static boolean isModifierChar(char c) {
        return lookup.contains(c);
    }
}
