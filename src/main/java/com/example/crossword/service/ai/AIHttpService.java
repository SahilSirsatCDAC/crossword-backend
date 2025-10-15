 package com.example.crossword.service.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
public class AIHttpService {

    public enum AuthMode { API_KEY_HEADER, API_KEY_QUERY, BEARER, NONE }

    @Value("${ai.api.key:}")
    private String apiKey;

    @Value("${ai.endpoint.url}")
    private String aiEndpoint;

    @Value("${ai.auth.mode:API_KEY_HEADER}")
    private AuthMode authMode;

    private final RestTemplate rest = new RestTemplate();

    public String callGenerate(String theme, String difficulty, String language, int rows, int columns, Map<String,Object> extraOptions) {
        String prompt = String.format("""
            Generate a crossword with theme '%s', difficulty '%s', language '%s', %dx%d grid.
            Respond ONLY in JSON format:
            {\"words\":[{\"word\":\"...\",\"hint\":\"...\",\"row\":1,\"column\":1,\"direction\":\"ACROSS\"}]}
            """, theme, difficulty, language, rows, columns);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Auth handling
        String url = aiEndpoint;
        if (authMode == AuthMode.API_KEY_HEADER) {
            headers.set("x-goog-api-key", apiKey);
        } else if (authMode == AuthMode.BEARER) {
            headers.setBearerAuth(apiKey);
        } else if (authMode == AuthMode.API_KEY_QUERY) {
            url = aiEndpoint + (aiEndpoint.contains("?") ? "&" : "?") + "key=" + apiKey;
        }

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                ),
                "options", extraOptions == null ? Map.of() : extraOptions
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = rest.exchange(URI.create(url), HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return response.getBody().toString();
        } else {
            throw new IllegalStateException("AI call failed: " + response.getStatusCode() + " - " + response.getBody());
        }
    }
}
