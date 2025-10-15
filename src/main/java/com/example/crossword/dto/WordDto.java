package com.example.crossword.dto;

public record WordDto(
        Long id,
        Integer startRow,
        Integer startColumn,
        String hint,
        String direction
) {}