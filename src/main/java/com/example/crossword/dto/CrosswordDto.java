package com.example.crossword.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record CrosswordDto(
        Long id,
        String name,
        Integer rowCount,
        Integer columnCount,
        String difficulty,
        Boolean isAi,
        String language,
        OffsetDateTime createdOn,
        List<CellDto> cells,
        List<WordDto> words
) {}