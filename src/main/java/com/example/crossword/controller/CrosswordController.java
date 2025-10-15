package com.example.crossword.controller;

import com.example.crossword.dto.CrosswordDto;
import com.example.crossword.service.CrosswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crosswords")
public class CrosswordController {

    private final CrosswordService service;

    public CrosswordController(CrosswordService service) { this.service = service; }

    @GetMapping("/{id}")
    public ResponseEntity<CrosswordDto> get(@PathVariable Long id) {
        CrosswordDto dto = service.getCrossword(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }
}
