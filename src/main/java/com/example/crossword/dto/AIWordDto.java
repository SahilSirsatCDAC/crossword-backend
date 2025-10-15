 package com.example.crossword.dto;

import jakarta.validation.constraints.*;

public record AIWordDto(
        @NotBlank String word,
        @NotBlank String hint,
        @Min(1) int row,
        @Min(1) int column,
        @NotBlank String direction
) {}

