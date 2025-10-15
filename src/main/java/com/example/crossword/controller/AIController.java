package com.example.crossword.controller;


import com.example.crossword.service.ai.AIStub;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    private final AIStub aiStub;

    @GetMapping("/generate")
    public ResponseEntity<?> generateTest(
            @RequestParam String theme,
            @RequestParam String difficulty,
            @RequestParam String language,
            @RequestParam int rows,
            @RequestParam int columns) throws Exception {

        var dto = aiStub.getHardcodedResponse();
        return ResponseEntity.ok(dto);
    }
}
