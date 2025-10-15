package com.example.crossword.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record AIResponseDto(
        @NotEmpty List<@Valid AIWordDto> words
) {}
