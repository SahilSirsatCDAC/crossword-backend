package com.example.crossword.service.ai;


import com.example.crossword.dto.AIResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class AIStub {
    private final ObjectMapper mapper = new ObjectMapper();

    public AIResponseDto getHardcodedResponse() throws Exception {
        String json = """
        {
          "words":[
            {"word":"APPLE","hint":"Keeps the doctor away","row":1,"column":1,"direction":"ACROSS"},
            {"word":"PEAR","hint":"Green fruit","row":2,"column":1,"direction":"ACROSS"},
            {"word":"APE","hint":"Primate","row":1,"column":1,"direction":"DOWN"}
          ]
        }
        """;

        return mapper.readValue(json, AIResponseDto.class);
    }
}

