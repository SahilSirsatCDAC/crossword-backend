 package com.example.crossword.service;

import com.example.crossword.dto.AIResponseDto;
import com.example.crossword.dto.AIWordDto;
import com.example.crossword.model.Crossword;
import com.example.crossword.model.CrosswordCell;
import com.example.crossword.model.CrosswordWord;
import com.example.crossword.repository.CrosswordCellRepository;
import com.example.crossword.repository.CrosswordRepository;
import com.example.crossword.repository.CrosswordWordRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CrosswordGeneratorService {

    private final CrosswordRepository crosswordRepo;
    private final CrosswordWordRepository wordRepo;
    private final CrosswordCellRepository cellRepo;
    private final Validator validator;

    @Transactional
    public Crossword createFromAIResponse(AIResponseDto resp, String name,
                                          int rows, int columns, String language, Crossword.Difficulty difficulty) {

        Set<ConstraintViolation<AIResponseDto>> violations = validator.validate(resp);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (resp.words().size() > 200) {
            throw new IllegalArgumentException("Too many words");
        }

        Character[][] grid = new Character[rows][columns];

        // Place words into the grid for validation
        for (AIWordDto wordDto : resp.words()) {
            String word = wordDto.word().trim();
            if (word.isEmpty()) throw new IllegalArgumentException("Empty word");
            if (word.length() > Math.max(rows, columns)) throw new IllegalArgumentException("Word too long: " + word);
            if (wordDto.hint().length() > 512) throw new IllegalArgumentException("Hint too long");

            int startRow = wordDto.row() - 1;
            int startColumn = wordDto.column() - 1;
            boolean isAcross = "ACROSS".equalsIgnoreCase(wordDto.direction());

            int endRow = startRow + (isAcross ? 0 : word.length() - 1);
            int endColumn = startColumn + (isAcross ? word.length() - 1 : 0);
            if (startRow < 0 || startColumn < 0 || endRow >= rows || endColumn >= columns) {
                throw new IllegalArgumentException("Word does not fit: " + word);
            }

            for (int i = 0; i < word.length(); i++) {
                int r = startRow + (isAcross ? 0 : i);
                int c = startColumn + (isAcross ? i : 0);
                Character existing = grid[r][c];
                char ch = word.charAt(i);
                if (existing != null && existing != ch) {
                    throw new IllegalArgumentException("Letter conflict at " + (r + 1) + "," + (c + 1) + " for word " + word);
                }
                grid[r][c] = ch;
            }
        }

        Crossword crossword = Crossword.builder()
                .name(name)
                .rowCount(rows)
                .columnCount(columns)
                .language(language)
                .difficulty(difficulty)
                .isAi(true)
                .build();
        crossword = crosswordRepo.save(crossword);

        List<CrosswordCell> cellsToSave = new ArrayList<>();

        for (AIWordDto wordDto : resp.words()) {
            String word = wordDto.word().trim();
            int startRow = wordDto.row() - 1;
            int startColumn = wordDto.column() - 1;
            boolean isAcross = "ACROSS".equalsIgnoreCase(wordDto.direction());

            CrosswordWord crosswordWord = CrosswordWord.builder()
                    .crossword(crossword)
                    .hint(sanitizeHint(wordDto.hint()))
                    .startRow(wordDto.row())
                    .startColumn(wordDto.column())
                    .direction(isAcross ? CrosswordWord.Direction.ACROSS : CrosswordWord.Direction.DOWN)
                    .build();
            crosswordWord = wordRepo.save(crosswordWord);

            for (int i = 0; i < word.length(); i++) {
                int r = startRow + (isAcross ? 0 : i);
                int c = startColumn + (isAcross ? i : 0);
                CrosswordCell cell = CrosswordCell.builder()
                        .crossword(crossword)
                        .rowNum(r + 1)
                        .columnNum(c + 1)
                        .letter(String.valueOf(word.charAt(i)))
                        .isBlocked(false)
                        .build();
                cellsToSave.add(cell);
            }
        }

        // Remove duplicate cells at overlapping positions
        Map<String, CrosswordCell> uniqueCells = new LinkedHashMap<>();
        for (CrosswordCell cell : cellsToSave) {
            String key = cell.getRowNum() + "_" + cell.getColumnNum();
            uniqueCells.putIfAbsent(key, cell);
        }

        cellRepo.saveAll(uniqueCells.values());

        return crossword;
    }

    private String sanitizeHint(String hint) {
        if (hint == null) return null;
        return hint.replaceAll("<", "&lt;").replaceAll(">", "&gt;").trim();
    }
}
