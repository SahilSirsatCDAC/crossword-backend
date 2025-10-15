package com.example.crossword.dto;

public record CellDto(
        Long id,
        Integer rowNum,
        Integer columnNum,
        String letter,
        Boolean isBlocked
) {}
