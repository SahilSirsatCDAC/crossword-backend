package com.example.crossword.service;

import com.example.crossword.dto.AIResponseDto;
import com.example.crossword.dto.CellDto;
import com.example.crossword.dto.CrosswordDto;
import com.example.crossword.dto.WordDto;
import com.example.crossword.model.Crossword;
import com.example.crossword.model.CrosswordCell;
import com.example.crossword.model.CrosswordWord;
import com.example.crossword.repository.CrosswordCellRepository;
import com.example.crossword.repository.CrosswordRepository;
import com.example.crossword.repository.CrosswordWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrosswordService {

    private final CrosswordRepository crosswordRepo;
    private final CrosswordCellRepository cellRepo;
    private final CrosswordWordRepository wordRepo;

    @Transactional(readOnly = true)
    public CrosswordDto getCrossword(Long id) {
        Crossword cw = crosswordRepo.findById(id).orElse(null);
        if (cw == null) return null;

        List<CellDto> cells = cellRepo.findByCrosswordIdOrderByRowNumAscColumnNumAsc(id)
                .stream()
                .map(this::mapCell)
                .collect(Collectors.toList());

        List<WordDto> words = wordRepo.findByCrosswordId(id)
                .stream()
                .sorted(Comparator.comparing(CrosswordWord::getId))
                .map(this::mapWord)
                .collect(Collectors.toList());

        return new CrosswordDto(
                cw.getId(),
                cw.getName(),
                cw.getRowCount(),
                cw.getColumnCount(),
                cw.getDifficulty() != null ? cw.getDifficulty().name() : null,
                cw.isAi(),
                cw.getLanguage(),
                cw.getCreatedOn(),
                cells,
                words
        );
    }
    public AIResponseDto generateCrosswordHybridMethod() throws Exception {
        String startWord = "विज्ञान";


        return new AIResponseDto(null);
    }

    private CellDto mapCell(CrosswordCell c) {
        return new CellDto(c.getId(), c.getRowNum(), c.getColumnNum(), c.getLetter(), c.getIsBlocked());
    }

    private WordDto mapWord(CrosswordWord w) {
        return new WordDto(w.getId(), w.getStartRow(), w.getStartColumn(), w.getHint(), w.getDirection().name());
    }
}
