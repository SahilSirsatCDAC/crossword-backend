package com.example.crossword.domain;

import java.util.*;

public class TokenizedWord {

    private final List<String> tokens;

    public TokenizedWord(List<String> tokens) {
        this.tokens = Collections.unmodifiableList(new ArrayList<>(tokens));
    }

    public List<String> getTokens() {
        return tokens;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenizedWord)) return false;
        TokenizedWord other = (TokenizedWord) o;
        return this.tokens.equals(other.tokens);
    }

    @Override
    public int hashCode() {
        return tokens.hashCode();
    }


    public boolean contains(String seq) {
//        for (int i = 0; i <= tokens.size() - seq.size(); i++) {
//            if (tokens.subList(i, i + seq.size()).equals(seq)) return true;
//        }
        for (String token : tokens){
            if (token.equals(seq)) return true;
        }
        return false;
    }


    public Set<String> tokenSet() {
        return new HashSet<>(tokens);
    }


    @Override
    public String toString() {
        return String.join("", tokens);
    }
}
