 package com.example.crossword.controller;

import com.example.crossword.service.CrosswordGeneratorService;
import com.example.crossword.service.ai.AIStub;
import com.example.crossword.model.Crossword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crosswords")
@RequiredArgsConstructor
public class CrosswordGenerateController {
    private final AIStub stub;
    private final CrosswordGeneratorService generatorService;

    @PostMapping("/generate-from-ai-stub")
    public ResponseEntity<?> generateFromStub(@RequestParam String name,
                                              @RequestParam int rows,
                                              @RequestParam int columns,
                                              @RequestParam String language,
                                              @RequestParam(defaultValue = "EASY") String difficulty) throws Exception {
        var dto = stub.getHardcodedResponse();
        var cw = generatorService.createFromAIResponse(dto, name, rows, columns, language,
                Crossword.Difficulty.valueOf(difficulty.toUpperCase()));
        return ResponseEntity.ok(cw);
    }
}
