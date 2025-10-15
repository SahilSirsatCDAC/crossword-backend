 package com.example.crossword.service.ai;

import com.example.crossword.dto.AIResponseDto;
import com.example.crossword.dto.AIWordDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AIResponseParser {

    @SuppressWarnings("unchecked")
    public AIResponseDto parseResponse(Map<String, Object> responseBody) {
        List<AIWordDto> wordsList = new ArrayList<>();

        if (responseBody == null || !responseBody.containsKey("candidates")) {
            throw new IllegalArgumentException("Invalid AI response: missing candidates");
        }

        List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");

        for (Map<String, Object> candidate : candidates) {
            Map<String, Object> content = (Map<String, Object>) candidate.get("content");
            if (content == null || !content.containsKey("parts")) continue;

            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");

            for (Map<String, Object> part : parts) {
                String text = (String) part.get("text");
                if (text == null || text.isBlank()) continue;

                AIResponseDto dto = parseJsonText(text);
                if (dto != null && dto.words() != null) {
                    wordsList.addAll(dto.words());
                }
            }
        }

        return new AIResponseDto(wordsList);
    }


    private AIResponseDto parseJsonText(String text) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(text, AIResponseDto.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse AI JSON text: " + text, e);
        }
    }
}
