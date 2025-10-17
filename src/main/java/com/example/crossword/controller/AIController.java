package com.example.crossword.controller;


import com.example.crossword.dto.AIResponseDto;
import com.example.crossword.model.MarathiDictionaryWord;
import com.example.crossword.service.CrosswordService;
import com.example.crossword.service.MarathiDictionaryWordService;
import com.example.crossword.service.ai.AIStub;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {
    private final AIStub aiStub;
    private final CrosswordService crosswordService;
    private final MarathiDictionaryWordService marathiDictionaryWordService;
    @GetMapping("/generate")
    public ResponseEntity<?> generateTest(
            @RequestParam String theme,
            @RequestParam String difficulty,
            @RequestParam String language,
            @RequestParam int rows,
            @RequestParam int columns) throws Exception {

//        var dto = aiStub.getHardcodedResponse();
        AIResponseDto dto = crosswordService.generateCrosswordHybridMethod();
        return ResponseEntity.ok(dto);
    }
    @GetMapping("/words")
    public ResponseEntity<?> getPossibleCrosswordWords(@RequestParam String word) throws Exception {
//        System.out.println("अद्वितीय".contains("वि"));
        return ResponseEntity.ok(marathiDictionaryWordService.searchWordsByStringSequence(word));
    }
}
